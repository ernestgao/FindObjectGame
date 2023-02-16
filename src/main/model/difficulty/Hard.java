package model.difficulty;

import model.Event;
import model.EventLog;

import java.util.ArrayList;
import java.util.Arrays;

//build hard game board
public class Hard extends Easy {

    //EFFECT: construct a GameBoard in hard
    public Hard() {
        this.difficulty = "Hard";
        hidden = new ArrayList<>(Arrays.asList(hammer, shovel, phone, ball, saw));
        EventLog.getInstance().logEvent(new Event("Game started with \"Hard\""));
    }

}
