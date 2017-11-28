package override_view;

import interfaces.DiyViews;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectButton extends JButton implements DiyViews {
    private String state = "normal";
    private String content = null;


    public SelectButton(String s) {
        content = s;
        setMargin(new Insets(0,0,0,0));
        setContentAreaFilled(false);
        setBorderPainted(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                state = "selectied";
                repaint();
            }
        });
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

    public void setState(String state) {
        this.state = state;
        repaint();
    }
}
