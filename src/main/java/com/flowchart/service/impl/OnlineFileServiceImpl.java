package com.flowchart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flowchart.entity.OnlineFile;
import com.flowchart.entity.OnlineTemplateBean;
import com.flowchart.enums.Constants;
import com.flowchart.mapper.OnlineFileMapper;
import com.flowchart.mapper.OnlineTemplateMapper;
import com.flowchart.mapstruct.OnlineFileConversion;
import com.flowchart.param.*;
import com.flowchart.service.OnlineFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flowchart.util.FileUtils;
import com.flowchart.util.UuidGeneratorUtil;
import com.flowchart.vo.OnlineFileVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 文件数据表 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-02-28
 */
@Service
public class OnlineFileServiceImpl extends ServiceImpl<OnlineFileMapper, OnlineFile> implements OnlineFileService {

    @Resource
    private OnlineFileMapper onlineFileMapper;

    @Resource
    private OnlineTemplateMapper onlineTemplateMapper;

    @Value("${upload.uploadAddr}")
    private String uploadAddr;

    @Override
    public OnlineFileVO selectFilesList(OnlineFileParam onlineFileParam) {
        OnlineFileVO result = new OnlineFileVO();

        IPage<OnlineFile> page = new Page<>(onlineFileParam.getPageIndex(), onlineFileParam.getPageSize());
        QueryWrapper<OnlineFile> wrapper = new QueryWrapper<>();
        /**
         * 0我的文件：创建时间倒序，正常状态0
         * 1最近修改：修改时间倒序，正常状态0
         * 2回收站：  修改时间倒序，删除状态1
         */
        int index = onlineFileParam.getType();
        if(index == 1){
            //我的文件，根据创建时间倒序排列，查询未删除的文件
            wrapper.orderByDesc( "create_time")
                    .eq("status",Constants.ONLINE_FILE_STATUS_SHOW.getCode());
        }
        if(index == 2){
            //计算日期
            //获取今天日期
            LocalDateTime now = LocalDateTime.now();
            //获取上月的今天
            LocalDateTime minusDays = now.minusDays(30L);


            //我的修改，查询最近七天进行过修改的文件
            wrapper.orderByDesc( "update_time")
                    .eq("status",Constants.ONLINE_FILE_STATUS_SHOW.getCode())
                    .lt(onlineFileParam.getType() == 1, "update_time", now)
                    .gt(onlineFileParam.getType() == 1, "update_time", minusDays);
        }
        if(index == 3){
            //我的删除，修改时间倒序，查询所有被删除文件
            wrapper.orderByDesc( "update_time")
                    .eq( "status", Constants.ONLINE_FILE_STATUS_HIDE.getCode());
        }
        //通用条件
        wrapper.eq("user_id", onlineFileParam.getUserId())
                .like(StringUtils.isNotBlank(onlineFileParam.getFileName()), "file_name", onlineFileParam.getFileName())
                .orderByDesc("file_name");


        IPage<OnlineFile> iPage = onlineFileMapper.selectPage(page, wrapper);

        List<OnlineFile> records = iPage.getRecords();
        result.setPageIndex(iPage.getCurrent());
        result.setPageSize(iPage.getSize());
        result.setTotal(iPage.getTotal());
        result.setRecords(records);
        return result;
    }

    @Override
    public Integer deleteFileById(OnlineFile onlineFile) {
        QueryWrapper<OnlineFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", onlineFile.getId());
        onlineFile.setStatus(Constants.ONLINE_FILE_STATUS_HIDE.getCode());
        return onlineFileMapper.update(onlineFile, queryWrapper);
    }

    @Transactional
    @Override
    public OnlineFile insertFile(OnlineFileInsertParam onlineFileInsertParam) {
        OnlineFile onlineFile = OnlineFileConversion.INSTANCE.insertToOnlineFile(onlineFileInsertParam);
        //生成协同码
        onlineFile.setSocketCode(UuidGeneratorUtil.getuuid32().toUpperCase());
        //创建空文件
        String filePath = FileUtils.createEmptyFile(onlineFile.getSocketCode(), onlineFileInsertParam.getFileData(), "chart");
        onlineFile.setFilePath(filePath);
        onlineFileMapper.insert(onlineFile);
        return onlineFile;
    }

    @Override
    public OnlineFile updateFile(OnlineFileUpdateParam onlineFileUpdateParam) {
        OnlineFile onlineFile = OnlineFileConversion.INSTANCE.updateToOnlineFile(onlineFileUpdateParam);
        onlineFile.setUpdateTime(LocalDateTime.now());
        onlineFileMapper.updateById(onlineFile);
        return onlineFileMapper.selectById(onlineFile.getId());
    }

