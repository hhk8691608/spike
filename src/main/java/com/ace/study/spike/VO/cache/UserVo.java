package com.ace.study.spike.VO.cache;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Integer age;
    /**
     * 性别 1=男 2=女 其他=保密
     */
    private Integer sex;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }

}
