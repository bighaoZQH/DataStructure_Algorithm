package com.bighao.stack;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/18 11:25
 * @Version 1.0
 *
 * 中缀表达式
 *
 * 使用栈完成表达式的计算 思路
 * 1. 通过一个 index  值（索引），来遍历我们的表达式
 * 2. 如果我们发现是一个数字, 就直接入数栈
 * 3. 如果发现扫描到是一个符号,  就分如下情况
 * 3.1 如果发现当前的符号栈为 空，就直接入栈
 * 3.2 如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符， 就需要从数栈中pop出两个数,在从符号栈中pop出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入符号栈， 如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈.
 * 4. 当表达式扫描完毕，就顺序的从 数栈和符号栈中pop出相应的数和符号，并运行.
 * 5. 最后在数栈只有一个数字，就是表达式的结果
 *
 * 注意:
 * 1.当处理多位数时，不能发现是一个数就立即入栈，因为他可能是多为数
 * 2.在处理数字时，需要向expression的表达式的index后再看一位，如果是数就进行扫描，如果是符号才入栈
 * 3.定义一个字符串变量，用于拼接
 */
public class Calculator {
    public static void main(String[] args) {
        // 根据思路，完成表达式的运算
        String expression = "70000+2*6-4";
        // 创建两个栈，一个数栈，一个符号栈
        ArrayStack2 numberStack = new ArrayStack2(10);
        ArrayStack2 operStack = new ArrayStack2(10);
        // 定义需要的相关变量
        int index = 0;// 用于扫描
        int num1 = 0; // 数字栈pop出的数字
        int num2 = 0; // 数字栈pop出的数字
        int oper = 0; // 符号栈pop出的符号
        int res = 0; //  计算结果
        char ch = ' ';//将每次扫描得到的char保存到ch
        String keepNum = "";//用于拼接多位数
        // 开始while循环的扫描expression
        while (true) {
            // 第一次得到expression的每一个字符
            ch = expression.substring(index, index + 1).charAt(0);
            // 判断ch是什么，然后做相应的处理
            if (operStack.isOper(ch)) {
                // 如果是运算符, 判断当前的符号栈是否为空
                if (!operStack.isEmpty()) {
                    // 如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符
                    // 就需要从数栈中pop出两个数,再从符号栈中pop出一个符号，进行运算，将得到结果，入数栈
                    // 然后将当前的操作符入符号栈， 如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈.
                    if (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        // 从数栈中pop出两个数
                        num1 = numberStack.pop();
                        num2 = numberStack.pop();
                        // 从符号栈中pop出一个符号
                        oper = operStack.pop();
                        res = numberStack.cal(num1, num2, oper);
                        // 把计算的结果如数栈
                        numberStack.push(res);
                        // 将当前的操作符入符号栈
                        operStack.push(ch);
                    } else {
                        //如果当前的操作符的优先级大于栈中的操作符， 就直接入符号栈
                        operStack.push(ch);
                    }

                } else {
                    // 为空就直接入栈
                    operStack.push(ch);
                }

            } else {
                /**
                 * 如果是数，则直接入栈 ascii码中 数字1是49
                 * 1.当处理多位数时，不能发现是一个数就立即入栈，因为他可能是多为数
                 * 2.在处理数字时，需要向expression的表达式的index后再看一位，如果是数就进行扫描，如果是符号才入栈
                 * 3.定义一个字符串变量，用于拼接
                 */

                // 处理多位数
                keepNum += ch;

                // 如果ch已经是表达式的最后一位，则直接入栈
                if (index == expression.length() - 1) {
                    numberStack.push(Integer.parseInt(keepNum));
                } else {

                    // 判断下一个字符是不是数字，如果是数字，就继续扫描,如果是运算符，则入栈
                    if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                        // 如果后一位是运算符，则入栈
                        numberStack.push(Integer.parseInt(keepNum));
                        // 清空keepNum
                        keepNum = "";
                    }
                }

            }
            // 让index + 1，并判断是否扫描到expression最后
            index++;
            if (index >= expression.length()) {
                break;
            }
        }

        // 当表达式扫描完毕，就顺序的从 数栈和符号栈中pop出相应的数和符号，并运行
        while (true) {
            // 如果符号栈为空，则计算结束，取得数栈的结果
            if (operStack.isEmpty()) {
                break;
            }
            // 从数栈中pop出两个数
            num1 = numberStack.pop();
            num2 = numberStack.pop();
            // 从符号栈中pop出一个符号
            oper = operStack.pop();
            res = numberStack.cal(num1, num2, oper);
            numberStack.push(res);
        }
        // 计算结束，取得数栈的结果
        System.out.printf("表达式 %s = %d", expression, numberStack.pop());

    }

}





/** 先创建一个栈，直接使用前面基于数组的栈 但需要扩展功能 */
class ArrayStack2 {
    // 栈的最大大小
    private int maxSize;
    // 数组模拟,数据就存在该数组中
    private int[] stack;
    // top表示栈顶，初始化为-1，表示没有数据
    private int top = -1;


    public ArrayStack2() {
        // 默认初始化栈最大大小10
        this(10);
    }

    // 构造器
    public ArrayStack2(int maxSize) {
        if(maxSize < 0) {
            throw new RuntimeException("栈初始化大小错误:" + maxSize);
        }
        this.maxSize = maxSize;
        stack = new int[maxSize];
    }

    // 栈满
    public boolean isFull() {
        return top == maxSize - 1;
    }

    // 栈空
    public boolean isEmpty() {
        return top == -1;
    }

    /** 查看当前栈顶的值，但不出栈 */
    public int peek() {
        return stack[top];
    }

    // 入栈-push
    public void push(int value) {
        // 先判断栈是否满
        if(isFull()) {
            System.out.println("栈满");
            return;
        }
        top++;
        stack[top] = value;
    }

    // 出栈-pop 将栈顶的数据返回
    public int pop() {
        // 先判断栈是否空
        if(isEmpty()) {
            throw new RuntimeException("栈空，没有数据");
        }
        int value = stack[top];
        top--;
        return value;
    }

    // 获取当前栈有多少个元素
    public int size() {
        return top == -1 ? 0 : top + 1;
    }

    // 显示栈的情况 - 遍历栈, 遍历时，需要从栈顶开始显示数据
    public void list() {
        if(isEmpty()) {
            System.out.println("栈空，没有数据");
            return;
        }
        // 从栈顶开始循环
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }


    /**
     * 返回运算符的优先级,优先级由程序员来确定，优先级使用数字来表示
     * 数字越大，优先级越高
     */
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            // 假定目前的表达式里只有+-*/
            return -1;
        }
    }

    // 判断是不是一个运算符
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    // 计算方法
    public int cal(int num1, int num2, int oper) {
        // 用于存放计算的结果
        int res = 0;
        switch (oper) {
            case '+':
                res = num1 + num2;
                break;
            case '-':
                // 注意顺序
                res = num2 - num1;
                break;
            case '*':
                res = num1 * num2;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }

}

