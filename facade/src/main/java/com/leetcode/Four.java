package com.leetcode;

import java.util.Arrays;

/**
 * 两个排序数组的中位数
 * 
 * @author SilverHu
 *
 */
public class Four {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] total = new int[nums1.length + nums2.length];
        for (int i = 0; i < nums1.length; i++) {
            total[i] = nums1[i];
        }
        for (int i = 0; i < nums2.length; i++) {
            total[nums1.length + i] = nums2[i];
        }
        Arrays.sort(total);
        if (total.length % 2 == 1) {
            return total[(total.length - 1) / 2];
        } else {
            double double1 = total[total.length / 2];
            double double2 = total[total.length / 2 - 1];
            return ((double1 + double2) / 2);
        }
    }

    public static void main(String[] args) {
        Four four = new Four();
        System.out.println(four.findMedianSortedArrays(new int[] { 1,3 }, new int[] {2}));
    }
}
