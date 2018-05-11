package com.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者模式
 * 
 * @author SilverHu
 *
 */
public class ThreadSix {
	/** 产品数量，总数量50 */
	public static volatile int productnum;

	public static void main(String[] args) {
		ThreadSix six = new ThreadSix();

		// 同步锁，生产者等待信号，消费者等待信号
		ReentrantLock lock = new ReentrantLock(false);
		Condition producerCondition = lock.newCondition();
		Condition consumerCondition = lock.newCondition();
		Resource Resource = six.new Resource(lock, producerCondition, consumerCondition, 20);
		Runnable producer = new Runnable() {
			public void run() {
				while (true) {
					Resource.producer();
				}
			}
		};
		Runnable consumer = new Runnable() {
			public void run() {
				while (true) {
					Resource.consumer();
				}
			}
		};
		new Thread(producer).start();
		new Thread(consumer).start();
	}

	/**
	 * 资源
	 * 
	 * @author SilverHu
	 *
	 */
	class Resource {

		private ReentrantLock lock;// 同步锁
		Condition producerCondition;// 生产者等待信号
		Condition consumerCondition;// 消费者等待信号
		private int productnum;// 产品数量
		private int totalnum; // 总数量

		public Resource(ReentrantLock lock, Condition producerCondition, Condition consumerCondition, int totalnum) {
			super();
			this.lock = lock;
			this.producerCondition = producerCondition;
			this.consumerCondition = consumerCondition;
			this.totalnum = totalnum;
		}

		/**
		 * 生产产品
		 */
		public void producer() {
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " get lock");
				if (productnum < totalnum) {
					productnum++;
					System.out.println(Thread.currentThread().getName() + " producer create a product, total num is : "
							+ productnum);
					consumerCondition.signalAll();
				} else {
					System.out
							.println(Thread.currentThread().getName() + " producer wait, total num is : " + productnum);
					producerCondition.await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				System.out.println(Thread.currentThread().getName() + " release lock");
			}
		}

		public void consumer() {
			try {
				lock.lock();
				System.out.println(Thread.currentThread().getName() + " get lock");
				if (productnum > 0) {
					productnum--;
					System.out.println(
							Thread.currentThread().getName() + " consumer get a product, total num is : " + productnum);
					producerCondition.signalAll();
				} else {
					System.out
							.println(Thread.currentThread().getName() + " consumer wait, total num is : " + productnum);
					consumerCondition.await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
				System.out.println(Thread.currentThread().getName() + " release lock");
			}
		}
	}

}
