package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Goodstype;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxq
 * @since 2023-10-31
 */
public interface IGoodstypeService extends IService<Goodstype> {
    IPage pageCC(IPage<Goodstype> page, Wrapper wrapper);
}
