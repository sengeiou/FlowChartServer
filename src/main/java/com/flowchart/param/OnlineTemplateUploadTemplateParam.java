package com.flowchart.param;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @className: OnlineTemplateUploadTemplateParam
 * @Description: TODO
 * @author: ct
 * @date: 2022/3/30 12:08
 */
@Data
public class OnlineTemplateUploadTemplateParam implements Serializable {

    private String tempName;

    private MultipartFile tempData;

    private MultipartFile tempImg;


}
