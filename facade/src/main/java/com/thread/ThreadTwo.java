package com.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTwo {

    public static void main(String[] args) {
        // 线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ThreadTwo threadTwo = new ThreadTwo();
        Target target = threadTwo.new Target();
        Increase increase = threadTwo.new Increase(target);
        Decrease decrease = threadTwo.new Decrease(target);
        executorService.execute(new Thread(increase, "increase"));
        executorService.execute(new Thread(decrease, "decrease"));
        executorService.shutdown();
    }

    class Decrease implements Runnable {

        private Target target;

        public Decrease(Target target) {
            super();
            this.target = target;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                target.decrease();
            }
        }

    }

    class Increase implements Runnable {

        private Target target;

        public Increase(Target target) {
            super();
            this.target = target;
        }

        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                target.increase();
            }
        }
    }

    class Target {
        private int i = 0;

        public synchronized void increase() {
            if (i == 3) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i++;
            System.out.println(Thread.currentThread().getName() + " (increase) : " + i);

            // 唤醒decrease
            notifyAll();
        }

        public synchronized void testWait() {
            try {
                System.out.println(Thread.currentThread().getName() + " (wait) : " + i);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void decrease() {
            if (i == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            i--;
            System.out.println(Thread.currentThread().getName() + " (decrease) : " + i);

            // 唤醒increase
            notify();
        }
    }
}
