package com.bighao.recursion;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/19 17:30
 * @Version 1.0
 *
 * 递归
 * 递归调用规则：
 * 1. 当程序执行到一个方法时，就会开辟一个独立的空间(栈)
 * 2. 每个空间的数据(局部变量)，是独立的.
 * 3. 引用类型是共享的
 *
 * 递归用于解决什么样的问题
 *  各种数学问题如: 8皇后问题 , 汉诺塔, 阶乘问题, 迷宫问题, 球和篮子的问题(google编程大赛)
 *  各种算法中也会使用到递归，比如快排，归并排序，二分查找，分治算法等.
 *  将用栈解决的问题-->第归代码比较简洁
 *
 * 递归需要遵守的重要规则
 *  1).执行一个方法时，就创建一个新的受保护的独立空间(栈空间)
 *  2).方法的局部变量是独立的，不会相互影响, 比如n变量
 *  3).如果方法中使用的是引用类型变量(比如数组)，就会共享该引用类型的数据.
 *  4).递归必须向退出递归的条件逼近，否则就是无限递归,出现StackOverflowError，死龟了:)
 *  5).当一个方法执行完毕，或者遇到return，就会返回，遵守谁调用，就将结果返回给谁，同时当方法执行完毕或者返回时，该方法也就执行完毕。
 */
public class RecursionTest {
    public static void main(String[] args) {
        // 通过打印问题回顾递归调用
        //test(4); // 结果是 n=2 n=3 n=4 由于是递归调用，方法在栈中，先进的后出，因此2最先打印

        int res = factorial(5);
        System.out.println("res=" + res);
    }


    /** 打印问题 */
    public static void test(int n) {
        if (n > 2) {
            test(n - 1);
        } //else {
            System.out.println("n=" + n);
        //}
    }

    /** 阶乘问题 */
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;  // n=5 >> 1 * 2 * 3 * 4 * 5 = 120
        }
    }


}
