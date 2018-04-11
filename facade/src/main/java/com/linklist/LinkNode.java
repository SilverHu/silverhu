package com.linklist;

import java.util.Random;
import java.util.Scanner;

public class LinkNode {

	private int data;
	private LinkNode next;

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public LinkNode getNext() {
		return next;
	}

	public void setNext(LinkNode next) {
		this.next = next;
	}

	public static void main(String[] args) {
		LinkNode head = null;
		for (int i = 0; i < 10; i++) {
			LinkNode newNode = new LinkNode();
			Random random = new Random();
			newNode.setData(random.nextInt(10));
			head = insertHead(head, newNode);
			// head = insertTail(head, newNode);
			// head = insertLocal(head, newNode, 0);

			output(head);
		}

		Scanner scan = new Scanner(System.in);
		System.out.print("pls input del data (-1 is end) : ");
		int data = scan.nextInt();
		while (data != -1) {
			head = delNode(head, data);
			output(head);
			System.out.print("pls input del data (-1 is end) : ");
			data = scan.nextInt();
		}
	}

	public static LinkNode insertHead(LinkNode head, LinkNode newNode) {
		if (newNode == null) {
			return head;
		}
		newNode.setNext(head);
		return newNode;
	}

	public static LinkNode insertTail(LinkNode head, LinkNode newNode) {
		if (newNode != null) {
			newNode.setNext(null);
			if (head == null) {
				head = newNode;
			} else {
				LinkNode temp = head;
				while (temp.getNext() != null) {
					temp = temp.getNext();
				}
				temp.setNext(newNode);
			}
		}
		return head;
	}

	public static LinkNode insertLocal(LinkNode head, LinkNode newNode, int local) {
		if (newNode != null) {
			if (local == 0) {
				head = insertHead(head, newNode);
			} else {
				int no = 0;
				LinkNode temp = head;
				while (temp != null) {
					no++;
					if (no == local) {
						newNode.setNext(temp.getNext());
						temp.setNext(newNode);
					}
				}
			}
		}
		return head;
	}

	public static LinkNode delNode(LinkNode head, int data) {
		if (head == null) {
			return null;
		}

		if (head.getData() == data) {
			return head.getNext();
		}

		LinkNode prev = head;
		LinkNode temp = head.getNext();
		while (temp != null && temp.getData() != data) {
			prev = temp;
			temp = temp.getNext();
		}

		if (temp != null) {
			prev.setNext(temp.getNext());
		}
		return head;
	}

	public static void output(LinkNode head) {
		LinkNode temp = head;
		while (temp != null) {
			System.out.print(temp.getData() + " ");
			temp = temp.getNext();
		}
		System.out.println();
	}
}
