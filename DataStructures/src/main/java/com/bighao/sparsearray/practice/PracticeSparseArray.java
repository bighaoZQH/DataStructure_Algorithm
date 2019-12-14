package com.bighao.sparsearray.practice;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/15 1:15
 * @Version 1.0
 *
 * 练习稀疏数组
 */
public class PracticeSparseArray {
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
        // 1.先遍历原来的二维数据获取非0数的个数
        int sum = 0;
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }

        // 2.定义稀疏数组
        int[][] sparseArr = new int[sum + 1][3];
        // 3.初始化稀疏数组的第一行数据
        sparseArr[0][0] = chessArr1.length;
        sparseArr[0][1] = chessArr1[0].length;
        sparseArr[0][2] = sum;

        // 遍历二维数组，将非0的值存放到sparseArr中
        int count = 0; // count 用于记录是第几个非0数据
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    // 第一行用来存元数据
                    count++;
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

        /** 将稀疏数组 恢复成 原始二维数组的思路 */
        // 1.先获取原始二维数据的大小
        int rowCount = sparseArr[0][0];
        int colCount = sparseArr[0][1];
        int[][] chessArr2 = new int[rowCount][colCount];
        // 遍历稀疏数据，将数据放入原始的二维数组中
        for (int i = 1; i < sparseArr.length; i++) {
            // 数据的行号
            int rowNum = sparseArr[i][0];
            // 数据的列号
            int colNum = sparseArr[i][1];
            // 数据的值
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
