package ui;

import model.background.Background;
import model.Avatar;
import model.difficulty.GameBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// build a game panel with background and avatar
public class GamePanel extends JPanel {
    private static final String OVER = "Well done, you have found all the objects.";
    private static final String QUIT = "Game quited.";
    private Avatar avatar;
    private GameBoard game;
    private boolean isQuit = false;

    //EFFECT: construct a GamePanel with proper size, color and fields.
    public GamePanel(Avatar avatar) {
        setPreferredSize(new Dimension(GameBoard.WIDTH, GameBoard.HEIGHT));
        setFocusable(true);
        setBackground(new Color(135, 206, 235));
        this.avatar = avatar;
        this.game = avatar.getGameBoard();
    }

    //EFFECT: draw the components on the panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGame(g);

        if (game.isOver()) {
            gameOver(g);
        }
        if (isQuit) {
            gameQuit(g);
        }
    }

    //MODIFIES: g
    //EFFECT: draw specific components of the game
    private void drawGame(Graphics g) {
        drawBackgrounds(g);
        drawAvatar(g);
    }

    //MODIFIES: g
    //EFFECT: draw avatar
    private void drawAvatar(Graphics g) {
        Color savedCol = g.getColor();
        g.setColor(Avatar.COLOR);
        g.fillOval(avatar.getAvaX() - Avatar.AVA_WIDTH / 4, Avatar.AVA_Y - Avatar.AVA_HEIGHT * 2 / 3,
                Avatar.AVA_WIDTH / 2, Avatar.AVA_HEIGHT / 3);
        g.setColor(savedCol);
        g.drawLine(avatar.getAvaX() - Avatar.AVA_WIDTH / 2, Avatar.AVA_Y,
                avatar.getAvaX() + Avatar.AVA_WIDTH / 2, Avatar.AVA_Y);
        g.drawLine(avatar.getAvaX(), Avatar.AVA_Y - Avatar.AVA_HEIGHT / 3, avatar.getAvaX(), Avatar.AVA_Y);
        g.drawLine(avatar.getAvaX(), Avatar.AVA_Y,
                avatar.getAvaX() - Avatar.AVA_WIDTH / 2, Avatar.AVA_Y + Avatar.AVA_HEIGHT / 3);
        g.drawLine(avatar.getAvaX(), Avatar.AVA_Y,
                avatar.getAvaX() + Avatar.AVA_WIDTH / 2, Avatar.AVA_Y + Avatar.AVA_HEIGHT / 3);
    }

    //MODIFIES: g
    //EFFECT: draw backgrounds
    private void drawBackgrounds(Graphics g) {
        for (Background b : game.getBackgrounds()) {
            drawBackground(g, b);
        }
    }

    //MODIFIES: g
    //EFFECT: draw one background element
    private void drawBackground(Graphics g, Background b) {
        BufferedImage img;
        try {
            img = ImageIO.read(new File("./src/image/backgrounds/" + b.getTitle() + ".png"));
            g.drawImage(img.getScaledInstance(b.width(), b.height(),
                    Image.SCALE_DEFAULT), b.getBgx(), b.getBgy(), null);
        } catch (IOException e) {
            System.out.println("Bad");
        }
    }

    // Draws the "game over" message
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameOver(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(OVER, g, fm, GameBoard.HEIGHT / 2);
        g.setColor(saved);
    }

    // Draws the "game over" message
    // modifies: g
    // effects:  draws "game over" and replay instructions onto g
    private void gameQuit(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(0, 0, 0));
        g.setFont(new Font("Arial", 20, 20));
        FontMetrics fm = g.getFontMetrics();
        centreString(QUIT, g, fm, GameBoard.HEIGHT / 2);
        g.setColor(saved);
    }

    // Centres a string on the screen
    // modifies: g
    // effects:  centres the string str horizontally onto g at vertical position posY
    private void centreString(String str, Graphics g, FontMetrics fm, int posY) {
        int width = fm.stringWidth(str);
        g.drawString(str, (GameBoard.WIDTH - width) / 2, posY);
    }

    public void setQuit() {
        isQuit = true;
    }
}
