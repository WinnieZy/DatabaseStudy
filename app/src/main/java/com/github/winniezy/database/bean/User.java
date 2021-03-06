package com.github.winniezy.database.bean;

import com.github.winniezy.database.annotation.DbField;
import com.github.winniezy.database.annotation.DbTable;

// 得到User对应表名
@DbTable("tb_user")
public class User {

    // 得到User对应列名
    @DbField("u_id")
    private Integer id;//fix:查询失败，由于int非object导致认为默认值0是查询条件
    private String username;
    private String password;
    private Integer status;

    public User(){}

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}
