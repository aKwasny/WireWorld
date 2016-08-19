package work.other.cells;

import java.awt.*;

/**
 * Created by andrzej on 04.06.16.
 */
public class Isolator implements State {
    @Override
    public State nextState (int heads) {
        return new Isolator();
    }
    @Override
    public Color getColor () {
        return Color.BLACK;
    }
}
