package com.bighao.stack;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/18 17:42
 * @Version 1.0
 *
 * 逆波兰表达式(后缀表达式) 支持+ ,-, *, /,小数,括号
 *      从左至右扫描表达式，遇到数字时，将数字压入堆栈，遇到运算符时，弹出栈顶的两个数，
 *      用运算符对它们做相应的计算（次顶元素 和 栈顶元素），并将结果入栈；
 *      重复上述过程直到表达式最右端，最后运算得出的值即为表达式的结果
 *
 * execl 726
 *
 * 例如: (3+4)×5-6 对应的后缀表达式就是 3 4 + 5 × 6 - , 针对后缀表达式求值步骤如下:
 *  1).从左至右扫描，将3和4压入堆栈；
 *  2).遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
 *  3).将5入栈；
 *  4).接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
 *  5).将6入栈；
 *  6).最后是-运算符，计算出35-6的值，即29，由此得出最终结果
 *
 * 中缀表达式转后缀表达式的步骤:
 *  1) 初始化两个栈：运算符栈s1和储存中间结果的栈s2；
 *  2) 从左至右扫描中缀表达式；
 *  3) 遇到操作数时，将其压s2；
 *  4) 遇到运算符时，比较其与s1栈顶运算符的优先级：
 *      1.如果s1为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
 *      2.否则，若优先级比栈顶运算符的高，也将运算符压入s1；
 *      3.否则，将s1栈顶的运算符弹出并压入到s2中，【再次转到(4.1)与s1中新的栈顶运算符相比较】
 *  5) 遇到括号时：
 *      1. 如果是左括号“(”，则直接压入s1
 *      2. 如果是右括号“)”，则依次弹出s1栈顶的运算符，并压入s2，直到遇到左括号为止，此时将这一对括号丢弃
 *  6) 重复步骤2至5，直到表达式的最右边
 *  7) 将s1中剩余的运算符依次弹出并压入s2
 *  8)  依次弹出s2中的元素并输出，结果的逆序即为中缀表达式对应的后缀表达式
 *
 *  最后实际上，中缀转后缀的中间结果不需要用栈来存储，不然最后还需要进行逆序，可以用ArrayList或者队列来做
 */
public class ReversePolish {
    public static void main(String[] args) {
        /**
         * 完成将一个中缀表达式转成后缀表达式的功能
         * 说明
         * 1. 1+((2+3)×4)-5 => 1 2 3 + 4 × + 5 –
         * 2.因为直接对字符串进行操作不方便，先将"1+((2+3)×4)-5" 转成 中缀表达式对应的list
         * 3.将中缀表达式对应的list转为后缀表达式对应的list
         *      [1,+,(,(,2,+,3,),*,4,),-,5] =》 [1,2,3,+,4,*,+,5,-]
         */
        String expression = "2.2+((2+3)*4)-5"; // 注意这里的乘号用的是'*'不是'×'
        List<String> infixExpressionList = toInfixExpressionList(expression);
        System.out.println("中缀表达式对应的list==>" + infixExpressionList); //[1,+,(,(,2,+,3,),*,4,),-,5]
        List<String> suffixExpressionList = parseSuffixExpressionList(infixExpressionList);
        System.out.println("后缀表达式对应的list==>" + suffixExpressionList);//[1,2,3,+,4,*,+,5,-]

        System.out.printf("expression=%f", calculate(suffixExpressionList));

    }


