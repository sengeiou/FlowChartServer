package com.flowchart.param;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @className: OnlineFileParam
 * @Description: 在线文件请求类
 * @author: ct
 * @date: 2022/2/28 18:11
 */
@Data
public class OnlineFileParam extends BasePageParam{

    /**
     * 创建人ID
     */
    private String userId;

    /**
     * 接口类型，1：查询全部，2：查询最近一周，3，查询被删除的数据
     */
    private Integer type;

    /**
     * 接口类型，1：查询全部，2：查询最近一周，3，查询被删除的数据
     */
    private String fileName;


}
