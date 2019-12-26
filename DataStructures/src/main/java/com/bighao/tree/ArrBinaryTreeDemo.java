package com.bighao.tree;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/25 17:22
 * @Version 1.0
 *
 * 数组方式存储二叉树,并遍历
 * 
 * 顺序存储二叉树的概念
 * 基本说明
 *  从数据存储来看，数组存储方式和树的存储方式可以相互转换，即数组可以转换成树，树也可以转换成数组
 *
 * 顺序存储二叉树的特点:
 *  顺序二叉树通常只考虑完全二叉树
 *  第n个元素的左子节点为  2 * n + 1
 *  第n个元素的右子节点为  2 * n + 2
 *  第n个元素的父节点为  (n-1) / 2
 *  n : 表示二叉树中的第几个元素(按0开始编号如图所示)
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        System.out.println("前序遍历===>");
        arrayBinaryTree.preOrder();
        System.out.println("中序遍历===>");
        arrayBinaryTree.infixOrder();
        System.out.println("后序遍历===>");
        arrayBinaryTree.postOrder();

    }
}







/** ArrayBinaryTree，实现顺序存储二叉树遍历 */
class ArrayBinaryTree {
    // 存储二叉树节点的数组
    private int[] arr;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder() {
        this.preOrder(0);
    }

    public void infixOrder() {
        this.infixOrder(0);
    }

    public void postOrder() {
        this.postOrder(0);
    }

    /**
     * 顺序存储二叉树的一个前序遍历
     * @param index 数组下标
     */
    public void preOrder(int index) {
        // 校验数组是否有节点
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空,不能按照二叉树的前序遍历");
            return;
        }
        // 输出当前元素
        System.out.println(arr[index]);
        // 向左递归遍历
        if ((index * 2 + 1) < arr.length) {
            preOrder(index * 2 + 1);
        }
        // 向右递归遍历
        if ((index * 2 + 2) < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    public void infixOrder(int index) {
        // 校验数组是否有节点
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空,不能按照二叉树的前序遍历");
            return;
        }
        // 向左递归遍历
        if ((index * 2 + 1) < arr.length) {
            preOrder(index * 2 + 1);
        }

        // 输出当前元素
        System.out.println(arr[index]);

        // 向右递归遍历
        if ((index * 2 + 2) < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    public void postOrder(int index) {
        // 校验数组是否有节点
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空,不能按照二叉树的前序遍历");
            return;
        }
        // 向左递归遍历
        if ((index * 2 + 1) < arr.length) {
            preOrder(index * 2 + 1);
        }

        // 向右递归遍历
        if ((index * 2 + 2) < arr.length) {
            preOrder(index * 2 + 2);
        }

        // 输出当前元素
        System.out.println(arr[index]);
    }

}