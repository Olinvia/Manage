package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lxq
 * @since 2023-10-18
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping("/list")
    public List<User> list(){
        return userService.list();
    }

    //新增
    @PostMapping("/save")
    public boolean save(@RequestBody User user){
        return userService.save(user);
    }
    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user){
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveormod")
    public boolean saveormod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    //删除
    @PostMapping("/delete")
    public boolean delete(Integer id){
        return userService.removeById(id);
    }

    //查询
    @PostMapping("/query")
    public List<User> query(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.like(User::getUserName,user.getUserName());
        return userService.list(lambdaQueryWrapper);
    }


}
