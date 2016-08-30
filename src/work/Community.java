package work;

import work.other.preparedforms.Element;
import work.other.preparedforms.ElementFactory;
import work.other.cells.Cell;
import work.other.cells.Isolator;
import work.other.cells.State;

/**
 * Created by andrzej on 04.06.16.
 */

/*Class representates the community of cells*/
public class Community {
    private Cell [][] board;
    private final int height;
    private final int width;

    public Community(int h, int w) { /*h - height, no. of rows in community, w - width, no. of colums in community*/
        height = h;
        width = w;
        board = new Cell[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                board[i][j] = new Cell(new Isolator());
            }
        }
    }

    /*Method that copys the community*/
    public Community copyCommunity() {
        Community toCopy = new Community(this.height, this.width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                toCopy.setElementOnBoard(i, j, ElementFactory.buildElement(this.getCell(i, j).getState()));
            }
        }
        return toCopy;
    }

    /*Returns cell that has given coordinates*/
    public Cell getCell (int i, int j) {
        if (((i >= 0 && i < height) && j >= 0) && j < width) {
            return board[i][j];
        } else {
            return new Cell();
        }
    }

    /*Method that sets the state of given cell; i - row, j - col*/
    public void setCellState (int i, int j, State state) {
        if ( i < height && i >= 0 && j < width && j >= 0) {
            board[i][j].setState(state);
        }
    }

    /*Method that returns the reference to next community.*/
    public Community nextCommunity() {
        int headsNumber;
        Community next = copyCommunity();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                headsNumber = getHeadsNumber(i, j);
                next.board[i][j].nextState(headsNumber);
            }
        }
        return next;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth () {
        return width;
    }

    /*Moore neighbourhood*/
    private int getHeadsNumber(int i, int j) {
        int result = 0;
        for (int a = i - 1; a <= i + 1; a++) {
            for (int b = j - 1; b <= j + 1; b++) {
                if ("Head".equals(getCell(a, b).getState())) {
                    result++;
                }
            }
        }
        return result;
    }

    /*Von Neumann neighbourhood*/
    /*
    private int getHeadsNumber(int i, int j) {
        int result = 0;
        if ("Head".equals(getCell(i + 1, j).getState())) result++;
        if ("Head".equals(getCell(i -1, j).getState())) result++;
        if ("Head".equals(getCell(i, j + 1).getState())) result++;
        if ("Head".equals(getCell(i, j - 1).getState())) result++;
        return result;
    }
    */

    /*Clearing method - sets all the cells as "Isolator"*/
    public void clearBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                board[i][j].setState(new Isolator());
            }
        }
    }

    /*Method that places given element into community*/
    public void setElementOnBoard  (int i, int j, Element element) {
        element.setElementOnBoard(i,j, this);
    }
}