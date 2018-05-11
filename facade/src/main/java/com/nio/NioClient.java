package com.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalTime;

/**
 * select应用：客户端
 * 
 * @author SilverHu
 *
 */
public class NioClient {
	private static NioClient client = new NioClient();

	private NioClient() {
	}

	/**
	 * get NioClient instance
	 * 
	 * @return
	 * @throws IOException
	 */
	public static NioClient getClient() throws IOException {
		client.init();
		return client;
	}

	private SocketChannel socketChannel;

	private Charset charset = Charset.forName("UTF-8");

	private void init() throws IOException {
		socketChannel = SocketChannel.open();
		InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), 8080);
		socketChannel.socket().connect(address);
	}

	public void write(String content) throws IOException {
		if (content != null) {
			socketChannel.write(charset.encode(content));
		}
	}

	public static void main(String[] args) {
		NioClient client = null;
		try {
			client = NioClient.getClient();
		} catch (IOException e) {
			System.out.println("get NioClient instance is error!");
			e.printStackTrace();
		}
		try {
			while(true){
				client.write(LocalTime.now().toString());
				Thread.sleep(5000);
			}
		} catch (IOException e) {
			System.out.println("connect refuse");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
