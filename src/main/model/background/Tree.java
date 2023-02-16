package model.background;

import model.object.Objects;

import java.util.List;

//Specifically tree in background
public class Tree extends Background {

    protected static final int WIDTH = 120;
    protected static final int HEIGHT = 120;

    //EFFECT: construct a bush background
    public Tree(int x, int y) {
        super(x,y);
        title = "Tree";
    }

    //REQUIRES: Saw is found
    //EFFECT: return the object near the background
    public Objects objectAround(List<Objects> hidden) {
        if (!specificInHidden("Saw", hidden)) {
            return super.objectAround(hidden);
        } else {
            return null;
        }
    }

    //EFFECT: return tree width
    @Override
    public int width() {
        return WIDTH;
    }

    //EFFECT: return tree height
    @Override
    public int height() {
        return HEIGHT;
    }
}
