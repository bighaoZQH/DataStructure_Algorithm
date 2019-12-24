package com.bighao.hashtab;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/24 11:26
 * @Version 1.0
 */
public class Emp {
    public int id;
    public String name;
    // next默认为空
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
