package com.bighao.queue;

import java.util.Scanner;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/14 14:28
 * @Version 1.0
 *
 * 数组实现环形队列
 * execl 366 video p14-15
 * 这里可能会比较绕，需要自己【画图】方便理解
 *
 * 对n(正整数)取模，就表示值只会在0-n之间，不会越界，满了就能回到原点形成环形，即n%n=0
 *
 * 思路如下:
 * 1.  front 变量的含义做一个调整： front 就指向队列的第一个元素, 也就是说 arr[front] 就是队列的第一个元素
 *      front 的初始值 = 0
 * 2.  rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定.
 *      rear 的初始值 = 0 那么有效数据个数就是数组长度-1
 * 3. 当队列满时，条件是  (rear  + 1) % maxSize == front 【满】
 * 4. 对队列为空的条件， rear == front 空
 * 5. 当我们这样分析， 队列中【有效】数据的个数为 (rear + maxSize - front) % maxSize   // rear = 1 front = 0 maxSize=3
 * 6. 我们就可以在原来的队列上修改得到，一个环形队列
 *
 * 为了方便你们理解，说明下要注意预留位也是在环形里面的，也是会移动的
 *
 * 为防止不理解 举例 建议【画图】理解：
 * 我们假设数组长度为maxsize=7
 * 而数组又预留了一个位置,因此当队列【第一次!!】刚好满的时候，
 * 数组里的数据最多有maxsize-1=6个,即最后一个【有效】数据的下标为maxsize-1-1=5，
 * 而rear又指向了最后一个【有效数据】的后一个下标(即预留空间的下标)，因此rear=maxsize-1=6
 * 得出:
 * front=0，rear=6, maxsize=7
 * 1.那么计算有效数据个数:
 * (rear + maxSize - front) % maxSize
 * (6 + 7 - 0) % 7 = 6
 * 2.那么计算队列是否已经满:
 * (rear  + 1) % maxSize == front
 * (6 + 1) % 7 = 0
 * 3.此时取出队列的头元素后，再加一个元素，预留空间的下标就变成了0 (画图理解下)
 *
 * 因为是环形rear就也可能小于front
 *
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        System.out.println("测试数组模拟环形队列的案例");

        // 创建一个队列
        CircleArray queue = new CircleArray(4); //这里设置了4，其队列的有效数据最大是3
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

/** 数组实现环形队列 */
class CircleArray {
    // 表示数组的最大容量
    private int maxSize;
    /**
     * 队列头
     * front 变量的含义做一个调整： front 就指向队列的第一个元素,也就是说 arr[front] 就是队列的第一个元素
     * front 的初始值 = 0
     */
    private int front;
    /**
     * 队列尾
     * rear 变量的含义做一个调整：rear 指向队列的最后一个元素的后一个位置. 因为希望空出一个空间做为约定.
     * rear 的初始值 = 0
     */
    private int rear;
    // 该数组用于存放数据，模拟队列
    private int[] arr;

    /** 创建队列的构造器 */
    public CircleArray(int arrMaxSize) {
        maxSize = arrMaxSize;
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
        if (isFull()) {
            System.out.println("队列已满，无法加入数据...");
            return;
        }
        // 直接将数据加入
        arr[rear] = n;
        // 将rear后移，这里必须考虑取模，假如已经到最后一个结果就为0，形成环形
        /*if(++rear == maxSize) {
            rear = 0;
        }*/
        rear = ++rear % maxSize;
    }

    /** 获取队列的数据，出队列 */
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }
        // 这里需要分析出front是指向队列的第一个元素
        // 1.先把front对应的值保存到一个临时的变量
        // 2.将front后移,防止数据越界，考虑取模。满就回到原点
        // 3.将临时保存的变量返回
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
        // 思路：从front开始遍历，遍历多少个元素? 从front开始向后遍历size()个数据
        // 动脑筋
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
        // 这里front就不需要再+1了，因为这里的front本身就指向了头元素
        return arr[front];
    }

}

