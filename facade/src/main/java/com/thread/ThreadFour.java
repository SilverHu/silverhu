package com.thread;

/**
 * 当一个线程执行了对象的wait()方法后，线程会释放对象，进入对象的等待池中
 * @author SilverHu
 *
 */
public class ThreadFour {

	public static void main(String[] args) {
		Object obj = new Object();
		ThreadFour four = new ThreadFour();
		ThreadA a = four.new ThreadA(obj);
		ThreadB b = four.new ThreadB(obj);
		new Thread(a).start();
		new Thread(b).start();
	}

	class ThreadA implements Runnable {
		private Object obj;

		public ThreadA(Object obj) {
			super();
			this.obj = obj;
		}

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println(Thread.currentThread().getName() + " wait……");
				try {
					obj.wait();
					System.out.println(Thread.currentThread().getName() + " notify……");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class ThreadB implements Runnable {
		private Object obj;

		public ThreadB(Object obj) {
			super();
			this.obj = obj;
		}

		@Override
		public void run() {
			synchronized (obj) {
				System.out.println(Thread.currentThread().getName() + " executor notify command");
				//obj.notify();
			}
		}
	}

}
