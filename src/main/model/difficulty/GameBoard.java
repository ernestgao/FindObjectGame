package model.difficulty;

import model.background.*;
import model.Avatar;
import model.object.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Build GameBoard
public abstract class GameBoard implements Writable {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int LEFT_BOUND = Avatar.AVA_WIDTH;
    public static final int RIGHT_BOUND = WIDTH - Avatar.AVA_WIDTH;
    List<Objects> hidden;
    protected String difficulty;
    protected static Bush bush1 = new Bush(25,380);
    protected static Bush bush2 = new Bush(140,380);
    protected static Bush bush3 = new Bush(230,380);
    protected static Tree tree1 = new Tree(60,280);
    protected static Tree tree2 = new Tree(260,280);
    protected static Bench bench = new Bench(170, 375);
    protected static Box box = new Box(365,380);
    protected static List<Background> backgrounds =
            new ArrayList<>(Arrays.asList(bush1,bush2,bush3,tree1,tree2,bench,box));
    Ball ball = new Ball(tree1.getBgx(), tree1.getBgy());
    Saw saw = new Saw(bush3.getBgx(), bush3.getBgy());
    Hammer hammer = new Hammer(bush1.getBgx(), bush1.getBgy());
    Shovel shovel = new Shovel(bench.getBgx(), bench.getBgy());
    Phone phone = new Phone(box.getBgx(), box.getBgy());

    //EFFECT: construct game with avatar and allFound is false
    public GameBoard() {
    }

    public List<Objects> getHidden() {
        return hidden;
    }

    public void setHidden(List<Objects> hidden) {
        this.hidden = hidden;
    }

    public List<Background> getBackgrounds() {
        return backgrounds;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isOver() {
        return hidden.isEmpty();
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hidden",hiddenToJson());
        return jsonObject;
    }

    private JSONArray hiddenToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Objects o : this.hidden) {
            jsonArray.put(o.toJson());
        }

        return jsonArray;
    }


}

