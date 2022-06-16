package com.flowchart.enums;

import lombok.Data;

/**
 * @className: Constants
 * @Description: 枚举常量类
 * @author: ct
 * @date: 2022/3/18 15:09
 */
public enum  Constants {

    /**
     * 全局公共枚举参数
     */
    ONLINE_FILE_STATUS_SHOW(0,"文件显示"),

    ONLINE_FILE_STATUS_HIDE(1,"文件隐藏"),

    DEFAULT_PAGE_INDEX(1,"分页页码"),

    DEFAULT_PAGE_SIZE(10,"每页数据条数");


    /**
     * 常量Code
     */
    private final int code;


    /**
     * 构造方法
     * @param code
     * @param desc
     */
    Constants(int code, String desc) {
        this.code = code;
    }

    /**
     * 获取Code方法
     * @return
     */
    public Integer getCode() {
        return code;
    }

}
