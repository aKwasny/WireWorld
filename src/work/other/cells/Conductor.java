package work.other.cells;

import java.awt.*;

/**
 * Created by andrzej on 04.06.16.
 */
public class Conductor implements State {

    @Override
    public State nextState (int heads) {
        if (heads == 1 || heads == 2) {
            return new Head();
        } else {
            return new Conductor();
        }
    }

    @Override
    public Color getColor() {
        return Color.YELLOW;
    }
}
