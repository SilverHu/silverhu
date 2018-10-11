package com.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * channel数据传输
 * @author SilverHu
 *
 */
public class Five {

    public static void main(String[] args) {
        Five five = new Five();
        five.transfer();
    }

    public void transfer() {
        try (FileInputStream ins = new FileInputStream("D:/1.jpg");
                FileOutputStream outs = new FileOutputStream("D:/3.jpg");
                FileChannel infc = ins.getChannel();
                FileChannel outfc = outs.getChannel()) {
            outfc.transferFrom(infc, 0, infc.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

}
