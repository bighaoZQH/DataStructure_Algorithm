package com.bighao.sparsearray;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/13 22:07
 * @Version 1.0
 *
 * 稀疏数组SparseArray
 * 图解在Execl文档中第336行起 video 7-9
 * 因为该二维数组的很多值是默认值0, 因此记录了很多没有意义的数据.->稀疏数组。
 *
 * 1.基本介绍
 *  当一个数组中大部分元素为０，或者为同一个值的数组时，可以使用稀疏数组来保存该数组。
 *
 * 2.稀疏数组的处理方法是:
 * 记录数组一共有几行几列，有多少个不同的值
 * 把具有不同值的元素的行列及值记录在一个小规模的数组中，从而缩小程序的规模
 *
 * 3.转换:
 * 二维数组 转 稀疏数组的思路
 *  1. 遍历  原始的二维数组，得到有效数据的个数 sum
 *  2. 根据sum 就可以创建 稀疏数组 sparseArr   int[sum + 1] [3] 3是固定 用来描述数据的 行号 列号 值
 *  3. 将二维数组的有效数据数据存入到 稀疏数组
 *          row   col   val
 *      0   11     11    2 --第一行用来保存原始的二维数组总共有几行 几列 几个非0的值
 *      1   1      2     1
 *      2   2      3     2
 *
 * 稀疏数组转原始的二维数组的思路
 *  1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
 *  2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可
 *
 *
 * 练习要求：
 * 在前面的基础上，将稀疏数组保存到磁盘上，比如 map.data
 * 恢复原来的数组时，读取map.data 进行恢复
 */
public class SparseArray {
    public static void main(String[] args) {
        // 模拟棋盘 创建一个原始的二维数组 11*11
        // 0-表示没有棋子 1-黑子 2-蓝子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;

        // 输出原始的二维数组
        System.out.println("原始的二维数组=======>");
        for(int[] row : chessArr1) {
            for(int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        /** 将二维数组 转 稀疏数组的思路 */
        // 1.先遍历二维数组 得到非0数据的个数
        int sum = 0;
        for(int i = 0; i < chessArr1.length; i++) {
            for(int j = 0; j < chessArr1[i].length; j++) {
                if(chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }
        System.out.println("sum=" + sum);

        // 2.创建对应的稀疏数组
        // 稀疏数组第一行需要来描述原来的二维数组，因此需要+1 3是稀疏数组固定用来描述原来数据的列数
        int sparseArr[][] = new int[sum + 1][3];
        // 给稀疏数组赋值
        sparseArr[0][0] = 11;
        sparseArr[0][1] = 11;
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值存放到sparseArr中
        int count = 0; // count 用于记录是第几个非0数据
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    count++; // 第一行用来存元数据
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = chessArr1[i][j];
                }
            }
        }

        // 输出稀疏数组的形式
        System.out.println("\n得到的稀疏数组为========>");
        for (int i = 0; i < sparseArr.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr[i][0], sparseArr[i][1],  sparseArr[i][2]);
        }
        System.out.println();


        /** 将稀疏数组 恢复成 原始的二维数组 */
        /*
          1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
          2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
        */

        // 1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int rowCount = sparseArr[0][0]; //原始数组的行数
        int colCount = sparseArr[0][1]; //原始数组的列数
        int chessArr2[][] = new int[rowCount][colCount];

        // 2.在读取稀疏数组后几行(从第二行开始)的数据，并赋给 原始的二维数组 即可
        for (int i = 1; i < sparseArr.length; i++) {
            int rowNum = sparseArr[i][0]; //原来数据的行号
            int colNum = sparseArr[i][1]; //原来数据的列号
            chessArr2[rowNum][colNum] = sparseArr[i][2];
        }

        // 输出恢复后的二维数组
        System.out.println("\n恢复后的二维数组");
        for(int[] row : chessArr2) {
            for(int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }


}
