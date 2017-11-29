package interfaces;

/**
 * 棋盘界面与服务器，客户端之间的数据传递
 * put发送BoradPannel窜出的数据
 * get接受客户端或者服务器的数据
 */
public interface Call {
    public void Put(String s);

    public void Get();
}
