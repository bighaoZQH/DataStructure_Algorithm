package com.bighao.search;

import java.util.Arrays;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/23 16:43
 * @Version 1.0
 *
 * 斐波那契(黄金分割法)查找算法
 * {1,1,2,3,5,8,13,21,34,55} 1+1=2 2+3=5 3+5=8 即 F[k] = F[k-1] + F[k-2]
 * 公式推导:
 * F[k] = F[k-1] + F[k-2]  ===>  (F[k] -1) = (F[k-1] - 1) + (F[k-2] - 1) +1
 * 之所以要减1是因为空出个中间的位置，即low+F[k-1]-1= mid
 * 
 * 1.斐波那契查找原理与前两种相似，仅仅改变了中间结点（mid）的位置，mid不再是中间或插值得到，
 * 而是位于黄金分割点附近，即mid=low+F(k-1)-1（F代表斐波那契数列），如下图所示
 *
 * 2.对F(k-1)-1的理解：
 * 由斐波那契数列 F[k]=F[k-1]+F[k-2] 的性质，可以得到 （F[k]-1）=（F[k-1]-1）+（F[k-2]-1）+1
 * 该式说明：只要顺序表的长度为F[k]-1，则可以将该表分成长度为F[k-1]-1和F[k-2]-1的两段，
 * 即如上图所示。从而中间位置为mid=low+F(k-1)-1
 *
 * 3.类似的，每一子段也可以用相同的方式分割
 * 但顺序表长度n不一定刚好等于F[k]-1，所以需要将原来的顺序表长度n增加至F[k]-1。
 * 这里的k值只要能使得F[k]-1恰好大于或等于n即可，由以下代码得到,顺序表长度增加后，
 * 新增的位置（从n+1到F[k]-1位置），都赋为n位置的值即可
 *
 *
 * 4.斐波那契查找与折半查找很相似，他是根据斐波那契序列的特点对有序表进行分割的。
 * 他要求开始表中记录的个数为某个斐波那契数小1，及n=F(k)-1；
 * 开始将k值与第F(k-1)位置的记录进行比较(及mid=low+F(k-1)-1)，比较结果也分为三种：
 * （1）相等，则mid位置的元素即为所求；
 * （2）>，则low=mid+1，k-=2；
 *      说明：low=mid+1说明待查找的元素在[mid+1,high]范围内，
 *      k-=2 说明范围[mid+1,high]内的元素个数为n-(F(k-1))=F(k)-1 - F(k-1) = F(k)-F(k-1)-1=F(k-2)-1个，
 *      所以可以递归的应用斐波那契查找。
 * （3）<，则high=mid-1，k-=1。
 *      说明：low=mid+1说明待查找的元素在[low,mid-1]范围内，
 *      k-=1 说明范围[low,mid-1]内的元素个数为F(k-1)-1个，所以可以递归的应用斐波那契查找。
 *
 * 5.在最坏情况下，斐波那契查找的时间复杂度还是O(log2n)，且其期望复杂度也为O(log2n)，但是与折半查找相比，
 * 斐波那契查找的优点是它只涉及加法和减法运算，而不用除法，而除法比加减法要占用更多的时间，
 * 因此，斐波那契查找的运行时间理论上比折半查找小，但是还是得视具体情况而定。
 */
public class FibonacciSearch {
    public static int maxSize = 20;

    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println(fibSearch(arr, 8));
    }

    /**
     * 因为后面我们mid=low+F(k-1)-1,需要使用到斐波那契数列，因此我们需要先获取到一个斐波那契数列
     * 非递归的方式得到一个斐波那契数列
     * F(n)=F(n-1)+F(n-2)
     */
    public static int[] fib() {
        int[] f = new int[maxSize];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < maxSize; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }

    /** 递归方式实现斐波那契数列 */
    public static long fibonacci(int n) {
        if (n == 0 || n == 1) {
            return 1;
        } else {
            return fibonacci(n-1)+fibonacci(n-2);
        }
    }

    /**
     * 斐波那契查找算法 非递归方式
     * @param arr
     * @param key 需要查找的值
     * @return 返回对应下标，没有就返回-1
     * 公式:
     * mid=low+F(k-1)-1
     */
    public static int fibSearch(int[] arr, int key) {
        int low = 0;
        int high = arr.length - 1;
        int k = 0; //表示斐波那契数列分割数值的下标
        int mid = 0; //存放mid值
        int f[] = fib(); //获取到斐波那契数列
        // 获取到斐波那契分割数值的下标 这里的k值只要能使得F[k]-1恰好大于或等于n即可
        while (arr.length > f[k] - 1) {
            k++;
        }
        // 因为f[k]可能 大于 数组的长度，因此我们需要使用Arrays类构造一个新的数组，并指向arr,不足的部分用0填充
        int[] temp = Arrays.copyOf(arr, f[k]);
        /**
         * 实际上需要用arr数组最后的数据填充temp数组 因为数组要求是顺序数组，那就最好用最大值来填充
         * 举例:
         *  temp = {1, 8, 10, 89, 1000, 1234, 0, 0} => {1, 8, 10, 89, 1000, 1234, 1234, 1234}
         */
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        // 使用while循环, 找到我们的数 key
        while (low <= high) { //只要这个条件满足，就可以找
            mid = low + f[k - 1] - 1;
            if (key < temp[mid]) { //我们应该继续向数组的前面查找(左边)
                high = mid - 1;
                /**
                 * 为什么要k--?
                 * 1.全部元素 = 前面的元素 + 后面的元素
                 * 2.f[k] = f[k-1] + f[k-2]
                 * 因为前面有f[k-1]个元素，所以我们可以继续拆分 f[k-1] = f[k-2] + f[k-3] 发现相差了1
                 * 即在f[k-1]的前面继续查找 因此k--
                 * 即下次下次循环的时候mid = f[k-1-1] - 1
                 */
                k--;
            } else if (key > temp[mid]) { //说明向数组的后面查找(右边)
                low = mid + 1;
                /**
                 * 为什么是k-2?
                 * 1.全部元素 = 前面的元素 + 后面的元素
                 * 2.f[k] = f[k-1] + f[k-2]
                 * 3.因为后面有f[k-2]个元素 所以可以继续拆分 f[k-1] = f[k-3] + f[f-4] 发现相差了2
                 * 4.即在f[k-2]的前面继续查找,因此 k-=2
                 * 5.即下次循环 mid = f[k - 1 - 2] - 1
                 */
                k -= 2;
            } else { //找到了
                // 需要确定返回的是哪个下标
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        return -1;
    }

}
