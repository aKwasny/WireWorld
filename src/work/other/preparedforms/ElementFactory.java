package work.other.preparedforms;

import work.other.cells.*;

/**
 * Created by andrzej on 04.06.16.
 */
public class ElementFactory {
    public static Element buildElement (String element) {
        switch (element) {
            case "Isolator":
                return new Cell(new Isolator());
            case "Head":
                return new Cell(new Head());
            case "Tail":
                return new Cell(new Tail());
            case "Conductor":
                return new Cell(new Conductor());
            case "Diode":
                return new Diode();
            case "NAND":
                return new NAND();
            case "AndNot":
                return new AndNot();
            case "OR":
                return new OR();
            default:
                return new Cell(new Isolator());
        }
    }
}
