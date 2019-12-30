# Data Structure And Algorithm 数据结构和算法(Java语言描述)

## 说明
**这个项目主要是为了自己以后复习使用**

2019-12-13开始学习韩顺平老师的数据结构和算法的代码以及笔记  
本人目前是1年工作经验的java开发，在小公司搬砖(但有一颗去大厂的心)

周一到周五一般没有时间学习，主要学习和代码笔记更新在周末  



目前在学习数据结构的时候没有学算法，因此数据结构部分的代码没有用到算法或者说没有去考虑性能，只是让自己理解数据结构，算法等学到了就会用上  
另外为了方便自己以后复习，代码中的注释可能比较多，所以你们看着会比较乱  

## practice包和improvement大家忽略就行

**practice包下的代码都是自己练习的代码，因为跟着老师写完为了加深印象和理解思路，有的不熟的自己会再手写一遍,因为平时上班用的电脑不一定是自己的，为了做个备份也就上传了，大家忽略就行**

**improvement包是打算自己学完算法后为了加深印象,有空时手写实现一些加泛型的数据结构的，方法有的也会比基础版的多一些，目前做的很少**



**个人认为学习的时候，代码不是要全记下来，但是原理和思路一定要理解清楚**

**具体的笔记都在代码注释里**



## 目前已经学习的
## 数据结构:

```
数据结构包括：线性结构和非线性结构。
1.线性结构:
线性结构作为最常用的数据结构，其特点是数据元素之间存在一对一的线性关系
线性结构有两种不同的存储结构，即顺序存储结构和链式存储结构。顺序存储的线性表称为顺序表，顺序表中的存储元素是连续的
链式存储的线性表称为链表，链表中的存储元素不一定是连续的，元素节点中存放数据元素以及相邻元素的地址信息
线性结构常见的有：数组、队列、链表和栈

2.非线性结构:
非线性结构包括：二维数组，多维数组，广义表，树结构，图结构
```



  ### 1.稀疏数组和数组模拟队列

    1).稀疏数组(sparsearray包下)
    2).数组实现单向队列和环形队列(queue包下)
  ### 2.链表(linkedlist包下)
    1).单向链表的增删改查，顺序插入，统计，反转，反向打印，顺序合并(SingleLinkedListDemo.java)
    2).双向链表的增删改查，顺序插入(DoubleLinkedListDemo.java)
    3).单向环形链表的应用(Josepfu.java)
    4).单向环形链表解决约瑟夫问题(Josepfu.java)

##  3.栈(stack包下)

```
1).数组实现栈(ArrayStackDemo.java)
2).单向链表实现栈(LinkStackDemo.java)
3).中缀表达式(可略过)
4.)中缀转后缀表达式，以及后缀表达式(逆波兰表达式)实现简单的计算器(ReversePolish.java)
```

## 4.递归(recursion包下)

```
1).递归的规则与迷宫回溯案例(RecursionTest.java, MiGong.java)
2).八皇后问题(回溯算法来解决 Queen8.java)
```

## 5.八大排序算法(sort包下)

```
1).冒泡排序(BubbleSort.java)
2).选择排序(SelectSort.java)
3).插入排序(InsertSort.java)
4).希尔排序(交换式和移位式 ShellSort.java)
5).快速排序(QuickSort.java)
6).归并排序(MergetSort.java)
7).基数排序(RadixSort.java)
8).堆排序(HeapSort.java) 堆排序需要懂二叉树的知识
```

## 6.查找算法(search包下)

```
1).二分查找(BinarySearch.java)
2).插值查找(InsertValueSearch.java)
3).斐波那契查找算法(FibonacciSearch.java)
```

## 7.哈希表(hashtab包下)

```
1).基于数组和链表实现简答的哈希表
2).哈希表的增删改查
```

##  8.简单二叉树(tree包下)

```
1).简单的二叉树的前序,中序,后序遍历,遍历查找，删除(非二叉排序树 BinaryTreeDemo.java)
2).顺序存储二叉树(数组方式存储二叉树)，并遍历(ArrBinaryTreeDemo.java)
3).前序,中序,后序线索化二叉树，遍历前中后序线索化二叉树(ThreadedBinaryTreeDemo.java)
```

## 9.赫夫曼树与赫夫曼编码

```
1).赫夫曼树(huffmantree包下HuffmanTree.java)
2).赫夫曼编码与文件的压缩和解压缩(huffmancode包下HuffmanCode.java)
```

## 10.二叉排序树(binarysorttree包下)

```
1).二叉排序树(BTS)的添加查找删除(BinarySortTreeDemo.java)
```

