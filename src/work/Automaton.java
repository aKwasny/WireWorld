package work;

/**
 * Created by andrzej on 04.06.16.
 */

/*Klasa automatu komórkowego*/
public class Automaton {

    private Community current;
    private Community next;
    private long n;

    /*Konstruktor z domyśnymi ustawieniami*/
    public Automaton() {
        current = new Community(60, 60);
        next = null;
        n = 1000;
    }

    /*Constructor that creates an automaton with given community*/
    public Automaton(Community community) {
        current = community;
        next = null;
        n = 1000;
    }

    /*Ustawianie populacji w automacie*/
    public void setCommunity(Community community) {
        current = community;
    }

    /*Zwracanie uchwytu do ustawionego społeczeństwa*/
    public Community getCommunity() {
        return current;
    }

    /*Ustawianie liczby generacji do przeprowadzenia*/
    public void setGenerationsNumber(long n) {
        this.n = n;
    }

    public long getGenerationsNumber() {
        return n;
    }

    /*Sets the automaton in a next generation*/
    public void nextGeneration() {
        if (n > 0) {
            next = current.nextCommunity();
            Community temp = current;
            current = next;
            next = temp;
            n--;
        }
    }

    public void start () {
        while (n > 0) {
            nextGeneration();
        }
    }
}