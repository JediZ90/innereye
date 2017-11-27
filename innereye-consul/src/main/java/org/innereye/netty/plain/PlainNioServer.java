package org.innereye.netty.plain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioServer {

    public void server(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address); // 绑定服务器到制定端口
        Selector selector = Selector.open();// 打开 selector 处理 channel
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 注册ServerSocket到ServerSocket ，并指定这是专门意接受连接
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        for (;;) {
            try {
                selector.select();
                // 等待新的事件来处理。这将阻塞，直到一个事件是传入
            }
            catch (IOException ex) {
                ex.printStackTrace();
                // handle exception
                break;
            }
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            // 从收到的所有事件中 获取 SelectionKey 实例
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        // 检查该事件是一个新的连接准备好接受
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
                        // 接受客户端，并用 selector 进行注册
                        System.out.println("Accepted connection from " + client);
                    }
                    if (key.isWritable()) {
                        // 检查 socket 是否准备好写数据
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            if (client.write(buffer) == 0) {
                                // 将数据写入到所连接的客户端。如果网络饱和，连接是可写的，那么这个循环将写入数据，
                                // 直到该缓冲区是空的
                                break;
                            }
                        }
                        client.close();
                        // 关闭连接
                    }
                }
                catch (IOException ex) {
                    key.cancel();
                    try {
                        key.channel().close();
                    }
                    catch (IOException cex) {
                        // 在关闭时忽略
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        PlainNioServer plainNioServer = new PlainNioServer();
        plainNioServer.server(8080);
    }
}
