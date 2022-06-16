package com.flowchart.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件数据表
 * </p>
 *
 * @author ct
 * @since 2022-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class OnlineFile extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件路徑
     */
    private String filePath;

    /**
     * 图片路徑
     */
    private String imgPath;

    /**
     * 创建人ID
     */
    private String userId;

    /**
     * 协同码
     */
    private String socketCode;

    /**
     * 画布宽度
     */
    private Integer width;

    /**
     * 画布高度
     */
    private Integer height;

    /**
     * 当前状态，0：正常，1：删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;



}
