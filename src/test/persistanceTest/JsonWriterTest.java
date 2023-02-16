package persistanceTest;

import model.Avatar;
import model.object.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {

    Avatar avatar;
    JsonWriter writer;

    @BeforeEach
    public void setup() {
        avatar = new Avatar("Easy");
    }

    @Test
    void testWriterInvalidFile() {
        try {
            writer = new JsonWriter("./data\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNothingFound() {
        try {
            writer = new JsonWriter("./data/testWriterNothingFound.json");
            writer.open();
            writer.write(avatar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNothingFound.json");
            Avatar testAvatar = reader.read();
            assertEquals("Easy", testAvatar.getDifficulty());
            assertEquals(10, testAvatar.getAvaX());
            assertEquals(0, testAvatar.getFoundList().size());
            assertEquals(3, testAvatar.getGameBoard().getHidden().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterFoundSomething() {
        try {
            avatar.setAvaX(20);
            Objects o = avatar.getFindList().get(0);
            avatar.removeFound(o);
            writer = new JsonWriter("./data/testWriterFoundSomething.json");
            writer.open();
            writer.write(avatar);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFoundSomething.json");
            Avatar testAvatar = reader.read();
            assertEquals("Easy", testAvatar.getDifficulty());
            assertEquals(20, testAvatar.getAvaX());
            List<Objects> objects = testAvatar.getFoundList();
            List<Objects> hidden = testAvatar.getGameBoard().getHidden();
            assertEquals(1, objects.size());
            checkObjects("Hammer", 5, 185, objects.get(0));
            assertEquals(2, hidden.size());
            checkObjects("Shovel", 90,180, hidden.get(0));
            checkObjects("Phone", 170,190, hidden.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}