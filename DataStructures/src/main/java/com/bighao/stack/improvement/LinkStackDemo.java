package com.bighao.stack.improvement;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/16 10:23
 * @Version 1.0
 *
 * 单向链表实现栈 加了泛型
 */
public class LinkStackDemo {
    public static void main(String[] args) {
        LinkStack<HashMap<String, Object>> stack = new LinkStack(4);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("a", "a");
        map1.put("b", "b");
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("c", "c");
        map2.put("d", "d");
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("e", "e");
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("f", "f");
        // 节点入栈
        stack.push(map1);
        stack.push(map2);
        stack.push(map3);
        stack.push(map4);

        System.out.println("查看节点=======>");
        HashMap<String, Object> peek1 = stack.peek(1);
        System.out.println(peek1.toString());

        HashMap<String, Object> peek2 = stack.peek(2);
        System.out.println(peek2.toString());

        HashMap<String, Object> peek3 = stack.peek(3);
        System.out.println(peek3.toString());

        HashMap<String, Object> peek4 = stack.peek(4);
        System.out.println(peek4.toString());

        //HashMap<String, Object> peek5 = stack.peek(5);

        System.out.println("节点出栈=======>");
        int size = stack.size(); // 不要把这方法直接放在循环里，size()是当前元素个数，元素出栈后size()会发生变化
        for (int i = 0; i < size; i++) {
            HashMap<String, Object> pop = stack.pop();
            System.out.println(pop.toString());
        }


    }

    @Test
    public void test() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        Date date = localDateTimeConvertToDate(localDateTime);
        System.out.println(date);
    }

    private Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

}

/** 单向链表实现栈 */
class LinkStack<E> {
    // 栈的最大大小
    private int maxSize;
    // 栈顶节点(链表的最后加入的节点)
    private Node<E> data;
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

    // 获取【当前】栈元素个数
    public int size() {
        return top == -1 ? 0 : top + 1;
    }

    // 入栈-push
    public void push(E element) {
        // 先判断栈是否已满
        if(isFull()) {
            throw new RuntimeException("栈内存溢出异常");
        }
        data = new Node<E>(element, data);
        top++;
    }

    // 出栈-pop 将栈顶的数据返回
    public E pop() {
        // 先判断是否为空
        if(isEmpty()) {
            throw new RuntimeException("空栈异常");
        }
        // 获取栈顶节点元素
        Node<E> value = data;
        // 将顶节点元素下移
        data = data.next;
        // 将原来的栈顶节点元素从链表中移除
        value.next = null;
        top--;
        return value.getElement();
    }

    /**
     * 查看栈元素，但不出栈
     * @param index 栈顶向下第index个元素
     */
    public E peek(int index) {
        if(isEmpty()) {
            throw new RuntimeException("空栈异常");
        }
        if (index > top + 1) {
            throw new RuntimeException("超过最大堆栈");
        }

        // 由于不能改变栈结构，需要一个临时变量
        Node<E> temp = data;
        for (int i = 1; i < index; i++) {
            // 下移栈
            temp = temp.next;
        }
        return temp.getElement();
    }

    // 显示栈的情况 - 遍历栈, 遍历时，需要从栈顶开始显示数据
    public void list() {
        // 先判断是否为空
        if(isEmpty()) {
            throw new RuntimeException("空栈异常");
        }

        // 由于不能改变栈结构，需要一个临时变量
        Node<E> temp = data;
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

class Node<E> {
    E element;
    Node<E> next;

    public E getElement() {
        return element;
    }

    // 构造器 新加入节点的next是上一个加入的节点，实现了先入后出
    public Node(E element, Node<E> next) {
        this.element = element;
        this.next = next;
    }

}


