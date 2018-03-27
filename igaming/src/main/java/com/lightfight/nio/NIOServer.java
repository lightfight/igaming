package com.lightfight.nio;

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

/**
 * <pre>
 *
 *     http://cmsblogs.com/?p=2464
 *
 * </pre>
 *
 * @author ursobeautiful@qq.com
 * @since 2018/3/27/0027 13:34
 */
public class NIOServer {

    /*接受数据缓冲区*/
    private ByteBuffer sendbuffer = ByteBuffer.allocate(1024);
    /*发送数据缓冲区*/
    private ByteBuffer receivebuffer = ByteBuffer.allocate(1024);

    private Selector selector;

    public NIOServer(int port) throws IOException {
        // 打开服务器套接字通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 服务器配置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 检索与此通道关联的服务器套接字
        ServerSocket serverSocket = serverSocketChannel.socket();
        // 进行服务的绑定
        serverSocket.bind(new InetSocketAddress(port));
        // 通过open()方法找到Selector
        selector = Selector.open();
        // 注册到selector，等待连接
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server Start----:");
    }

    //
    private void listen() throws IOException {
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                try {
                    handleKey(selectionKey);
                } catch (IOException e) {
                    selectionKey.cancel(); // 如果链接出现问题就取消链接，如果不取消那么下一个还是会获取这个channel
                    e.printStackTrace();
                }
            }

            selectionKeys.clear();
        }
    }

    private void handleKey(SelectionKey selectionKey) throws IOException {
        // 接受请求
        ServerSocketChannel server = null;
        SocketChannel clientChannel = null;
        String receiveText;
        String sendText;
        int count = 0;
        // 测试此键的通道是否已准备好接受新的套接字连接。
        if (selectionKey.isAcceptable()) {
            // 返回为之创建此键的通道。
            server = (ServerSocketChannel) selectionKey.channel();
            // 接受到此通道套接字的连接。
            // 此方法返回的套接字通道（如果有）将处于阻塞模式。
            clientChannel = server.accept();
            // 配置为非阻塞
            clientChannel.configureBlocking(false);
            // 注册到selector，等待连接
            clientChannel.register(selector, SelectionKey.OP_READ);
        } else if (selectionKey.isReadable()) {
            // 返回为之创建此键的通道。
            clientChannel = (SocketChannel) selectionKey.channel();
            //将缓冲区清空以备下次读取
            receivebuffer.clear();
            //读取服务器发送来的数据到缓冲区中
            count = clientChannel.read(receivebuffer);
            if (count > 0) {
                receiveText = new String(receivebuffer.array(), 0, count);
                System.out.println("服务器端接受客户端数据--:" + receiveText);
                clientChannel.register(selector, SelectionKey.OP_WRITE);
            }
        } else if (selectionKey.isWritable()) {
            //将缓冲区清空以备下次写入
            sendbuffer.clear();
            // 返回为之创建此键的通道。
            clientChannel = (SocketChannel) selectionKey.channel();
            sendText = "message from server--";
            //向缓冲区中输入数据
            sendbuffer.put(sendText.getBytes());
            //将缓冲区各标志复位,因为向里面put了数据标志被改变要想从中读取数据发向服务器,就要复位
            sendbuffer.flip();
            //输出到通道
            clientChannel.write(sendbuffer);
            System.out.println("服务器端向客户端发送数据--：" + sendText);
            clientChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 8080;
        NIOServer server = new NIOServer(port);
        server.listen();
    }
}
