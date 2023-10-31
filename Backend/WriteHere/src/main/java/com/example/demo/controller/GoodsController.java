package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Goods;
import com.example.demo.entity.Menu;
import com.example.demo.entity.Storage;
import com.example.demo.entity.User;
import com.example.demo.service.IGoodsService;
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
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService goodsService;

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)?Result.success():Result.fail();
    }
    //修改
    @PostMapping("/mod")
    public Result mod(@RequestBody Goods goods){
        return goodsService.updateById(goods)?Result.success():Result.fail();
    }
    //新增或修改
    @PostMapping("/saveormod")
    public boolean saveormod(@RequestBody Goods goods){
        return goodsService.saveOrUpdate(goods);
    }

    //删除
    @PostMapping("/delete")
    public Result delete(Integer id){
        return goodsService.removeById(id)?Result.success():Result.fail();
    }

    @PostMapping("/result")
    public Result ListResult(@RequestBody QueryPageParam query){

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String storage = (String)param.get("storage");
        String goodstype = (String)param.get("goodstype");


        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<Goods>();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goods::getName,name);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage,storage);
        }

        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }

        IPage result = goodsService.pageCC(page,lambdaQueryWrapper);

        return Result.success(result.getTotal(),result.getRecords());
    }


}
