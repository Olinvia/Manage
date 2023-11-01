package com.example.demo.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.QueryPageParam;
import com.example.demo.common.Result;
import com.example.demo.entity.Goods;
import com.example.demo.entity.Record;
import com.example.demo.entity.Storage;
import com.example.demo.service.IGoodsService;
import com.example.demo.service.IRecordService;
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
 * @since 2023-11-01
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private IRecordService recordService;

    @Autowired
    private IGoodsService goodsService;

    @GetMapping("/list")
    public Result list(){
        List list = recordService.list();
        return !list.isEmpty() ?Result.success(list):Result.fail();
    }

    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        Goods goods = goodsService.getById(record.getGoods());
        int n = record.getCount();
        int num = goods.getCount()+n;
        goods.setCount(num);
        goodsService.updateById(goods);
        return recordService.save(record)?Result.success():Result.fail();
    }

    @PostMapping("/save1")
    public Result save1(@RequestBody Record record){
        Goods goods = goodsService.getById(record.getGoods());
        int n = record.getCount();
        int num = goods.getCount()-n;
        goods.setCount(num);
        goodsService.updateById(goods);
        return recordService.save(record)?Result.success():Result.fail();
    }

    @PostMapping("/result")
    public Result ListResult(@RequestBody QueryPageParam query){

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        HashMap param = query.getParam();
        String goodsname = (String)param.get("goodsname");
        String storage = (String)param.get("storage");
        String goodstype = (String)param.get("goodstype");
        String roleId = (String)param.get("roleId");
        String userId = (String)param.get("userId");

        QueryWrapper<Record> queryWrapper = new QueryWrapper<Record>();
        queryWrapper.apply("  a.goods = b.id and b.storage=c.id and b.goodsType=d.id ");
        if("2".equals(roleId)){
            queryWrapper.eq("a.userId",userId);
        }

        if(StringUtils.isNotBlank(goodsname) && !"null".equals(goodsname)){
            queryWrapper.like("b.name",goodsname);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.eq("c.id",storage);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            queryWrapper.eq("d.id",goodstype);
        }

        IPage result = recordService.pageCC(page,queryWrapper);

        return Result.success(result.getTotal(),result.getRecords());
    }

}
