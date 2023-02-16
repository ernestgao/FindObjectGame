package persistanceTest;

import model.object.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkObjects(String title, int x, int y, Objects objects) {
        assertEquals(title, objects.getTitle());
        assertEquals(x, objects.getObjectX());
        assertEquals(y, objects.getObjectY());
    }
}
