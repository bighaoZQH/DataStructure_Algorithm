package com.bighao.avl;


/**
 * @Author: bighao周启豪
 * @Date: 2019/12/30 10:09
 * @Version 1.0
 *
 * 二叉平衡树
 *
 * 某结点的左子树与右子树的高度(深度)差即为该结点的平衡因子（BF,Balance Factor）。
 * 平衡二叉树上所有结点的平衡因子只可能是 -1，0 或 1。
 *
 * BST 存在的问题分析:
 * 左子树全部为空，从形式上看，更像一个单链表.
 * 插入速度没有影响
 * 查询速度明显降低(因为需要依次比较), 不能发挥BST的优势，因为每次还需要比较左子树，其查询速度比单链表还慢
 * 解决方案-平衡二叉树(AVL)
 *
 * 二叉平衡树基本介绍
 * 平衡二叉树也叫平衡二叉搜索树（Self-balancing binary search tree）又被称为AVL树， 可以保证查询效率较高。
 * 具有以下特点：它是一 棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
 * 平衡二叉树的常用实现方法有红黑树、AVL、替罪羊树、Treap、伸展树等。
 *
 *
 */
public class AvlTreeDemo {
    public static void main(String[] args) {
        //int[] arr = {4, 3, 6, 5, 7, 8}; //测试左旋转
        //int[] arr = {10, 12, 8, 9, 7, 6}; //测试右旋转
        int[] arr = {10, 11, 7, 6, 8, 9}; //测试双旋转
        // 创建一个AVL tree
        AvlTree avlTree = new AvlTree();
        // 添加节点(并处理平衡)
        for (int i = 0; i < arr.length; i++) {
            avlTree.add(new Node(arr[i]));
        }
        // 中序遍历
        System.out.println("中序遍历===>");
        avlTree.infixOrder();

        System.out.println("旋转之后===>");
        System.out.println("树的高度是==> " + avlTree.root.height()); //4 => 3
        System.out.println("树的左子树高度是==> " + avlTree.root.leftHeight()); //1 => 2
        System.out.println("树的右子树高度是==> " + avlTree.root.rightHeight()); //3 => 2
        System.out.println("旋转后的根节点是===>" + avlTree.root); //8
        // 验证是否正确
        System.out.println("双旋转后的根节点的左子节点===>" + avlTree.root.left);//7
        System.out.println("双旋转后的根节点的左子节点===>" + avlTree.root.left.left);//7


    }
}

/** 平衡树 */
class AvlTree {
    public Node root;

    /**
     * 删除以node为根节点的二叉排序树的最小节点(在node的左子树)，并返回其值
     * @param node 传入的节点(二叉排序树的根节点)
     * @return 返回的是以node为根节点的二叉排序树的最小节点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        // 循环查找左子节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        // 这时target就指向了最小节点,删除它
        delNode(target.value);
        return target.value;
    }

    /** 删除节点 */
    public void delNode(int value) {
        if (root == null) {
            return;
        }
        // 处理删除root节点
        if (root.left == null && root.right == null && root.value == value) {
            root = null;
            return;
        } else if (root.left == null && root.right != null && root.value == value) {
            root = root.right;
            return;
        } else if (root.left != null && root.right == null && root.value == value) {
            root = root.left;
            return;
        }

        // 处理删除非root节点
        Node targetNode = root.search(value);
        if (targetNode == null) {
            return;
        }
        Node parent = searchParent(value);
        // 【如果要删除的节点是叶子节点】
        if (targetNode.left == null && targetNode.right == null) {
            if (parent.left != null && parent.left.value == value) {
                parent.left = null;
            } else if (parent.right != null && parent.right.value == value) {
                parent.right = null;
            }

        } else if (targetNode.left != null && targetNode.right != null) {
            // 【删除有两课子树的节点】
            int minVal = delRightTreeMin(targetNode.right);
            targetNode.value = minVal;

        } else {
            // 【删除只有一颗子树的节点】
            if (targetNode.left != null) {
                if (parent.left.value == value) {
                    parent.left = targetNode.left;
                } else {
                    parent.right = targetNode.left;
                }

            } else {
                if (parent.left.value == value) {
                    parent.left = targetNode.right;
                } else {
                    parent.right = targetNode.right;
                }
            }
        }

    }



    /** 查找要删除的节点 */
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    /** 查找要删除节点的父节点 */
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    /** 添加节点 */
    public void add(Node node) {
        if (root == null) {
            root = node;
        } else {
            root.add(node);
        }
    }

