package com.flowchart.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @className: OnlineFileUploadParam
 * @Description: 文件上传入参
 * @author: ct
 * @date: 2022/3/10 15:29
 */
@Data
public class OnlineFileUploadParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建人ID
     */
    private String userId;

    /**
     * 文件初始数据
     */
    private MultipartFile file;
}
