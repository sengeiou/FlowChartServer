package com.flowchart.controller;


import com.flowchart.entity.ChartClissifyBean;
import com.flowchart.service.ChartClissifyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 模板分类表 前端控制器
 * </p>
 *
 * @author ct
 * @since 2022-05-30
 */
@RestController
@RequestMapping("/clissify")
public class ChartClissifyController {

    @Resource
    private ChartClissifyService chartClissifyService;

    @PostMapping("/selectClissifyList")
    public List<ChartClissifyBean> selectClissifyList(){
        return chartClissifyService.selectClissifyList();
    }

}
