package com.bighao.huffmancode;

import java.io.*;
import java.util.*;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/28 16:01
 * @Version 1.0
 *
 * 赫夫曼编码和数据压缩 ppt162
 *
 * 一.赫夫曼编码
 *  (1)通信领域中信息的处理方式1-定长编码
 *  (2)通信领域中信息的处理方式2-变长编码
 *       字符的编码都不能是其他字符编码的前缀，符合此要求的编码叫做前缀编码，即不能匹配到重复的编码
 *  (3)通信领域中信息的处理方式3-赫夫曼编码
 *      各个字符对应的个数按照字符出现的次数构建一颗赫夫曼树, 次数作为权值
 *
 * 【注意】:这个赫夫曼树根据排序方法不同，也可能不太一样，这样对应的赫夫曼编码也不完全一样，但是wpl 是一样的，都是最小的,
 *
 * 二.压缩字符串的分步过程
 *  得到字符串的字节数组后
 *  1.将字节数组转为list  getNodes(contentBytes)
 *  2.创建二叉树  createHuffmanTree(nodes)
 *  3.生成对应的赫夫曼编码表  getCodes(huffmanTreeRoot)
 *  4.生成赫夫曼编码表对应的byte数组 zip(contentBytes, codes)
 *      1-4整合成了一个方法huffmanZip(contentBytes)
 *  5.解压decode(huffmanCodes, huffmanCodeBytes) huffmanCodes编码表 huffmanCodeBytes编码后的字节数组
 *
 * 三.赫夫曼编码压缩文件注意事项
 *  (1)如果文件本身就是经过压缩处理的，那么使用赫夫曼编码再压缩效率不会有明显变化, 比如视频,ppt 等等文件  [举例压一个 .ppt]
 *  (2)赫夫曼编码是按字节来处理的，因此可以处理所有的文件(二进制文件、文本文件) [举例压一个.xml文件]
 *  (3)如果一个文件中的内容，重复的数据不多，压缩效果也不会很明显.
 *
 */
public class HuffmanCode {
    public static void main(String[] args) {
        // 测试压缩文件
        /*String srcFile = "D:/code_yl/赫夫曼编码压缩文件测试/秀智.GIF";
        String dstFile ="D:/code_yl/赫夫曼编码压缩文件测试/秀智图片.zip";
        zipFile(srcFile, dstFile);
        System.out.println("压缩文件成功");*/
        // 测试解压文件
        String zipFile = "D:/code_yl/赫夫曼编码压缩文件测试/秀智图片.zip";
        String dstFile = "D:/code_yl/赫夫曼编码压缩文件测试/秀智_解压后.GIF";
        unZipFile(zipFile, dstFile);
        System.out.println("解压文件成功");

        // 压缩字符串
        /*String content = "i like like like java do you like a java";
        byte[] contentBytes = content.getBytes();
        //System.out.println(contentBytes.length); //40
        // 压缩数据
        byte[] huffmanCodeBytes = huffmanZip(contentBytes);
        //System.out.println("压缩后的结果===>" + Arrays.toString(huffmanCodeBytes));

        // 解压数据
        // 测试byteToBitString
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodeBytes);
        System.out.println("原来的字符串===>" + new String(sourceBytes));*/

    }


