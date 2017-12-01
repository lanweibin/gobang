package socket;

import interfaces.Call;
import panels.PlayerPanel;
import utool.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerClient implements Call {
    private Socket socket = null;
    private PlayerPanel panel;
    private int state_color;

    public PlayerClient(String host, int port,  PlayerPanel panel, int state_color) {
        this.panel = panel;
        this.state_color = state_color;

        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发送数据到输入流
    public void Send(String s) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream()); //获取输出流对象
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.println(s); // 数据写入到输出流
        writer.flush(); // 刷新输入流
    }

    public void Close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Put(String s) {
        Send(s);
    }

    @Override
    public void Get() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            String str = null;
            try {
                str = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (str!=null){
                String[] data = str.split(",");
                System.out.println("PlayClient收到"+str);
                int x = Integer.valueOf(data[0]);
                int y = Integer.valueOf(data[1]);

                Point point = null;
                if (state_color == 1){
                    point = new Point(x,y,Point.STATE_WHITE);
                }else {
                    point = new Point(x,y,Point.STATE_BLACK);
                }

                panel.receivePoint(point);//添加棋子到棋盘
                System.out.println("Client get"+point.getX()+":"+point.getY());
            }
        }
    }
}
