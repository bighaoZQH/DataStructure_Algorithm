package com.bighao.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/31 11:11
 * @Version 1.0
 *
 * 图
 */
public class Graph {
    private ArrayList<String> vertexList; //存储顶点集合
    private int[][] edges; //存储图对应的邻接矩阵
    private int numOfEdges;//表示边的个数
    // 用于记录某个节点是否被访问过
    private boolean[] isVisited;

    // 构造器
    public Graph(int n) {
        if(n < 0)
            throw new RuntimeException("参数错误");
        // 初始化矩阵和vertexList
        edges = new int[n][n];
        vertexList = new ArrayList<>(n);
        numOfEdges = 0; //默认为0
        isVisited = new boolean[n];
    }
    /** 插入节点 */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 添加边
     * @param v1 表示点的下标 即是第几个顶点 "A"-"B" "A"->0 "B"->1
     * @param v2 第二个顶点对应的下标
     * @param weight 表示是否关联 0不关联,1关联
     */
    public void insertEdge(int v1, int v2, int weight) {
        if (v1 == v2)
            weight = 0;
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    /** ========================图中常用的方法start================== */

    /** 返回节点的个数 */
    public int getNumOfVertex() {
        return vertexList.size();
    }

    /** 返回边的数据 */
    public int getNumOfEdges() {
        return numOfEdges;
    }

    /** 返回节点i(下标)对应的数据 0->"A" 1->"B" */
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    /** 返回v1和v2的权值 */
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    /** 显示图对应的矩阵 */
    public void showGraph() {
        for (int[] link : edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    /** ========================深度优先算法start================== */
    /**
     * 得到初始节点V的第一个邻接节点的下标w
     * @param index
     * @return 如果存在就返回对应下标，否则返回-1
     */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 根据前一个邻接节点的下标来获取下一个邻接节点
     * @param v1 起始结点v的下标
     * @param v2 当前邻接节点的下标w
     * @return
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++) {
            if (edges[v1][j] > 0) {
                return j;
            }
        }
        return -1;
    }

    /** 深度优先遍历算法 */
    private void dfs(boolean[] isVisited, int i) {
        // 首先访问该节点
        System.out.print(getValueByIndex(i) + "->");
        // 将该节点设置为已访问
        isVisited[i] = true;
        // 查找结点i的第一个邻接结点w
        int w = getFirstNeighbor(i);
        while (w != -1) { //说明有邻接节点w
            // 并且w没有被访问过
            if (!isVisited[w]) {
                dfs(isVisited, w);
            }
            // 如果i的邻接点w已经被访问过,就获取w的下一个邻接点
            w = getNextNeighbor(i, w);
        }
    }

    /** 对dfs进行重载,遍历我们所有的节点，并进行dfs */
    public void dfs() {
        // 遍历所有的节点，进行dfs
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                dfs(isVisited, i);
            }
        }
        isVisited = new boolean[getNumOfVertex()];
    }

    /** ========================广度优先算法start================== */

    /** 对一个节点进行广度优先遍历的方法 */
    private void bfs(boolean[] isVisited, int i) {
        int u; //队列头结点对应的下标
        int w; //u的邻接节点的下标w
        // 队列，记录节点访问顺序
        LinkedList<Integer> queue = new LinkedList<>();
        // 访问节点
        System.out.print(getValueByIndex(i) + "->");
        // 标记为已访问
        isVisited[i] = true;
        // 将节点加入队列
        queue.addLast(i);

        while (!queue.isEmpty()) {
            // 取出队列头节点下标
            u = queue.removeFirst();
            // 得到u的第一个邻接点下标w
            w = getFirstNeighbor(u);
            while (w != -1) {// 找到
                // 是否访问过
                if (!isVisited[w]) {
                    System.out.print(getValueByIndex(w) + "->");
                    // 标记已经访问过
                    isVisited[w] = true;
                    // 入队列
                    queue.addLast(w);
                }
                // 如果访问过，就以u为起点,找w后面的下一个邻接点
                w = getNextNeighbor(u, w); //体现出广度优先
            }
        }
    }

    /** 广度优先-遍历所有节点 */
    public void bfs() {
        for (int i = 0; i < getNumOfVertex(); i++) {
            if (!isVisited[i]) {
                bfs(isVisited, i);
            }
        }
        isVisited = new boolean[getNumOfVertex()];
    }

    public static void main(String[] args) {
        // 测试图
        int n = 5; //节点的个数
        String[] vertexs = {"A", "B", "C", "D", "E"};
        // 创建图对象
        Graph graph = new Graph(n);
        // 循环添加顶点
        for (String vertex : vertexs) {
            graph.insertVertex(vertex);
        }
        // 添加边 A-B A-C B-C B-D B-E
        graph.insertEdge(0, 1, 1);//A-B
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);

        // 显示
        graph.showGraph();

        // 测试 深度遍历优先算法
        System.out.println("深度优先遍历===>");
        graph.dfs();

        System.out.println("\n广度优先遍历===>");
        graph.bfs();

    }



}
