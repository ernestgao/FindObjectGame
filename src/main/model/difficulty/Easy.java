package model.difficulty;

import model.Event;
import model.EventLog;

import java.util.ArrayList;
import java.util.Arrays;

//Build easy game board
public class Easy extends GameBoard {

    //EFFECT: construct a GameBoard in easy
    public Easy() {
        this.difficulty = "Easy";
        hidden = new ArrayList<>(Arrays.asList(hammer, shovel, phone));
        EventLog.getInstance().logEvent(new Event("Game started with \"Easy\""));
    }
}
