package com.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO学习：第一课
 * 
 * @author SilverHu
 *
 */
public class One {

    public static void main(String[] args) {
        One one = new One();
        one.copyFile();
    }

    public void copyFile() {

        // channel是双向的，但是具有只读和只写的特性，从FileInputStream中获取通道channel（只读），从FileInputStream中获取通道channel（只写）
        try (FileInputStream inputStream = new FileInputStream("D:/1.jpg");
                FileOutputStream outStream = new FileOutputStream("D:/2.jpg");
                FileChannel inChannel = inputStream.getChannel();
                FileChannel outChannel = outStream.getChannel();) {

            // 创建缓冲区，缓冲数组大小为1024
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            // 将数据读入到缓冲区
            int num = inChannel.read(buffer);
            while (num != -1) {
                // 将写模式变成读模式，{limit=position, position=0, mark=-1}
                buffer.flip();
                outChannel.write(buffer);
                // 重置缓冲区，{limit=capacity, position=0, mark=-1}
                buffer.clear();
                num = inChannel.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
