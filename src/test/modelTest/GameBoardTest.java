package modelTest;

import model.background.*;
import model.Avatar;
import model.difficulty.Easy;
import model.difficulty.GameBoard;
import model.difficulty.Hard;
import model.object.Hammer;
import model.object.Objects;
import model.object.Phone;
import model.object.Shovel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameBoardTest {

    List<Objects> hidden;
    Bush bush1;
    Bush bush2;
    Bush bush3;
    Tree tree1;
    Tree tree2;
    Bench bench;
    Box box;
    Hammer hammer;
    Shovel shovel;
    Phone phone;
    List<Background> backgrounds;
    Avatar avatar;
    GameBoard testGame1;
    GameBoard testGame2;

    @BeforeEach
    public void setup() {
        testGame1 = new Easy();
        testGame2 = new Hard();
    }

    @Test
    public void constructorTest() {
        assertEquals(3, testGame1.getHidden().size());
        assertEquals("Hammer", testGame1.getHidden().get(0).getTitle());
        assertEquals("Phone", testGame1.getHidden().get(2).getTitle());
        assertEquals("Bush", testGame1.getBackgrounds().get(0).getTitle());
        assertEquals(5, testGame2.getHidden().size());
        assertEquals("Hammer", testGame2.getHidden().get(0).getTitle());
        assertEquals("Saw", testGame2.getHidden().get(4).getTitle());
        assertEquals("Bush", testGame2.getBackgrounds().get(0).getTitle());
    }
}
