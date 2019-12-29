package com.bighao.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/28 0:05
 * @Version 1.0
 *
 * 构建赫夫曼树 execl 1125
 *
 * 1.基本介绍
 *  (1)给定n个权值作为n个叶子结点，构造一棵二叉树，若该树的带权路径长度(wpl)达到最小，称这样的二叉树为最优二叉树，
 *  也称为哈夫曼树(Huffman Tree), 还有的书翻译为霍夫曼树。
 *  (2)赫夫曼树是带权路径长度【最短】的树，权值较大的结点离根较近。
 *
 * 2.赫夫曼树几个重要概念和举例说明
 *  (1)路径和路径长度：在一棵树中，从一个结点往下可以达到的孩子或孙子结点之间的通路，称为路径。
 *  通路中分支的数目称为路径长度。若规定根结点的层数为1，则从根结点到第L层结点的路径长度为L-1
 *  (2)结点的权及带权路径长度：若将树中结点赋给一个有着某种含义的数值，则这个数值称为该结点的权。
 *      结点的带权路径长度为：从根结点到该结点之间的路径长度与该结点的权的乘积
 *
 *  树的带权路径长度：树的带权路径长度规定为所有叶子结点的带权路径长度之和，记为WPL(weighted path length) ,
 *  权值越大的结点离根结点越近的二叉树才是最优二叉树。WPL最小的就是赫夫曼树
 *
 * 3.案例:
 *  {13, 7, 8, 3, 29, 6, 1}
 * 构成赫夫曼树的步骤：
 *  (1)从小到大进行排序, 将每一个数据，每个数据都是一个节点 ， 每个节点可以看成是一颗最简单的二叉树
 *  (2)取出根节点权值最小的两颗二叉树
 *  (3)组成一颗新的二叉树, 该新的二叉树的根节点的权值是前面两颗二叉树根节点权值的和
 *  (4)再将这颗新的二叉树，以根节点的权值大小 再次排序， 不断重复  1-2-3-4 的步骤，
 *     直到数列中，所有的数据都被处理，就得到一颗赫夫曼树
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        preOrder(root);
    }

    /** 创建赫夫曼数  返回创建好后的赫夫曼树的root节点 */
    public static Node createHuffmanTree(int[] arr) {
        // 第1步，为了操作方便
        // 遍历arr数组,将arr的每个元素构建成一个Node放入到ArrayList中
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }

        // 处理过程是一个循环的过程
        while (nodes.size() > 1) {
            // 排序 从小到大
            Collections.sort(nodes);

            // 取出根节点权值最小的两颗二叉树
            // (1)取出权值最小的节点(这个节点就看作是二叉树)
            Node leftNode = nodes.get(0);
            // (2)取出权值第二小的节点(这个节点就看作是二叉树)
            Node rightNode = nodes.get(1);

            // (3)构建一棵新的二叉树(父节点,其权值就是两个值相加)
            Node parent = new Node(leftNode.value + rightNode.value);
            parent.left = leftNode;
            parent.right = rightNode;

            // (4)从list中删除处理过的二叉树(或节点)
            nodes.remove(leftNode);
            nodes.remove(rightNode);

            // (5)将parent加入到list中
            nodes.add(parent);
        }
        //System.out.println("第一次处理后===>" + nodes);
        // 返回赫夫曼树的root节点
        return nodes.get(0);
    }


    /** 前序遍历 */
    public static void preOrder(Node root) {
        if (root == null)
            throw new RuntimeException("tree is empry");
        root.preOrder();
    }

}




/** 创建节点类 为了让node支持Collections的排序，让node实现Comparable接口*/
class Node implements Comparable<Node> {
    // 节点权值
    int value;
    // 指向左字节点
    Node left;
    // 指向右子节点
    Node right;

    public Node(int value) {
        this.value = value;
    }


    /** 前序遍历 */
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }


    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.value - o.value;
    }
}