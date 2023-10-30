package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author lxq
 * @since 2023-10-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("menuCode")
    private String menucode;

    @TableField("menuName")
    private String menuname;

    @TableField("menuLevel")
    private String menulevel;

    @TableField("menuParentCode")
    private String menuparentcode;

    @TableField("menuClick")
    private String menuclick;

    @TableField("menuRight")
    private String menuright;

    @TableField("menuComponent")
    private String menucomponent;

    @TableField("menuIcon")
    private String menuicon;


}