    @Override
    public OnlineFile selectFileBySocketCode(OnlineFile onlineFile) {
        QueryWrapper<OnlineFile> wrapper = new QueryWrapper<>();
        wrapper.eq("socket_code", onlineFile.getSocketCode());
        return onlineFileMapper.selectOne(wrapper);
    }

    @Override
    public String getFileDataByCode(OnlineFile onlineFile) {
        return FileUtils.getFileData(onlineFile.getFilePath(), "chart");
    }

    @Override
    public void updateFileDataByCode(OnlineFileUpdateDataParam ofudp) {
        FileUtils.updateFileData(ofudp.getFilePath(), ofudp.getFileData(), "chart");
    }

    @Override
    public OnlineFile selectFileById(OnlineFile onlineFile) {
        return onlineFileMapper.selectById(onlineFile.getId());
    }

    @Override
    public OnlineFile uploadFile(MultipartFile file, String userId) {
        OnlineFile onlineFile = new OnlineFile();
        onlineFile.setUserId(userId);
        String fileName = file.getOriginalFilename();
        if (fileName != null) {
            fileName = fileName.substring(0, fileName.lastIndexOf("."));
        } else {
            fileName = "未命名";
        }
        onlineFile.setFileName(fileName);
        //生成协同码
        onlineFile.setSocketCode(UuidGeneratorUtil.getuuid32().toUpperCase());
        //创建空文件
        String filePath = FileUtils.saveFileData(onlineFile.getSocketCode(), file, "chart");
        onlineFile.setFilePath(filePath);
        onlineFileMapper.insert(onlineFile);

        return onlineFile;
    }

    @Override
    public Integer recoveryFileById(OnlineFile onlineFile) {
        QueryWrapper<OnlineFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", onlineFile.getId());
        onlineFile.setStatus(Constants.ONLINE_FILE_STATUS_SHOW.getCode());
        return onlineFileMapper.update(onlineFile, queryWrapper);
    }

    @Override
    public Integer recoveryForeverFileById(OnlineFile onlineFile) {
        //删除文件
        FileUtils.deleteFile(onlineFile.getFilePath());
        //删除数据
        return onlineFileMapper.deleteById(onlineFile.getId());
    }

    @Override
    public void createFileImage(String file, String socketCode) {
        //生成图片
        String imgPath = FileUtils.createFileImage(socketCode, file, "chart");
        OnlineFile onlineFile = new OnlineFile();
        onlineFile.setSocketCode(socketCode);
        onlineFile.setImgPath(imgPath);

        LambdaUpdateWrapper<OnlineFile> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(OnlineFile::getSocketCode, socketCode).set(OnlineFile::getImgPath, imgPath);

        onlineFileMapper.update(null,lambdaUpdateWrapper);
    }

    @Override
    public String insertImage(MultipartFile file, String socketCode) {
        //生成图片
        return FileUtils.insertImage(socketCode, file, "chart");
    }

    @Override
    public String setFileToTemplate(OnlineFile onlineFile) {
        //获取文件信息
        OnlineFile of = onlineFileMapper.selectById(onlineFile.getId());

        try {
            //1.读取本地文件图片
            File tempImg = new File(uploadAddr + of.getImgPath().substring(10,of.getImgPath().length()));
            MultipartFile imgFile = new MockMultipartFile(
                    "file",
                    tempImg.getName(),
                    null,
                    new FileInputStream(tempImg));


            Map<String,String> map = FileUtils.uploadImage(of.getSocketCode(), imgFile);
            if(map == null || map.isEmpty()) {
                return null;
            }

            //2.上传文件
            File tempFile = new File(uploadAddr + of.getFilePath());
            MultipartFile tempDataFile = new MockMultipartFile(
                    "file",
                    tempFile.getName(),
                    null,
                    new FileInputStream(tempFile));

            String filePath = FileUtils.saveFileData(of.getSocketCode(), tempDataFile, "template");

            //3.保存信息
            OnlineTemplateBean onlineTemplateBean = new OnlineTemplateBean();
            onlineTemplateBean.setTempName(of.getFileName());
            onlineTemplateBean.setTempImgPath(map.get("imgPath"));
            onlineTemplateBean.setTempFilePath(filePath);

            onlineTemplateMapper.insert(onlineTemplateBean);
        } catch (Exception e){
            System.out.println(e.toString());
        }

        return null;
    }
}
