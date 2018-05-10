package com.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class Two {

	public static void main(String[] args) {
		Two two = new Two();
		two.mappedBuffer();
	}

	/**
	 * 内存映射文件io，直接将文件映射到内存中，尽量不要做写操作。读取速度快，但是写入速度慢
	 */
	public void mappedBuffer() {
		File file = new File("D:/1.jpg");
		try (FileInputStream inputStream = new FileInputStream("D:/1.jpg");
				FileOutputStream outStream = new FileOutputStream("D:/2.jpg");
				FileChannel inChannel = inputStream.getChannel();
				FileChannel outChannel = outStream.getChannel();) {
			// 创建内存映射文件缓冲区
			MappedByteBuffer mapperdBuffer = inChannel.map(MapMode.READ_ONLY, 0, file.length());

			// 创建字节缓冲区，写入文件
			byte[] bytes = new byte[(int) file.length()];
			for (int i = 0; i < file.length(); i++) {
				bytes[i] = mapperdBuffer.get();
			}
			ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
			int num = inChannel.read(byteBuffer);
			while (num != -1) {
				byteBuffer.flip();
				outChannel.write(byteBuffer);
				byteBuffer.clear();
				num = inChannel.read(byteBuffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分片缓冲区
	 */
	public void slice() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		for (int i = 0; i < 1024; i++) {
			buffer.put((byte) i);
		}
		// 设定操作在文件读写中会有影响
		buffer.position(1);
		buffer.limit(4);

		// 分片缓冲区与原缓冲区共享部分数据
		ByteBuffer slice = buffer.slice();
		for (int i = 0; i < 3; i++) {
			slice.put((byte) 66);
		}
		while (buffer.remaining() > 0) {
			System.out.println(buffer.get());
		}

	}
}
