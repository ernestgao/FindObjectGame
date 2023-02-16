package modelTest;

import model.object.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectsTest {

    Objects testBall;
    Objects testPhone;
    Objects testHammer;
    Objects testShovel;
    Objects testSaw;

    @Test
    public void constructorTest() {
        testBall = new Ball(10,10);
        testPhone = new Phone(180,70);
        testHammer = new Hammer(180,70);
        testSaw = new Saw(180,70);
        testShovel = new Shovel(180,70);
        assertEquals(10,testBall.getObjectX());
        assertEquals(10,testBall.getObjectY());
        assertEquals("Ball", testBall.getTitle());
        assertEquals(180,testPhone.getObjectX());
        assertEquals(70,testPhone.getObjectY());
        assertEquals("Phone", testPhone.getTitle());
        assertEquals("Saw", testSaw.getTitle());
        assertEquals("Shovel", testShovel.getTitle());
        assertEquals("Hammer", testHammer.getTitle());
    }

}
