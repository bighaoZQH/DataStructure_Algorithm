package com.bighao.hashtab;

import java.util.Scanner;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/24 11:25
 * @Version 1.0
 *
 * 哈希表
 * 数组+链表 构成哈希表
 */
public class HashTabDemo {
    public static void main(String[] args) {
        // 创建哈希表
        HashTab hashTab = new HashTab(7);
        // 写一个菜单来测试
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("add:  添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("del: 删除雇员");
            System.out.println("exit: 退出系统");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建 雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "del":
                    try {
                        System.out.println("请输入要删除的id");
                        id = scanner.nextInt();
                        hashTab.del(id);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}


/** 创建哈希表 管理多条链表 */
class HashTab {
    private EmpLinkedList[] empLinkedListArray;
    // 共有多少条链表
    private int size;

    public HashTab() {
        this(10);
    }

    public HashTab(int size) {
        if (size <= 0) {
            throw new RuntimeException("size error");
        }
        this.size = size;
        // 初始化empLinkedListArray
        empLinkedListArray = new EmpLinkedList[size];
        // 有一个坑，要分别初始化每一条链表
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    /** 添加节点 */
    public void add(Emp emp) {
        // 根据员工的id，得到该员工应当添加到那条链表
        int linkedListNo = hashFun(emp.id);
        // 将emp添加到对应的链表中
        empLinkedListArray[linkedListNo].add(emp);
    }

    /** 根据id查找节点 */
    public void findEmpById(int id) {
        // 使用散列函数确定到那条链表查找
        int linkedListNo = hashFun(id);
        Emp emp  = empLinkedListArray[linkedListNo].findEmpById(id);
        if (emp != null) {
            System.out.printf("在第%d条链表中找到该节点 id=%d\n", (linkedListNo + 1), id);
        } else {
            System.out.println("在哈希表中没有找到该节点");
        }
    }

    /** 遍历哈希表 即所有的链表 */
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    /** 删除雇员 */
    public void del(int id) {
        int linkedListNo = hashFun(id);
        empLinkedListArray[linkedListNo].remove(id);
    }

    /** 散列函数 使用一个简单的取模法 */
    public int hashFun(int id) {
        return id % size;
    }

}

/** 创建EmpLinkedList，表示链表 */
class EmpLinkedList {
    // 头指针，指向第一个节点,因此我们这个链表的head是有效的，是直接指向第一个节点的，默认为null
    private Emp head;

    /**
     * 添加雇员到链表
     * 说明:
     *  1.假定当添加雇员时，id是自增的，即id的分配总是从小到大，因此把新增节点放到链表最后
     */
    public void add(Emp emp) {
        // 如果是添加第一个节点
        if (head == null) {
            head = emp;
            return;
        }
        // 如果不是第一个节点,则使用一个辅助变量(指针)，帮助定位到最后
        Emp curEmp = head;
        while (true) {
            // 找到链表最后一个
            if (curEmp.next == null) {
                break;
            }
            // 后移
            curEmp = curEmp.next;
        }
        // 退出循环后直接将emp加入链表
        curEmp.next = emp;
    }

    /** 删除节点 */
    public void remove(int id) {
        if (head == null) {
           throw new RuntimeException("linked list is empty");
        }
        // 判断头节点
        if (head.id == id) {
            if (head.next == null) {
                head = null;
                return;
            }
            head = head.next;
        }
        // 辅助变量
        Emp curEmp = head;
        while (true) {
            // 找到要删除节点的上一个节点
            if (curEmp.next.id == id) {
                // 被删除节点的上一个节点的next指向要删除节点的下一个节点
                curEmp.next = curEmp.next.next;
            }
            curEmp = curEmp.next;
        }
    }

    /** 遍历链表节点信息 */
    public void list(int no) {
        if (head == null) {
            // 说明链表为空
            System.out.print("\n第" + (no + 1) + " 链表为空");
            return;
        }
        System.out.print("\n第" + (no + 1) + " 链表的信息为=====>");
        // 辅助变量
        Emp curEmp = head;
        while (true) {
            System.out.printf(" id =%d name=%s\t", curEmp.id, curEmp.name);
            if (curEmp.next == null) {
                // 说明curEmp是最后的节点
                break;
            }
            // 后移，遍历
            curEmp = curEmp.next;
        }

    }


    /**
     * 根据id查询节点信息
     * 找到返回节点对象，没有找到返回null
     */
    public Emp findEmpById(int id) {
        // 判断链表是否为空
        if (head == null) {
            System.out.print("链表为空");
            return null;
        }
        // 辅助变量
        Emp curEmp = head;
        while (true) {
            if (curEmp.id == id) {
                return curEmp;
            }
            // 后移
            curEmp = curEmp.next;
            if (curEmp.next == null) {
                // 没找到返回null
                return null;
            }
        }

    }



}
