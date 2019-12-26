package com.bighao.linkedlist;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/15 14:08
 * @Version 1.0
 *
 * 双向链表
 * execl 588 ppt 34
 *
 * 分析 双向链表的遍历，添加，修改，删除的操作思路===》代码实现
 * 1. 遍历 方和 单链表一样，只是可以向前，也可以向后查找
 * 2. 添加 (默认添加到双向链表的最后)
 * (1) 先找到双向链表的最后这个节点
 * (2) temp.next = newHeroNode
 * (3) newHeroNode.pre = temp;
 * 3. 修改 思路和 原来的单向链表一样.
 * 4. 删除
 * (1) 因为是双向链表，因此，我们可以实现自我删除某个节点
 * (2) 直接找到要删除的这个节点，比如temp
 * (3)  temp.pre.next = temp.next
 * (4) temp.next.pre = temp.pre;
 *
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        System.out.println("双向链表的测试=========>");
        // 先创建节点
        HeroNode2 hero1 = new HeroNode2(1, "杨超越", "小超越");
        HeroNode2 hero2 = new HeroNode2(2, "裴秀智", "秀智");
        HeroNode2 hero3 = new HeroNode2(3, "秦岚", "白月光");
        HeroNode2 hero4 = new HeroNode2(4, "宣美", "腿精");
        // 创建一个双向链表对象
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        /*doubleLinkedList.add(hero1);
        doubleLinkedList.add(hero2);
        doubleLinkedList.add(hero3);
        doubleLinkedList.add(hero4);*/
        // 顺序添加
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero3);
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.myAddByOrder(hero2);
        doubleLinkedList.myAddByOrder(hero4);

        doubleLinkedList.list();

        // 修改
        HeroNode2 newHeroNode = new HeroNode2(4, "长腿老婆宣美", "国民腿精");
        doubleLinkedList.update(newHeroNode);
        System.out.println("\n修改后的链表情况======>");
        doubleLinkedList.list();

        // 删除
        doubleLinkedList.remove(2);
        System.out.println("\n删除后的链表情况======>");
        doubleLinkedList.list();

    }
}






/** 创建一个双向链表的类 */
class DoubleLinkedList {
    // 先初始化一个头节点,头节点不要动,不存放具体数据
    private HeroNode2 head = new HeroNode2(0, null, null);

    public HeroNode2 getHead() {
        return head;
    }

    /** 自己写的顺序添加 也没问题 其实和下面的addByOrder()方法一个思路*/
    public void myAddByOrder(HeroNode2 heroNode) {
        // temp 是位于 添加位置的前一个节点
        HeroNode2 temp = head;
        while (true) {
            if (temp.next == null) { // 说明temp已经在链表的最后,那此时要添加的元素就要放到链表的最后
                // 将当前节点和temp双向链接起来
                heroNode.pre = temp;
                temp.next = heroNode;
                break;
            }
            if (temp.next.no > heroNode.no) { // 位置找到，就在temp的后面插入
                // 将当前节点和temp.next节点双向链接起来
                heroNode.next = temp.next;
                temp.next.pre = heroNode;
                // 将当前节点和temp双向链接起来
                heroNode.pre = temp;
                temp.next = heroNode;
                break;
            } else if (temp.next.no == heroNode.no) { // 希望添加的heroNode的编号已然存在
                System.out.printf("准备插入元素的编号%d 已经存在，不能加入\n", heroNode.no);
                break;
            }
            // 后移遍历当前链表
            temp = temp.next;
        }
    }


    /**
     * 顺序添加：根据元素的编号排序，编号不能重复
     * 先找到要添加节点的后一个节点
     */
    public void addByOrder(HeroNode2 heroNode) {
        // temp 是位于 添加位置的前一个节点
        HeroNode2 temp = head;
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
            // 如果temp.next不为空，那么新节点插入到temp和temp.next的中间
            if(temp.next != null) {
                // 将当前节点和temp.next节点双向链接起来
                heroNode.next = temp.next;
                temp.next.pre = heroNode;
            }
            // 如果temp.next为空,就直接插入到temp后面,不与temp.next进行双向链接，否则空指针
            // 将当前节点和temp双向链接起来
            heroNode.pre = temp;
            temp.next = heroNode;
        }
    }

    /** 添加节点到双向链表的最后 */
    public void add(HeroNode2 hereNode ) {
        // 因为head节点不能动，因此我们需要一个变量temp来辅助我们迭代
        HeroNode2 temp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if (temp.next == null) {
                break;
            }
            // 如果没有找到，将temp后移
            temp = temp.next;
        }
        // 当退出while时，temp就指向了链表最后的节点
        // 形成双向链表
        temp.next = hereNode;
        hereNode.pre = temp;
    }

    /** 修改一个节点的内容 双向链表的节点内容修改和单向链表思路一样 */
    public void update(HeroNode2 newHeroNode) {
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 找到需要修改的节点，根据no编号
        // 先定义一个辅助迭代变量
        HeroNode2 temp = head.next;
        // 表示是否找到该节点
        boolean flag = false;
        while (true) {
            if (temp == null) {
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
     * 从双向链表中删除一个节点
     * 对于双向链表，我们可以【直接找到】要删除的节点，进行自我删除即可
     * 而单项链表是找到待删除节点的【前一个】节点
     */
    public void remove(int no) {
        // 判断当前链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 双向链表不需要从头节点开始找
        HeroNode2 temp = head.next;
        // 标识是否找到待删除节点的前一个节点
        boolean flag = false;
        while (true) {
            if (temp == null) {
                // 此时temp已经是链表最后一个节点的next
                break;
            }
            if (temp.no == no) {
                // 找到了待删除节点的前一个节点
                flag = true;
                break;
            }
            // 后移temp
            temp = temp.next;
        }

        if (flag) {
            // 删除节点
            temp.pre.next = temp.next;
            // 如果要删除的是最后一个节点，就不需要执行下面的代码，否则出现空指针异常
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("要删除的 %d 节点不存在\n", no);
        }
    }


    /** 遍历双向链表的方法 */
    public void list() {
        if(head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头节点不能动，因此我们需要一个辅助迭代变量来遍历
        HeroNode2 temp = head.next;
        while (true) {
            if (temp == null) {
                break;
            }
            System.out.println(temp);
            temp = temp.next;
        }
    }

}







/** 定义HereNode，每个HeroNode对象就是一个节点 */
class HeroNode2 {
    public int no;
    public String name;
    public String nickName;
    // 指向下一个节点，默认为null
    public HeroNode2 next;
    // 指向前一个节点，默认为null
    public HeroNode2 pre;

    // 构造器
    public HeroNode2(int no, String name, String nickName) {
        this.no = no;
        this.name = name;
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "HereNode{" + "no=" + no + ", name='" + name + '\'' + ", nickName='" + nickName + '}';
    }

}


