package com.linklist;

/**
 * 链表
 * @author SilverHu
 *
 */
public class ListNode {

	private int data;
	private ListNode next;

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public ListNode getNext() {
		return next;
	}

	public void setNext(ListNode next) {
		this.next = next;
	}

	public static ListNode insertHead(ListNode head, ListNode newNode) {
		if (newNode == null) {
			return head;
		}
		newNode.setNext(head);
		return newNode;
	}

	public static ListNode insertTail(ListNode head, ListNode newNode) {
		if (newNode != null) {
			newNode.setNext(null);
			if (head == null) {
				head = newNode;
			} else {
				ListNode temp = head;
				while (temp.getNext() != null) {
					temp = temp.getNext();
				}
				temp.setNext(newNode);
			}
		}
		return head;
	}

	public static ListNode insertLocal(ListNode head, ListNode newNode, int local) {
		if (newNode != null) {
			if (local == 0) {
				head = insertHead(head, newNode);
			} else {
				int no = 0;
				ListNode temp = head;
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

	public static ListNode delNode(ListNode head, int data) {
		if (head == null) {
			return null;
		}

		if (head.getData() == data) {
			return head.getNext();
		}

		ListNode prev = head;
		ListNode temp = head.getNext();
		while (temp != null && temp.getData() != data) {
			prev = temp;
			temp = temp.getNext();
		}

		if (temp != null) {
			prev.setNext(temp.getNext());
		}
		return head;
	}

	public static void output(ListNode head) {
		ListNode temp = head;
		while (temp != null) {
			System.out.print(temp.getData() + " ");
			temp = temp.getNext();
		}
		System.out.println();
	}
}
