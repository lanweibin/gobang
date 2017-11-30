package panels;

import frames.BoradFrame;
import frames.WelcomeFrame;
import interfaces.DiyViews;
import override_view.BlankPanel;
import override_view.ImageButtrn;
import socket.Client;
import socket.Server;
import utool.Calculate;
import utool.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 根据 Servic 或者Client添加棋子
 */
public class BoradPanel extends JPanel implements DiyViews, ActionListener {
    private List<Point> points;//存放棋盘中存在的点
    private Server server = null;
    private Client client = null;
    Calculate calculate;
    private int state_start;//保存启动方式
    private int state_color;//保存所用棋子的颜色
    private BlankPanel beginpanel;//开始的面板
    private JLabel label_message;//开始的提示信息
    private JButton btnBegin ;//开始的按钮
    private int flag = 1;

    public BoradPanel(String host, int port, int state_start, int state_color) {
        this.state_start = state_start;
        this.state_color = state_color;
        points = new ArrayList<Point>();
        initViews();
        setViews();
        addViews();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        if (state_start == BoradFrame.STATE_SERVICE){
            System.out.println("以服务器形式来启动");
            calculate = new Calculate(state_color);
            server = new Server(port, BoradPanel.this, state_color);
            new Thread(){
                @Override
                public void run() {
                    server.Get(); // 在客户端连接之前会阻塞，且要持续运行来接受数据
                }
            }.start();
            
        }else {
            System.out.println("以客户端形式启动");
            calculate = new Calculate(state_color);
            client = new Client(host, WelcomeFrame.port, this,  state_color);
            new Thread(){
                @Override
                public void run() {
                    client.Get();
                }
            }.start();
        }

    }

    //只添加不立即走下一步，用于第一次添加点  只有黑子的时候的才调用
    public void JustAdd(){
        calculate.addPoint(new Point(10,10,state_color));
        System.out.println("第一步");
        Point point; //服务器第一步。根据服务器自己选的颜色来落子
        if (state_color == BoradFrame.STATE_BLACK){
            point  = new Point(10,10,Point.STATE_BLACK);
        }else {
            point = new Point(10,10,Point.STATE_WHITE);
        }
        points.add(point);
    }


    //接收棋子添加到棋盘 // 需要判断所选颜色
    public void addPoint(Point point){
        System.out.println("添加棋子"+point.getX()+"-----"+point.getY());
        if (flag == 1){
            this.remove(beginpanel);
            updateUI();
            flag++;
        }
        if (calculate.JudegeWin(point)){
            calculate.addPoint(point);
            points.add(point);
            updateUI();// 是刷新面板用的

            Point result = calculate.getNext();

            result.setState(state_color);
            calculate.addPoint(result);
            points.add(point);//添加棋子到list中
            updateUI();
            System.out.println("发送"+result.getX()+"-----"+result.getY());

            if (server != null){
                server.Put(result.getX()+","+result.getY());
            }else {
                client.Put(result.getX()+","+result.getY());
            }
        }else {
            System.out.println("游戏结束");
            System.exit(0);
        }
    }


    @Override
    public void initViews() {
        Font f = new Font("幼圆",Font.PLAIN, 15);
        UIManager.put("Label.font", f);
        UIManager.put("Label.foreground", Color.black);
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

        //JPanel
        beginpanel = new BlankPanel(200);

        //JLabel
        label_message = new JLabel();
        //JButten
        btnBegin = new ImageButtrn("确定");

    }



    @Override
    public void setViews() {
        beginpanel.setBounds(250,250,240,240);
        beginpanel.setLayout(null);//null即为清空布局管理器

        label_message.setBounds(10,100,220,40);

        btnBegin.setBounds(80,180,80,40);
        btnBegin.addActionListener(this);
    }

    //增加组件
    @Override
    public void addViews() {
        if (state_color == BoradFrame.STATE_BLACK){
            label_message.setText("轮到你先行！\n点击确定开始游戏");
        }else {
            label_message.setText("请等对方先行");
        }

        beginpanel.add(label_message);
        if (state_start == BoradFrame.STATE_SERVICE){
            beginpanel.add(btnBegin);
        }
        this.add(beginpanel);
        setLayout(null);
    }

    //面板绘制方法
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon board = new ImageIcon("drawable/board.png");
        g.drawImage(board.getImage(),0,0,740,740,null);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Point point : points){
            ImageIcon icon = null;
            if (point.getState() == Point.STATE_WHITE){
                icon = new ImageIcon("drawable/white.png");//白色棋子
            }else {
                icon = new ImageIcon("drawable/black.png");//黑色棋子
            }
            g.drawImage(icon.getImage(),point.getX()*36 - 9, 710 - point.getY()*36 -5,36,26,null);

        }
    }

    //刷新
    public void refresh(Graphics graphics){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBegin){
            this.remove(beginpanel);
            updateUI();
            JustAdd();
        }

    }
}
