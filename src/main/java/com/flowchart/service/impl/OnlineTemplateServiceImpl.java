package com.flowchart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flowchart.entity.OnlineTemplateBean;
import com.flowchart.mapper.OnlineTemplateMapper;
import com.flowchart.param.OnlineTemplateSelectListParam;
import com.flowchart.param.OnlineTemplateUploadTemplateParam;
import com.flowchart.service.OnlineTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flowchart.util.FileUtils;
import com.flowchart.vo.PageListOnlineTemplateVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 图表模板表 服务实现类
 * </p>
 *
 * @author ct
 * @since 2022-03-30
 */
@Service
public class OnlineTemplateServiceImpl extends ServiceImpl<OnlineTemplateMapper, OnlineTemplateBean> implements OnlineTemplateService {

    @Resource
    private OnlineTemplateMapper onlineTemplateMapper;

    @Override
    public PageListOnlineTemplateVO selectOnlineTemplateList(OnlineTemplateSelectListParam param) {
        System.err.println(param.getPageIndex());
        PageListOnlineTemplateVO result = new PageListOnlineTemplateVO();
        //只翻页，不做页面数据展示条数限制
        IPage<OnlineTemplateBean> page = new Page<>(param.getPageIndex(),param.getPageSize());

        QueryWrapper<OnlineTemplateBean> wrapper = new QueryWrapper<>();
        //创建时间倒序排列
        wrapper.orderByDesc("create_time");
        //是否通过文件名/类型查询
        wrapper.like(!StringUtils.isBlank(param.getTempName()),"temp_name",param.getTempName());
        //通过分类ID检索模板列表
        //模糊查询法
        if(CollectionUtils.isNotEmpty(param.getTempType())){
            param.getTempType().forEach(e-> {
                wrapper.or().apply("FIND_IN_SET ("+e+",temp_type)");
            });
        }

        IPage<OnlineTemplateBean> templatePageList = onlineTemplateMapper.selectPage(page, wrapper);
        result.setPageIndex(templatePageList.getCurrent());
        result.setPageSize(templatePageList.getSize());
        result.setTotal(templatePageList.getTotal());
        result.setRecords(templatePageList.getRecords());
        return result;
    }

    @Override
    public String uploadTemplate(OnlineTemplateUploadTemplateParam param) {

        //1.上传图片
        MultipartFile tempImg = param.getTempImg();
        String fileName = UUID.randomUUID().toString().toUpperCase();

        Map<String,String> map = FileUtils.uploadImage(fileName, tempImg);
        if(map == null || map.isEmpty()) {
            return null;
        }

        //2.上传文件
        MultipartFile tempData = param.getTempData();
        String filePath = FileUtils.saveFileData(fileName, tempData, "template");

        //3.保存信息
        OnlineTemplateBean onlineTemplateBean = new OnlineTemplateBean();
        onlineTemplateBean.setTempName(param.getTempName());
        onlineTemplateBean.setTempImgPath(map.get("imgPath"));
        onlineTemplateBean.setTempFilePath(filePath);

        onlineTemplateMapper.insert(onlineTemplateBean);
        return null;
    }

    @Override
    public String getFileDataByCode(OnlineTemplateBean onlineTemplateBean) {
        return FileUtils.getFileData(onlineTemplateBean.getTempFilePath(),"template");
    }
}
