package frames;

/**
 * 欢迎界面
 */


import interfaces.DiyViews;
import override_view.BlankPanel;
import override_view.ImageButtrn;
import override_view.SelectButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomeFrame extends JFrame implements DiyViews,ActionListener {
    public static String host = "127.1.0.0";//指定全局端口号
    private JButton btnStart, btnClose;//启动按钮
    private SelectButton selectService, selectClient, selectBlack, selcetWhite;//启动方式，颜色选择
    private JLabel label_select_state, label_select_color;//选择方式，选择按钮
    private JLabel BackLabel, label_message;//背景标签
    private ImageIcon icon = new ImageIcon("drawable/welcome.png");
//    private int select_state =  BoradFrame ;//启动方式，默认为服务器方式
//    private int select_color = BoradFrame;//棋子颜色，默认黑色
    int mx = 0, my = 0, jfx = 0, jfy = 0;
    private BlankPanel blankPanel;//透明面板
    private int Frame_width = 1000;
    private int Frame_height = 500;

    public WelcomeFrame()  {
        Font f = new Font("幼圆",Font.PLAIN,15);
        UIManager.put("Label.font",f );
        UIManager.put("Label.foreground",Color.black );
        UIManager.put("Button.font",f );
        UIManager.put("Menu.font",f );
        UIManager.put("MenuItem.font",f );
        UIManager.put("List.font",f );
        UIManager.put("CheckBox.font",f );
        UIManager.put("RadioButton.font",f );
        UIManager.put("ComboBox.font",f );
        UIManager.put("TextArea.font",f );
        UIManager.put("EditorPane.font",f );
        UIManager.put("ScorllPane",f );
        UIManager.put("TooTip.font",f );
        UIManager.put("TextField.font",f );
        UIManager.put("TableHeader.font",f );
        UIManager.put("Table.font",f );
        initViews();
        setViews();
        addViews();

        this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) { //鼠标被拖动
                setLocation(e.getXOnScreen() - mx + jfx, e.getYOnScreen() - my + jfy);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mx = e.getXOnScreen();
                my = e.getYOnScreen();
                jfx = e.getX();//得到鼠标的（象素）位置
                jfy = e.getY();
            }
        });

    }

    public void initViews() {
        BackLabel = new JLabel(){
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                icon.paintIcon(this, g,0,0);
            }
        };
        //标签初始化
        label_select_state = new JLabel("启动方式");
        label_select_color = new JLabel("棋子颜色");
        //Jbutton
        selectService = new SelectButton("服务器");
        selectClient = new SelectButton("客户端");
        selectBlack = new SelectButton("黑棋");
        selcetWhite = new SelectButton("白棋");

        btnStart = new ImageButtrn("开始游戏");
        btnClose = new ImageButtrn("结束游戏");

        //JPanel
        blankPanel = new BlankPanel(180);


    }

    public void setViews() {

    }

    public void addViews() {

    }

    public void actionPerformed(ActionEvent e) {

    }

    public static void main(String[] args) {
        WelcomeFrame welcomeFrame = new WelcomeFrame();
    }

}
