package com.bighao.linkedlist.improvement;



import java.util.HashMap;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/14 16:51
 * @Version 1.0
 *
 * 手写实现单向链表 加泛型
 * 目前正在学习，还没有完成
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        // 测试
        SingleLinkedList<HashMap<String, String>> list = new SingleLinkedList<>();
        HashMap map1 = new HashMap();
        map1.put("a"," a");
        HashMap map2 = new HashMap();
        map2.put("b"," b");
        HashMap map3 = new HashMap();
        map3.put("c"," c");
        list.add(map1);
        list.add(map1);
        list.add(map1);
        list.add(map3);
        list.add(map2);
        list.list();
        //System.out.println(list.size());
        // 移除第一个节点
        /*System.out.println("\n===========移除节点");
        HashMap<String, String> remove = list.remove();
        list.list();
        System.out.println(list.size());*/

        // 移除指定节点
        System.out.println("\n===========移除节点");
        //list.remove(1);
        list.remove(4);
        list.list();
    }
}

/** 自定义单链表SingleLinkedList 来管理DataNode */
class SingleLinkedList<E> {
    private E node;
    // 先初始化一个头节点,头节点不要动,不存放具体数据
    private Node<E> head = new Node<>( node, null);

    public Node<E> getHead() {
        return head;
    }

    private int size;

    /** 获取链表大小 */
    public int size() {
        return size;
    }

    /**
     * 添加方法 到单向链表的尾部
     * 思路：当不考虑编号顺序时
     * 1.找到当前链表的最后一个节点
     * 2.将最后这个节点的next 指向 新的节点
     */
    public void add(E data) {
        // 因为head节点不能动，因此我们需要一个变量temp
        Node<E> temp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if (temp.next == null) {
                break;
            }
            // 如果没有找到，将temp后移
            temp = temp.next;
        }
        // 当退出while时，temp就指向了链表最后的节点，再把这个节点的next指向新加节点
        temp.next = new Node<E>(data, null);
        size++;
    }

    /** 显示链表[遍历] */
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头节点不能动，因此我们需要一个辅助变量来遍历
        Node<E> temp = head.next;
        while (true) {
            // 判断是否到链表的最后
            if (temp == null) {
                break;
            }
            // 输出节点的信息
            System.out.println(temp.data);
            // 将temp后移，一定小心
            temp = temp.next;
        }
    }

    /** 移除链表第一个元素(除头结点) */
    public E remove() {
        if(head.next == null) {
            throw new RuntimeException("没有元素...");
        }
        Node<E> temp = head.next;
        head.next = head.next.next;
        size--;
        return temp.data;
    }

    /** 移除链表第index个元素 */
    public E remove(int index) {
        if (index > size) {
            throw new RuntimeException("超过链表最大元素个数...");
        }
        if (index == 1) {
            return remove();
        }

        // 要移除的节点的上一个节点
        Node<E> temp = head.next;
        // 移除的节点
        Node<E> helper = temp.next;
        // 找到要删除节点的【前一个】节点,因此index要-1
        for (int i = 1; i < index - 1; i++) {
            temp = temp.next;
            helper = helper.next;
        }
        // 移除当前节点
        if (index == size) {
            temp.next = null;
        } else {
            temp.next = helper.next;
        }
        size--;
        return helper.data;
    }

    /** 清空链表 */
    public void clear() {
        head.next = null;
        size = 0;
    }


    private class Node<E> {
        E data;
        Node<E> next;

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

}


