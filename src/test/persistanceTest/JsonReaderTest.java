package persistanceTest;

import model.Avatar;
import model.object.Objects;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    JsonReader reader;
    Avatar avatar;

    @Test
    void testReaderNotExistFile() {
        reader = new JsonReader("./data/badFile.json");
        try {
            avatar = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNothingFound() {
        reader = new JsonReader("./data/testReaderNothingFound.json");
        try {
            avatar = reader.read();
            assertEquals("Easy", avatar.getDifficulty());
            assertEquals(10, avatar.getAvaX());
            assertEquals(0, avatar.getFoundList().size());
            assertEquals(3, avatar.getGameBoard().getHidden().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFoundOneThing() {
        reader = new JsonReader("./data/testReaderFoundOneThing.json");
        try {
            avatar = reader.read();
            assertEquals("Easy", avatar.getDifficulty());
            assertEquals(100, avatar.getAvaX());
            List<Objects> objects = avatar.getFoundList();
            assertEquals(1, objects.size());
            checkObjects("Shovel", 90,180, objects.get(0));
            List<Objects> hidden = avatar.getGameBoard().getHidden();
            assertEquals(2, hidden.size());
            checkObjects("Hammer", 5,185, hidden.get(0));
            checkObjects("Phone", 170,190, hidden.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFoundTwoThings() {
        reader = new JsonReader("./data/testReaderFoundTwoThings.json");
        try {
            avatar = reader.read();
            assertEquals("Easy", avatar.getDifficulty());
            assertEquals(15, avatar.getAvaX());
            List<Objects> objects = avatar.getFoundList();
            assertEquals(2, objects.size());
            checkObjects("Shovel", 90,180, objects.get(0));
            checkObjects("Hammer", 5,185, objects.get(1));
            List<Objects> hidden = avatar.getGameBoard().getHidden();
            assertEquals(1, hidden.size());
            checkObjects("Phone", 170,190, hidden.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderFoundEverything() {
        reader = new JsonReader("./data/testReaderFoundEverything.json");
        try {
            avatar = reader.read();
            assertEquals("Easy", avatar.getDifficulty());
            assertEquals(15, avatar.getAvaX());
            List<Objects> objects = avatar.getFoundList();
            assertEquals(3, objects.size());
            checkObjects("Shovel", 90,180, objects.get(0));
            checkObjects("Hammer", 5,185, objects.get(1));
            checkObjects("Phone", 170,190, objects.get(2));
            List<Objects> hidden = avatar.getGameBoard().getHidden();
            assertEquals(0, hidden.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}