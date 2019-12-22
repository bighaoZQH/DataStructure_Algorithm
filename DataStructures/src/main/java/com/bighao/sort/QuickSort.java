package com.bighao.sort;

import java.time.Duration;
import java.time.Instant;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/22 17:13
 * @Version 1.0
 *
 * 快速排序
 *
 * 对 [-9,78,0,23,-567,70] 进行从小到大的排序
 *
 * 第一次while循环完后，数组就被分成了两部分，pivot的左边全是<=pivot的,右边全是>=pivot的,
 * 接下来就要分别再对这两部分进行分组排序,即递归
 *
 * 说明[验证分析]:
 * 如果取消左右递归，结果是  -9 -567 0 23 78 70
 * 如果取消右递归,结果是  -567 -9 0 23 78 70
 * 如果取消左递归,结果是  -9 -567 0 23 70 78
 */
public class QuickSort {
    public static void main(String[] args) {
        /*int[] arr = {-9, 22,78, 0, 23, -567, 70, 10, 23};
        quickSort(arr, 0, arr.length - 1);*/

        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int)(Math.random() * 8000000); //生成[0,8000000)的数
        }

        Instant start = Instant.now();
        quickSort(arr, 0, arr.length - 1);
        //quickSort2(arr, 0, arr.length-1);
        Instant end = Instant.now();
        System.out.println("排序时间" + Duration.between(start, end).getSeconds() + "s");

        //System.out.println("arr=" + Arrays.toString(arr));
    }


    public static void quickSort(int[] arr, int left, int right) {
        int l = left; //左下标
        int r = right; //右下标
        // 取得pivot中轴值
        int pivot = arr[(left + right) / 2];
        int temp; //临时变量，作为交换时使用
        // while循环的目的是让比 pivot 值小的放到pivot的左边 比pivot大的放到pivot的右边
        while (l < r) {
            // 在pivot的左边一直找，找到>=pivot的值
            while (arr[l] < pivot) {
                l++;
            }
            // 在pivot的右边一直找，找到<=pivot的值
            while (arr[r] > pivot) {
                r--;
            }
            // 如果l >= r说明pivot的左右两边的值，已经按照左边全部是<=pivot的值 右边全部是>=pivot的值
            if (l >= r) {
                break;
            }

            // 交换
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            // 如果交换完后，发现这个arr[l] == pivot值 r--, r向pivot靠
            // 为什么
            if (arr[l] == pivot) {
                r--;
            }
            // 如果交换完后，发现这个arr[r] == pivot值 l++, l向pivot靠
            if (arr[r] == pivot) {
                l++;
            }
        }

        /**
         * 第一次while循环完后，数组就被分成了两部分，pivot的左边全是<=pivot的,右边全是>=pivot的,
         * 接下来就要分别再对这两部分进行分组排序,即递归
         */

        // 如果l == r,必须l++,r--,否则或出现栈溢出 为什么这么做?
        if (l == r) {
            /**
             * 比如pivot的下标是5，第一次循环完后，数组被分成了两组，分别在pivot的左右
             * 这是两边都要再进行递归调用排序
             * 因此左边的数组的左起始下标【始终为】0,右起始下标为pivot-1
             * 因此右边的数组的左起始下标为pivot+1,右起始下标【始终为】为length-1
             */
            l++; //l+1是因为当前l=pivot，要向右递归，这是l是要作为右边部分的左起始下标用的
            r--; //r-1是因为当前r=pivot，要向左递归，这是r是要作为右边部分的右起始下标用的
        }
        // 向左递归
        if (left < r) { // 因为递归之前r会被-- 因此当左边部分递归排完序后，r就会比left小，此时就不用再排序了
            quickSort(arr, left, r); //这里的left不要写死0,因为往右递归的时候left就不是0了
        }
        // 向右递归
        if (right > l) {
            quickSort(arr, l, right);//这里的right不要写死length-1,因为往左递归的时候right就不是length-1了
        }

    }

    /** 递归另一种写法 */
    public static void quickSort(int[] array) {
        int len;
        if(array == null
                || (len = array.length) == 0
                || len == 1) {
            return ;
        }
        sort(array, 0, len - 1);
    }

    /**
     * 快排核心算法，递归实现
     * @param array
     * @param left
     * @param right
     */
    public static void sort(int[] array, int left, int right) {
        if(left > right) {
            return;
        }
        // base中存放基准数
        int base = array[left];
        int i = left, j = right;
        while(i != j) {
            // 顺序很重要，先从右边开始往左找，直到找到比base值小的数
            while(array[j] >= base && i < j) {
                j--;
            }

            // 再从左往右边找，直到找到比base值大的数
            while(array[i] <= base && i < j) {
                i++;
            }

            // 上面的循环结束表示找到了位置或者(i>=j)了，交换两个数在数组中的位置
            if(i < j) {
                int tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }

        // 将基准数放到中间的位置（基准数归位）
        array[left] = array[i];
        array[i] = base;

        // 递归，继续向基准的左右两边执行和上面同样的操作
        // i的索引处为上面已确定好的基准值的位置，无需再处理
        sort(array, left, i - 1);
        sort(array, i + 1, right);
    }



}
