package com.example.demo.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.Record;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lxq
 * @since 2023-11-01
 */
public interface IRecordService extends IService<Record> {
    IPage pageCC(IPage<Record> page, Wrapper wrapper);
}
