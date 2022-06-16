package com.flowchart.controller;


import com.flowchart.entity.OnlineTemplateBean;
import com.flowchart.param.OnlineTemplateSelectListParam;
import com.flowchart.param.OnlineTemplateUploadTemplateParam;
import com.flowchart.service.OnlineTemplateService;
import com.flowchart.vo.PageListOnlineTemplateVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 图表模板表 前端控制器
 * </p>
 *
 * @author ct
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/onlineTemplate")
public class OnlineTemplateController {

    @Resource
    private OnlineTemplateService onlineTemplateService;


    /**
     * 查询在线模板列表
     * @param param 接收分页参数，名称，类型
     * @return 返回模板列表数据
     */
    @PostMapping("/selectOnlineTemplateList")
    public PageListOnlineTemplateVO selectOnlineTemplateList(@RequestBody OnlineTemplateSelectListParam param){
        return onlineTemplateService.selectOnlineTemplateList(param);
    }

    /**
     * 表单形式提交数据，上传模板信息
     * @param param  接收模板文件名，数据，图片
     * @return 返回修改记录数
     */
    @PostMapping("/uploadTemplate")
    public String uploadTemplate(OnlineTemplateUploadTemplateParam param){
        return onlineTemplateService.uploadTemplate(param);
    }


    /**
     * 通过模板路径获取模板数据
     * @param onlineTemplateBean 接收模板路径参数
     * @return 返回模板数据
     */
    @PostMapping("/getFileDataByCode")
    public String getFileDataByCode(@RequestBody OnlineTemplateBean onlineTemplateBean){
        return onlineTemplateService.getFileDataByCode(onlineTemplateBean);
    }


}
