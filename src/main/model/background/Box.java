package model.background;

import model.object.Objects;

import java.util.List;

//Specifically box in background
public class Box extends Background {

    protected static final int WIDTH = 20;
    protected static final int HEIGHT = 20;

    //EFFECT: construct a box background
    public Box(int x, int y) {
        super(x, y);
        title = "Box";
    }

    //REQUIRES: only return when hammer is found
    //EFFECT: return the object near the background
    public Objects objectAround(List<Objects> hidden) {
        if (!specificInHidden("Hammer", hidden)) {
            return super.objectAround(hidden);
        } else {
            return null;
        }
    }

    //EFFECT: return box width
    @Override
    public int width() {
        return WIDTH;
    }

    //EFFECT: return box height
    @Override
    public int height() {
        return HEIGHT;
    }
}
