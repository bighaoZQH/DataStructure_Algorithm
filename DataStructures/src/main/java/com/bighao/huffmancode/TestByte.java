package com.bighao.huffmancode;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/28 17:18
 * @Version 1.0
 */
public class TestByte {
    public static void main(String[] args) {
        String strByte = "10101000";
        System.out.println((byte)Integer.parseInt(strByte, 2)); //-88

        int b = 77;
        b |= 256;
        System.out.println(b);
    }
}
