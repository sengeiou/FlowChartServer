package com.flowchart.param;

import lombok.Data;

import java.io.File;

/**
 * @className: OnlineFileUpdateDataParam
 * @Description: TODO
 * @author: ct
 * @date: 2022/3/7 14:31
 */
@Data
public class OnlineFileUpdateDataParam {

    private String filePath;

    private String fileData;
}
