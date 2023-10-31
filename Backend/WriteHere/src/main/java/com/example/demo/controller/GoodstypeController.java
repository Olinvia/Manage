package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Goodstype;
import com.example.demo.entity.Storage;
import com.example.demo.service.IGoodstypeService;
import com.example.demo.service.IStorageService;
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
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/goodstype")
public class GoodstypeController {
    @Autowired
    private IGoodstypeService goodstypeService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goodstype goodstype){
        return goodstypeService.save(goodstype)?Result.success():Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody Goodstype goodstype){
        return goodstypeService.updateById(goodstype)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveormod")
    public boolean saveormod(@RequestBody Goodstype goodstype){
        return goodstypeService.saveOrUpdate(goodstype);
    }

    //删除
    @PostMapping("/delete")
    public Result delete(Integer id){
        return goodstypeService.removeById(id)?Result.success():Result.fail();
    }

    @PostMapping("/result")
    public Result ListResult(@RequestBody QueryPageParam query){

        Page<Goodstype> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String)param.get("name");

        LambdaQueryWrapper<Goodstype> lambdaQueryWrapper = new LambdaQueryWrapper<Goodstype>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goodstype::getName,name);
        }

        IPage result = goodstypeService.pageCC(page,lambdaQueryWrapper);

        return Result.success(result.getTotal(),result.getRecords());
    }

    @GetMapping("/list")
    public Result list(){
        List list = goodstypeService.list();
        return !list.isEmpty() ?Result.success(list):Result.fail();
    }

}
