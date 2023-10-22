package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.success():Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody User user){
        return userService.updateById(user)?Result.success():Result.fail();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        List list = userService.lambdaQuery().eq(User::getNum,user.getNum())
                .eq(User::getPs,user.getPs()).list();
        return !list.isEmpty() ?Result.success(list.get(0)):Result.fail();
    }

    //新增或修改
    @PostMapping("/saveormod")
    public boolean saveormod(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    //删除
    @PostMapping("/delete")
    public Result delete(Integer id){
        return userService.removeById(id)?Result.success():Result.fail();
    }

    @PostMapping("/findByNum")
    public Result findByNum(String num){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        if(StringUtils.isNotBlank(num)){
            lambdaQueryWrapper.eq(User::getNum,num);
        }
        List list = userService.list(lambdaQueryWrapper);
        return !list.isEmpty() ?Result.success(list):Result.fail();
    }

    //查询
    @PostMapping("/query")
    public Result query(@RequestBody User user){
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        if(StringUtils.isNotBlank(user.getUserName())){
            lambdaQueryWrapper.like(User::getUserName,user.getUserName());
        }
        return Result.success(userService.list(lambdaQueryWrapper));
    }

    @PostMapping("/listPage")
    public List<User> listPage(@RequestBody QueryPageParam query){

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.like(User::getUserName,name);

        IPage result = userService.page(page,lambdaQueryWrapper);

        System.out.println(result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/listPageC")
    public List<User> listPageC(@RequestBody QueryPageParam query){
        System.out.println(query);

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        lambdaQueryWrapper.like(User::getUserName,name);

        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println(result.getTotal());

        return result.getRecords();
    }

    @PostMapping("/result")
    public Result ListResult(@RequestBody QueryPageParam query){

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String sex = (String)param.get("sex");

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(User::getUserName,name);
        }
        if(StringUtils.isNotBlank(sex)){
            lambdaQueryWrapper.like(User::getSex,sex);
        }



        IPage result = userService.pageCC(page,lambdaQueryWrapper);

        System.out.println(result.getTotal());

        return Result.success(result.getTotal(),result.getRecords());
    }

}
