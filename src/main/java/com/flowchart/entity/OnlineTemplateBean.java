package com.flowchart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 图表模板表
 * </p>
 *
 * @author ct
 * @since 2022-03-30
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("online_template")
public class OnlineTemplateBean extends Model<OnlineTemplateBean> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 模板名称
     */
    @TableField("temp_name")
    private String tempName;

    /**
     * 模板文件路径
     */
    @TableField("temp_file_path")
    private String tempFilePath;

    /**
     * 模板文件分类
     */
    @TableField("temp_type")
    private String tempType;

    /**
     * 模板图片路径
     */
    @TableField("temp_img_path")
    private String tempImgPath;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
