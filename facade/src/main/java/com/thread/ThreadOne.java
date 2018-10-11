package com.thread;

/**
 * 线程1 卖票
 * 
 * @author SilverHu
 *
 */
public class ThreadOne implements Runnable {

    // 票数
    private int ticket = 10;

    public void run() {
        int total = ticket;
        for (int i = 0; i < total; i++) {
            synchronized (this) {
                if (ticket > 0) {
                    try {
                        Thread.sleep(100);
                        ticket--;
                        System.out.println(Thread.currentThread().getName() + "剩余票数：" + ticket);
                    } catch (InterruptedException e) {
                    }

                }
            }
        }
    }

    public static void main(String[] args) {
        ThreadOne threadOne = new ThreadOne();
        Thread one = new Thread(threadOne, "线程1");
        Thread two = new Thread(threadOne, "线程2");
        one.start();
        two.start();
    }
}
