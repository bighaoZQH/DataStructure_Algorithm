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
 * https://blog.51cto.com/yushiwh/2439518
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
        // 手动创建二叉树,目前先简单处理二叉树，后面再递归创建
        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node1.setRight(node4);
        node2.setLeft(node5);

        // 测试中序线索化二叉树
        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        //threadedBinaryTree.preThreadedNodes();
        //threadedBinaryTree.infixThreadedNodes();
        threadedBinaryTree.postThreadedNodes();
        // 查看结果
        TreeNode leftNode = node4.getLeft();
        TreeNode rightNode = node4.getRight();
        System.out.println("leftNode==> " + leftNode + " rightNode===> " + rightNode);

        System.out.println("使用线索化二叉树的方式遍历====>");
        //threadedBinaryTree.preThreadedList();
        //threadedBinaryTree.infixThreadedList();
        threadedBinaryTree.postThreadedList();


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
            // 找到起始节点
            node = node.getLeft();
        }
        while (node != null) {
            if (node.getRightType() == 1) {
                System.out.println(node);
                pre = node;
                node = node.getRight();
            } else {
                if (node.getRight() == pre) {
                    System.out.print(node + ", ");
                    if (node == root) {
                        return;
                    }
                    pre = node;
                    //node = node.getParent();
                } else {    //如果从左节点的进入则找到有子树的最左节点
                    node = node.getRight();
                    while ( node != null && node.getLeftType() == 0 ) {
                        node = node.getLeft();
                    }
                }
            }
        }
        System.out.println(pre);
    }

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


    /** 删除节点 */
    public boolean delNode(int no) {
        if (root != null) {
            // 判断root是不是要删除的节点
            if (root.getNo() == no) {
                root = null;
                return true;
            } else {
                return root.delNode(no);
            }
        } else {
            System.out.println("这是个空树");
            return false;
        }
    }


    /** 前序遍历 */
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /** 中序遍历 */
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /** 后序遍历 */
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /** 前序遍历查找 */
    public TreeNode preOrderSearch(int no) {
        return root == null ? null : root.preOrderSearch(no);
    }

    /** 中序遍历查找 */
    public TreeNode infixOrderSearch(int no) {
        return root == null ? null : root.infixOrderSearch(no);
    }

    /** 后序遍历查找 */
    public TreeNode postOrderSearch(int no) {
        return root == null ? null : root.postOrderSearch(no);
    }

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

    /**
     * 递归删除节点
     * 如果是叶子节点,就删除该节点,如果是非叶子节点,就删除该子树
     */
    public boolean delNode(int no) {
        // 如果当前节点的左子节点不为空，并且左子节点就是要删除的节点，就将this.left=null;并且就返回(结束递归删除)
        if (this.left != null && this.left.no == no) {
            this.left = null;
            return true;
        }
        // 如果当前节点的右子节点不为空，并且右子节点就是要删除的节点，就将this.right=null;并且就返回(结束递归删除)
        if (this.right != null && this.right.no == no) {
            this.right = null;
            return true;
        }
        // 向左递归
        if (this.left != null) {
            this.left.delNode(no);
        }
        //  向右递归
        if (this.right != null) {
            this.right.delNode(no);
        }
        return false;
    }

    /** 前序遍历 */
    public void preOrder() {
        // 先输出父节点
        System.out.println(this);
        // 递归向左子树前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        // 递归向右子树前序遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /** 中序遍历 */
    public void infixOrder() {
        // 递归向左子树中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        // 输出父节点(当前节点)
        System.out.println(this);
        // 递归向右子树中序遍历
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /** 后序遍历 */
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序遍历查找
     * @param no 编号
     * @return 找到返回该节点，没找到返回null
     */
    public TreeNode preOrderSearch(int no) {
        //System.out.println("前序遍历查找");
        // 比较当前节点是不是
        if (this.no == no) {
            return this;
        }
        // 如果不等则判断当前节点的左子节点是否为空，如果不为空，则递归前序查找
        TreeNode resNode = null;
        if (this.left != null) {
            resNode = this.left.preOrderSearch(no);
        }
        if (resNode != null) {
            // 左子树找到就返回
            return resNode;
        }
        // 左递归没找到，就右递归
        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }
        return resNode;
    }


    /** 中序遍历查找 */
    public TreeNode infixOrderSearch(int no) {
        TreeNode resNode = null;
        // 判断当前节点的左子节点是否为空，如果不为空，则递归中序查找
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }

        //System.out.println("中序遍历查找");
        // 如果没有找到就和当前节点比较，如果是就返回当前节点
        if (this.no == no) {
            return this;
        }

        // 否则右递归中序查找
        if (this.right != null) {
            resNode = this.right.infixOrderSearch(no);
        }
        return resNode;
    }


    /** 后序遍历查找 */
    public TreeNode postOrderSearch(int no) {
        TreeNode resNode = null;
        // 判断当前节点的左子节点是否为空，如果不为空，则递归后序查找
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }
        // 左子树找到就返回
        if (resNode != null) {
            return resNode;
        }

        // 如果左子树没找到，就向右子树递归
        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }

        //System.out.println("后序遍历查找");
        // 如果左右都没找到,就比较当前节点
        if (this.no == no) {
            return this;
        }

        return resNode;
    }
}



