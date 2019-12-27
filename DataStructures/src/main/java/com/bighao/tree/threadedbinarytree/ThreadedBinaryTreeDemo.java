package com.bighao.tree.threadedbinarytree;

import lombok.Data;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/26 10:06
 * @Version 1.0
 *
 * 线索化二叉树
 * 前序，中序，后序
 * https://www.cnblogs.com/lishanlei/p/10707830.html
 * https://blog.csdn.net/jisuanjiguoba/article/details/81092812
 * https://blog.51cto.com/yushiwh/2439518 这个文档写的后序遍历线索化二叉树的代码 我测试后发现有问题，我写的测试后没问题
 *
 *
 * 遍历线索化二叉树
 *  前序:
 *      1.先打印当前节点
 *      2.遍历左子节点,打印
 *      3.如果没有左子节点，会有线索,就根据线索遍历打印
 *
 *  中序:
 *      1.先找到左子树的起始节点，然后打印起始节点
 *      2.根据后继线索依次打印
 *      3.如果没有后继线索，则说当前节点是中间节点(即父节点),就将当前节点指向其右子树
 *      5.重复上面操作
 *
 *  后序:
 *      备注:后序遍历线索化二叉树的话需要一个parnet变量来指向父节点,中序和前序不需要
 *      1.先从root节点的左子树开始,找到左子树的起始节点
 *      2.打印左子树的起始节点，然后根据这个节点的后继线索开始遍历打印
 *      3.如果当前节点没有后继线索了，就将当前节点指向其父节点
 *          说明:此时当前节点的父节点是一个有完整左右子树的节点
 *              为什么? 因为这是后序线索化后的二叉树，一个节点如果没有完整的左右子树,也是会被连在线索中的
 *      4.找到当前节点的右子树的起始节点，然后根据这个节点的后继线索开始遍历打印
 *      5.重复3步骤
 *      6.如果3步骤的当前节点的父节点是root就打印root节点并退出
 *
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        // 创建需要的节点
        TreeNode root = new TreeNode(1, "我的老婆们");
        TreeNode node1 = new TreeNode(3, "杨超越");
        TreeNode node2 = new TreeNode(6, "裴秀智");
        TreeNode node3 = new TreeNode(8, "秦岚");
        TreeNode node4 = new TreeNode(10, "李宣美");
        TreeNode node5 = new TreeNode(14, "桥本有菜");
        TreeNode node6 = new TreeNode(15, "桥本有菜2");
        TreeNode node7 = new TreeNode(16, "桥本有菜3");
        TreeNode node8 = new TreeNode(17, "桥本有菜4");
        TreeNode node9 = new TreeNode(18, "桥本有菜5");


        // 手动创建二叉树,目前先简单处理二叉树，后面再递归创建
        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        node2.setLeft(node5);
        /*node4.setLeft(node7);
        node5.setLeft(node6);
        node3.setLeft(node8);
        node3.setRight(node9);*/


        // 测试线索化二叉树
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        //threadedBinaryTree.preThreadedNodes();
        threadedBinaryTree.infixThreadedNodes();
        //threadedBinaryTree.postThreadedNodes();

        // 查看结果
        /*TreeNode leftNode = node4.getLeft();
        TreeNode rightNode = node4.getRight();
        System.out.println("leftNode==> " + leftNode + " rightNode===> " + rightNode);*/

        System.out.println("使用线索化二叉树的方式遍历====>");
        //threadedBinaryTree.preThreadedList();
        threadedBinaryTree.infixThreadedList();

        //如果是后序，需要创建二叉树的时候，将parent进行保存。这个是用于后序二叉树的遍历的
        node1.setParent(root);
        node2.setParent(root);
        node3.setParent(node1);
        node4.setParent(node1);
        node5.setParent(node2);
        node6.setParent(node5);
        node7.setParent(node4);
        node8.setParent(node3);
        node9.setParent(node3);

        //threadedBinaryTree.postThreadedList();


    }
}






/** 定义ThreadedBinaryTree 实现线索化二叉树 */
class ThreadedBinaryTree {
    private TreeNode root;
    // 为了实现线索化，需要创建一个指向当前节点的前驱节点的变量
    private TreeNode pre = null;

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    public void preThreadedNodes() {
        this.preThreadedNodes(root);
    }

    public void infixThreadedNodes() {
        this.infixThreadedNodes(root);
    }

    public void postThreadedNodes() {
        this.postThreadedNodes(root);
    }


    /** ===========================遍历线索化二叉树start================================== */

    /** 遍历前序线索化二叉树 */
    public void preThreadedList() {
        TreeNode node = root;
        while (node != null) {
            // 为左子节点时
            while (node.getLeftType() == 0) {
                System.out.println(node);
                node = node.getLeft();
            }
            // 没有左子节点时,会有后驱节点,向后驱节点移动
            System.out.println(node);
            // 替换这个遍历的结点
            node = node.getRight();
        }
    }

