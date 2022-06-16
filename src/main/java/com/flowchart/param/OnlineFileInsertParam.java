package com.flowchart.param;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @className: OnlineFileInsertParam
 * @Description: 新增文件接收实体类
 * @author: ct
 * @date: 2022/3/1 15:24
 */
@Data
public class OnlineFileInsertParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件初始数据
     */
    private String fileData;

    /**
     * 创建人ID
     */
    private String userId;





}
