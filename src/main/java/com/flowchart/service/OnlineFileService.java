package com.flowchart.service;

import com.flowchart.entity.OnlineFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.flowchart.param.*;
import com.flowchart.vo.OnlineFileVO;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 文件数据表 服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-02-28
 */
public interface OnlineFileService extends IService<OnlineFile> {

    OnlineFileVO selectFilesList(OnlineFileParam onlineFileParam);

    Integer deleteFileById(OnlineFile onlineFile);

    OnlineFile insertFile(OnlineFileInsertParam onlineFileInsertParam);

    OnlineFile updateFile(OnlineFileUpdateParam onlineFileUpdateParam);

    OnlineFile selectFileBySocketCode(OnlineFile onlineFile);

    String getFileDataByCode(OnlineFile onlineFile);

    void updateFileDataByCode(OnlineFileUpdateDataParam ofudp);

    OnlineFile selectFileById(OnlineFile onlineFile);

    OnlineFile uploadFile(MultipartFile file, String userId);

    Integer recoveryFileById(OnlineFile onlineFile);

    Integer recoveryForeverFileById(OnlineFile onlineFile);

    void createFileImage(String file, String socketCode);

    String insertImage(MultipartFile file, String socketCode);

    String setFileToTemplate(OnlineFile onlineFile);
}
