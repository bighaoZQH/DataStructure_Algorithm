package com.bighao.linkedlist;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/15 18:46
 * @Version 1.0
 *
 * 单向环形链表
 * execl 616 ppt 35-38
 *
 * Josephu(约瑟夫、约瑟夫环)  问题
 * 设编号为1，2，… n的n个人围坐一圈，约定编号为k（1<=k<=n）的人从1开始报数，
 * 数到m 的那个人出列，它的下一位又从1开始报数，数到m的那个人又出列，
 * 依次类推，直到所有人出列为止，由此产生一个出队编号的序列。
 *
 * 提示：用一个不带头结点的循环链表来处理Josephu 问题：
 * 先构成一个有n个结点的单循环链表，然后由k结点起从1开始计数，计到m时，对应结点从链表中删除，
 * 然后再从被删除结点的下一个结点又从1开始计数，直到最后一个结点从链表中删除算法结束。
 *
 *
 * 1.构建一个单向的环形链表思路
 *  1). 先创建第一个first节点，并形成环形
 *  2). 创建一个临时迭代变量(指针)指向第一个节点,并用来进行迭代
 *  3). 后面当我们每创建一个新的节点，就将迭代变量的next指向新添加的节点,再把新节点的next指向first节点
 *  4). 最后将迭代变量指向新加入的节点，进行后续的迭代
 *
 *
 * 2.遍历环形链表
 *  1). 先让一个辅助指针(变量) curBoy，指向first节点
 *  2). 然后通过一个while循环遍历 该环形链表即可 curBoy.next  == first 结束
 *
 *
 * 3.出圈：
 *  根据用户的输入，生成一个小孩出圈的顺序
 *  n = 5 , 即有5个人
 *  k = 1, 从第一个人开始报数
 *  m = 2, 数2下
 *
 *  1). 需求创建一个辅助指针(变量) helper , 事先应该指向环形链表的最后这个节点.
 *  2). 小孩报数前，先让 first 和  helper 移动 k - 1次,就是将first移动到开始报数的那个小孩,helper移动到前一个
 *  3). 当小孩报数时，让first 和 helper 指针同时 的移动  m  - 1 次,之所以要减一，是因为当前小孩自己也要数一下
 *  4). 这时就可以将first 指向的小孩节点 出圈
 *      first = first .next
 *      helper.next = first
 *      原来first 指向的节点就没有任何引用，就会被回收
 *
 *  出圈的顺序
 *  2->4->1->5->3
 *
 */
public class Josepfu {
    public static void main(String[] args) {
        // 测试构建环形链表和遍历
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        // 加入5个节点
        circleSingleLinkedList.addNode(5);
        circleSingleLinkedList.showNodes();

        // 测试出圈 2->4->1->5->3
        circleSingleLinkedList.countNode(1, 2, 5);
    }
}

/** 创建一个单向环形链表 */
class CircleSingleLinkedList {
    // 创建一个first节点，当前没有编号
    private Node first = null;

    /** 加入Boy节点，构建成一个环形链表 */
    public void addNode(int nums) {
        // check nums...
        if (nums < 1) {
            System.out.println("nums的值不正确");
            return;
        }

        // 创建辅助变量(指针)进行迭代，帮助构建环形链表
        Node curNode = null;
        // 使用for循环来创建环形链表
        for (int i = 1; i <= nums; i++) {
            // 根据编号，创建节点
            Node node = new Node(i);
            // 如果是第一个节点
            if (i == 1) {
                first = node;
                // 构成环形
                first.setNext(first);
                // 让辅助变量(迭代指针)指向第一个节点,因为第一个节点不能动
                curNode = first;
            } else {
                // 将新添加的节点和链表链接起来
                curNode.setNext(node);
                // 将新添加的节点和第一个节点链接起来构成环形
                node.setNext(first);
                // 将curNode指向新加入的节点
                curNode = node;
            }
        }
    }

    /** 遍历当前环形列表 */
    public void showNodes() {
        // 判断链表是否为空
        if(first == null) {
            System.out.println("链表为空...");
            return;
        }
        // 因为first不能动，因此我们仍然创建临时迭代变量进行遍历
        Node curNode = first;
        while (true) {
            System.out.printf("节点编号 %d \n", curNode.getNo());
            // 当curNode的下一个节点等于first是说明已经遍历完成
            if (curNode.getNext() == first) {
                break;
            }
            // 后移节点
            curNode = curNode.getNext();
        }
    }


    /**
     * 根据用户的输入，计算节点出圈的顺序
     * @param startNo 从第几个节点开始数
     * @param countNum 数几下
     * @param nums 最初有多少节点形成环形
     */
    public void countNode(int startNo, int countNum, int nums) {
        // 先对数据进行校验
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("参数输入有误..");
            return;
        }
        // 创建一个辅助变量(指针)
        Node helper = first;
        // 将辅助指针指向环形链表的最后一个节点
        while (true) {
            if (helper.getNext() == first) {
                // 此时说明helper指向了最后一个节点
                break;
            }
            helper = helper.getNext();
        }
        // 遍历之前，先让 first 和  helper 移动 startNo - 1次,因为是从startNo开始报数
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            helper = helper.getNext();
        }
        // 当小孩报数时，让first和helper指针同时移动 countNum-1 次,之所以要减一，是因为当前节点自己也要数一下
        // 这里是一个循环的操作，直到圈中只有一个节点
        while (true) {
            if (helper == first) { //说明圈中只有一个节点
                break;
            }
            // 让first 和 helper 指针同时 的移动 countNum-1 次
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                helper = helper.getNext();
            }
            // 这时first指向的节点，就是要出圈的节点
            System.out.printf("%d出圈\n", first.getNo());
            // 这时将first指向的节点出圈
            first = first.getNext();
            helper.setNext(first);
        }
        System.out.printf("最后留在圈中的节点编号%d \n", first.getNo());
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

