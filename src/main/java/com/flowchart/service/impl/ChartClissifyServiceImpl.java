package com.flowchart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flowchart.entity.ChartClissifyBean;
import com.flowchart.mapper.ChartClissifyMapper;
import com.flowchart.service.ChartClissifyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 模板分类表 服务实现类
 * </p>
 *
 * @author ct
 * @since 2022-05-30
 */
@Service
public class ChartClissifyServiceImpl extends ServiceImpl<ChartClissifyMapper, ChartClissifyBean> implements ChartClissifyService {

    @Resource
    private ChartClissifyMapper chartClissifyMapper;
    @Override
    public List<ChartClissifyBean> selectClissifyList() {
        return chartClissifyMapper.selectList(new QueryWrapper<>());
    }
}
