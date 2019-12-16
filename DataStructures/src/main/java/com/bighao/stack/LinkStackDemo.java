package com.bighao.stack;


import java.util.Scanner;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/16 10:23
 * @Version 1.0
 *
 * 单向链表实现栈
 */
public class LinkStackDemo {
    public static void main(String[] args) {
        // 测试
        LinkStack stack = new LinkStack(4);
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
                    try {
                        stack.list();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "push":
                    System.out.println("请输入一个数字");
                    int value = scanner.nextInt();
                    try {
                        stack.push(value);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "pop":
                    try {
                        Node res = stack.pop();
                        System.out.printf("出栈的数据是 %d\n", res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "size":
                    System.out.println(stack.size());
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

/** 单向链表实现栈 */
class LinkStack {
    // 栈的最大大小
    private int maxSize;
    // 栈顶节点(链表的最后加入的节点)
    private Node data;
    // top表示栈顶，初始化为-1，表示没有数据
    private int top = -1;


    public LinkStack() {
        // 默认初始化栈最大大小
        this(Integer.MAX_VALUE);
    }

    public LinkStack(int maxSize) {
        if(maxSize < 0) {
            throw new RuntimeException("栈初始化大小错误:" + maxSize);
        }
        this.maxSize = maxSize;
    }


    // 判断栈是否满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 判断栈是否为空
    public boolean isEmpty() {
        return top == -1;
    }

    // 获取当前栈元素个数
    public int size() {
        return top == -1 ? 0 : top;
    }

    // 入栈-push
    public void push(int value) {
        // 先判断栈是否已满
        if(isFull()) {
            throw new RuntimeException("栈内存溢出异常");
        }
        data = new Node(value, data);
        top++;
    }

    // 出栈-pop 将栈顶的数据返回
    public Node pop() {
        // 先判断是否为空
        if(isEmpty()) {
            throw new RuntimeException("空栈异常");
        }
        // 获取栈顶节点元素
        Node value = data;
        // 将顶节点元素下移
        data = data.next;
        // 将原来的栈顶节点元素从链表中移除
        value.next = null;
        top--;
        return value;
    }

    // 显示栈的情况 - 遍历栈, 遍历时，需要从栈顶开始显示数据
    public void list() {
        // 先判断是否为空
        if(isEmpty()) {
            throw new RuntimeException("空栈异常");
        }

        // 由于不能改变数据结构，需要一个临时变量
        Node temp = data;
        while (true) {
            System.out.println(temp);
            if (temp.next == null) {
                // 到达栈底
                break;
            }
            // 下移栈
            temp = temp.next;
        }
    }

}

class Node {
    int no;
    Node next;

    // 构造器 新加入节点的next是上一个加入的节点，实现了先入后出
    public Node(int no, Node next) {
        this.no = no;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node{" +
                "no=" + no +'}';
    }
}


