package com.flowchart.param;

import com.flowchart.enums.Constants;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: BasePageParam
 * @Description: 数据分页基础类
 * @author: ct
 * @date: 2022/2/28 18:13
 */
@Data
public class BasePageParam implements Serializable {

    private static final long serialVersionUID = -3263921252635611410L;

    /**
     * 页码，默认为1
     */
    private Long pageIndex = new Long(Constants.DEFAULT_PAGE_INDEX.getCode());

    /**
     * 每页数据条数，默认为10
     */
    private Long pageSize = new Long(Constants.DEFAULT_PAGE_SIZE.getCode());


    public void setPageIndex(Long pageIndex) {
        if (pageIndex <= 0) {
            this.pageIndex = new Long(Constants.DEFAULT_PAGE_INDEX.getCode());
        } else {
            this.pageIndex = pageIndex;
        }
    }

    public void setPageSize(Long pageSize) {
        if (pageSize <= 0) {
            this.pageSize = new Long(Constants.DEFAULT_PAGE_SIZE.getCode());
        } else {
            this.pageSize = pageSize;
        }
    }
}
