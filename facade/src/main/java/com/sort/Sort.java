package com.sort;

import com.alibaba.fastjson.JSON;

/**
 * 排序
 * 
 * @author SilverHu
 *
 */
public class Sort {
    /**
     * 快速排序
     * 
     * @param nums
     */
    public static void quickSort(int[] nums, int start, int end) {
        int key = nums[start];
        int i = start;
        int j = end;

        while (i < j) {
            // j向前循环，将比key小的数放在key左边
            for (j = nums.length - 1; i < j; j--) {
                if (nums[j] < key) {
                    nums[i] = nums[j];
                    nums[j] = key;
                    break;
                }
            }

            // i向后循环，将比key大的数放在key右边
            for (i = 0; i < j; i++) {
                if (nums[i] > key) {
                    nums[j] = nums[i];
                    nums[i] = key;
                    break;
                }
            }
        }
        if (start < i - 1) {
            quickSort(nums, start, i - 1);
        }
        if (end > j + 1) {
            quickSort(nums, j + 1, end);
        }
    }

    /**
     * 冒泡排序
     * 
     * @param nums
     */
    public static void bubbleSort(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = new int[] { 3, 1, 2, 8, 7, 4 };
        //quickSort(nums, 0, nums.length - 1);
        bubbleSort(nums);
        System.out.println(JSON.toJSONString(nums));
    }
}
