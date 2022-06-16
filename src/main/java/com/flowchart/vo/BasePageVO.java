package com.flowchart.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: BasePageVO
 * @Description: 分页VO
 * @author: ct
 * @date: 2022/3/1 9:09
 */
@Data
public class BasePageVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前页
     */
    private Long pageIndex;

    /**
     * 每页条数
     */
    private Long pageSize;

    /**
     * 总条数
     */
    private Long total;


}
