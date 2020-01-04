package com.bighao.kmp;

import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/2 14:51
 * @Version 1.0
 *
 * KMP算法
 * https://www.bilibili.com/video/av11866460?from=search&seid=17663103278152628062
 * https://www.bilibili.com/video/av16828557/?spm_id_from=333.788.videocard.0
 * 灯哥讲的比较好
 * 但KMP算法我是看了知乎(如何更好的理解和掌握KMP算法这个问题下的回答)才理解的
 */
public class KMPAlgorithm {
    public static void main(String[] args) {
        String str1 = "BBC ABCDAB ABCDABCDABDE";
        String str2 = "ABCDABD";

        int[] next = kmpNext(str2);
        System.out.println(Arrays.toString(next));

        int i = kmpSearch(str1, str2, next);
        System.out.println(i);
    }

    /** kmp搜索算法 */
    public static int kmpSearch(String str1, String str2, int[] next) {
        for (int i = 0, j = 0; i < str1.length(); i++) {
            while (j > 0 && str1.charAt(i) != str2.charAt(j)) {
                j = next[j - 1];
            }
            if (str1.charAt(i) == str2.charAt(j)) {
                j++;
            }
            if (j == str2.length()) {
                // 找到后，返回起始下标，这里要+1是因为i的迭代条件比j少了一次
                return i - j + 1;
            }
        }
        return -1;
    }


    /** 获取到一个字符串部分匹配值表 */
    public static int[] kmpNext(String dest) {
        // 创建next数组，保存部分匹配值
        int[] next = new int[dest.length()];
        next[0] = 0; //如果字符串的长度为1，则部分匹配值就是0
        int maxLen = 0;
        for (int i = 1; i < dest.length(); i++) {
            char i1 = dest.charAt(i);
            char j2 = dest.charAt(maxLen);
            // 当dest.charAt(i) != dest.charAt(j)时，我们需要从next[j-1]获取新的j，直到dest.charAt(i) == dest.charAt(j)时才退出
            while (maxLen > 0 && dest.charAt(i) != dest.charAt(maxLen)) {
                maxLen = next[maxLen - 1];
            }
            // 当dest.charAt(i) == dest.charAt(j)时，部分匹配值的长度就+1
            if (dest.charAt(i) == dest.charAt(maxLen)) {
                maxLen++;
            }
            next[i] = maxLen;
        }
        return next;
    }

}
