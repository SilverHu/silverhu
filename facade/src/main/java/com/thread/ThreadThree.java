package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreadThree implements Runnable{
    /** 信号量 */
    private Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            // 获取访问许可
            semaphore.acquire();
            System.out.println(Thread.currentThread().getName() + " get access permission, residual semaphore is : " + semaphore.availablePermits());
            
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
            ThreadThree three = new ThreadThree();
            ExecutorService executor = Executors.newFixedThreadPool(5);
            for (int i = 0; i < 30; i++) {
                executor.execute(new Thread(three));
            }
            executor.shutdown();
    }
}
