package work.other.cells;

import java.awt.*;

/**
 * Created by andrzej on 04.06.16.
 */
public class Tail implements State{

    @Override
    public State nextState (int heads) {
        return new Conductor();
    }

    @Override
    public Color getColor () {
        return Color.RED;
    }
}