package gui;

import work.Automaton;
import work.InputOutput;
import work.Community;
import work.other.cells.*;
import work.other.preparedforms.AndNot;
import work.other.preparedforms.Diode;
import work.other.preparedforms.NAND;
import work.other.preparedforms.OR;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by andrzej on 04.06.16.
 */
public class GUI extends JFrame {

    private Automaton game = new Automaton();
    private boolean isStopped;

    private BOARD board;

    private JMenuBar menuBar;
    private JMenu fileMenu, infoMenu;
    private JMenuItem openFileMenuItem, saveFileMenuItem, exitMenuItem, aboutMenuItem;
    private final JFileChooser fileChooser = new JFileChooser();

    private JButton startButton, nextGenerationButton, clearButton, stopButton, defaultSettingsButton;
    private JLabel counterLabel, sliderLabel, elementsLabel, optionsLabel;
    private JTextField counterTextField;
    private ButtonGroup elementBox;
    private JRadioButton diodeRadioButton, conductorRadioButton, headRadioButton, tailRadioButton, insulatorRadioButton, nandRadioButton, andNotRadioButton, orRadioButton;
    private JSlider delaySlider;
    private JCheckBox checkGrid;

    private String aboutMessage = "Automaton is a programme that simulates cellular automaton with the use of Moore neighbourhood.";

    private Color defaultBackgroundColor = Color.CYAN;

    public GUI() {
        isStopped = true;
        game = new Automaton();

        /*try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (IllegalAccessException | ClassNotFoundException | UnsupportedLookAndFeelException | InstantiationException e) {
            e.printStackTrace();
        }*/
        SwingUtilities.updateComponentTreeUI(this);
        initFrame();
        initUpMenu();
        initSteeringMenu();

        board = new BOARD((game.getCommunity()));
        board.setBounds(0,0, board.getPanelHeight(), board.getPanelWidth());
        board.addMouseListener(board);
        board.addMouseMotionListener(board);
        board.setElement(new Cell(new Conductor()));
        add(board);
        board.setVisible(true);
        setVisible(true);
    }

    private void initFrame () {
        setSize(800, 650);
        setResizable(false);
        setTitle("Automaton");
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //middle of the screen
    }

