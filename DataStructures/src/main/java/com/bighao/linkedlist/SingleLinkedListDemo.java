package com.bighao.linkedlist;

import org.junit.jupiter.api.Test;

import java.util.Stack;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/14 16:51
 * @Version 1.0
 *
 * 单向链表
 *
 * 链表(Linked List)介绍
 * EXECL 391 ppt 29
 * 链表是有序的列表，但是它在内存中不一定是连续存储的，而数组是连续的
 * 链表是以节点的方式来存储,是链式存储
 * 每个节点包含 data 域， next 域：指向下一个节点.
 *
 * 链表分带头节点的链表和没有头节点的链表，根据实际的需求来确定
 *
 * 以下是单链表的应用实例
 * 完成单链表的增删改查和顺序插入
 *
 * 单链表常见面试题(新浪、百度、腾讯)
 *  1.求单链表中有效节点的个数
 *  2.查找单链表中的倒数第k个结点 【新浪面试题】
 *  3.单链表的反转【腾讯面试题，有点难度】
 *  4.从尾到头打印单链表 【百度，要求方式1：反向遍历 。 方式2：Stack栈】
 *  5.合并两个有序的单链表，合并之后的链表依然有序【练习】
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        // 进行测试
        // 先创建节点
        HeroNode hero1 = new HeroNode(1, "杨超越", "小超越");
        HeroNode hero2 = new HeroNode(2, "裴秀智", "秀智");
        HeroNode hero3 = new HeroNode(3, "秦岚", "白月光");
        HeroNode hero4 = new HeroNode(4, "宣美", "腿精");

        // 创建一个链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        // 添加元素到链表的末尾
        /*singleLinkedList.add(hero1);
        singleLinkedList.add(hero4);
        singleLinkedList.add(hero2);
        singleLinkedList.add(hero3);*/

        // 添加元素按编号的顺序
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero3);

        // 遍历显示
        singleLinkedList.list();

        // 测试修改节点的代码
        HeroNode newHeroNode = new HeroNode(1, "我要娶杨超越", "小小超越");
        singleLinkedList.update(newHeroNode);

        System.out.println("\n修改后的链表======>");
        singleLinkedList.list();

        // 删除节点
        /*singleLinkedList.del(1);
        singleLinkedList.del(4);
        singleLinkedList.del(2);
        singleLinkedList.del(3);

        System.out.println("\n删除后的链表======>");
        singleLinkedList.list();*/

        // 1.求单链表的有效节点个数
        System.out.println("有效的节点个数为： " + getLength(singleLinkedList.getHead()));

        // 2.测试得到倒数第k个元素
        int index = 3;
        HeroNode res = findLastIndexNode(singleLinkedList.getHead(), index);
        System.out.println("\n测试得到倒数第" + index + "个元素 res===>" + res);

        // 4.逆序打印单链表
        System.out.println("\n逆序打印单链表 没有改变链表的结构===>");
        reversePrint(singleLinkedList.getHead());

        // 3.反转
        System.out.println("\n反转后的链表===>");
        myReverseList(singleLinkedList.getHead());
        //reverseList(singleLinkedList.getHead());
        singleLinkedList.list();

    }

    /** 测试 合并两个有序的单链表，合并之后的链表依然有序 */
    @Test
    public void testMerge() {
        HeroNode hero1 = new HeroNode(1, "杨超越", "小超越");
        HeroNode hero2 = new HeroNode(2, "裴秀智", "秀智");
        HeroNode hero3 = new HeroNode(3, "秦岚", "白月光");
        HeroNode hero4 = new HeroNode(4, "宣美", "腿精");

        HeroNode hero5 = new HeroNode(5, "aaa", "aaa");
        HeroNode hero6 = new HeroNode(6, "bbb", "bbb");
        HeroNode hero7 = new HeroNode(7, "ccc", "ccc");
        HeroNode hero8 = new HeroNode(8, "ddd", "ddd");
        HeroNode hero9 = new HeroNode(9, "eee", "eee");
        HeroNode hero10 = new HeroNode(10, "fff", "fff");
        HeroNode hero11 = new HeroNode(1, "杨超越", "小超越");


        SingleLinkedList list1 = new SingleLinkedList();
        SingleLinkedList list2 = new SingleLinkedList();

        list1.addByOrder(hero1);
        list1.addByOrder(hero5);
        list1.addByOrder(hero4);
        list1.addByOrder(hero2);
        list1.addByOrder(hero10);
        list1.addByOrder(hero9);

        list2.addByOrder(hero7);
        list2.addByOrder(hero11);
        list2.addByOrder(hero6);
        list2.addByOrder(hero3);
        list2.addByOrder(hero8);

        System.out.println("顺序合并两个链表，返回一个新链表=======>");
        SingleLinkedList mergeList = mergeSingleLinkedList(list1, list2);
        mergeList.list();

        System.out.println("向一个链表中顺序插入另一个链表的全部元素=======>");
        /*list1.addAllByOrder(list2);
        list1.list();*/
    }

    /**
     * 自己的练习：合并两个有序的单链表，合并之后的链表依然有序
     * 两个思路
     * 1.创建一个新链表，遍历并比较两个旧链表将数据顺序插入,返回新链表
     * 2.将一个链表顺序插入到另一个链表 写了一个addAllByOrder()方法来实现
     *
     * 这里用的是方式1 里面就没有调用addByOrder()方法，效率反而不高，手写了下
     */
    public static SingleLinkedList mergeSingleLinkedList(SingleLinkedList list1, SingleLinkedList list2) {
        // 创建两个链表的当前节点元素
        HeroNode node1 = list1.getHead().next;
        HeroNode node2 = list2.getHead().next;
        // 先创建合并后的链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        // 创建新链表头的当前临时节点
        HeroNode tempNode = singleLinkedList.getHead();

        while (true) {
            // 当两个链表都不为空时
            if (node1 != null && node2 != null) {
                if(node1.no < node2.no) {
                    // 将新链表的当前节点的下一个元素指向新加入的节点
                    tempNode.next = node1;
                    // 将新链表的当前临时节点变为新加入的节点
                    tempNode = node1;
                    // 后移被移除元素的链表
                    node1 = node1.next;
                } else if (node1.no > node2.no) {
                    // 将新链表的当前节点的下一个元素指向新加入的节点
                    tempNode.next = node2;
                    // 将新链表的当前临时节点变为新加入的节点
                    tempNode = node2;
                    // 后移被移除元素的链表
                    node2 = node2.next;
                } else {
                    System.out.printf("具有重复值编号为 %d \n", node1.no);
                    // 将新链表的当前节点的下一个元素指向新加入的节点
                    tempNode.next = node1;
                    // 将新链表的当前临时节点变为新加入的节点
                    tempNode = node1;
                    // 后移被移除元素的链表
                    node1 = node1.next;
                    node2 = node2.next;
                }
            } else if(node2 != null) {
                // 当第一个链表遍历结束后(或本身就为为空)，将第二个链表剩余元素顺序插入
                tempNode.next = node2;
                tempNode = node2;
                node2 = node2.next;
            } else if(node1 != null) {
                // 当第二个链表遍历结束后(或本身就为为空)，将第一个链表剩余元素顺序插入
                tempNode.next = node1;
                tempNode = node1;
                node1 = node1.next;
            } else {
                // 两个都为空，退出循环
                break;
            }
        }

        return singleLinkedList;
    }

    /** 从尾到头打印单链表 百度 方式2：Stack栈 */
    public static void reversePrint(HeroNode head) {
        if (head.next == null) {
            return;
        }
        // 创建一个栈，将各个节点压入栈中
        Stack<HeroNode> stack = new Stack<>();
        HeroNode cur = head.next;
        // 将链表的所有节点压入栈中
        while (cur != null) {
            stack.push(cur);
            // 后移cur
            cur = cur.next;
        }
        // 将栈中的节点进行打印
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }
    }



    /**
     * 将单链表反转
     * 1. 先定义一个节点 reverseHead = new HeroNode();
     * 2. 从头到尾遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端.
     * 3. 原来的链表的head.next = reverseHead.next
     */
    public static void reverseList(HeroNode head) {
        // 如果当前链表为空，或者只有一个节点，无需反转
        if(head.next == null || head.next.next == null) {
            return;
        }
        // 定义临时变量，帮助我们遍历原来的链表
        HeroNode cur = head.next;
        // 指向当前节点[cur]的下一个节点
        HeroNode next = null;
        HeroNode reverseHead = new HeroNode(0, null, null);
        // 遍历原来的链表，每遍历一个节点，就将其取出，并放在新的链表reverseHead 的最前端
        while (cur != null) {
            // 先暂时保存当前节点的下一个节点，因为后面要用
            next = cur.next;
            // 将cur的下一个节点指向新的链表的头部
            cur.next = reverseHead.next;
            // 将cur连接到新的链表上
            reverseHead.next = cur;
            // 后移cur
            cur = next;
        }
        // 旧链表头指向反转后的链表
        head.next = reverseHead.next;
        reverseHead.next = null;
    }

    /**
     * 自己写的反转链表方法 主要是写思路 自己画图理解下
     * 1.定义新链表头
     * 2.定义临时变量保存当前元素的下一个元素
     * 3.遍历原来的链表进行反转，被反转的元素指向新链表头后的第一个元素
     * 4.新链表头指向新元素
     * 5.再把当前元素指向下一个要反转的元素
     */
    public static void myReverseList(HeroNode head) {
        if(head.next == null || head.next.next == null) {
            return;
        }
        // 当前元素
        HeroNode cur = head.next;
        // 新链表头
        HeroNode h2 = new HeroNode(0, null, null);
        // 定义临时变量保存当前元素[cur]的下一个元素
        HeroNode next = null;
        while (cur != null) {
            next = cur.next;
            // 当前被反转的元素 指向 新链表头后的第一个元素
            cur.next = h2.next;
            // 新链表头 指向 当前被反转的元素
            h2.next = cur;
            // 再把当前元素 指向 下一个要反转的元素
            cur = next;
        }
        // 旧链表头指向反转后的链表
        head.next = h2.next;
        // 新链表头的下一个元素设为null断开关联
        h2.next = null;
    }


    /**
     * 查找单链表中的倒数第k个结点 【新浪面试题】
     * 思路：
     *    编写一个方法，接收head节点，同时接收一个index 表示倒数第index个节点
     *    1.先把链表从头到尾遍历，得到链表总的长度getLength()
     *    2.得到size后，我们从链表的第一个开始遍历(size - index)个，就可以定位到该节点
     * 如果找到则返回该节点，否则返回null
     */
    public static HeroNode findLastIndexNode(HeroNode head, int index) {
        // 判断如果链表为空，返回null
        if(head.next == null) {
            return null;
        }
        // 第一次遍历得到链表的长度(节点个数)
        int size = getLength(head);
        // 第二次遍历到第(size - index)位置，就是我们倒数的第k个节点
        // 先做一个index的校验
        if(index <= 0 || index > size) {
            return null;
        }
        // 定义一个临时变量, 循环到定位到倒数第index节点
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    /**
     * 获取到单链表的节点的个数（如果是带头节点的链表，需要不统计头结点）
     * @param head 链表的头节点
     * @return 返回的就是有效节点的个数
     */
    public static int getLength(HeroNode head) {
        // 如果是带头结点的空链表
        if(head.next == null) {
            return 0;
        }
        int length = 0;
        // 定义一个临时变量 这里我们没有统计头节点
        HeroNode cur = head.next;
        while (cur != null) {
            length++;
            // 遍历链表
            cur = cur.next;
        }
        return length;
    }

}

