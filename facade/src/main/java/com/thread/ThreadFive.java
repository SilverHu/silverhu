package com.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadFive {

	private ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		ThreadFive five = new ThreadFive();
		new Thread(new Runnable() {
			public void run() {
				five.functionB();
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				five.functionB();
			}
		}).start();
	}

	public void functionA() {
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " get ReentrantLock : " + lock.getHoldCount());
		} catch (Exception e) {
		} finally {
			System.out.println(Thread.currentThread().getName() + " release ReentrantLock : " + lock.getHoldCount());
			lock.unlock();
		}
	}

	public void functionB() {
		if (lock.tryLock()) {
			try {
				System.out.println(Thread.currentThread().getName() + " get ReentrantLock : " + lock.getHoldCount());
			} catch (Exception e) {
			} finally {
				System.out.println(Thread.currentThread().getName() + " release ReentrantLock : " + lock.getHoldCount());
				lock.unlock();
			}
		} else {
			System.out.println("我是" + Thread.currentThread().getName() + "有人占着锁，我就不要啦");
		}

	}

	public void functionC() {
		lock.lock();
		try {
			System.out.println("functionB get ReentrantLock : " + lock.getHoldCount());
		} catch (Exception e) {
		} finally {
			lock.unlock();
		}
	}
}
