package ui;

import model.Event;
import model.EventLog;
import model.Avatar;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

//start playing
public class PlayGame extends JFrame {
    private static final String JSON_STORE = "./data/newGame.json";
    private static final int INTERVAL = 10;
    private static final String EASY = "easy";
    private static final String HARD = "hard";
    private Avatar avatar;
    boolean gameOver = false;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private GamePanel gp;
    private ListPanel lp;
    private KeyHandler keyHandler;
    private JButton quit;
    private JLabel instruction;

    private Timer timer;

    //EFFECT: start game
    public PlayGame() {
        super("Now You Find Me");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setup();
        gp = new GamePanel(avatar);
        lp = new ListPanel(avatar);
        keyHandler = new KeyHandler();
        quit = new JButton("Quit");
        instruction = new JLabel("left, right to move, \"i\" for interaction");
        addComponents();
    }

    // Set up timer
    // modifies: none
    // effects:  initializes a timer that updates game each
    //           INTERVAL milliseconds
    private void addTimer() {
        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                gp.repaint();
                lp.update();
                update();
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    //MODIFIES: this
    //EFFECT: add components to JFrame
    private void addComponents() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                endQuit();
                gp.setQuit();
            }
        });
        add(instruction, BorderLayout.PAGE_START);
        add(gp, BorderLayout.CENTER);
        add(lp, BorderLayout.AFTER_LINE_ENDS);
        add(quit,BorderLayout.SOUTH);
        addKeyListener(keyHandler);
        gp.addKeyListener(keyHandler);
        lp.addKeyListener(keyHandler);
        pack();
        centreOnScreen();
        setVisible(true);
        addTimer();
    }

    //EFFECT: start game with load option
    public void setup() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        JFrame jf = new JFrame();
        int result = JOptionPane.showConfirmDialog(jf, "Would you like to load your progress from last time?");
        if (result == 0) {
//            System.out.println("You pressed Yes");
            this.load();
        } else if (result == 1) {
//            System.out.println("You pressed NO");
            newPlay();
        }
    }

    //EFFECT:display quitting menu
    public void endQuit() {
        JFrame jf = new JFrame();
        int result = JOptionPane.showConfirmDialog(jf, "Would you like to save your progress?");
        if (result == 0) {
//            System.out.println("You pressed Yes");
            this.save();
        }
        printLog();
    }

    //MODIFIES: this
    //EFFECT: start new game
    public void newPlay() {
        chooseDifficulty();
    }


    //MODIFIES: this
    //EFFECT: create new game with chosen difficulty
    public void chooseDifficulty() {
        JFrame jf = new JFrame();
        String difficultyLevel = JOptionPane.showInputDialog(jf, "Choose difficulty level: Easy / Hard");
        if (difficultyLevel.equalsIgnoreCase(EASY)) {
            this.avatar = new Avatar(EASY);
        } else if (difficultyLevel.equalsIgnoreCase(HARD)) {
            this.avatar = new Avatar(HARD);
        }
    }

    //MODIFIES: this
    //EFFECT: update the game on clock tick
    public void update() {
        if (avatar.getFindList().isEmpty()) {
            delete();
            gameOver = true;
            timer.stop();
            printLog();
        }
    }

    private void printLog() {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString() + "\n");
        }
    }

    //MODIFIES: this
    //EFFECT: write the data to json
    public void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(avatar);
            jsonWriter.close();
            System.out.println("Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //MODIFIES: this
    //EFFECT: reload the stored data
    public void load() {
        try {
            avatar = jsonReader.read();
            System.out.println("Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            System.exit(1);
        }
    }

    //EFFECT: delete the data if the game finished by finding all objects
    public void delete() {
        File jsonFile = new File(JSON_STORE);
        if (jsonFile.delete()) {
            System.out.println("File deleted");
        } else {
            System.out.println("No file");
        }
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    /*
     * A key handler to respond to key events
     */
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            avatar.keyPressed(e.getKeyCode());
        }
    }
}

