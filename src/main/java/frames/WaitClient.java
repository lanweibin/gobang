package frames;

import interfaces.DiyViews;
import override_view.BlankPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 用于服务器连接时的窗口
 */
public class WaitClient extends JFrame implements DiyViews, ActionListener{
    private int Frame_Width = 500;
    private int Frame_Height = 500;
    private JLabel label_back, label_host, label_prot, label_showMessage;
    private int port;
    ImageIcon icon = new ImageIcon("drawable/loading.png");
    private JButton btnStartLocal, btnCancle;
    boolean isConnectioned = false;
    private int state_color; // 用于存放服务器端要选用的棋子颜色
    private BlankPanel blankPanel;
    int mx = 0, my = 0, jfx = 0, jfy = 0;

    public WaitClient(int port, int state_color)  {
        this.port = port;
        this.state_color = state_color;
        new Thread(){
            @Override
            public void run() {
                Bor
            }
        };
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
    public void actionPerformed(ActionEvent e) {

    }
}
