package com.bighao.prim;

import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/3 9:20
 * @Version 1.0
 *
 * 普利姆算法 - 修路问题
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        // 测试创建图
        char[] data = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        // 邻接矩阵的关系使用二维数组表示,10000这个较大数，表示两个点不连通
        int[][] weight=new int[][]{
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,10000,4,6,10000}};

        // 创建一个MGraph
        MGraph graph = new MGraph(data.length);
        // 创建一个最小生成树
        MinTree minTree = new MinTree();
        minTree.createGraph(graph, data, weight);
        minTree.showGraph(graph);
        // 测试普利姆算法
        minTree.prim(graph, 1);
    }
}


/** 最小生成树 */
class MinTree {
    /**
     * 创建图的邻接矩阵
     * @param graph 图对象
     * @param data 图的各个顶点的值
     * @param weight 图的邻接矩阵
     */
    public void createGraph(MGraph graph, char data[], int[][] weight) {
        for (int i = 0; i < data.length ; i++) {
            graph.data[i] = data[i];
            for (int j = 0; j < data.length; j++) {
                graph.weight[i][j] = weight[i][j];
            }
        }
    }

    /** 显示图的邻接矩阵 */
    public void showGraph(MGraph graph) {
        for (int[] link : graph.weight) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * prim算法，得到最小生成树
     * @param graph 图
     * @param v 表示从图的第几个顶点开始生成 'A'->0
     */
    public void prim(MGraph graph, int v) {
        // 标记顶点是否被访问过
        int[] visited = new int[graph.verxs];
        // 0表示没有访问过，java默认为0，可以不做，但要记得有这个流程
        /*for (int i = 0; i < visited.length; i++) {
            visited[i] = 0;
        }*/

        // 把初始节点节点标记为已访问
        visited[v] = 1;
        // h1和h2记录两个顶点的下标
        int h1 = -1;
        int h2 = -1;
        int minWeight = 10000; //将minWeight初始成一个较大数，后面在遍历的过程中会被替换
        // 第一个for用于生成边 因为有graph.verxs个顶点，prim算法结束后，会有graph.verxs-1条边 所以k从1开始
        for (int k = 1; k < graph.verxs; k++) {
            // 第二个和第三个for是用于生成子图 第二个for用于找到当前访问的节点，第三个for用于找到没有被访问过的节点
            for (int i = 0; i < graph.verxs; i++) {
                if (visited[i] == 0) {
                    continue;
                }
                for (int j = 0; j < graph.verxs; j++) {
                    // visited[i]为已经访问过的节点，visited[j] == 0为没有访问过的节点，当已访问的节点匹配上没有访问过的节点
                    // 并且这两个点的连线的权值小于minWeight(小于minWeight表示这两个点可以连通)
                    if (visited[j] == 0 && graph.weight[i][j] < minWeight) {
                        // 替换minWeight(寻找已经访问过的节点 和 未访问的节点的权值最小的边)
                        minWeight = graph.weight[i][j];
                        h1 = i;
                        h2 = j;
                    }
                }
            }
            // 找到了一条当前最小边
            System.out.println("边<" + graph.data[h1] + "," + graph.data[h2] + "> 权值:" + minWeight);
            // 将当前找到的h2节点标记为已访问
            visited[h2] = 1;
            // 重置minWeight
            minWeight = 10000;
        }

    }

}

class MGraph {
    int verxs; //图的节点个数
    char[] data; //保存节点数据
    int[][] weight; //邻接矩阵 存放边

    public MGraph(int verxs) {
        this.verxs = verxs;
        data = new char[verxs];
        weight = new int[verxs][verxs];
    }



}
