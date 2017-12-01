package override_view;

import javax.swing.*;
import java.awt.*;

public class BackPanel extends JPanel {
    public BackPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon back = new ImageIcon("drawable/background.png");
        g2d.drawImage(back.getImage(), 0, 0, 1280, 720, null);
    }
}
