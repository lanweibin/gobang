package panels;

import frames.BoradFrame;
import interfaces.DiyViews;
import socket.PlayerClient;
import utool.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerPanel extends JPanel implements DiyViews, MouseListener {
    private PlayerClient client = null ;
    protected List<Point> points;
    private int[][] datas = new int[20][20];
    private int state_color  ;

    public PlayerPanel(String host, int port, int stateWhite) {
        this.state_color = state_color;
        points = new ArrayList<>();
        initViews();
        setViews();
        addViews();

        for (int i = 0; i < datas.length; i++) {
            for (int j = 0; j < datas[0].length; j++) {
               datas[0][0] = 0;

            }

        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("以客户端形式来启动");
        client = new PlayerClient(host,port,PlayerPanel.this, state_color);
        new Thread(){
            @Override
            public void run() {
                client.Get();
            }
        }.start();
    }

    //接受棋子到棋盘
    public void addPoint(Point point){
        if (datas[point.getY()][point.getY()] == 0){
            points.add(point);
            if (state_color == BoradFrame.STATE_BLACK) {
                datas[point.getX()][point.getY()] = 1;
            } else {
                datas[point.getX()][point.getY()] = -1;
            }
            updateUI();
            client.Put(point.getX() + "," + point.getY());
        }
    }

    public void receivePoint(Point point) {
        points.add(point);
        updateUI();
    }

    @Override
    public void initViews() {

    }

    @Override
    public void setViews() {
        addMouseListener(this);
    }

    @Override
    public void addViews() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon board = new ImageIcon("drawable/board.png");
        g.drawImage(board.getImage(), 0, 0, 740, 740, null);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Point point : points){
            ImageIcon icon = null;
            if (point.getState() == Point.STATE_WHITE){
                icon = new ImageIcon("drawable/white.png");
            }else {
                icon = new ImageIcon("drawable/black.png");
            }

            g.drawImage(icon.getImage(),point.getX()*36-9, 710-point.getY()*36-5, 36, 36, null);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        System.out.println(x + "," + y);
        Point point = null;
        int X = (x)/ 36;
        int Y = (745-y) / 36;
        System.out.println(X+":"+Y);
        if (X > 0 && X < 20 && Y > 0 && Y < 20) {
            System.out.println("发送"+X + ":" + Y);
            if ((state_color == BoradFrame.STATE_WHITE)) {
                point = new Point(X, Y, Point.STATE_WHITE);
            } else {
                point = new Point(X, Y, Point.STATE_BLACK);
            }
            points.add(point);
            addPoint(point);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        PlayerPanel playerPanel = new PlayerPanel(null, 0, 0);
        frame.setContentPane(playerPanel);
        frame.setSize(740, 740);
        frame.setVisible(true);
    }
}
