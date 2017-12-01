package frames;

import com.sun.awt.AWTUtilities;
import interfaces.DiyViews;
import override_view.BlankPanel;
import override_view.ImageButtrn;
import panels.BoradPanel;
import socket.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 用于连接服务的
 */
public class ConnectService extends JFrame implements DiyViews, ActionListener {
    private JLabel label_background, label_host, label_port;
    private JTextField text_host, text_port;
    private JButton btnStart, btnCancle;
    private ImageIcon icon = new ImageIcon("drawable/loading.png");
    int mx = 0, my = 0, jfx = 0, jfy = 0;
    private BlankPanel blankPanel = null;
    private int state_color;
    private int width = 550;
    private int height = 550;

    public ConnectService(int state_color) throws HeadlessException {
        this.state_color = state_color;
        initViews();
        setViews();
        addViews();
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(e.getXOnScreen() - mx + jfx, e.getYOnScreen() - my + jfy);
            }
        });
        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mx = e.getXOnScreen();
                my = e.getYOnScreen();
                jfx = e.getX();//容器的位置坐标
                jfy = e.getY();
            }
        });
    }

    @Override
    public void initViews() {
        Font f = new Font("幼圆",Font.PLAIN, 15);
        UIManager.put("Label.font", f);
        UIManager.put("Label.foregrond", Color.WHITE);
        UIManager.put("Button.font", f);
        UIManager.put("Menu.font", f);
        UIManager.put("MenuItem.font", f);
        UIManager.put("List.font", f);
        UIManager.put("CheckBox.font", f);
        UIManager.put("RadioButton.font", f);
        UIManager.put("ComboBox.font", f);
        UIManager.put("TextArea.font", f);
        UIManager.put("EditorPane.font", f);
        UIManager.put("ScrollPane.font", f);
        UIManager.put("ToolTip.font", f);
        UIManager.put("TextField.font", f);
        UIManager.put("TableHeader.font", f);
        UIManager.put("Table.font", f);

        //绘制背景
        label_background = new JLabel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                icon.paintIcon(this, g, 0, 0);
            }
        };

        //JLabel
        blankPanel = new BlankPanel(180);
        label_host = new JLabel("IP地址：");
        label_port = new JLabel("端口号：");
        //JTextFiled
        text_host = new JTextField(WelcomeFrame.host);
        text_port = new JTextField(WelcomeFrame.port+"");

        //JButten
        btnStart = new ImageButtrn("启动游戏");
        btnCancle = new ImageButtrn("取消");

    }

    @Override
    public void setViews() {
        setUndecorated(true);//没有任何边框和标题栏的窗口

        label_host.setBounds(140,130,80,45);
        label_port.setBounds(140,180,80,45);

        text_host.setBounds(230,130,100,35);
        text_port.setBounds(230,130,100,35);

        btnStart.setBounds(120, 300, 100, 35);
        btnStart.addActionListener(this);
        btnCancle.setBounds(230,300,100,35);
        btnCancle.addActionListener(this);
    }

    @Override
    public void addViews() {
        add(label_host);
        add(label_port);

        add(text_host);
        add(text_port);

        add(btnStart);
        add(btnCancle);

        this.add(label_background);
        setUndecorated(true);
        setSize(width,height);
        AWTUtilities.setWindowOpaque(this,false);//设置窗口无边缘,透明
        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnStart){
            final String host = WelcomeFrame.host;
            if (Client.TestConnect(host,WelcomeFrame.port)){

                if (state_color == BoradFrame.STATE_BLACK){
                    new Thread(){
                        @Override
                        public void run() {
                            BoradPanel panel = new BoradPanel(host,WelcomeFrame.port, BoradFrame.STATE_CLIENT, BoradFrame.STATE_BLACK);
                            BoradFrame frame = new BoradFrame(panel,BoradFrame.STATE_SERVICE,state_color);//修改了
                            frame.setTitle("客户端");
                        }
                    }.start();
                }else {
                    new Thread(){
                        @Override
                        public void run() {
                            BoradPanel panel = new BoradPanel(host,WelcomeFrame.port,BoradFrame.STATE_CLIENT,BoradFrame.STATE_WHITE);
                            BoradFrame frame = new BoradFrame(panel,BoradFrame.STATE_CLIENT,state_color);
                            frame.setTitle("客户端");
                        }
                    }.start();
                }
            }else {
                ConnecteError frame = new ConnecteError();
            }
        }

        if (e.getSource() == btnCancle){
            WelcomeFrame welcomeFrame = new WelcomeFrame();
            ConnectService.this.dispose();
        }
    }
}
