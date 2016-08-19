package work;

import work.other.preparedforms.ElementFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by andrzej on 04.06.16.
 */
public class InputOutput {
    public static Community load(File file) {
        Community community = null;
        Scanner in = null;
        String line = null;
        String[] splited;
        try {
            in = new Scanner(file);
            line = in.nextLine();
            splited = line.split(" ");

            try {
                community = new Community(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]));
            } catch (NumberFormatException n) {
                System.err.println(n + " Given data in line " + line + " are not numbers!");
                community = new Community(60, 60);
            }catch (ArrayIndexOutOfBoundsException a) {
                System.err.println(a + " Too little fata in line " + line + ".");
                community = new Community(60, 60);
            }

            while (in.hasNext()) {
                try {
                    line = in.nextLine();
                    splited = line.split("\\s+");
                    community.setElementOnBoard(Integer.parseInt(splited[1]) - 1, Integer.parseInt(splited[2]) - 1, ElementFactory.buildElement(splited[0]));
                } catch (NumberFormatException ex) {
                    System.err.println(ex + " Line " + line.toString() + " ignored. Given arguments are not digits.");
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.err.println(ex + " Line " + line.toString() + " ignored. Too few arguments."); //TODO
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return community;
    }

    public static void save(Community community, File file) {
        PrintWriter buffer = null;
        try {
            buffer = new PrintWriter(file);
            buffer.println(community.getHeight() + " " + community.getWidth() + "\n");
            for (int i = 0; i < community.getHeight(); i++) {
                for (int j = 0; j < community.getWidth(); j++) {
                    if (!community.getCell(i, j).getState().equals("Isolator")) {
                        buffer.println(community.getCell(i, j).getState() + " " + (i + 1) + " " + (j + 1) + "\n");
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File " + file + " does not exist!");
        } finally {
            if (buffer != null) {
                buffer.close();
            }
        }
    }
}