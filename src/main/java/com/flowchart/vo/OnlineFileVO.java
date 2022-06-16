package com.flowchart.vo;

import com.flowchart.entity.OnlineFile;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @className: OnlineFileVO
 * @Description: TODO
 * @author: ct
 * @date: 2022/3/1 9:08
 */
@Data
public class OnlineFileVO extends BasePageVO {

    List<OnlineFile> records;
}
