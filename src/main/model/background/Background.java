package model.background;

import model.object.Objects;

import java.util.List;

//Generate Background
public abstract class Background {


    int bgx;
    int bgy;
    String title;

    //EFFECT construct background with objects hidden in
    public Background(int x, int y) {
        bgx = x;
        bgy = y;
    }

    //EFFECT: return true if the object is near its x
    public boolean inX(Objects o) {
        return o.getObjectX() <= bgx + width() / 2 && o.getObjectX() >= bgx - width() / 2;
    }

    //EFFECT: return true if the object is near its y
    public boolean inY(Objects o) {
        return o.getObjectY() <= bgy + height() / 2 && o.getObjectY() >= bgy - height() / 2;
    }

    //EFFECT: return the object if it is near the background
    public Objects objectAround(List<Objects> hiddenIn) {
        for (Objects h : hiddenIn) {
            if (inX(h) && inY(h)) {
                return h;
            }
        }
        return null;
    }

    //EFFECT: return true if a specific object is found(not in hidden list)
    public boolean specificInHidden(String specific, List<Objects> hidden) {
        for (Objects o : hidden) {
            if (specific.equals(o.getTitle())) {
                return true;
            }
        }
        return false;
    }

    public abstract int width();

    public abstract int height();

    public int getBgx() {
        return bgx;
    }

    public int getBgy() {
        return bgy;
    }

    public String getTitle() {
        return title;
    }
}
