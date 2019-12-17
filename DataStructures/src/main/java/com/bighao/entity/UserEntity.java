package com.bighao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: bighao周启豪
 * @Date: 2019/12/17 16:20
 * @Version 1.0
 *
 * 此类用户测试添加元素使用
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserEntity {
    private Integer userId;
    private String userName;

    public UserEntity(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
