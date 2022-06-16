package com.flowchart.vo;

import com.flowchart.entity.OnlineTemplateBean;
import lombok.Data;

import java.util.List;

/**
 * @className: pageListOnlineTemplateVO
 * @Description: 模板分页返回VO
 * @author: ct
 * @date: 2022/3/30 11:23
 */
@Data
public class PageListOnlineTemplateVO extends BasePageVO{

    List<OnlineTemplateBean> records;
}