    /**
     * 解压文件
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        // 定义文件的输入流
        InputStream is = null;
        // 定义一个对象输入流
        ObjectInputStream ois = null;
        // 定义文件的输出流
        OutputStream ops = null;
        try {
            // 创建文件输入流
            is = new FileInputStream(zipFile);
            // 创建一个和 文件输入流 关联的对象输入流
            ois = new ObjectInputStream(is);
            // 读取byte数组(压缩过后的赫夫曼树组huffmanBytes)
            byte[] huffmanBytes = (byte[]) ois.readObject();
            // 读取赫夫曼编码表
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();

            // 解码
            byte[] decodeBytes = decode(huffmanCodes, huffmanBytes);
            // 创建文件输出流
            ops = new FileOutputStream(dstFile);
            // 将解码后的字节数组写入到目标文件
            ops.write(decodeBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                ops.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩文件
     * @param srcFile 源文件的全路径
     * @param dstFile 压缩文件放到哪个目录
     */
    public static void zipFile(String srcFile, String dstFile) {
        // 创建文件的输入流
        FileInputStream fis = null;
        // 创建输出流
        OutputStream ops = null;
        ObjectOutputStream oos = null;
        try {
            fis = new FileInputStream(srcFile);
            // 创建一个和源文件大小一样的byte数组
            byte[] b = new byte[fis.available()];
            // 读取文件到byte数组中
            fis.read(b);
            // 获取文件对应的赫夫曼编码表，并压缩
            byte[] huffmanBytes = huffmanZip(b);
            // 创建一个文件的输出流，存放压缩文件
            ops = new FileOutputStream(dstFile);
            // 创建一个和文件输出流关联的ObjectOutPutStream
            oos = new ObjectOutputStream(ops);
            // 把赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            // 这里我们以对象流的方式写入赫夫曼编码表!，是为了恢复源文件时使用，否则以后恢复不了!
            oos.writeObject(huffmanCodes);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
                ops.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /** =========================== 解压start ========================================= */
    /**
     * 如何将数据进行解压(解码)
     * 1.将huffmanCodeBytes重新转回到赫夫曼编码对应的二进制字符串
     * 2.将赫夫曼编码对应的二进制字符串 ==>对照 赫夫曼编码表 ==> 重新转成字符串
     */

    /**
     * 解码
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码后得到的字节数组
     * @return 原来的字符串对应的字节数组
     */
    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        // 1.先得到huffmanBytes 对应的 二进制的字符串，形式"1010100010111111110...."
        StringBuilder sBuilder = new StringBuilder();
        // 将byte数组转成二进制的字符串
        for (int i = 0; i < huffmanBytes.length; i++) {
            // 判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            sBuilder.append(byteToBitString(!flag, huffmanBytes[i]));
        }

        // 把字符串按照指定的赫夫曼编码进行解码
        // 获取反向编码表，把赫夫曼编码表进行调换，因为反向查询 a->100 100->a
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        // 创建集合，存放byte
        List<Byte> list = new ArrayList<>();
        // i可以理解成一个索引值,扫描stringBuilder
        for (int i = 0; i < sBuilder.length(); /*i++*/) { // 这里千万不要i++
            int count = 1; //小的计数器
            boolean flag = true;
            Byte b = null;

            while (flag) {
                // "1010100010111111110...."
                // 递增取出key('1'或'0')，i不动，count动 直到匹配到一个字符
                String key = sBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    // 没有匹配到
                    count++;
                } else {
                    // 匹配到
                    flag = false;
                }
            }
            list.add(b);
            // i跳跃count个下标,for循环的迭代条件千万不要再i++了
            i += count;
        }
        // 当for循环结束后，list中就存放了所有的字符 "i like like like java do you like a java"
        // 把list中的数据放入到byte数组中，并返回
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }

    /**
     * 将一个byte转成二进制的字符串
     * @param b
     * @param flag 表示是否需要补高位,true需要补高位，如果是最后一个字节就无需补高位
     * @return 是b对应的二进制的字符串(注意是补码)
     */
    private static String byteToBitString(boolean flag, byte b) {
        // 将b转为int
        int temp =  b;
        // 如果是正数，还需要补高位 比如 1 按位或 256 ==> 1 0000 0000(256) | 0000 0001(1) => 1 0000 0001
        if (flag) {
            temp |= 256;
        }
        // 返回的是二进制的补码
        String str = Integer.toBinaryString(temp);
        // 负数就取最后8位,正数直接返回
        return flag ? str.substring(str.length() - 8) : str;
    }


    /** ################################ 解压end ################################ */


    /** =========================== 压缩start ========================================= */
    /***
     * 封装压缩的其他方法，便于调用
     * @param contentBytes 原始的字符串对应的字节数组
     * @return 返回的是经过 赫夫曼编码处理后的字节数组(也就是压缩后的)
     */
    private static byte[] huffmanZip(byte[] contentBytes) {
        // 1.将字节数组转为list
        List<Node> nodes = getNodes(contentBytes);
        // 2.创建赫夫曼树
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        // 3.生成对应的赫夫曼编码表
        Map<Byte, String> codes = getCodes(huffmanTreeRoot);
        // 4.生成赫夫曼编码, 得到压缩后的赫夫曼编码字节数组
        return zip(contentBytes, codes);
    }


