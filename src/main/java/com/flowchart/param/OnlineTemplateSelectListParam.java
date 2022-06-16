package com.flowchart.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @className: OnlineTemplateSelectListParam
 * @Description: TODO
 * @author: ct
 * @date: 2022/3/30 11:17
 */
@Data
public class OnlineTemplateSelectListParam extends BasePageParam {

    /**
     * 创建人ID
     */
    private String userId;

    /**
     * 模板类型
     */
    private List<String> tempType;

    /**
     * 模板文件名称
     */
    private String tempName;


}
