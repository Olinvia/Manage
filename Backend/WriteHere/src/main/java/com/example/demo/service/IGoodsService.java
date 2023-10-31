package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Goodstype;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxq
 * @since 2023-10-31
 */
public interface IGoodsService extends IService<Goods> {
    IPage pageCC(IPage<Goods> page, Wrapper wrapper);
}
