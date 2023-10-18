package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serial;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxq
 * @since 2023-10-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value="id",type = IdType.AUTO)
    private  Integer id;

    /**
     * 账号
     */

    private String num;

    /**
     * 名字
     */
    private String userName;

    /**
     * 密码
     */
    private String ps;

    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 电话
     */
    private String phone;

    /**
     * 角色 0超级管理员 1管理员 2普通用户
     */
    private Integer roleId;

    /**
     * 有效
     */
    @TableField("isValid")
    private String isvalid;


}
