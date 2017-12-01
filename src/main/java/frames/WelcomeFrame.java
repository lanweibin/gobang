package frames;

/**
 * 欢迎界面
 */


import com.sun.awt.AWTUtilities;
import interfaces.DiyViews;
import org.junit.Test;
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
    public static int port = 9527;
    public static String host = "127.1.0.0";//指定全局端口号
    private JButton btnStart, btnClose;//启动按钮
    private SelectButton selectService, selectClient, selectBlack, selectWhite;//启动方式，颜色选择
    private JLabel label_select_state, label_select_color;//选择方式，选择按钮
    private JLabel BackLabel, label_message;//背景标签
    private ImageIcon icon = new ImageIcon("drawable/welcome.png");
    private int select_state =  BoradFrame.STATE_SERVICE ;//启动方式，默认为服务器方式
    private int select_color = BoradFrame.STATE_BLACK;//棋子颜色，默认黑色
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
        selectWhite = new SelectButton("白棋");

        btnStart = new ImageButtrn("开始游戏");
        btnClose = new ImageButtrn("结束游戏");

        //JPanel
        blankPanel = new BlankPanel(180);
        if (select_state == 1){
            selectService.setState("selectied");
            selectClient.setState("normal");
        }else {
            selectService.setState("normal");
            selectClient.setState("selectied");
        }

        if (select_color == 1){
            selectBlack.setState("selectied");
            selectWhite.setState("normal");
        }else {
            selectBlack.setState("normal");
            selectWhite.setState("selectied");
        }

    }

    public void setViews() {
        //Jlabel
        label_select_state.setBounds(169,187,100,35);//(1)前两个是组件左上角在容器中的坐标。
        label_select_color.setBounds(169,240,100,35);//(2)后两个是宽度和高度。
        //JButton
        selectService.setBounds(260,187,100,35);
        selectClient.setBounds(400,187,100,35);

        selectBlack.setBounds(260,240,100,35);
        selectWhite.setBounds(400, 240, 100, 35);
        btnStart.setBounds(200,320,100,35);
        btnClose.setBounds(360,320,100,35);

        //Jpanel
        blankPanel.setBounds(5,5,Frame_width-5,Frame_height-5);

        selectService.addActionListener(this); //this 传的什么？
        selectClient.addActionListener(this);
        selectBlack.addActionListener(this);
        selectWhite.addActionListener(this);
        btnStart.addActionListener(this);



    }

    public void addViews() {
        this.add(label_select_state);
        this.add(label_select_color);
        this.add(selectService);
        this.add(selectClient);
        this.add(selectBlack);
        this.add(selectWhite);
        this.add(BackLabel);
        this.add(btnStart);
        this.add(btnClose);
//        this.add(blankPanel);  //使用透明模板
        this.add(BackLabel);

        setUndecorated(true); //不装饰
        setSize(Frame_width,Frame_height);
        AWTUtilities.setWindowOpaque(this,false);//透明
        setLocationRelativeTo(null);
        setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectService){
            selectClient.setState("normal");
            select_state = BoradFrame.STATE_SERVICE;//选择服务器
        }
        if (e.getSource() == selectClient){
            selectService.setState("normal");
            select_state = BoradFrame.STATE_CLIENT;//选择用户端
        }

        if (e.getSource() == selectBlack){
            selectWhite.setState("normal");
            select_color = BoradFrame.STATE_BLACK;
        }
        if (e.getSource() == selectWhite){
            selectBlack.setState("normal");
            select_color = BoradFrame.STATE_WHITE;//选择白棋子
        }

        if (e.getSource() == btnStart ){
            WaitClient frame = new WaitClient(WelcomeFrame.port, select_color);
            System.out.println("服务器模式，黑子");
            WelcomeFrame.this.dispose();
        }else {
            ConnectService clientFrame;
            if (select_color == BoradFrame.STATE_BLACK){
                //客户端选择黑子
                clientFrame = new ConnectService(BoradFrame.STATE_BLACK);
            }else {
                clientFrame = new ConnectService(BoradFrame.STATE_WHITE);
            }
            WelcomeFrame.this.dispose();
        }



    }


    public static void main(String[] args) {
        WelcomeFrame welcomeFrame = new WelcomeFrame();
    }

}
