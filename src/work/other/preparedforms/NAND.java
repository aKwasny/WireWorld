package work.other.preparedforms;

import work.Community;
import work.other.cells.Conductor;

/**
 * Created by andrzej on 04.06.16.
 */
public class NAND implements Element {

    @Override
    public void setElementOnBoard (int i, int j, Community community) {
        community.setCellState(i, j, new Conductor());
        community.setCellState(i + 1, j - 1, new Conductor());
        community.setCellState(i + 2, j - 1, new Conductor());
        community.setCellState(i + 3, j, new Conductor());
        community.setCellState(i + 3, j + 1, new Conductor());
        community.setCellState(i + 2, j + 1, new Conductor());
        community.setCellState(i + 3, j + 2, new Conductor());
        community.setCellState(i + 2, j + 3, new Conductor());
        community.setCellState(i + 1, j + 3, new Conductor());
        community.setCellState(i, j + 2, new Conductor());
        community.setCellState(i + 4, j + 3, new Conductor());
        community.setCellState(i + 5, j + 3, new Conductor());
        community.setCellState(i + 4, j + 1, new Conductor());
    }
}