    /**
     * 将一个字符串对应的btye数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码压缩后的byte数组
     * @param bytes 原始的字符串对应的byte[]
     * @param huffmanCodes 生成的赫夫曼编码表
     * @return 返回赫夫曼编码处理后的byte[]
     * 举例：String content = "i like like like java do you like a java"; ===> byte[] contentBytes = content.getBytes();
     * 返回的是 字符串"1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
     * ===> 转为对应的byte[] huffmanCodeBytes , 即8位对应一个byte，放入到huffmanCodeBytes中
     * huffmanCodeBytes[0] = 10101000(补码) => byte
     * [推导: 将补码变为反码再变为原码 10101000(补码)=> 10101000-1 => 10100111(反码) => 11011000(原码) = -88]
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        // 1.利用赫夫曼编码表 将bytes 转成 赫夫曼编码对应的字符串
        StringBuilder sbuilder = new StringBuilder();
        // 遍历原字符串的byte[],拼接赫夫曼编码对应的字符串
        for (byte b : bytes) {
            sbuilder.append(huffmanCodes.get(b));
        }

        // 将sbuilder的字符串转成byte数组
        // 计算返回的byte[] huffmanCodeBytes的长度
        int len; // 也可以是int len = (sbuilder.length() + 7) / 8
        if (sbuilder.length() % 8 == 0) {
            len = sbuilder.length() / 8;
        } else {
            len = sbuilder.length() / 8 + 1;
        }
        // 创建 存储压缩后的byte数组
        byte[] huffmanCodeBytes = new byte[len];
        // 记录是第几个byte
        int index = 0;
        // 因为是每8位对应一个byte,所以步长是+8
        for (int i = 0; i < sbuilder.length(); i += 8) {
            String strByte;
            // 如果不够8位
            if (i + 8 > sbuilder.length()) {
                strByte = sbuilder.substring(i);
            } else {
                strByte = sbuilder.substring(i, i + 8);
            }
            // 将strByte转成一个 byte，放入到huffmanCodeBytes
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }
        return huffmanCodeBytes;
    }


   /** 为了调用方便，重载下getCodes */
   private static Map<Byte, String> getCodes(Node root) {
        if (root == null) return null;
       getCodes(root, "", stringBuilder);
       return huffmanCodes;
   }

    /**
     * 生成赫夫曼树对应的赫夫曼编码表
     * 1.将赫夫曼编码表存放在Map<Byte, Stirng> 形式: 32->01, 97->100等等
     * 2.在生成赫夫曼编码表时，需要去拼接路径，定义一个StringBuilder存储某个叶子节点的路径
     */
    static Map<Byte, String> huffmanCodes = new HashMap();
    static StringBuilder stringBuilder = new StringBuilder();

    /**
     * 功能:将传入的node节点的所有叶子节点的赫夫曼编码得到，并放入到map中
     * @param node  传入的节点
     * @param code  路径: 左子节点是0，右子节点是1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder sbuilder = new StringBuilder(stringBuilder);
        // 将code 加入到stringBuilder2中
        sbuilder.append(code);
        if (node != null) {
            // 判断当前node是叶子节点还是非叶子节点 node.data == null就是非叶子节点
            if (node.data == null) {
                // 递归处理 向左递归
                getCodes(node.left, "0", sbuilder);
                // 向右递归
                getCodes(node.right, "1", sbuilder);
            } else {
                // 如果是叶子节点，就表示找到了某个路径的最后
                huffmanCodes.put(node.data, sbuilder.toString());
            }
        }
    }


    /**
     * 通过list 创建对应的赫夫曼树
     */
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            // 排序,从小到大
            Collections.sort(nodes);
            // 取出最小的二叉树
            Node leftNode = nodes.get(0);
            // 取出第二小的二叉树
            Node rightNode = nodes.get(1);
            // 创建一颗新的二叉树,它的根节点的没有data，只有权值，权值是leftNode的权值 + rightNode的权值
            Node parentNode = new Node(null, leftNode.weight + rightNode.weight);
            parentNode.left = leftNode;
            parentNode.right = rightNode;

            // 将已经处理的两颗二叉树从list中移除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            // 将新的二叉树加入到list中
            nodes.add(parentNode);
        }
        // 返回赫夫曼树的根节点
        return nodes.get(0);
    }



    /**
     * 将字节数组转为list
     * @param bytes 字节数组
     * @return 返回list 形式 [Node[date=97 ,weight = 5], Node[]date=32,weight = 9]......],
     */
    private static List<Node> getNodes(byte[] bytes) {
        // 1.chuang创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();
        // 2.存储 byte数组,统计每个byte出现的次数 =>map[ket,valus]
        HashMap<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            // 当map没有这个字符数据
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        // 遍历map，把每个键值对转成一个Node对象，并加入到list中
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) {
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }

    /** ################################ 压缩end ################################ */


    /** 前序遍历 */
    public static void preOrder(Node root) {
        if (root == null)
            throw new RuntimeException("tree is empry");
        root.preOrder();
    }


}




/** 创建Node,存放数据和权值 */
class Node implements Comparable<Node>{
    // 存放数据(字符)本身，比如'a'=>97
    Byte data;
    // 权值，表示字符出现的次数
    int weight;
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    /** 前序遍历 */
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    @Override
    public int compareTo(Node o) {
        // 从小到大排序
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" + "data=" + data + ", weight=" + weight + '}';
    }

}