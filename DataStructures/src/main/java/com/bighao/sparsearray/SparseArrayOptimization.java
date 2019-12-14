package com.bighao.sparsearray;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/13 22:07
 * @Version 1.0
 */

/**
 * 稀疏数组
 * 加上练习
 */
public class SparseArrayOptimization {
    public static void main(String[] args) {
        // 模拟棋盘 创建一个原始的二维数组 11*11
        // 0-表示没有棋子 1-黑子 2-蓝子
        int[][] chessArr1 = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;
        // 将原来的二维数组转为稀疏数组
        int[][] sparseArr = getSparseArray(chessArr1);
        // 将稀疏数组保存到磁盘
        saveSparseArrToFile(sparseArr);

        // 读取文件里的内容转为稀疏数组
        int[][] sparseArr2 = readFileToSparseArr();
        // 将稀疏数组恢复为原来的二维数组
        int[][] chessArr2 = recoverArray(sparseArr2);

        // 输出恢复后的二维数组
        System.out.println("\n恢复后的二维数组");
        for(int[] row : chessArr2) {
            for(int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }


    /** 将稀疏数组恢复为原来的二维数组 */
    private static int[][] recoverArray(int[][] sparseArr2) {
        // 1.先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组
        int rowCount = sparseArr2[0][0]; //原始数组的行数
        int colCount = sparseArr2[0][1]; //原始数组的列数
        int chessArr2[][] = new int[rowCount][colCount];

        // 2.在读取稀疏数组后几行(从第二行开始)的数据，并赋给 原始的二维数组 即可
        for (int i = 1; i < sparseArr2.length; i++) {
            // 原来数据的行号
            int rowNum = sparseArr2[i][0];
            // 原来数据的列号
            int colNum = sparseArr2[i][1];
            // 数据的值
            chessArr2[rowNum][colNum] = sparseArr2[i][2];
        }
        return chessArr2;
    }

    /** 将原来的二维数组转为稀疏数组 */
    private static int[][] getSparseArray(int[][] chessArr1) {
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
        sparseArr[0][0] = chessArr1.length;
        sparseArr[0][1] = chessArr1[0].length;
        sparseArr[0][2] = sum;

        // 3.遍历二维数组，将非0的值存放到sparseArr中
        // count 用于记录是第几个非0数据
        int count = 0;
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

        return sparseArr;
    }


    /** 将文件中的数据转为稀疏数组 */
    public static int[][] readFileToSparseArr() {
        BufferedReader buf = null;
        int[][] sparseArr = null;
        try {
            // 获取文件保存路径
            String currentJavaFilePath = getCurrentJavaFilePath();
            File file = new File(currentJavaFilePath + "map.txt");

            buf = new BufferedReader(new FileReader(file));
            String tempStr = null;
            // 将每一行数据先添加到该集合里
            List<String> list = new ArrayList<>();
            while ((tempStr = buf.readLine()) != null) {
                list.add(tempStr);
            }
            // 初始化sparseArr
            sparseArr = new int[list.size()][3];
            for (int i = 0; i < list.size(); i++) {
                String[] splitStr = list.get(i).split("\t");
                for (int j = 0; j < splitStr.length; j++) {
                    sparseArr[i][j] = Integer.valueOf(splitStr[j]);
                }
            }

            System.out.println("读取文件完成");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (buf != null) {
                try {
                    buf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sparseArr;
    }

    /** 将稀疏数组保存到磁盘 */
    public static void saveSparseArrToFile(int[][] sparseArr) {
        OutputStream out = null;
        try {
            // 获取文件保存路径
            String currentJavaFilePath = getCurrentJavaFilePath();
            File file = new File( currentJavaFilePath + "map.txt");
            if(!file.exists()) {
                file.createNewFile();
            }

            // 遍历稀疏数组将数据保存到文件中
            out = new FileOutputStream(file, false);
            for(int[] row : sparseArr) {
                for(int data : row) {
                    out.write(String.valueOf(data).getBytes());
                    out.write('\t');
                }
                // 写完一行
                out.write('\n');
            }

            System.out.println("写入文件完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /** 获取当前java文件的路径 */
    public static String getCurrentJavaFilePath() {
        String filePath = "D:\\code_yl\\";
        try {
            File file = new File(SparseArray.class.getName());
            String absolutePath = file.getAbsolutePath();
            int i = absolutePath.lastIndexOf('\\');
            filePath = absolutePath.substring(0, i + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePath;
    }


}
