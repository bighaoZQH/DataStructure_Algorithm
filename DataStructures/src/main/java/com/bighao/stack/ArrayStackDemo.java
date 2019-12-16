package com.bighao.stack;

import java.util.Scanner;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/16 9:23
 * @Version 1.0
 *
 * 用数组模拟栈
 *
 * 1.栈的一个实际需求
 * 计算式:[7*2*2-5+1-5+3-3]
 * 请问: 计算机底层是如何运算得到结果的？
 * 注意不是简单的把算式列出运算,因为我们看这个算式 7 * 2 * 2 - 5,
 * 但是计算机怎么理解这个算式的(对计算机而言，它接收到的就是一个字符串)，我们讨论的是这个问题。-> 栈
 *
 * 2.栈的介绍
 *  1).栈的英文为(stack)
 *  2).栈是一个【先入后出】(FILO-First In Last Out)的有序列表。
 *  3).栈(stack)是【限制线性表】中元素的插入和删除【只能在线性表的同一端】进行的一种特殊线性表。
 *      允许插入和删除的一端，为变化的一端，称为栈顶(Top)，另一端为固定的一端，称为栈底(Bottom)。
 *  4).根据栈的定义可知，最先放入栈中元素在栈底，最后放入的元素在栈顶，
 *      而删除元素刚好相反，最后放入的元素最先删除，最先放入的元素最后删除
 *
 *
 * 3.栈的应用场景
 *  1).子程序的调用：在跳往子程序前，会先将下个指令的地址存到堆栈中，直到子程序执行完后再将地址取出，以回到原来的程序中。
 *  2).处理递归调用：和子程序的调用类似，只是除了储存下一个指令的地址外，也将参数、区域变量等数据存入堆栈中。
 *  3).表达式的转换[中缀表达式转后缀表达式]与求值(实际解决)。
 *  4).二叉树的遍历。
 *  5).图形的深度优先(depth一first)搜索法。
 *
 */
public class ArrayStackDemo {
    public static void main(String[] args) {
        // 测试
        ArrayStack stack = new ArrayStack(4);
        String key = "";
        // 控制是否退出菜单
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);

        while (loop) {
            System.out.println("show: 表示显示栈");
            System.out.println("exit: 退出程序");
            System.out.println("push: 表示添加数据到栈");
            System.out.println("pop: 表示从栈取出数据(出栈)");
            System.out.println("size: 表示获取栈");
            System.out.println("请输入你的选则");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "push":
                    System.out.println("请输入一个数字");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.printf("出栈的数据是 %d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出...");
    }
}


/** 定义一个ArrayStack 表示栈结构 */
class ArrayStack {
    // 栈的最大大小
    private int maxSize;
    // 数组模拟,数据就存在该数组中
    private int[] stack;
    // top表示栈顶，初始化为-1，表示没有数据
    private int top = -1;


    public ArrayStack() {
        // 默认初始化栈最大大小10
        this(10);
    }

    // 构造器
    public ArrayStack(int maxSize) {
        if(maxSize < 0) {
            throw new RuntimeException("栈初始化大小错误:" + maxSize);
        }
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    // 栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 栈空
    public boolean isEmpty() {
        return top == -1;
    }

    // 入栈-push
    public void push(int value) {
        // 先判断栈是否满
        if(isFull()) {
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    // 出栈-pop 将栈顶的数据返回
    public int pop() {
        // 先判断栈是否空
        if(isEmpty()) {
            throw new RuntimeException("栈空，没有数据");
        }
        int value = stack[top];
        top--;
        return value;
    }

    // 获取当前栈有多少个元素
    public int size() {
        return top == -1 ? 0 : top;
    }

    // 显示栈的情况 - 遍历栈, 遍历时，需要从栈顶开始显示数据
    public void list() {
        if(isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }
        // 从栈顶开始循环
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }


}
