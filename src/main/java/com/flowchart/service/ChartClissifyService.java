package com.flowchart.service;

import com.flowchart.entity.ChartClissifyBean;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 模板分类表 服务类
 * </p>
 *
 * @author ct
 * @since 2022-05-30
 */
public interface ChartClissifyService extends IService<ChartClissifyBean> {

    List<ChartClissifyBean> selectClissifyList();
}
