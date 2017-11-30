package socket;

import interfaces.Call;
import panels.BoradPanel;
import utool.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端程序，客户端方式接受和发送数据
 */
public class Client implements Call {
    private Socket socket = null;
    private BoradPanel panel;
    private int state_color;

    public Client(String host,int port, BoradPanel panel, int state_color) {
        this.panel = panel;
        this.state_color = state_color;
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //判断是否连接
    public static boolean TestConnect(String host, int port){
        try {
            Socket socket = new Socket(host, port);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    //发送数据到输入流
    public void Send(String s){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.println(s);
        writer.flush();
    }

    //关闭连接
    public void Close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //传出数据
    @Override
    public void Put(String s) {
        Send(s);
    }

    @Override
    public void Get() {
        BufferedReader reader = null;//获取读入对象
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//得到输入流
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
            if (str == null){
                String[] data = str.split(",");
                int x = Integer.valueOf(data[0]);
                int y = Integer.valueOf(data[1]);

                Point point = null;
                if (state_color == 1){
                    point = new Point(x,y,point.STATE_WHITE);
                }else {
                    point = new Point(x,y,point.STATE_BLACK);
                }

                panel.addPoint(point);
            }
        }
    }
}
