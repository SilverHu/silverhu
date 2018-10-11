package com.leetcode;

import com.alibaba.fastjson.JSON;

/**
 * 两数之和
 * 
 * @author SilverHu
 */
public class One {

    /**
     * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。
     * 
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] resp = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    resp[0] = i;
                    resp[1] = j;
                }
            }
        }
        return resp;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 7, 3, 4, 5 };
        One twosum = new One();
        System.out.println(JSON.toJSONString(twosum.twoSum(nums, 7)));
    }
}
