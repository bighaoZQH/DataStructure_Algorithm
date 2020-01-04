package com.bighao.kruskal;

import lombok.ToString;

import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/3 16:49
 * @Version 1.0
 *
 * 克鲁斯卡尔算法和普利姆算法算出来的最小生成树是一样的
 */
public class KruskalCase {

    private int edgeNum; //记录边的个数
    private char[] vertexs; //顶点
    private int[][] matrix; //邻接矩阵
    private static final int INF = Integer.MAX_VALUE; //这个值表示两个顶点不连通

    // 初始化构造器
    public KruskalCase(char[] vertexs, int[][] matrix) {
        // 初始化顶点数和边的个数
        int vlen = vertexs.length;
        this.vertexs = new char[vlen];
        // 这样做是为了不改变传入进来的vertexs数组
        for (int i = 0; i < vertexs.length; i++) {
            this.vertexs[i] = vertexs[i];
        }
        this.matrix = new int[vlen][vlen];
        for (int i = 0; i < vlen; i++) {
            for (int j = 0; j < vlen; j++) {
                this.matrix[i][j] = matrix[i][j];
            }       
        }

        // 统计边
        for (int i = 0; i < vlen; i++) {
            for (int j = i + 1; j < vlen; j++) {
                if (this.matrix[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    /** 打印邻接矩阵 */
    public void print() {
        System.out.println("邻接矩阵为====>");
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = 0; j < vertexs.length; j++) {
                System.out.printf("%12d", matrix[i][j]);
            }
            System.out.println();
        }
    }

    /** 对边进行排序处理 */
    private void sortEdges(Edata[] edges) {
        for (int i = 0; i < edges.length - 1; i++) {
            for (int j = 0; j < edges.length - 1 - i; j++) {
                if (edges[j].weight > edges[j + 1].weight) {
                    Edata temp = edges[j];
                    edges[j] = edges[j + 1];
                    edges[j + 1] = temp;
                }
            }
        }
    }

    /** 返回顶点对应的下标,没有返回-1 */
    private int getPosition(char ch) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == ch) {
                return i;
            }
        }
        return -1;
    }


    /**
     * 获取图中的边，放到Edata[]数组中，后面要遍历这个数组
     * 通过邻接矩阵来获取
     * 形式 [['A','B',12], ['B','F',7]...]
     */
    private Edata[] getEdges() {
        int index = 0;
        Edata[] edges = new Edata[edgeNum];
        for (int i = 0; i < vertexs.length; i++) {
            for (int j = i + 1; j < vertexs.length; j++) {
                if (matrix[i][j] != INF) {
                    edges[index] = new Edata(vertexs[i], vertexs[j], matrix[i][j]);
                    index++;
                }
            }
        }
        return edges;
    }


    /**
     * 获取下标为i的顶点的终点，用于后面判断两个顶点的终点是否相同
     * 这段代码建议debug来理解
     * @param ends 记录了各个顶点对应的终点是哪个, 该数组是在遍历过程中逐步形成的
     * @param i 传入的顶点对应的下标
     * @return 返回的就是下标为i的这个顶点 所对应的终点的下标
     */
    private int getEnd(int[] ends, int i) {  //i=5 [0,0,0,0,5,0,0,0,0,0,0,0]
        while (ends[i] != 0) {
            i = ends[i];
        }
        return i; //为0就返回自己
    }


    public void kruskal() {
        int index = 0; //表示最后结果数组的索引
        int[] ends = new int[edgeNum]; //用于保存"已有最小生成树" 中的每个顶点在最小生成树中的终点
        Edata[] rets = new Edata[edgeNum]; //创建结果数组，保存最后的最小生成树

        Edata[] edges = getEdges();// 获取图中 所有的边的集合
        System.out.println("图的边的集合=" + Arrays.toString(edges) + "\t共" + edges.length + "条边\n");
        sortEdges(edges); // 按边的权值进行排序(从小到大)

        // 遍历edges[],将边添加到最小生成树中，并判断准备加入的边是否形成了回路,如果没有就加入rets
        for (int i = 0; i < edges.length; i++) {
            // 获取第i条边的第1个点
            int p1 = getPosition(edges[i].start); //<E,F> 4
            // 获取第i条边的第2个点
            int p2 = getPosition(edges[i].end); // 5

            // 获取p1这个顶点在已有的最小生成树中的终点
            int m = getEnd(ends, p1); //4
            // 获取p1这个顶点在已有的最小生成树中的终点
            int n = getEnd(ends, p2); //5
            // 判断是否构成回路
            if (m != n) {
                ends[m] = n; // 设置m在已有的最小生成树中的终点 <E,F> [0,0,0,0,5,0,0,0,0,0,0,0]
                //ends[n] = n; 没有必要写这句话
                rets[index++] = edges[i]; //有一条边加入到rets数组
            }
        }

        // 统计并打印"最小生成树",就是输出rets数组
        System.out.println("最小生成树为");
        for (int i = 0; i < index; i++) {
            System.out.println(rets[i]);
        }

    }

    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 克鲁斯卡尔算法的邻接矩阵
        int matrix[][] = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {   0,  12, INF, INF, INF,  16,  14},
                /*B*/ {  12,   0,  10, INF, INF,   7, INF},
                /*C*/ { INF,  10,   0,   3,   5,   6, INF},
                /*D*/ { INF, INF,   3,   0,   4, INF, INF},
                /*E*/ { INF, INF,   5,   4,   0,   2,   8},
                /*F*/ {  16,   7,   6, INF,   2,   0,   9},
                /*G*/ {  14, INF, INF, INF,   8,   9,   0}};
        //大家可以在去测试其它的邻接矩阵，结果都可以得到最小生成树.

        KruskalCase kruskalCase = new KruskalCase(vertexs, matrix);
        kruskalCase.print();
        // 获取图中的边
        Edata[] edges = kruskalCase.getEdges();
        System.out.println("排序前===>" + Arrays.toString(edges) + "\n");
        kruskalCase.sortEdges(edges);
        System.out.println("排序后===>" + Arrays.toString(edges) + "\n");


        kruskalCase.kruskal();
    }

}


/** 创建一个EData实例,用于表示一条边 */
@ToString
class Edata {
    char start; //边的起点
    char end;   //边的终点
    int weight; //边的权值

    public Edata(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}