package com.bighao.binarysorttree;

import lombok.Getter;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/29 19:50
 * @Version 1.0
 *
 * 二叉排序树(BST) 或叫二叉搜索树 execl 1308
 * 添加查找删除
 *
 * 二叉排序树：BST: (Binary Sort(Search) Tree), 对于二叉排序树的任何一个非叶子节点，
 * 要求左子节点的值比当前节点的值小，右子节点的值比当前节点的值大。
 * 特别说明：如果有相同的值，可以将该节点放在左子节点或右子节点
 *
 *
 * 二叉排序树 删除结点的 三种情况
 * int[] arr = {7, 3, 10, 12, 5, 1, 9, 0};
 *
 * 第一种情况:
 * 删除叶子节点 (比如：2, 5, 9, 12)
 * (1) 需求先去找到要删除的结点  targetNode
 * (2)  找到targetNode 的 父结点 parent
 * (3)  确定 targetNode 是 parent的左子结点 还是右子结点
 * (4)  根据前面的情况来对应删除
 *      左子结点 parent.left = null
 *      右子结点 parent.right = null;
 *
 * 第二种情况: 删除只有一颗子树的节点 比如 1
 * (1) 需求先去找到要删除的结点  targetNode
 * (2)  找到targetNode 的 父结点 parent
 * (3) 确定targetNode 的子结点是左子结点还是右子结点
 * (4) targetNode 是 parent 的左子结点还是右子结点
 * (5) 如果targetNode 有左子结点
 *      5. 1 如果 targetNode 是 parent 的左子结点
 *      parent.left = targetNode.left;
 *      5.2  如果 targetNode 是 parent 的右子结点
 *      parent.right = targetNode.left;
 * (6) 如果targetNode 有右子结点
 *      6.1 如果 targetNode 是 parent 的左子结点
 *      parent.left = targetNode.right;
 *      6.2 如果 targetNode 是 parent 的右子结点
 *      parent.right = targetNode.right
 *
 *
 * 情况三 ： 删除有两颗子树的节点. (比如：7, 3，10 )
 * (1) 需求先去找到要删除的结点  targetNode
 * (2)  找到targetNode 的 父结点 parent
 * (3)  从targetNode 的右子树找到最小的结点
 * (4) 用一个临时变量，将 最小结点的值保存 temp = 11
 * (5)  删除该最小结点
 * (6)  targetNode.value = temp
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 0};

        BinarySortTree binarySortTree = new BinarySortTree();
        // 循环的添加节点到二叉排序树
        for (int i = 0; i < arr.length; i++) {
            binarySortTree.add(new Node(arr[i]));
        }
        // 中序遍历二叉排序树
        binarySortTree.infixOrder();

        // 测试删除叶子节点
        binarySortTree.delNode(2);
        binarySortTree.delNode(5);
        binarySortTree.delNode(9);
        binarySortTree.delNode(12);
        binarySortTree.delNode(7);
        binarySortTree.delNode(3);
        binarySortTree.delNode(10);
        binarySortTree.delNode(1);
        System.out.println("\n删除节点后");
        binarySortTree.infixOrder();
    }
}




/** 二叉排序树 */
@Getter
class BinarySortTree {
    private Node root;

    /**
     * 删除以node为根节点的二叉排序树的最小节点(在node的左子树)，并返回其值
     * @param node 传入的节点(二叉排序树的根节点)
     * @return 返回的是以node为根节点的二叉排序树的最小节点的值
     * 注意 这个最小值一定是在node的左子树
     * 我的方法名叫delRightTreeMin意思是因为node是其父节点的右子树,我要删除的是其父节点的右子树(node)的最小值
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
            // 如果要删除的节点就是root且root没有子节点
            root = null;
            return;
        } else if (root.left == null && root.right != null && root.value == value) {
            // 如果root只有右子树
            root = root.right;
            return;
        } else if (root.left != null && root.right == null && root.value == value) {
            // 如果root只有左子树
            root = root.left;
            return;
        }

        // 处理删除非root节点
        // 1.先找到要删除的节点 targetNode
        Node targetNode = root.search(value);
        // 如果没有找到要删除的节点
        if (targetNode == null) {
            return;
        }
        // 2.找到targetNode的父节点
        Node parent = searchParent(value);
        // 【如果要删除的节点是叶子节点】
        if (targetNode.left == null && targetNode.right == null) {
            // 判断targetNode是父节点的左子节点还是右子节点
            if (parent.left != null && parent.left.value == value) {
                parent.left = null;
            } else if (parent.right != null && parent.right.value == value) {
                parent.right = null;
            }

        } else if (targetNode.left != null && targetNode.right != null) {
            // 【删除有两课子树的节点】 可以看画图理解下
            // 这时要么 找当前节点的右子树的最小节点(往右子树的左边找) 或 当前节点左子树的最大节点(往左子树的右边找)
            int minVal = delRightTreeMin(targetNode.right);
            targetNode.value = minVal;

        } else {
            // 【删除只有一颗子树的节点】
            // 如果要删除的节点有左子节点
            if (targetNode.left != null) {
                // 如果targetNode 是 parent的左子节点
                if (parent.left.value == value) {
                    parent.left = targetNode.left;
                } else {
                    // 如果targetNode 是 parent的右子节点
                    parent.right = targetNode.left;
                }

            } else {// 如果要删除的节点有右子节点
                // 如果targetNode 是 parent的左子节点
                if (parent.left.value == value) {
                    parent.left = targetNode.right;
                } else {
                    // 如果targetNode 是 parent的右子节点
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



/** 二叉排序树的节点 */
class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
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
