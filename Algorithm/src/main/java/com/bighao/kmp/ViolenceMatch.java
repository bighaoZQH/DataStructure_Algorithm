package com.bighao.kmp;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/2 9:33
 * @Version 1.0
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        // 测试暴力匹配
        String str1 = "杨超越 裴秀智 秀智aaaa 秦岚 李纯....aaaa";
        String str2 = "秀智a";
        int i = violenceMatch(str1, str2);
        System.out.println(i);
    }


    /** 暴力匹配算法 返回匹配到的起始下标*/
    public static int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int s1Len = s1.length;
        int s2Len = s2.length;

        if (s2Len > s1Len) {
            return -1;
        }

        int i = 0;
        int j = 0;
        while (i < s1Len && j < s2Len) {
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                i = i - j + 1; //回溯 i-j即回到str1与str2匹配到的第一个字符 +1就是从后一个字符开始回溯
                j = 0;
            }
        }
        // 判断是否匹配成功
        if (j == s2Len) {
            return i - j;
        } else {
            return -1;
        }
    }
}