    /** 中序遍历 */
    public void infixOrder() {
        if (root == null) {
            throw new RuntimeException("tree is empty");
        }
        root.infixOrder();
    }
}


/** 树的节点 */
class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
    }

    /** 右旋转 */
    private void rightRotate() {
        Node newNode = new Node(value);
        newNode.right = right;
        newNode.left = left.right;
        value = left.value;
        left = left.left;
        right = newNode;
    }

    /** 左旋转 */
    private void leftRotate() {
        // 创建新的节点,为当前根节点的值
        Node newNode = new Node(value);
        // 把新的节点的左子树 设置成 当前节点的左子树
        newNode.left = left;
        // 把新的节点的右子树 设置成 当前节点的右子树的左子树
        newNode.right = right.left;
        // 把当前节点的值替换成 右子节点的值
        value = right.value;
        // 把当前节点的右子树(节点) 设置成 右子节点的右子树
        right = right.right;
        // 把当前节点的左子树(节点) 设置成 新的节点
        left = newNode;
    }


    /** 返回左子树的高度 */
    public int leftHeight() {
        if (left == null) {
            return 0;
        }
        return left.height();
    }

    /** 返回右子树的高度 */
    public int rightHeight() {
        if (right == null) {
            return 0;
        }
        return right.height();
    }

    /** 返回当前节点的高度(以该节点看做为根节点的树的高度) */
    public int height () {
        return Math.max(left == null ? 0 : left.height(), right == null ? 0 : right.height()) + 1;
    }

    /**
     * 查找要删除的节点
     * @param value 要删除的节点的值
     * @return
     */
    public Node search(int value) {
        if (value == this.value) {
            // 找到节点
            return this;
        } else if (value < this.value) {
            if (this.left == null) {
                return null;
            }
            // 向左子树递归查找
            return this.left.search(value);
        } else {
            // 如果查找的值不小于当前节点
            if (this.right == null) {
                return null;
            }
            // 向右子树递归查找
            return this.right.search(value);
        }
    }

    /** 查找要删除节点的父节点 */
    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value) ||
                (this.right != null && this.right.value == value) ) {

            // 如果当前节点就是要删除的父节点,就返回
            return this;
        } else {
            // 如果查找的值小于当前节点的值,并且当前节点的左子节点不为空
            if (value < this.value && this.left != null) {
                // 递归向左子树查找
                return this.left.searchParent(value);
            } else if (value >= this.value && this.right != null) {
                return this.right.searchParent(value);
            } else {
                // 没有找到就返回null
                return null;
            }
        }
    }


    /** 递归添加节点，满足二叉排序树的要求 */
    public void add(Node node) {
        if (node == null)
            throw new RuntimeException("node is empty");
        // 判断传入节点的值和当前子树的根节点的值的大小
        if (node.value < this.value) {
            // 如果当前节点的左子节点为null，就直接挂在左子节点
            if (this.left == null) {
                this.left = node;
            } else {
                // 不为空就向左子树递归添加
                this.left.add(node);
            }
        } else { // 如果添加的值大于等于当前节点的值
            if (this.right == null) {
                this.right = node;
            } else {
                // 向右子树递归添加
                this.right.add(node);
            }
        }

        // 当添加完一个节点后，如果：(右子树的高度 - 左子树的高度) > 1，就要左旋转
        if (rightHeight() - leftHeight() > 1) {
            // 如果 当前节点的右子树的左子树高度 大于 它的右子树的右子树的高度
            if (right != null && right.leftHeight() > right.rightHeight()) {
                // 先对当前节点的右子树 进行 右旋转
                right.rightRotate();
            }
            // 再进行左旋转
            leftRotate();
            // 此时可以return，因为此时已经平衡
            return;
        }
        // 当添加完一个节点后，如果：(左子树的高度 - 右子树的高度) > 1，就要右旋转
        if (leftHeight() - rightHeight() > 1) {
            // 如果 当前节点的左子树的右子树高度 大于 它的左子树的左子树的高度
            if (left != null && left.rightHeight() > left.leftHeight()) {
                // 先对当前节点的左节点(左子树)进行左旋转
                left.leftRotate();
            }
            // 再进行右旋转
            rightRotate();
        }
    }

    /** 中序遍历 */
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    @Override
    public String toString() {
        return "Node{" + "value=" + value + '}';
    }
}

