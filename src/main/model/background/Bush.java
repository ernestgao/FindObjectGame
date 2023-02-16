package model.background;


import model.object.Objects;

import java.util.List;

//Specifically bush in background
public class Bush extends Background {

    protected static final int WIDTH = 30;
    protected static final int HEIGHT = 14;

    //EFFECT: construct a bush background
    public Bush(int x, int y) {
        super(x,y);
        title = "Bush";
    }

    //REQUIRES:Shovel is objectAround()
    //EFFECT: return the object near the background
    public Objects objectAround(List<Objects> hidden) {
        if (!specificInHidden("Shovel", hidden)) {
            return super.objectAround(hidden);
        } else {
            return null;
        }
    }

    //EFFECT: return bush width
    @Override
    public int width() {
        return WIDTH;
    }

    //EFFECT: return bush height
    @Override
    public int height() {
        return HEIGHT;
    }
}
