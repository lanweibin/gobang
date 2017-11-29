package socket;

import interfaces.Call;
import panels.BoradPanel;

import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Call {
    private BoradPanel panel;//添加棋子中BoradPanel对象
    ServerSocket serverSocket;
    private Socket socket = null;
    private int state_color;//用来存放己方气质颜色;



    @Override
    public void Put(String s) {

    }

    @Override
    public void Get() {

    }
}
