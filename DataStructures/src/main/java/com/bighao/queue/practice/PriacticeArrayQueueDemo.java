package com.bighao.queue.practice;

import java.util.Scanner;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/15 1:42
 * @Version 1.0
 */
public class PriacticeArrayQueueDemo {

    public static void main(String[] args) {
        // 测试
        // 创建一个队列
        ArrayQueuePriactice queue = new ArrayQueuePriactice(3);
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

/** 练习 使用数组模拟队列-编写一个ArrayQueue类 */
class ArrayQueuePriactice {
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    public ArrayQueuePriactice(int maxSize) {
        this.maxSize = maxSize;
        front = -1;
        rear = front;
    }

    /** 判断队列是否满 */
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    /** 判断队列是否为空 */
    public boolean isEmpty() {
        return rear == front;
    }

    /** 添加数据到队列 */
    public void addQueue(int n) {
        // 先判断是否已满
        if(isFull()) {
            System.out.println("队列已满，无法加入数据...");
            return;
        }
        // 没满则添加数据到队列
        rear++;
        arr[rear] = n;
    }

    /** 获取队列的数据，出队列 */
    public int getQueue() {
        // 先判断队列是否为空
        if(isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }
        // 不为空就取
        // front先后移 因为front指向的是头部的前一个下标
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
