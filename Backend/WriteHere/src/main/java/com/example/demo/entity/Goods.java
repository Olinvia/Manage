package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxq
 * @since 2023-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value="id",type = IdType.AUTO)
    private  Integer id;

    private String name;

    private Integer storage;

    @TableField("goodsType")
    private Integer goodstype;

    private Integer count;

    private String remark;


}
