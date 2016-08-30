package work.other.cells;

import java.awt.*;

/**
 * Created by andrzej on 04.06.16.
 */
public class Head implements State {

    @Override
    public State nextState (int heads) {
        return new Tail();
    }

    @Override
    public Color getColor() {
        return Color.BLUE;
    }
}