public class Coordinates {
    public int x;
    public int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(Coordinates old) {
        this.x = old.x;
        this.y = old.y;
    }
}
