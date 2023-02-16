package persistance;

import model.Avatar;
import model.difficulty.GameBoard;
import model.object.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

//Read json file
public class JsonReader {
    private String source;

    // Method was taken from JsonReader in:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads avatar from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Avatar read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseAvatar(jsonObject);
    }

    // Method was taken from JsonReader in:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses avatar from JSON object and returns it
    private Avatar parseAvatar(JSONObject jsonObject) {
        String difficulty = jsonObject.getString("difficulty");
        int avaX = jsonObject.getInt("x");
        Avatar avatar = new Avatar(difficulty);
        avatar.setAvaX(avaX);
        setGameBoard(avatar.getGameBoard(), jsonObject);
        avatar.setFindList(avatar.getGameBoard().getHidden());
        addFoundList(avatar, jsonObject);
        return avatar;
    }

    // MODIFIES: avatar
    // EFFECTS: parses game board from JSON object and set it in avatar
    private void setGameBoard(GameBoard gameBoard, JSONObject jsonObject) {
        JSONObject jsonObject1 = jsonObject.getJSONObject("gameBoard");
        JSONArray jsonArray = jsonObject1.getJSONArray("hidden");
        gameBoard.setHidden(new ArrayList<>());
        for (Object json : jsonArray) {
            JSONObject nextObjects = (JSONObject) json;
            gameBoard.getHidden().add(parseObjects(nextObjects));
        }
    }

    // MODIFIES: avatar
    // EFFECTS: parses foundList from JSON object and adds it to avatar
    private void addFoundList(Avatar avatar, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("found");
        for (Object json : jsonArray) {
            JSONObject nextObjects = (JSONObject) json;
            addObjects(avatar, nextObjects);
        }
    }

    //EFFECT: parses objects in game board hidden list
    private Objects parseObjects(JSONObject jsonObject) {
        Objects objects;
        String title = jsonObject.getString("title");
        int objectX = jsonObject.getInt("objectX");
        int objectY = jsonObject.getInt("objectY");
        if (title.equals("Ball")) {
            objects = new Ball(objectX,objectY);
        } else if (title.equals("Hammer")) {
            objects = new Hammer(objectX,objectY);
        } else if (title.equals("Phone")) {
            objects = new Phone(objectX,objectY);
        } else if (title.equals("Saw")) {
            objects = new Saw(objectX,objectY);
        } else {
            objects = new Shovel(objectX,objectY);
        }
        return objects;
    }

    //MODIFIES: avatar
    //EFFECT: parses objects and add it in found list
    private void addObjects(Avatar avatar, JSONObject jsonObject) {
        Objects objects;
        String title = jsonObject.getString("title");
        int objectX = jsonObject.getInt("objectX");
        int objectY = jsonObject.getInt("objectY");
        if (title.equals("Ball")) {
            objects = new Ball(objectX,objectY);
        } else if (title.equals("Hammer")) {
            objects = new Hammer(objectX,objectY);
        } else if (title.equals("Phone")) {
            objects = new Phone(objectX,objectY);
        } else if (title.equals("Saw")) {
            objects = new Saw(objectX,objectY);
        } else {
            objects = new Shovel(objectX,objectY);
        }
        avatar.addFoundList(objects);
    }
}
