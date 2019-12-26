package com.bighao.tree;

import lombok.Data;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/25 10:01
 * @Version 1.0
 *
 * 简单的二叉树的前序,中序,后序遍历,遍历查找，删除
 * 非二叉排序树!!
 *
 * 二叉树遍历
 *     前序遍历: 先输出父节点，再遍历左子树和右子树
 *     中序遍历: 先遍历左子树，再输出父节点，再遍历右子树
 *     后序遍历: 先遍历左子树，再遍历右子树，最后输出父节点
 *     小结: 看输出父节点的顺序，就确定是前序，中序还是后序
 *
 * 遍历步骤:
 *     1.创建一个二叉树
 *
 *     2.前序遍历
 *     2.1 先输出当前节点(初始的时候是root节点)
 *     2.2 如果左子节点不为空，则递归继续前序遍历
 *     2.3 如果右子节点不为空，则递归继续前序遍历
 *
 *     3.中序遍历
 *     3.1 如果当前节点的左子节点不为空，则则递归中序遍历
 *     3.2 输出当前节点
 *     3.3 如果当前节点的右子节点不为空，则则递归中序遍历
 *
 *     4.后序遍历
 *     4.1 如果当前节点的左子节点不为空，则则递归后序遍历
 *     4.2 如果当前节点的右子节点不为空，则则递归后序遍历
 *     4.3 输出当前节点
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        // 先需要创建一棵二叉树
        BinaryTree binaryTree = new BinaryTree();
        // 创建需要的节点
        TreeNode root = new TreeNode(0, "我的老婆们");
        TreeNode node1 = new TreeNode(1, "杨超越");
        TreeNode node2 = new TreeNode(2, "裴秀智");
        TreeNode node3 = new TreeNode(3, "秦岚");
        TreeNode node4 = new TreeNode(4, "李宣美");
        // 新加节点，分析遍历顺序，目前不考虑排序
        TreeNode node5 = new TreeNode(5, "桥本有菜");

        // 说明，先手动创建该二叉树，后面再以递归的方式创建二叉树
        binaryTree.setRoot(root);
        root.setLeft(node1);
        root.setRight(node2);
        node1.setLeft(node3);
        node2.setLeft(node4);
        node2.setRight(node5);

        // 测试遍历
        System.out.println("========>前序遍历"); // 0,1,3,2,4  0,1,3,2,4,5
        binaryTree.preOrder();

        System.out.println("========>中序遍历"); // 3,1,0,2,4  3,1,0,4,2,5
        binaryTree.infixOrder();

        System.out.println("========>后序遍历"); // 3,1,4,2,0  3,1,4,5,2,0
        binaryTree.postOrder();

        // 测试遍历查找
        System.out.println("遍历查找===>");
        //TreeNode resNode = binaryTree.preOrderSearch(5); //6次
        TreeNode resNode = binaryTree.infixOrderSearch(5); //6次
        //TreeNode resNode = binaryTree.postOrderSearch(5); //4次
        System.out.println(resNode);

        // 删除节点
        System.out.println("离婚前=====>");
        binaryTree.preOrder();
        binaryTree.delNode(5);
        System.out.println("离婚后=====>");
        binaryTree.preOrder();

    }
}





/** 定义BinaryTree 二叉树 */
class BinaryTree {
    private TreeNode root;

    public void setRoot(TreeNode root) {
        this.root = root;
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
    private com.bighao.tree.TreeNode left;
    /** 右子节点，默认为空 */
    private com.bighao.tree.TreeNode right;

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
    public com.bighao.tree.TreeNode preOrderSearch(int no) {
        //System.out.println("前序遍历查找");
        // 比较当前节点是不是
        if (this.no == no) {
            return this;
        }
        // 如果不等则判断当前节点的左子节点是否为空，如果不为空，则递归前序查找
        com.bighao.tree.TreeNode resNode = null;
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
    public com.bighao.tree.TreeNode infixOrderSearch(int no) {
        com.bighao.tree.TreeNode resNode = null;
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
    public com.bighao.tree.TreeNode postOrderSearch(int no) {
        com.bighao.tree.TreeNode resNode = null;
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

