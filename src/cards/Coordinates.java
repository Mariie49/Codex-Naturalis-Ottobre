package cards;

public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Metodo toString() per rappresentare le coordinate come stringa
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}