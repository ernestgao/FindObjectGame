package model.object;

import org.json.JSONObject;
import persistance.Writable;

//make an object needed to find
public abstract class Objects implements Writable {

    int objectX;
    int objectY;
    String title;

    //EFFECT; construct an object with x and y location
    public Objects(int x, int y) {
        objectX = x;
        objectY = y;
    }

    public String getTitle() {
        return title;
    }

    public int getObjectX() {
        return objectX;
    }

    public int getObjectY() {
        return objectY;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", title);
        json.put("objectX", objectX);
        json.put("objectY", objectY);
        return json;
    }
}
