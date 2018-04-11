package com.example;

/**
 * 指令重排
 * 
 * @author chuanyinhu
 *
 */
public class OperationReordering {
	int x = 0;
	int y = 0;

	public void writer() {
		x = 1;
		y = 2;
	}

	public void reader() {
		int r1 = y;
		int r2 = x;
		System.out.println("x = " + r2 + "\t y = " + r1);
	}

	public static void main(String[] args) {
		final OperationReordering or = new OperationReordering();
		for (int i = 0; i < 4000; i++) {
			Thread th1 = new Thread(new Runnable() {
				public void run() {
					or.writer();
				}
			});
			Thread th2 = new Thread(new Runnable() {
				public void run() {
					or.reader();
				}
			});
			th2.start();
			th1.start();
		}
	}
}
