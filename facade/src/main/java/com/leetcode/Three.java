package com.leetcode;

/**
 * 无重复字符的最长子串
 * 
 * @author SilverHu
 *
 */
public class Three {

    public int lengthOfLongestSubstring(String s) {
        // 记录子串
        String[] subString = new String[s.length()];
        int j = 0;

        // 记录不重复的字符串
        String noRepeat = "";
        for (int i = 0; i < s.length(); i++) {
            if (noRepeat.indexOf(s.charAt(i)) == -1) {
                noRepeat += s.charAt(i);
            } else {
                subString[j] = noRepeat;
                j++;
                noRepeat = noRepeat.substring(noRepeat.indexOf(s.charAt(i)) + 1, noRepeat.length())
                        + String.valueOf(s.charAt(i));
            }
        }

        if (subString.length > j) {
            subString[j] = noRepeat;
        }

        // 获取最长子串
        String maxSubstring = "";
        for (String string : subString) {
            if (string != null && string.length() > maxSubstring.length()) {
                maxSubstring = string;
            }
        }
        return maxSubstring.length();
    }

    public static void main(String[] args) {
        Three three = new Three();
        System.out.println(three.lengthOfLongestSubstring("pwwkew"));
    }
}