    private void initUpMenu () {
        menuBar = new JMenuBar();

        fileMenu = new JMenu("File");
        infoMenu = new JMenu("Information");
        openFileMenuItem = new JMenuItem("Open file...");
        openFileMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        openFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStopped= true;
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File opened = fileChooser.getSelectedFile();
                    game.setCommunity(InputOutput.load(opened));
                    refreshBoard();
                }
            }
        });
        saveFileMenuItem = new JMenuItem("Save file as...");
        saveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        saveFileMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isStopped = true;
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File save = fileChooser.getSelectedFile();
                    InputOutput.save(game.getCommunity(), save);
                }
            }
        });
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setAccelerator(KeyStroke.getKeyStroke("ctrl E"));
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, aboutMessage, "About programme", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        menuBar.add(infoMenu);
        fileMenu.add(openFileMenuItem);
        fileMenu.add(saveFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        infoMenu.add(aboutMenuItem);
    }

    private void initSteeringMenu() {
        startButton = new JButton("Start");
        startButton.setBounds(620, 0, 80, 30);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startClick();
            }
        });
        startButton.setToolTipText("Push to run the automaton.");
        add(startButton);

        stopButton = new JButton("Stop");
        stopButton.setBounds(700, 0, 80, 30);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopClick();
            }
        });
        stopButton.setToolTipText("Push to stop the automaton.");
        add(stopButton);

        nextGenerationButton = new JButton("Next generation");
        nextGenerationButton.setBounds(620, 30, 160, 30);
        nextGenerationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextGenerationClick();
            }
        });
        nextGenerationButton.setToolTipText("Push to do just one generation.");
        add(nextGenerationButton);

        counterLabel = new JLabel("Generations no.");
        counterLabel.setBounds(655, 65, 90, 15);
        add(counterLabel);

        counterTextField = new JTextField();
        counterTextField.setBounds(620, 80, 160, 30);
        counterTextField.setToolTipText("Write here the number of generations to be made");
        counterTextField.setText(String.valueOf(game.getGenerationsNumber()));
        counterTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setGenerationsNumber(Long.parseLong(counterTextField.getText()));
            }
        });
        add(counterTextField);

        sliderLabel = new JLabel("Generations frequency");
        sliderLabel.setBounds(645, 125, 100, 15);
        add(sliderLabel);

        delaySlider = new JSlider(100, 1000, 100);
        delaySlider.setBounds(620, 140, 160, 60);
        delaySlider.setMajorTickSpacing(200);
        delaySlider.setMinorTickSpacing(100);
        delaySlider.setPaintTicks(true);
        delaySlider.setPaintLabels(true);
        delaySlider.setToolTipText("Move the slider to adjust the generation frequency.");
        add(delaySlider);

        clearButton = new JButton("Clear");
        clearButton.setBounds(620, 210, 160, 30);
        clearButton.setToolTipText("Push to clearBoard the board.");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearClick();
            }
        });
        add(clearButton);

        elementsLabel = new JLabel("Automaton characteristic elemenets");
        elementsLabel.setBounds(640, 250, 120, 30);
        add(elementsLabel);


        conductorRadioButton = new JRadioButton("Conductor", false);
        conductorRadioButton.setBounds(600, 280, 100, 20);
        conductorRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new Cell(new Conductor()));
            }
        });
        add(conductorRadioButton);

        insulatorRadioButton = new JRadioButton("Isolator", false);
        insulatorRadioButton.setBounds(700, 280, 100, 20);
        insulatorRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new Cell(new Isolator()));
            }
        });
        add(insulatorRadioButton);

        headRadioButton = new JRadioButton("Head", false);
        headRadioButton.setBounds(600, 300, 100, 20);
        headRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new Cell(new Head()));
            }
        });
        add(headRadioButton);

        tailRadioButton = new JRadioButton("Tail", false);
        tailRadioButton.setBounds(700, 300, 100, 20);
        tailRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new Cell(new Tail()));
            }
        });
        add(tailRadioButton);

        diodeRadioButton = new JRadioButton("Diode", false);
        diodeRadioButton.setBounds(600, 320, 100, 20);
        diodeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new Diode());
            }
        });
        add(diodeRadioButton);

        nandRadioButton = new JRadioButton("NAND", false);
        nandRadioButton.setBounds(700, 320, 100, 20);
        nandRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new NAND());
            }
        });
        add(nandRadioButton);

        orRadioButton = new JRadioButton("OR", false);
        orRadioButton.setBounds(700, 340, 100, 20);
        orRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new OR());
            }
        });
        add(orRadioButton);

        andNotRadioButton = new JRadioButton("AndNot", false);
        andNotRadioButton.setBounds(600, 340, 100, 20);
        andNotRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.setElement(new AndNot());
            }
        });
        add(andNotRadioButton);

        elementBox = new ButtonGroup();
        elementBox.add(diodeRadioButton);
        elementBox.add(conductorRadioButton);
        elementBox.add(headRadioButton);
        elementBox.add(tailRadioButton);
        elementBox.add(insulatorRadioButton);
        elementBox.add(nandRadioButton);
        elementBox.add(orRadioButton);
        elementBox.add(andNotRadioButton);

        optionsLabel = new JLabel("Options");
        optionsLabel.setBounds(670, 405, 60, 30);
        add(optionsLabel);

        checkGrid = new JCheckBox("Net visibility", true);
        checkGrid.setBounds(640, 435, 120, 30);
        checkGrid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.isGridDrawn(checkGrid.isSelected());
                board.repaint();
            }
        });
        add(checkGrid);

        defaultSettingsButton = new JButton("Restore to default");
        defaultSettingsButton.setBounds(620, 465, 160, 30);
        defaultSettingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restoreDefaultClick();
            }
        });
        defaultSettingsButton.setToolTipText("Push to restore to default.");
        add(defaultSettingsButton);

    }

    private void startClick () {
        if (isStopped) {
            isStopped = false;
            new Thread() {
                @Override
                public void run () {
                    game.setGenerationsNumber(Long.parseLong(counterTextField.getText()));
                    while (game.getGenerationsNumber() > 0 && !isStopped) {
                        game.nextGeneration();
                        counterTextField.setText(String.valueOf(game.getGenerationsNumber()));
                        refreshBoard();
                        try {
                            Thread.sleep(delaySlider.getValue());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }.start();
        }
    }

    private void stopClick () {
        isStopped = true;
    }

    private void nextGenerationClick () {
        game.nextGeneration();
        counterTextField.setText(String.valueOf(game.getGenerationsNumber()));
        refreshBoard();
    }

    private void clearClick () {
        game.getCommunity().clearBoard();
        refreshBoard();
    }

    private void restoreDefaultClick() {
        isStopped = true;
        game.setGenerationsNumber(1000);
        game.setCommunity(new Community(60, 60));
        counterTextField.setText(String.valueOf(game.getGenerationsNumber()));
        refreshBoard();
    }

    private void refreshBoard() {
        board.setCommunity(game.getCommunity());
        board.repaint();
    }
}
