package com.bighao.dijkstra;

import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/4 18:21
 * @Version 1.0
 *
 * 地杰斯特拉算法 - 最短路径问题
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535; //表示不可以连接
        matrix[0] = new int[]{N, 5, 7, N, N, N, 2};
        matrix[1] = new int[]{5, N, N, 9, N, N, 3};
        matrix[2] = new int[]{7, N, N, N, 8, N, N};
        matrix[3] = new int[]{N, 9, N, N, N, 4, N};
        matrix[4] = new int[]{N, N, 8, N, N, 5, 4};
        matrix[5] = new int[]{N, N, N, 4, 5, N, 6};
        matrix[6] = new int[]{2, 3, N, N, 4, 6, N};
        // 创建 Graph对象
        Graph graph = new Graph(vertex, matrix);
        // 测试, 看看图的邻接矩阵是否ok
        graph.showGraph();

        // 测试迪杰斯特拉算法
        graph.dsj(6);
        graph.showDijkstra();
    }
}


/** 已访问顶点集合 */
class VisitedVertex {
    // 记录各个顶点是否访问过 1表示访问过,0未访问,会动态更新
    public int[] alreadyArr;
    // 每个下标对应的值为前一个顶点下标, 会动态更新
    public int[] preVisited;
    // 记录出发顶点到其他所有顶点的距离,比如G为出发顶点，就会记录G到其它顶点的距离，会动态更新，求的最短距离就会存放到dis
    public int[] dis;

    /**
     * 构造器
     * @param length 顶点的合数
     * @param index 出发顶点对应的下标
     */
    public VisitedVertex(int length, int index) {
        this.alreadyArr = new int[length];
        this.preVisited = new int[length];
        Arrays.fill(preVisited, -1); // -1表示前驱节点就是自己
        this.dis = new int[length];
        Arrays.fill(dis, 65535); //65535表示没有连通
        // 设置出发顶点到自己的距离为0
        this.dis[index] = 0;
        // 设置出发顶点被访问过
        this.alreadyArr[index] = 1;
    }

    /**
     * 判断index下标对应的顶点是否被访问过
     * @param index
     * @return 访问过返回true else false
     */
    public boolean in(int index) {
        return alreadyArr[index] == 1;
    }


    /**
     * 更新出发顶点到index这个顶点的距离
     * @param index
     * @param len
     */
    public void updateDis(int index, int len) {
        dis[index] = len;
    }

    /**
     * 更新pre顶点的前驱顶点为index顶点
     * @param pre
     * @param index
     */
    public void updatePre(int pre, int index) {
        preVisited[pre] = index;
    }


    /** 返回出发顶点到index顶点的距离 */
    public int getDis(int index) {
        return dis[index];
    }

    /**
     * 继续选择并返回新的访问顶点，比如这里的出发点G 完后，就是A点作为新的访问顶点(注意不是出发点)
     */
    public int updateArr() {
        int min = 65535, index = 0;
        for (int i = 0; i < alreadyArr.length; i++) {
            if (alreadyArr[i] == 0 && dis[i] < min) {
                min = dis[i];// 将路径长度修改
                index = i;// 将新的顶点赋给index
            }
        }
        //更新当前index这个顶点被访问过
        alreadyArr[index] = 1;
        return index;
    }

    /** 显示最后的结果 就是把上面3个数组的最后的情况输出一下*/
    public void show(char[] vertex) {
        System.out.println("\n===========================>");
        // 输出alreadyArr
        for (int i : alreadyArr) {
            System.out.print(i + " ");
        }
        System.out.println();
        // 输出preVisited
        for (int i : preVisited) {
            System.out.print(i + " ");
        }
        System.out.println();
        // 输出dis
        for (int i : dis) {
            System.out.print(i + " ");
        }
        System.out.println();
        // 输出结果
        int count = 0;
        for (int i : dis) {
            if (i != 65535) {
                System.out.print(vertex[count] + "(" + i + ") ");
            } else {
                System.out.println("N ");
            }
            count++;
        }
    }

}




/** 图 */
class Graph {
    private char[] vertex; //顶点
    private int[][] matrix; //邻接矩阵
    private VisitedVertex vv; //已访问的顶点的集合

    public Graph(char[] vertex, int[][] matrix) {
        this.vertex = vertex;
        this.matrix = matrix;
    }

    /** 显示图 */
    public void showGraph() {
        for (int[] link : matrix) {
            System.out.println(Arrays.toString(link));
        }
    }


    /**
     * 迪杰斯特拉算法
     * @param index 表示出发顶点的下标
     */
    public void dsj(int index) {
        vv = new VisitedVertex(vertex.length, index);
        update(index); //更新 index顶点 到 周围顶点的距离和前驱顶点
        for (int i = 0; i < vertex.length; i++) {
            index = vv.updateArr(); //选择并返回新的访问顶点
            update(index); //更新 新的index顶点 到 周围顶点的距离和前驱顶点
        }
    }

    /** 更新index下标顶点 到 周围顶点的距离 和 周围顶点的前驱顶点 */
    private void update(int index) {
        int len = 0;
        // 遍历邻接矩阵的 matrix[index]这行
        for (int i = 0; i < matrix[index].length; i++) {
            // len表示 出发顶点到index顶点的距离 + 从index顶点到i顶点的距离 这行代码可以debug理解下
            len = vv.getDis(index) + matrix[index][i];
            // 如果i这个顶点没有被访问过，并且len小于出发顶点到i顶点的距离，就需要更新
            if (!vv.in(i) && len < vv.getDis(i)) {
                vv.updatePre(i, index); //更新i顶点的前驱节点为index顶点
                vv.updateDis(i, len); //更新出发顶点到j顶点的距离
            }
        }
    }

    /** 显示算法结果 */
    public void showDijkstra() {
        vv.show(vertex);
    }

}


