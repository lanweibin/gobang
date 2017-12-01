package panels;

import interfaces.DiyViews;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel implements DiyViews {
    private ImageIcon icon = new ImageIcon("drawable/background.png");

    public ScorePanel() {
        initViews();
        setViews();
        addViews();

    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {

    }

    @Override
    public void addViews() {

    }


    @Override
    public void paint(Graphics g) {

        g.drawImage(icon.getImage(), 10, 10, getWidth() - 10, getHeight() - 10, null);
    }
}
