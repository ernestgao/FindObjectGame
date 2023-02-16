package model.background;

//Specifically bench in background
public class Bench extends Background {

    protected static final int WIDTH = 42;
    protected static final int HEIGHT = 20;

    //EFFECT: construct a bench background
    public Bench(int x, int y) {
        super(x,y);
        title = "Bench";
    }

    //EFFECT: return bench width
    @Override
    public int width() {
        return WIDTH;
    }

    //EFFECT: return bench height
    @Override
    public int height() {
        return HEIGHT;
    }
}
