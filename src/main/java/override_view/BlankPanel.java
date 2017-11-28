package override_view;

import javax.swing.*;
import java.awt.*;

public class BlankPanel extends JPanel {
    private int trans = 40;
    public BlankPanel(int trans) {
        this.trans = trans;
        setOpaque(false); //透明度
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON); //消除线段的锯齿状边缘
        g2d.setColor(Color.BLACK);
        g2d.setColor(new Color(255,255,255,trans));
        g2d.setStroke(new BasicStroke(3));//画笔的粗细
        g2d.fillRoundRect(0,0,getWidth()-1, getHeight()-1, 20, 20);
    }
}
