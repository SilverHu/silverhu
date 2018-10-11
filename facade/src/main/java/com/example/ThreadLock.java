package com.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadLock {
    public static ReentrantLock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread th = new ThreadObject(i);
            th.start();
        }
        condition.signal();
    }

}

class ThreadObject extends Thread {
    int i = 0;

    public ThreadObject(int i) {
        super();
        this.i = i;
    }

    @Override
    public void run() {
        try {
            ThreadLock.lock.lock();
            if (i == 8) {
                ThreadLock.condition.signal();
            } else if (i == 9) {
                ThreadLock.condition.await(5000, TimeUnit.MILLISECONDS);
                ThreadLock.condition.signalAll();
            } else {
                System.out.println(this.getName() + " get lock and waiting");
                ThreadLock.condition.await();
                System.out.println(this.getName() + "notify");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            ThreadLock.lock.unlock();
        }
        super.run();
    }
}
