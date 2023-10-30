package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Storage;
import com.example.demo.entity.User;
import com.example.demo.service.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lxq
 * @since 2023-10-30
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    private IStorageService storageService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){
        return storageService.save(storage)?Result.success():Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody Storage storage){
        return storageService.updateById(storage)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveormod")
    public boolean saveormod(@RequestBody Storage storage){
        return storageService.saveOrUpdate(storage);
    }

    //删除
    @PostMapping("/delete")
    public Result delete(Integer id){
        return storageService.removeById(id)?Result.success():Result.fail();
    }

    @PostMapping("/result")
    public Result ListResult(@RequestBody QueryPageParam query){

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper<Storage>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Storage::getName,name);
        }

        IPage result = storageService.pageCC(page,lambdaQueryWrapper);

        return Result.success(result.getTotal(),result.getRecords());
    }

}
