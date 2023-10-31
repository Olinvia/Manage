package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Goodstype;
import com.example.demo.entity.Storage;
import com.example.demo.mapper.GoodstypeMapper;
import com.example.demo.mapper.StorageMapper;
import com.example.demo.service.IGoodstypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lxq
 * @since 2023-10-31
 */
@Service
public class GoodstypeServiceImpl extends ServiceImpl<GoodstypeMapper, Goodstype> implements IGoodstypeService {
    @Resource
    private GoodstypeMapper goodstypeMapper;
    @Override
    public IPage pageCC(IPage<Goodstype> page, Wrapper wrapper) {
        return goodstypeMapper.pageCC(page, wrapper);
    }

}