    /**
     * 将中缀表达式对应的list转为后缀表达式对应的list
     *    [1,+,(,(,2,+,3,),*,4,),-,5] =》 [1,2,3,+,4,*,+,5,-]
     */
    public static List<String> parseSuffixExpressionList(List<String> list) {
        // 符号栈
        Stack<String> s1 = new Stack<>();
        // 存储中间结果(这里就不需要用栈了)
        //Stack<String> s2 = new Stack<>();
        List<String> s2 = new ArrayList<>();

        // 遍历list
        for (String item : list) {
            // 如果是一个数，就入栈
            if (item.matches("^\\d?[.\\d]*$")) {
                s2.add(item);
            } else if (item.equals("(")) {
                s1.push(item);
            } else if (item.equals(")")) {
                // 如果是右括号，则依次弹出s1栈顶的运算符，并加入s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!s1.peek().equals("(")) {
                    s2.add(s1.pop());
                }
                // 添加完后，将"("小括号弹出，丢弃这一对括号
                s1.pop();
            } else {
                // 当item的优先级<=s1栈顶的运算符的时候,将s1栈顶的运算符弹出并加入到s2中，再次转到(4.1)与s1中新的栈顶运算符相比较
                while (s1.size() != 0 && Operation.getValue(item) <= Operation.getValue(s1.peek())) {
                    s2.add(s1.pop());
                }
                // 还需要将item压入栈中
                s1.push(item);
            }
        }
        // 将s1中剩余的运算符依次弹出并加入到s2中
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        // list就不需要逆序了，结果就是逆波兰表达式的list
        return s2;
    }


    /** 将中缀表达式转成对应的list 主要是处理多位数和小数*/
    public static List<String> toInfixExpressionList(String s) {
        // 定义一个List，存放中缀表达式 对应的内容
        List<String> list = new ArrayList<>();
        // 用于遍历中缀表达式字符串
        int i = 0;
        // 用于做对多位数的拼接
        String str;
        // 每遍历到一个字符，就放入到c
        char c;
        do {
            // 如果c是一个非数字，就直接加入到list里
            if ((c = s.charAt(i)) != 46 && ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57)) {
                list.add(String.valueOf(c));
                i++;
            } else {
                // 如果是一个数字，需要考虑多位数和小数
                // 先将str置为空串
                str = "";
                while (i < s.length() && (((c = s.charAt(i)) >= 48 && (c = s.charAt(i)) <= 57) || (c = s.charAt(i)) == 46)) {
                    // 拼接
                    str += c;
                    i++;
                }
                list.add(str);
            }
        } while (i < s.length());
        return list;
    }

    /** 将逆波兰表达式，依次将数据和运算符放入到ArrayList中 */
    public static List<String> getListString(String suffixExpression) {
        String[] split = suffixExpression.split(" ");
        List<String> list = new ArrayList<>();
        for (String  ele : split) {
            list.add(ele);
        }
        return list;
    }

    /**
     * 完成对逆波兰表达式的运算 3 4 + 5 * 6 -
     *  1.从左至右扫描，将3和4压入堆栈；
     *  2.遇到+运算符，因此弹出4和3（4为栈顶元素，3为次顶元素），计算出3+4的值，得7，再将7入栈；
     *  3.将5入栈；
     *  4.接下来是×运算符，因此弹出5和7，计算出7×5=35，将35入栈；
     *  5.将6入栈；
     *  6.最后是-运算符，计算出35-6的值，即29，由此得出最终结果
     */
    public static Double calculate(List<String> list) {
        // 创建栈，只需要一个栈
        Stack<String> stack = new Stack<>();
        // 遍历 list
        for (String item : list) {
            // 使用正则表达式来取数
            if (item.matches("^\\d?[.\\d]*$")) {
                // 入栈
                stack.push(item);
            } else {
                // pop出两个数，并运算，再入栈
                Double num2 = Double.parseDouble(stack.pop()); //先弹的用num2接收了
                Double num1 = Double.parseDouble(stack.pop());
                Double res = 0.0;
                if (item.equals("+")) {
                    res = num1 + num2;
                } else if(item.equals("-")) {
                    res = num1 - num2;
                } else if(item.equals("*")) {
                    res = num1 * num2;
                } else if(item.equals("/")) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("运算错误");
                }
                stack.push(String.valueOf(res));
            }
        }
        // 最后留在stack中的数据是运算结果
        return Double.parseDouble(stack.pop());
    }

}

/** 该类用于返回运算符对应的优先级 */
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    /** 返回对应的优先级 */
    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符号");
                break;
        }
        return result;
    }
}