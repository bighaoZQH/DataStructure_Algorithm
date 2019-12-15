package com.bighao.linkedlist;

import java.util.Stack;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/15 11:53
 * @Version 1.0
 *
 * 演示栈Stack的基本使用
 */
public class TestStack {
    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        // 入栈
        stack.add("jack");
        stack.add("tom");
        stack.add("smith");

        // 出栈
        // smith tom jack
        while (stack.size() > 0) {
            // pop()将栈顶的数据取出
            System.out.println(stack.pop());
        }
    }
}
