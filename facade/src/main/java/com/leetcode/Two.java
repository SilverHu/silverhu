package com.leetcode;

/**
 * 两数相加
 * 
 * @author SilverHu
 *
 */
public class Two {

	/**
	 * 给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。 你可以假设除了数字 0
	 * 之外，这两个数字都不会以零开头。
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode first = l1;
		while (l1 != null || l2 != null) {
			l1.val = l1.val + l2.val;
			if (l1.next == null && l2.next != null) {
				l1.next = new ListNode(0);
			}
			if (l1.next != null && l2.next == null) {
				l2.next = new ListNode(0);
			}
			l1 = l1.next;
			l2 = l2.next;
		}
		l1 = first;
		while (l1 != null) {
			if (l1.val >= 10) {
				if (l1.next == null) {
					l1.next = new ListNode(0);
				}
				l1.next.val += (int) (l1.val / 10);
				l1.val = l1.val % 10;
			}
			l1 = l1.next;
		}
		return first;
	}

	public static void main(String[] args) {
		ListNode l1 = new ListNode(0);
		ListNode l2 = new ListNode(7);
		l2.next = new ListNode(3);
		ListNode.output(addTwoNumbers(l1, l2));
	}

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
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
							newNode.next = temp.next;
							temp.next = newNode;
						}
					}
				}
			}
			return head;
		}

		public static ListNode insertHead(ListNode head, ListNode newNode) {
			if (newNode == null) {
				return head;
			}
			newNode.next = head;
			return newNode;
		}

		public static void output(ListNode head) {
			ListNode temp = head;
			while (temp != null) {
				System.out.print(temp.val + " ");
				temp = temp.next;
			}
			System.out.println();
		}
	}

}
