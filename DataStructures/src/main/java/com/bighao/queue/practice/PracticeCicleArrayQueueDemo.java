package com.bighao.queue.practice;

import java.util.Scanner;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/15 1:57
 * @Version 1.0
 */
public class PracticeCicleArrayQueueDemo {
    public static void main(String[] args) {
        System.out.println("测试数组模拟环形队列的案例");

        // 创建一个队列
        CircleArrayPractice queue = new CircleArrayPractice(4); //这里设置了4，其队列的有效数据最大是3
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
            key = scanner.next().charAt(0);
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

/** 练习 数组实现环形队列 */
class CircleArrayPractice {
    private int maxSize;
    private int front;
    private int rear;
    private int[] arr;

    public CircleArrayPractice(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    /** 判断队列是否满 */
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        arr[rear] = n;
        // 形成环形 当rear+1后等于maxSize时，取模就为0
        /*if(++rear == maxSize) {
            rear = 0;
        }*/
        rear = ++rear % maxSize;
    }

    /** 获取队列的数据，出队列 */
    public int getQueue() {
        if(isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }
        int value = arr[front];
        /*if(++front == maxSize) {
            front = 0;
        }*/
        front = ++front % maxSize;
        return value;
    }

    /** 显示队列的所有数据 */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据..");
            return;
        }
        // 从front开始向后遍历size()个数据
        for (int i = front; i < front + size(); i++) {
            // 由于是环形的 所以front可能会比rear大，因此这里的下标是i % maxSize 这样下标才能回到原点形成环形
            // 当i < maxSize  i % maxSize = i, 当i > maxSize 就取模 形成环形
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    /** 求出当前队列有效数据的个数 */
    public int size() {
        // 举例:rear=2 fron=1 maxSize=3
        return (rear + maxSize - front) % maxSize;
    }

    /** 显示队列的头部，注意不是取出数据 */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列是空的，没有数据..");
        }
        return arr[front];
    }
}
