package com.bighao.linkedlist.practice;



/**
 * @Author: bighao周启豪
 * @Date: 2019/12/15 20:15
 * @Version 1.0
 *
 * 练习单项环形链表
 */
public class PracticeJosepfu {
    public static void main(String[] args) {
        // 测试构建环形链表和遍历
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        // 加入5个节点
        circleSingleLinkedList.addNode(25);
        circleSingleLinkedList.showNodes();
    }
}

/** 创建一个单向环形链表 */
class CircleSingleLinkedList {
    // 创建一个first节点，当前没有编号
    private Node first = null;

    /** 加入Boy节点，构建成一个环形链表 */
    public void addNode(int nums) {
        // check nums
        if (nums < 1) {
            throw new RuntimeException("nums的值不正确");
        }

        // 创建临时指针进行迭代
        Node curNode = null;
        for (int i = 0; i <= nums; i++) {
            // 创建节点
            Node node = new Node(i);
            if (i == 1) {
                // 将第一个节点指向该新添加的节点
                first = node;
                // 形成环形
                first.setNext(node);
                // 将迭代指针指向first
                curNode = first;
            }
            // 将新添加的节点和链表链接起来
            curNode.setNext(node);
            // 将新添加的节点和第一个节点链接起来构成环形
            node.setNext(first);
            // 将curNode指向新加入的节点
            curNode = node;
        }
    }

    /** 遍历当前环形列表 */
    public void showNodes() {
       if (first == null) {
           System.out.println("链表为空...");
           return;
       }

       // 创建临时迭代变量进行遍历
       Node curNode = first;
       while (true) {
           System.out.println(curNode);
           if (curNode.getNext() == first) {
               break;
           }
           // 后移节点
           curNode = curNode.getNext();
       }

    }
}


/** 先创建一个Node类，表示一个节点 */
class Node {
    // 编号
    private int no;
    // 指向下一个节点，默认null
    private Node next;

    public Node(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}


