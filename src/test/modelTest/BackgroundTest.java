package modelTest;

import model.background.*;
import model.object.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.junit.jupiter.api.Assertions.*;

public class BackgroundTest {
    Background bench;
    Background box;
    Background bush;
    Background tree;
    Objects testPhone;
    Objects testHammer;
    Objects testShovel;
    Objects testSaw;
    List<Objects> objectsHiddenList;

    @BeforeEach
    public void setup() {
        bench = new Bench(20,10);
        box = new Box(20,100);
        bush = new Bush(60,120);
        tree = new Tree(150,30);
        testPhone = new Phone(20,100);
        testHammer = new Hammer(62,118);
        testShovel = new Shovel(100,10);
        testSaw = new Saw(70,60);
        objectsHiddenList = new ArrayList<>();
    }

    @Test
    public void constructorTest(){
        assertEquals(20,bench.getBgx());
        assertEquals(10,bench.getBgy());
        assertEquals("Bench",bench.getTitle());
        assertEquals(60,bush.getBgx());
        assertEquals(120,bush.getBgy());
        assertEquals("Bench",bench.getTitle());
    }

    @Test
    public void inXTest(){
        assertTrue(box.inX(testPhone));
        assertFalse(box.inX(testHammer));
        assertTrue(bush.inX(testHammer));
        assertFalse(tree.inX(testPhone));
    }

    @Test
    public void inYTest(){
        assertTrue(box.inY(testPhone));
        assertFalse(bench.inY(testHammer));
        assertTrue(bench.inY(testShovel));
        assertTrue(tree.inY(testShovel));
    }

    @Test
    public void objectAroundTest() {
        assertTrue(isNull(box.objectAround(objectsHiddenList)));
        objectsHiddenList.add(testPhone);
        assertEquals(testPhone, box.objectAround(objectsHiddenList));
        objectsHiddenList.add(testHammer);
        assertEquals(testHammer, bush.objectAround(objectsHiddenList));
        objectsHiddenList.add(testShovel);
        assertTrue(isNull(bush.objectAround(objectsHiddenList)));
        assertTrue(isNull(box.objectAround(objectsHiddenList)));
        assertTrue(isNull(bench.objectAround(objectsHiddenList)));
    }

    @Test
    public void specificInHiddenTest() {
        assertFalse(bush.specificInHidden("Shovel", objectsHiddenList));
        objectsHiddenList.add(testShovel);
        assertFalse(box.specificInHidden("Hammer", objectsHiddenList));
        objectsHiddenList.add(testHammer);
        assertTrue(box.specificInHidden("Hammer", objectsHiddenList));
        assertFalse(tree.specificInHidden("Saw", objectsHiddenList));
        objectsHiddenList.add(testSaw);
        assertTrue(tree.specificInHidden("Saw", objectsHiddenList));
    }
}
