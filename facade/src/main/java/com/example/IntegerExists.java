package com.example;

public class IntegerExists {
    static int[] file = { 0, 1, -1, 4, 5, 7, 8, 11, 23, 142 };
    static int[] record = new int[10];

    /**
     * 判断整数是否存在
     */
    public static void isExistInt() {

        for (int i : file) {
            int index = Math.abs(i) / 16;
            int mode = Math.abs(i) % 16;

            // 将指定位置的数置为1
            if (i > 0) {
                record[index] = record[index] | 1 << (mode * 2);
            } else {
                record[index] = record[index] | 1 << (mode * 2 + 1);
            }
        }

        for (int i = 0; i < record.length; i++) {
            for (int mode = 0; mode < 16; mode++) {
                // 获取绝对值
                int num = i * 16 + mode;

                // 获取正负标识
                int zsign = (record[i] & 1 << (mode * 2)) >> (mode * 2);
                int fsign = (record[i] & 1 << (mode * 2 + 1)) >> (mode * 2 + 1);

                if (fsign == 1) {
                    // 负数
                    System.out.println(num * -1);
                }
                if (zsign == 1) {
                    // 正数
                    System.out.println(num);
                }
            }
        }
    }
}
