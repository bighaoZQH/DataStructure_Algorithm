package com.bighao.recursion;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/19 17:30
 * @Version 1.0
 *
 * 递归
 */
public class RecursionTest {
    public static void main(String[] args) {
        test(4); // 结果是 n=2 n=3 n=4 由于是递归调用，方法在栈中，先进的后出，因此2最先打印
    }
    // 打印问题
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        } //else {
            System.out.println("n=" + n);
        //}
    }

    // 阶乘问题
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }
}
