package org.innereye.netty.plain;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

public class PlainOioServer {

    public void server(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port); // 绑定服务器到指定的端口
        for (;;) {
            final Socket clientSocket = socket.accept(); // 接受一个连接
            System.out.println("Accepted connection from " + clientSocket);
            new Thread(new Runnable() { // 创建一个新的线程来处理连接

                @Override
                public void run() {
                    OutputStream out;
                    try {
                        out = clientSocket.getOutputStream();
                        out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8"))); // 将消息发送到连接的客户端
                        out.flush();
                        clientSocket.close(); // 一旦消息被写入和刷新时就关闭连接
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                        try {
                            clientSocket.close();
                        }
                        catch (IOException ex) {
                            // ignore on close
                        }
                    }
                }
            }).start(); // 启动线程
        }
    }

    public static void main(String[] args) throws IOException {
        PlainOioServer plainOioServer = new PlainOioServer();
        plainOioServer.server(8080);
    }
}
