package socket;

import frames.BoradFrame;
import interfaces.Call;
import panels.BoradPanel;
import utool.Point;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * 服务端程序，
 */
public class Server implements Call {
    private BoradPanel panel;//添加棋子中BoradPanel对象
    ServerSocket serverSocket;
    private Socket socket = null;
    private int state_color;//用来存放己方棋子颜色;

    public Server(int port ,BoradPanel panel, int state_color) {
        this.panel = panel;
        this.state_color = state_color;

        try {
            serverSocket = new ServerSocket(port);//根据端口号创建对象
            socket = serverSocket.accept();//在没有客户端连接之前会一直阻塞，此处会在一个加载动画中来进行等待
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //向输入流中 发送数据
    public void Send(String s){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.println(s);
        writer.flush();//刷新输出流
    }

    public void Close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFirstPoint(){
        Put("10,10");
        panel.JustAdd();
    }

    @Override
    public void Put(String s) {
        Send(s);
    }

    ///Get方法，此方法会阻塞线程，所以在创建服务器对象之后，要通过线程的方式来使用此方法
    //此方法会一直执行，获取传进的数据，并使用BoradPanel引用来添加点到面板
    @Override
    public void Get() {
        try {
            BufferedReader reader =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while(true){
                String str = null;
                try {
                    reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] data = str.split(",");
                int x = Integer.valueOf(data[0]);
                int y = Integer.valueOf(data[1]);
                Point point;
                if (state_color == BoradFrame.STATE_BLACK){
                    point = new Point(x,y,Point.STATE_WHITE);
                    System.out.println("service get white");
                }else {
                    point = new Point(x,y,Point.STATE_BLACK);
                    System.out.println("service get black");
                }
                panel.addPoint(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
