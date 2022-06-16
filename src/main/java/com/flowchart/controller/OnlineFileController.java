package com.flowchart.controller;


import com.flowchart.entity.OnlineFile;
import com.flowchart.param.*;
import com.flowchart.service.OnlineFileService;
import com.flowchart.util.FileUtils;
import com.flowchart.vo.OnlineFileVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @className: onlineFile
 * @Description: 文件服务系统
 * @author: ct
 * @date: 2022/2/28 10:51
 */
@RestController
@RequestMapping("/onlineFile")
public class OnlineFileController {

    @Resource
    private OnlineFileService onlineFileService;

    /**
     * 查询用户文件基本信息列表
     *
     * @param onlineFileParam 查询文件条件
     * @return 文件基本列表，带分页
     */
    @PostMapping("/selectFilesList")
    public OnlineFileVO selectFilesList(@RequestBody OnlineFileParam onlineFileParam) {
        return onlineFileService.selectFilesList(onlineFileParam);
    }

    /**
     * 查询用户文件列表
     *
     * @param onlineFile 文件ID
     * @return 文件基本信息
     */
    @PostMapping("/selectFileById")
    public OnlineFile selectFileById(@RequestBody OnlineFile onlineFile) {
        return onlineFileService.selectFileById(onlineFile);
    }

    /**
     * 通过ID删除文件
     *
     * @param onlineFile 文件ID
     * @return 响应状态
     */
    @PostMapping("/deleteFileById")
    public Integer deleteFileById(@RequestBody OnlineFile onlineFile) {
        return onlineFileService.deleteFileById(onlineFile);
    }

    /**
     * 通过ID删除文件
     *
     * @param onlineFile 文件ID
     * @return 响应状态
     */
    @PostMapping("/recoveryFileById")
    public Integer recoveryFileById(@RequestBody OnlineFile onlineFile) {
        return onlineFileService.recoveryFileById(onlineFile);
    }

    /**
     * 通过ID永久删除文件
     *
     * @param onlineFile 文件ID
     * @return 响应状态
     */
    @PostMapping("/recoveryForeverFileById")
    public Integer recoveryForeverFileById(@RequestBody OnlineFile onlineFile) {
        return onlineFileService.recoveryForeverFileById(onlineFile);
    }


    /**
     * 新增文件信息
     *
     * @param onlineFileInsertParam 文件基本信息
     * @return 文件基本信息
     */
    @PostMapping("/insertFile")
    public OnlineFile insertFile(@RequestBody OnlineFileInsertParam onlineFileInsertParam) {
        return onlineFileService.insertFile(onlineFileInsertParam);
    }

    /**
     * 修改文件信息
     *
     * @param onlineFileUpdateParam 文件基本信息
     * @return 文件基本信息
     */
    @PostMapping("/updateFile")
    public OnlineFile updateFile(@RequestBody OnlineFileUpdateParam onlineFileUpdateParam) {
        return onlineFileService.updateFile(onlineFileUpdateParam);
    }


    /**
     * 通过socketCode查询文件信息，判断文件是否存在
     *
     * @param onlineFile 文件Socket
     * @return 文件基本信息
     */
    @PostMapping("/selectFileBySocketCode")
    public OnlineFile selectFileBySocketCode(@RequestBody OnlineFile onlineFile) {
        return onlineFileService.selectFileBySocketCode(onlineFile);
    }

    /**
     * 通过文件路徑获取文件数据
     *
     * @param onlineFile 文件地址
     * @return 文件数据
     */
    @PostMapping("/getFileDataByCode")
    public String getFileDataByCode(@RequestBody OnlineFile onlineFile) {
        return onlineFileService.getFileDataByCode(onlineFile);
    }

    /**
     * 通过socketCode修改文件数据
     *
     * @param ofudp 文件数据与文件名
     */
    @PostMapping("/updateFileDataByCode")
    public void updateFileDataByCode(@RequestBody OnlineFileUpdateDataParam ofudp) {
        onlineFileService.updateFileDataByCode(ofudp);
    }

    /**
     * 文件从本地进行导入
     *
     * @param file   上传的文件
     * @param userId 上传的用户ID
     * @return 文件信息
     */
    @PostMapping("/uploadFile")
    public OnlineFile uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        return onlineFileService.uploadFile(file, userId);
    }


    /**
     * 从文件中获取协同总数
     *
     * @return 返回协同总数
     */
    @PostMapping("/getSocketConnentNumber")
    public String getSocketConnentNumber() {
        return FileUtils.getWebSocketNumberInFile();
    }

    /**
     * 生成文件缩略图
     *
     * @param file 文件缩略图
     * @param socketCode   文件ID
     */
    @PostMapping("/createFileImage")
    public void createFileImage(@RequestParam("file") String file, @RequestParam("socketCode") String socketCode) {
        onlineFileService.createFileImage(file, socketCode);
    }

    /**
     * 图表插入图片
     *
     * @param file 文件缩略图
     * @param socketCode   文件ID
     */
    @PostMapping("/insertImage")
    public String insertImage(@RequestParam("file") MultipartFile file, @RequestParam("socketCode") String socketCode) {
        return onlineFileService.insertImage(file, socketCode);
    }

    /**
     * 图表插入图片
     *
     * @param onlineFile   文件信息
     */
    @PostMapping("/setFileToTemplate")
    public String setFileToTemplate(@RequestBody OnlineFile onlineFile) {
        return onlineFileService.setFileToTemplate(onlineFile);
    }

}