/** 自定义单链表SingleLinkedList 来管理HereNode */
class SingleLinkedList {
    // 先初始化一个头节点,头节点不要动,不存放具体数据
    private HeroNode head = new HeroNode(0, null, null);

    public HeroNode getHead() {
        return head;
    }

    /** 将另一个链表全部顺序插入到当前链表 前提当前链表也是排好序的 */
    public void addAllByOrder(SingleLinkedList list) {
        HeroNode node = list.getHead().next;
        if (node == null) {
            return;
        }
        HeroNode temp = node;
        while (temp != null) {
            // 先将temp指向下一个要添加的节点
            temp = node.next;
            // 将当前节点顺序插入到另一个链表
            addByOrder(node);
            // 再将node2指回下一个要添加的元素
            node = temp;
        }
    }

    /**
     * 添加方法 到单向链表的尾部
     * 思路：当不考虑编号顺序时
     * 1.找到当前链表的最后一个节点
     * 2.将最后这个节点的next 指向 新的节点
     */
    public void add(HeroNode hereNode ) {
        // 因为head节点不能动，因此我们需要一个变量temp
        HeroNode temp = head;
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
        temp.next = hereNode;
    }

    /**
     * 顺序添加：根据元素的编号排序，编号不能重复
     * 先找到要添加节点的后一个节点
     * 1. 首先找到新添加的节点的位置, 是通过辅助变量(指针), 通过遍历来搞定
     * 2. 新的节点.next = temp.next
     * 3. 将temp.next = 新的节点
     */
    public void addByOrder(HeroNode heroNode) {
        // 因为头节点不能动，因此我们仍需要通过一个辅助变量来帮助找到添加的位置
        // 因为这是单链表，因此我们找到temp 是位于 添加位置的前一个节点，否则插入不了
        HeroNode temp = head;
        // 标志添加的编号是否存在，默认为false
        boolean flag = false;
        while (true) {
            if (temp.next == null) { // 说明temp已经在链表的最后,那此时要添加的元素就要放到链表的最后
                break;
            }
            if (temp.next.no > heroNode.no) { // 位置找到，就在temp的后面插入
                break;
            } else if (temp.next.no == heroNode.no) { // 希望添加的heroNode的编号已然存在
                // 改为true说明编号存在
                flag = true;
                break;
            }
            // 后移遍历当前链表
            temp = temp.next;
        }
        // 判断flag的值
        if (flag) { // 不能添加，说明编号存在
            System.out.printf("准备插入元素的编号%d 已经存在，不能加入\n", heroNode.no);
        } else {
            // 插入到链表中, 也就是temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    /** 修改节点的信息，根据 no 编号来修改，no编号不能改 */
    public void update(HeroNode newHeroNode) {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 找到需要修改的节点，根据no编号
        // 先定义一个辅助迭代变量
        HeroNode temp = head.next;
        // 表示是否找到该节点
        boolean flag = false;
        while (true) {
            if (temp == null) {
                // 已经遍历完链表
                break;
            }
            if (temp.no == newHeroNode.no) {
                // 找到节点后
                flag = true;
                break;
            }
            temp = temp.next;
        }
        // 根据flag 判断是否找到要修改的节点
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickName = newHeroNode.nickName;
        } else {
            System.out.printf("没有找到 编号%d 的节点,不能修改\n", newHeroNode.no);
        }
    }

    /**
     * 删除节点
     * 思路: 通过临时迭代变量temp来找到待删除节点的【前一个】节点
     * 在比较时，是temp.next.no 和 需要删除的节点的no进行比较
     * 找到后将 前一个节点 指向 待删除节点的后一个节点 temp.next = temp.next.next
     * 被删除的节点，将不会有其它引用指向，会被GC回收
     */
    public void del(int no) {
        HeroNode temp = head;
        // 标识是否找到待删除节点的前一个节点
        boolean flag = false;
        while (true) {
            if (temp.next == null) {
                // 已经到链表的最后
                break;
            }
            if (temp.next.no == no) {
                // 找到了待删除节点的前一个节点
                flag = true;
                break;
            }
            // 后移temp
            temp = temp.next;
        }

        if (flag) {
            // 可以删除
            temp.next = temp.next.next;
        } else {
            System.out.printf("要删除的 %d 节点不存在\n", no);
        }
    }


    /** 显示链表[遍历] */
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头节点不能动，因此我们需要一个辅助迭代变量来遍历
        HeroNode temp = head.next;
        while (true) {
            // 判断是否到链表的最后
            if (temp == null) {
                break;
            }
            // 输出节点的信息
            System.out.println(temp);
            // 将temp后移，一定小心
            temp = temp.next;
        }
    }

}

/** 定义HereNode，每个HeroNode对象就是一个节点 */
class HeroNode {
    public int no;
    public String name;
    public String nickName;
    // 指向下一个节点，默认为null
    public HeroNode next;

    // 构造器
    public HeroNode(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HereNode{" + "no=" + no + ", name='" + name + '\'' + ", nickName='" + nickName + '}';
    }

}
