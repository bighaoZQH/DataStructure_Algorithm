package com.bighao.greedy;

import java.util.*;

/**
 * @Author: bighao周启豪
 * @Date: 2020/1/2 17:24
 * @Version 1.0
 *
 * 贪心算法
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        // 创建广播电台，放入到map里
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        // 将各个电台放入到broadcasts
        HashSet<String> hastSet1 = new HashSet<>();
        hastSet1.add("北京");
        hastSet1.add("上海");
        hastSet1.add("天津");

        HashSet<String> hastSet2 = new HashSet<>();
        hastSet2.add("广州");
        hastSet2.add("北京");
        hastSet2.add("深圳");

        HashSet<String> hastSet3 = new HashSet<>();
        hastSet3.add("成都");
        hastSet3.add("上海");
        hastSet3.add("杭州");

        HashSet<String> hastSet4 = new HashSet<>();
        hastSet4.add("上海");
        hastSet4.add("天津");

        HashSet<String> hastSet5 = new HashSet<>();
        hastSet5.add("杭州");
        hastSet5.add("大连");

        // 加入到map
        broadcasts.put("K1", hastSet1);
        broadcasts.put("K2", hastSet2);
        broadcasts.put("K3", hastSet3);
        broadcasts.put("K4", hastSet4);
        broadcasts.put("K5", hastSet5);

        // 用于存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        // 将全部地区放入allAreas
        for (String key : broadcasts.keySet()) {
            HashSet<String> areas = broadcasts.get(key);
            allAreas.addAll(areas);
        }

        // 创建ArrayList，存放选择的电台集合
        List<String> selects = new ArrayList<>();

        // 保存遍历的过程中的电台覆盖的地区 和 当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();

        // 保存在一次遍历过程中，拥有最大未覆盖地区的电台key
        // 如果maxKey不为null，就加入到selects
        String maxKey = null;
        while (allAreas.size() != 0) {
            maxKey = null;
            // 遍历broadcasts，取出对应key
            for(String key : broadcasts.keySet()) {
                tempSet.clear();
                // 当前这个key能够覆盖的地区
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                // 求出tempSet和所有地区的集合的交集 并放入到tempSet中
                tempSet.retainAll(allAreas);
                // 如果当前这个集合包含的未覆盖地区的数量，比maxKey指向的集合的地区还多
                // 就需要重置maxKey
                // tempSet.size() > broadcasts.get(maxKey).size())体现出贪心算法的特点，每次都选择局部最优的
                if (tempSet.size() > 0 &&
                        (maxKey == null || tempSet.size() > broadcasts.get(maxKey).size())) {
                    maxKey = key;
                }
            }

            // maxKey != null，就应该将maxKey加入到selects中
            if (maxKey != null) {
                selects.add(maxKey);
                // 将maxKey指向的广播电台覆盖的地区从allAreas中移除
                allAreas.removeAll(broadcasts.get(maxKey));
            }

        }
        System.out.println("得到的选择结果是 " + selects);

    }
}
