package utool;

public class Point {
    public static int STATE_WHITE = 1000;
    public static int STATE_BLACK = 1001;
    private int x;
    private int y;
    private int state;

    public Point() {
        state = STATE_BLACK;
    }

    public Point(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return x + ":" + y + ":" + state;
    }
}