    /** 遍历中序线索化二叉树 */
    public void infixThreadedList() {
        // 定义一个变量，存储当前遍历的节点,从root开始
        TreeNode node = root;
        while (node != null) {
            // 循环找到leftType==1的节点, leftType==1说明该节点是按照线索化处理后的有效节点
            while (node.getLeftType() == 0) {
                node = node.getLeft();
            }
            // 打印当前这个节点,这个节点就是遍历的第一个节点
            System.out.println(node);
            // 如果当前节点的右指针指向的是后继节点，就一直输出
            while (node.getRightType() == 1) {
                // 获取到当前节点的后继节点
                node = node.getRight();
                System.out.println(node);
            }
            // 后移node
            node = node.getRight();
        }
    }


    /** 遍历后序线索化二叉树 */
    public void postThreadedList() {
        TreeNode node = root;
        while (node != null && node.getLeftType() == 0) {
            // 先遍历左子树,找到左子树起始节点
            node = node.getLeft();
        }

        while (node != null) {
            // 如果有后序线索
            if (node.getRightType() == 1) {
                System.out.println(node);
                node = node.getRight();
            } else {
                System.out.println(node);
                if (node == root) {
                    return;
                }
                // 让node指向其父节点，开始往父节点的右子树遍历，此时node指向一个有完整左右子树的父节点(不一定是root节点)
                node = node.getParent();
                if (node.getRight() == null) {
                    // 如果进这个if,说明此时节点为root，如果root的右子树为空,就输出root,结束遍历
                    // 为什么? 因为这是后序线索化后的二叉树，非root节点如果没有完整的左右子树,
                    // 也是会被连在线索中的，因此除了root都不会进入到这个if里来
                    System.out.println(node);
                    //return; //这个return可写可不写，不写的话最外层的while循环判断也会退出
                }
                node = node.getRight();
                // 找到右子树的起始节点
                while (node != null && node.getLeftType() == 0) {
                    node = node.getLeft();
                }
            } // end of else
        }
    }

    /** ###########################遍历线索化二叉树end################################ */

    /** ===========================线索化二叉树start================================== */

    /** 对二叉树进行前序线索化 */
    public void preThreadedNodes(TreeNode node) {
        if (node == null) return;
        // 线索化当前节点
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }
        pre = node;
        // 这里和中序不一样，需要判断是否已经设置过前驱和后驱节点,否则会死循环
        if (node.getLeftType() == 0) {
            preThreadedNodes(node.getLeft());
        }
        if (node.getRightType() == 0) {
            preThreadedNodes(node.getRight());
        }

    }


    /**
     * 对二叉树进行中序线索化
     * @param node 当前需要线索化的节点
     */
    public void infixThreadedNodes(TreeNode node) {
        if (node == null) return;
        // 1.先线索化左子树
        infixThreadedNodes(node.getLeft());
        // 2.线索化当前节点
        // 处理当前节点的前驱节点(当pre指向当前节点的前置节点,node指向当前节点时)
        if (node.getLeft() == null) {
            // 让当前节点的左指针指向前驱节点
            node.setLeft(pre);
            // 修改当前节点的左指针的类型
            node.setLeftType(1);
        }
        // 处理后继节点(当pre指向当前节点的时候，node指向当前节点的后置节点时,因为要先找到它的后置节点)
        if (pre != null && pre.getRight() == null) {
            // 让前驱节点的右指针指向当前节点
            pre.setRight(node);
            pre.setRightType(1);
        }
        // 每处理一个节点后，让当前节点是下一个节点的前驱节点
        pre = node;

        // 3.再线索化右子树
        infixThreadedNodes(node.getRight());
    }


    /** 对二叉树进行后序线索化 */
    public void postThreadedNodes(TreeNode node) {
        if (node == null) return;
        // 1.先线索化左子树
        postThreadedNodes(node.getLeft());
        // 2.再线索化右子树
        postThreadedNodes(node.getRight());
        // 3.线索化当前节点
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }
        pre = node;
    }

    /** ###########################线索化二叉树end################################ */


}





/** TreeNode 树的节点 */
@Data
class TreeNode {

    private int no;
    private String name;
    /** 左子节点，默认为空 */
    private TreeNode left;
    /** 右子节点，默认为空 */
    private TreeNode right;
    /** 父节点(后序线索化遍历需要) */
    private TreeNode parent;


    /** 左指针域类型 0：指向子节点、1：前驱或后继线索 */
    private int leftType;
    /** 右指针域类型 0：指向子节点、1：前驱或后继线索 */
    private int rightType;

    public TreeNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}



