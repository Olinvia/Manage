package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.demo.entity.Menu;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lxq
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    @GetMapping("/list")
    public Result list(@RequestParam String roleId){
        List list = menuService.lambdaQuery().like(Menu::getMenuright,roleId).list();
        return !list.isEmpty() ?Result.success(list):Result.fail();
    }
}
