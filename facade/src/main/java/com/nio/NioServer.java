package com.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * select应用，服务端
 * 
 * @author SilverHu
 *
 */
public class NioServer {
	private static NioServer server = new NioServer();

	private NioServer() {
	}

	/**
	 * get NioServer instance
	 * 
	 * @return
	 */
	public static NioServer getServer() {
		return server;
	}

	/** 选择器：用来注册感兴趣的IO事件，当事件发生时，会告诉我们所发生的事件 */
	private Selector selector;

	/** 定义实现编码、解码的字符集对象 */
	private Charset charset = Charset.forName("UTF-8");

	// 打开注册中心
	{
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 注册感兴趣的io事件
	 * 
	 * @param ip
	 *            监听ip地址
	 * @param port
	 *            监听端口号
	 * @param ops
	 *            监听事件
	 * @throws IOException
	 */
	public void register(InetSocketAddress socketAddress, int ops) throws IOException {
		// 每个端口都有一个ServerSocketChannel，设置为非阻塞，为ServerSocketChannel绑定ip和端口
		ServerSocketChannel server = ServerSocketChannel.open();
		server.configureBlocking(false);
		server.socket().bind(socketAddress);

		// 注册selector，第一个参数指定注册中心，第二个参数指定监听事件
		server.register(selector, ops);
	}

	/**
	 * 处理事件
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void handler() throws IOException {
		while (true) {
			if (selector.select() > 0) {
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey selectionKey = it.next();
					// 删除已选择的key
					it.remove();
					if (selectionKey.isAcceptable()) {
						// 准备接收客户端连接
						System.out.println("ready accept please from client");
						SocketChannel channel = ((ServerSocketChannel) selectionKey.channel()).accept();

						// 注册监控写请求
						channel.configureBlocking(false);
						channel.register(selector, SelectionKey.OP_READ);
					} else if (selectionKey.isReadable()) {
						// 读数据
						System.out.println("start to read");
						SocketChannel channel = (SocketChannel) selectionKey.channel();
						try {
							String content = read(channel);
							System.out.println("server content : " + content);
						} catch (IOException e) {
							System.out.println("closed connect");
							channel.close();
							e.printStackTrace();
						}
					}
				}
			}

		}

	}

	/**
	 * 读取渠道数据
	 * 
	 * @param channel
	 * @return
	 * @throws IOException
	 */
	public String read(SocketChannel channel) throws IOException {
		String content = "";
		ByteBuffer bf = ByteBuffer.allocate(1024);
		int mark;

		mark = channel.read(bf);

		while (mark > 0) {
			bf.flip();
			content += charset.decode(bf).toString();
			bf.clear();
			mark = channel.read(bf);
		}
		return content;
	}

	public static void main(String[] args) {
		try {
			NioServer.getServer().register(new InetSocketAddress(InetAddress.getLocalHost(), 8080),
					SelectionKey.OP_ACCEPT);
			NioServer.getServer().handler();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
