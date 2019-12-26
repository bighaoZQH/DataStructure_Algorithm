package com.bighao.queue;

import java.util.Scanner;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/14 12:08
 * @Version 1.0
 *
 * 数组实现单向队列
 *
 * 队列介绍 video p11-13
 * 队列是一个【有序】列表，可以用【数组】或是【链表】来实现。
 * 遵循【先入先出】的原则。即：先存入队列的数据，要先取出。后存入的要后取出
 * 示意图：(使用数组模拟队列示意图) 见ppt p24
 *
 * 问题与优化：
 *      1.目前数组数组使用一次就不能使用，没有达到复用的效果
 *      2.将这个数组始用算法，改进成一个环形的队列 取模：%
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        // 测试
        // 创建一个队列
        ArrayQueue queue = new ArrayQueue(3);
        char key = ' '; //接受用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        // 输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("e(exir): 退出程序");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            key = scanner.next().charAt(0); //接受一个字符
            switch (key) {
                case 's':
                    queue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数字");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;
                //取出数据
                case 'g':
                    try {
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                //查看队列头的数据
                case 'h':
                    try {
                        int res = queue.headQueue();
                        System.out.printf("队列头的数据是%d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出");
    }

}








/** 使用数组模拟队列-编写一个ArrayQueue类 */
class ArrayQueue {
    // 表示数组的最大容量
    private int maxSize;
    // 队列头
    private int front;
    // 队列尾
    private int rear;
    // 该数组用于存放数据，模拟队列
    private int[] arr;

    /** 创建队列的构造器 */
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        //指向队列头部，分析出front是指向队列头的前一个位置(意思就是等于0的话表示数组已经有数据了)
        front = -1;
        //指向队列尾，指向队列尾的数据(即就是队列最后一个数据)，初始化的时候数组为空 因此头尾是一样的
        //rear = -1;
        rear = front;
    }

    /** 判断队列是否满 */
    public boolean isFull() {
        return rear == maxSize -1;
    }

    /** 判断队列是否为空 */
    public boolean isEmpty() {
        return rear == front;
    }

    /** 添加数据到队列 */
    public void addQueue(int n) {
        // 先判断队列是否已满
        if (isFull()) {
            System.out.println("队列已满，无法加入数据...");
            return;
        }

        // 没满则添加数据到队列
        //arr[++rear] = n;
        //rear后移
        rear++;
        arr[rear] = n;
    }

    /** 获取队列的数据，出队列 */
    public int getQueue() {
        // 判断队列是否为空
        if (isEmpty()) {
            // 通过抛出异常来处理 顺便帮你返回
            throw new RuntimeException("队列为空，不能取数据");
        }
        // front先后移 因为front指向的是头部的前一个下标
        //return arr[++front];
        front++;
        return arr[front];
    }

    /** 显示队列的所有数据 */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据..");
            return;
        }
        // 遍历
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    /** 显示队列的头部，注意不是取出数据 */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列是空的，没有数据..");
        }
        return arr[front + 1];
    }

}
