package com.flowchart.service;

import com.flowchart.entity.OnlineTemplateBean;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flowchart.param.OnlineTemplateSelectListParam;
import com.flowchart.param.OnlineTemplateUploadTemplateParam;
import com.flowchart.vo.PageListOnlineTemplateVO;

/**
 * <p>
 * 图表模板表 服务类
 * </p>
 *
 * @author ct
 * @since 2022-03-30
 */
public interface OnlineTemplateService extends IService<OnlineTemplateBean> {

    PageListOnlineTemplateVO selectOnlineTemplateList(OnlineTemplateSelectListParam param);

    String uploadTemplate(OnlineTemplateUploadTemplateParam param);

    String getFileDataByCode(OnlineTemplateBean onlineTemplateBean);

}
