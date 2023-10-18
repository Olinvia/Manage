package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private int id;
    private String num;
    private String userName;
    private String ps;
    private int age;
    private int sex;
    private String phone;
    private int roleId;
    @TableField("isvalid")
    private String isValid;


}
