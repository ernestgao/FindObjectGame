package model;

import model.background.*;
import model.difficulty.Easy;
import model.difficulty.GameBoard;
import model.difficulty.Hard;
import model.object.*;
import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

//Create avatar and control it
public class Avatar implements Writable {

    public static final int AVA_WIDTH = 40;
    public static final int AVA_HEIGHT = 60;
    public static final Color COLOR = new Color(0,0,0);
    private static final int DISTANCE_PER_MOVE = 15;
    public static final int AVA_Y = GameBoard.HEIGHT - AVA_HEIGHT / 3;
    public static final int AVA_ORIGINAL_X = GameBoard.LEFT_BOUND;
    private List<Objects> foundList;
    private List<Objects> findList;
    private int avaX;
    private String difficulty;
    private GameBoard gameBoard;

    //EFFECT: construct an avatar at lower left position
    public Avatar(String difficulty) {
        this.difficulty = difficulty;
        this.avaX = AVA_ORIGINAL_X;
        buildGameBoard(difficulty);
        findList = gameBoard.getHidden();
        foundList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECT: build game board
    public void buildGameBoard(String difficulty) {
        if (difficulty.equalsIgnoreCase("Easy")) {
            this.gameBoard = new Easy();
        } else if (difficulty.equalsIgnoreCase("Hard")) {
            this.gameBoard =  new Hard();
        }
    }

    //MODIFIES: this
    //EFFECT: subtract x by DISTANCE_PER_MOVE
    public void moveLeft() {
        if (avaX >= GameBoard.LEFT_BOUND) {
            avaX -= DISTANCE_PER_MOVE;
            EventLog.getInstance().logEvent(new Event("Avatar moved left"));
        }
    }

    //MODIFIES: this
    //EFFECT: add x by DISTANCE_PER_MOVE
    public void moveRight() {
        if (avaX <= GameBoard.RIGHT_BOUND) {
            avaX += DISTANCE_PER_MOVE;
            EventLog.getInstance().logEvent(new Event("Avatar moved right"));
        }
    }

    //EFFECT: return the surrounding background if any
    public Background checkSurrounding() {
        for (Background b : gameBoard.getBackgrounds()) {
            if (b.getBgx() + b.width() / 2 + AVA_WIDTH / 4 >= avaX
                    && b.getBgx() + b.width() / 2 - AVA_WIDTH / 4 <= avaX) {
                return b;
            }
        }
        return null;
    }

    //REQUIRES: Object either in findList (not empty) or is null
    //MODIFIES: this
    //EFFECT: remove the found object from findList and add it to foundList
    public void removeFound(Objects o) {
        if (!isNull(o)) {
            this.findList.remove(o);
            this.foundList.add(o);
            EventLog.getInstance().logEvent(new Event(o.getTitle() + " found."));
        }
    }

    //EFFECT: interact with background, and end if the last one is found
    public void interact() {
        EventLog.getInstance().logEvent(new Event("Avatar interacted"));
        Background b = checkSurrounding();
        if (!isNull(b)) {
            Objects o = b.objectAround(findList);
            if (o != null) {
                removeFound(o);
            }
        }
    }

    //MODIFIES:Avatar
    //EFFECT: move the avatar if player enter left or right or space
    public void keyPressed(int keyCode) {
        if (keyCode == KeyEvent.VK_KP_LEFT || keyCode == KeyEvent.VK_LEFT) {
            moveLeft();
        } else if (keyCode == KeyEvent.VK_KP_RIGHT || keyCode == KeyEvent.VK_RIGHT) {
            moveRight();
        } else if (keyCode == KeyEvent.VK_I) {
            interact();
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("difficulty", difficulty);
        json.put("found",foundListToJson());
        json.put("gameBoard", gameBoard.toJson());
        json.put("x",avaX);
        return json;
    }

    private JSONArray foundListToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Objects o : foundList) {
            jsonArray.put(o.toJson());
        }
        return jsonArray;
    }

    public int getAvaX() {
        return avaX;
    }

    public String getDifficulty() {
        return difficulty;
    }

    //For tests only
    public void setAvaX(int avaX) {
        this.avaX = avaX;
    }

    public List<Objects> getFoundList() {
        return foundList;
    }

    public void addFoundList(Objects o) {
        this.foundList.add(o);
    }

    public List<Objects> getFindList() {
        return findList;
    }

    public void setFindList(List<Objects> findList) {
        this.findList = findList;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
