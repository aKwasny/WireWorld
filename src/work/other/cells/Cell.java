package work.other.cells;

import work.Community;
import work.other.preparedforms.Element;

import java.awt.*;

/**
 * Created by andrzej on 04.06.16.
 */
public class Cell implements Element {
    private State state;

    /*Tworzenie nowej komórki będącej izolatorem*/
    public Cell () {
        state = new Isolator();
    }

    /*Konstruktor tworzący komórkę z podanym stanem*/
    public Cell (State state) {
        this.state = state;
    }

    public String getState() {
        return state.getClass().getSimpleName();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void nextState (int heads) {
        state = state.nextState(heads);
    }

    public Color getColor () {
        return state.getColor();
    }

    @Override
    public void setElementOnBoard (int i, int j, Community community) {
        community.setCellState (i, j, state);
    }
}