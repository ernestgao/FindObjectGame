package modelTest;

import model.Avatar;
import model.difficulty.GameBoard;
import model.object.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;

public class AvatarTest {

    GameBoard testGameBoard1;
    GameBoard testGameBoard2;
    Avatar testAvatar1;
    Avatar testAvatar2;

    @BeforeEach
    public void setup(){
        testAvatar1 = new Avatar("Easy");
        testAvatar2 = new Avatar("Hard");
        testGameBoard1 = testAvatar1.getGameBoard();
        testGameBoard2 = testAvatar2.getGameBoard();
    }

    @Test
    public void constructorTest() {
        assertEquals(40,testAvatar1.getAvaX());
        assertEquals("Easy", testAvatar1.getDifficulty());
        assertEquals("Hard", testAvatar2.getDifficulty());
        assertEquals(3,testAvatar1.getFindList().size());
        assertEquals(testGameBoard1.getHidden(),testAvatar1.getFindList());
        assertEquals(5,testAvatar2.getFindList().size());
        assertTrue(testAvatar1.getFoundList().isEmpty());
        assertTrue(testAvatar2.getFoundList().isEmpty());
    }

    @Test
    public void buildGameBoardTest() {
        testAvatar1.buildGameBoard("Easy");
        assertEquals("Easy", testAvatar1.getGameBoard().getDifficulty());
        testAvatar1.buildGameBoard("Hard");
        assertEquals("Hard", testAvatar1.getGameBoard().getDifficulty());
        testAvatar2.buildGameBoard("Easy");
        assertEquals("Easy", testAvatar2.getGameBoard().getDifficulty());
        testAvatar2.buildGameBoard("Hard");
        assertEquals("Hard", testAvatar2.getGameBoard().getDifficulty());
    }

    @Test
    public void moveLeftTest() {
        testAvatar1.moveLeft();
        assertEquals(25,testAvatar1.getAvaX());
        testAvatar1.moveLeft();
        assertEquals(25,testAvatar1.getAvaX());
        testAvatar1.setAvaX(360);
        testAvatar1.moveLeft();
        assertEquals(345,testAvatar1.getAvaX());
        testAvatar1.moveLeft();
        assertEquals(330,testAvatar1.getAvaX());
    }

    @Test
    public void moveRightTest() {
        testAvatar1.moveRight();
        assertEquals(55,testAvatar1.getAvaX());
        testAvatar1.moveRight();
        assertEquals(70,testAvatar1.getAvaX());
        testAvatar1.setAvaX(360);
        testAvatar1.moveRight();
        assertEquals(375,testAvatar1.getAvaX());
        testAvatar1.moveRight();
        assertEquals(375,testAvatar1.getAvaX());
    }

    @Test
    public void checkSurroundingTest() {
        testAvatar1.setAvaX(35);
        assertEquals("Bush",testAvatar1.checkSurrounding().getTitle());
        assertEquals(25,testAvatar1.checkSurrounding().getBgx());
        assertEquals(380,testAvatar1.checkSurrounding().getBgy());
        testAvatar1.setAvaX(170);
        assertTrue(isNull(testAvatar1.checkSurrounding()));
        testAvatar1.setAvaX(185);
        assertEquals("Bench",testAvatar1.checkSurrounding().getTitle());
        assertEquals(170,testAvatar1.checkSurrounding().getBgx());
        assertEquals(375,testAvatar1.checkSurrounding().getBgy());
        testAvatar1.setAvaX(275);
        assertTrue(isNull(testAvatar1.checkSurrounding()));
    }

    @Test
    public void removeFoundTest() {
        Objects o0 =testGameBoard1.getHidden().get(0);
        Objects o1 =testGameBoard1.getHidden().get(1);
        Objects o2 =testGameBoard1.getHidden().get(2);
        testAvatar1.removeFound(null);
        assertEquals(3, testAvatar1.getFindList().size());
        assertTrue(testAvatar1.getFoundList().isEmpty());
        testAvatar1.removeFound(o0);
        assertEquals(2, testAvatar1.getFindList().size());
        assertEquals(1, testAvatar1.getFoundList().size());
        testAvatar1.removeFound(o1);
        assertEquals(1, testAvatar1.getFindList().size());
        assertEquals(2, testAvatar1.getFoundList().size());
        testAvatar1.removeFound(o2);
        assertEquals(0, testAvatar1.getFindList().size());
        assertEquals(3, testAvatar1.getFoundList().size());
    }

    @Test
    public void interactTest() {
        Objects o0 =testGameBoard1.getHidden().get(0);
        Objects o1 =testGameBoard1.getHidden().get(1);
        Objects o2 =testGameBoard1.getHidden().get(2);
        testAvatar1.interact();
        assertEquals(3, testAvatar1.getFindList().size());
        assertTrue(testAvatar1.getFoundList().isEmpty());
        assertFalse(testAvatar1.getFoundList().contains(o0));
        testAvatar1.setAvaX(170);
        testAvatar1.interact();
        assertEquals(3, testAvatar1.getFindList().size());
        assertEquals(0, testAvatar1.getFoundList().size());
        assertFalse(testAvatar1.getFoundList().contains(o2));
        testAvatar1.setAvaX(85);
        testAvatar1.interact();
        assertEquals(0, testAvatar1.getFoundList().size());
        assertEquals(3,testAvatar1.getFindList().size());
        testAvatar1.setAvaX(185);
        testAvatar1.interact();
        assertEquals(1, testAvatar1.getFoundList().size());
        assertTrue(testAvatar1.getFoundList().contains(o1));
        assertEquals(2,testAvatar1.getFindList().size());
        assertFalse(testAvatar1.getFoundList().contains(o2));
        testAvatar1.setAvaX(30);
        testAvatar1.interact();
        assertEquals(2, testAvatar1.getFoundList().size());
        assertTrue(testAvatar1.getFoundList().contains(o1));
        assertTrue(testAvatar1.getFoundList().contains(o0));
        assertEquals(1,testAvatar1.getFindList().size());
        assertFalse(testAvatar1.getFoundList().contains(o2));
        testAvatar1.setAvaX(365);
        testAvatar1.interact();
        assertEquals(3, testAvatar1.getFoundList().size());
        assertTrue(testAvatar1.getFoundList().contains(o2));
    }
}
