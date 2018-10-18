package com.test.facade.coding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

public class ThreadCoding {

    ReentrantLock lock = new ReentrantLock();

    @Test
    public void standyLock() {
        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (lock.tryLock()) {
                            System.out.println(
                                    Thread.currentThread().getName() + " get lock : " + System.currentTimeMillis());
                        }
                    } finally {
                        if (lock.isHeldByCurrentThread()) {
                            lock.unlock();
                        }
                    }

                }
            });
            list.add(thread);
            thread.start();
        }
        list.stream().forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void standyExecutor() {
    }
}
