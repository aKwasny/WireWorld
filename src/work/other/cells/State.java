package work.other.cells;

import java.awt.*;

/**
 * Created by andrzej on 04.06.16.
 */
public interface State {
    public State nextState (int heads);
    public Color getColor ();
}
