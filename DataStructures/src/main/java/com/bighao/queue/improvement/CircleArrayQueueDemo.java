package com.bighao.queue.improvement;

import com.bighao.entity.UserEntity;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/14 14:28
 * @Version 1.0
 *
 * 数组实现环形队列 加泛型
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        // 测试
        UserEntity user1 = new UserEntity(1, "z3");
        UserEntity user2 = new UserEntity(2, "l4");
        UserEntity user3 = new UserEntity(3, "w5");
        UserEntity user4 = new UserEntity(4, "z6");
        UserEntity user5 = new UserEntity(5, "b7");


        CircleArray<UserEntity> circleArray = new CircleArray<>(3);
        // 入队列
        circleArray.addQueue(user1);
        circleArray.addQueue(user2);
        circleArray.addQueue(user3);
        circleArray.addQueue(user4);

        System.out.println("========打印当前队列的所有元素");
        circleArray.showQueue();

        System.out.println("==========出队列");
        circleArray.getQueue();
        System.out.println("==========" + user5 + " 入队列");
        circleArray.addQueue(user5);


        System.out.println("========打印当前队列的所有元素");
        circleArray.showQueue();
    }
}

/** 数组实现环形队列 加泛型*/
class CircleArray<E> {
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
    private Object[] arr;

    public CircleArray() {
        this(20);
    }

    /** 创建队列的构造器 */
    public CircleArray(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new Object[maxSize];
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
    public void addQueue(E n) {
        if (isFull()) {
            System.out.println("队列已满，无法加入数据...");
            return;
        }
        // 直接将数据加入
        arr[rear] = n;
        // 将rear后移，这里必须考虑取模，假如已经到最后一个结果就为0，形成环形
        rear = (rear + 1) % maxSize;
    }

    /** 获取队列的数据，出队列 */
    public E getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，不能取数据");
        }
        // 这里需要分析出front是指向队列的第一个元素
        // 1.先把front对应的值保存到一个临时的变量
        // 2.将front后移,防止数据越界，考虑取模。满就回到原点
        // 3.将临时保存的变量返回
        E value = (E) arr[front];
        front = (front + 1) % maxSize;
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
            System.out.printf("arr[%d]=%s\n", i % maxSize, (E)arr[i % maxSize]);
        }
    }

    /** 求出当前队列有效数据的个数 */
    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    /** 显示队列的头部，注意不是取出数据 */
    public E headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列是空的，没有数据..");
        }
        // 这里front就不需要再+1了，因为这里的front本身就指向了头元素
        return (E)arr[front];
    }

}

