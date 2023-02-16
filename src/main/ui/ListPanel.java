package ui;

import model.Avatar;
import model.difficulty.GameBoard;
import model.object.Objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

//list panel with objects hidden and found
public class ListPanel extends JPanel {

    public static final int IMAGE_LENGTH = 50;
    public static final int IMAGE_LARGER = 300;

    Avatar avatar;
    GameBoard game;
    JButton showHidden;
    JTextField found;
    JList<ImageIcon> foundObjects;
    JList<ImageIcon> hiddenObjects;
    MouseHandler mouseHandler;

    //EFFECT: construct a panel that shows the found objects
    public ListPanel(Avatar avatar) {
        this.avatar = avatar;
        this.game = avatar.getGameBoard();
        this.setLayout(new BorderLayout());
        addComponents();
    }

    //MODIFIES: this
    //EFFECT: add the components to the panel
    private void addComponents() {
        showHidden = new JButton("Show Hidden");
        found = new JFormattedTextField("Found:");
        showHidden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JDialog jd = new JDialog();
                jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                jd.setSize(50, 300);
                hiddenObjects = new JList<>(iconList(avatar.getFindList()));
                jd.add(hiddenObjects, BorderLayout.CENTER);
                centreOnScreen(jd);
                jd.setVisible(true);
            }
        });
        showHidden.setFocusable(false);
        foundObjects = new JList<>(iconList(avatar.getFoundList()));
        foundObjects.setFocusable(false);
        mouseHandler = new MouseHandler();
        foundObjects.addMouseListener(mouseHandler);
        add(found, BorderLayout.NORTH);
        add(foundObjects,BorderLayout.CENTER);
        add(showHidden, BorderLayout.SOUTH);
    }

    //EFFECT: return a list of ImageIcon based on the object list
    public ListModel<ImageIcon> iconList(List<Objects> objectsList) {
        DefaultListModel<ImageIcon> icons = new DefaultListModel<>();
        for (Objects o : objectsList) {
            ImageIcon imageIcon = new ImageIcon("./src/image/objects/" + o.getTitle() + ".png");
            imageIcon.setImage(imageIcon.getImage().getScaledInstance(IMAGE_LENGTH,IMAGE_LENGTH,Image.SCALE_DEFAULT));
            icons.addElement(imageIcon);
        }
        return icons;
    }

    //MODIFIES: this
    //EFFECT: update the found object list
    public void update() {
        foundObjects.setModel(iconList(avatar.getFoundList()));
        repaint();
    }

    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen(JDialog jf) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        jf.setLocation((screen.width - jf.getWidth()) / 2, (screen.height - jf.getHeight()) / 2);
    }

    /*
     * A mouse handler to respond to mouse events
     */
    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            JList theList = (JList) mouseEvent.getSource();
            if (mouseEvent.getClickCount() == 2) {
                int index = theList.locationToIndex(mouseEvent.getPoint());
                if (index >= 0) {
                    Object o = theList.getModel().getElementAt(index);
                    String object = o.toString().split("/")[4];
                    JDialog jd = new JDialog();
                    jd.setTitle(object);
                    jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    jd.setSize(350,350);
                    ImageIcon icon = new ImageIcon(o.toString());
                    icon.setImage(icon.getImage().getScaledInstance(IMAGE_LARGER,IMAGE_LARGER,Image.SCALE_DEFAULT));
                    jd.add(new JLabel(icon));
                    centreOnScreen(jd);
                    jd.setVisible(true);
                }
            }
        }
    }
}
