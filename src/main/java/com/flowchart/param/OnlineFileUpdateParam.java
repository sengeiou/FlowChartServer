package com.flowchart.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @className: OnlineFileInsertParam
 * @Description: 新增文件接收实体类
 * @author: ct
 * @date: 2022/3/1 15:24
 */
@Data
public class OnlineFileUpdateParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    private Integer id;

    /**
     * 文件名
     */
    private String fileName;


    /**
     * 创建人ID
     */
    private String userId;

    /**
     * 协同码
     */
    private String socketCode;

    /**
     * 画布宽度
     */
    private Integer width;

    /**
     * 画布高度
     */
    private Integer height;


}
