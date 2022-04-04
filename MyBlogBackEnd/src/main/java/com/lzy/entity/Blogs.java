package com.lzy.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuzhiyu
 * @since 2022-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "blogs",schema = "blogschema")
public class Blogs implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "blog_id", type = IdType.AUTO)
    private Long blogId;

    private Long userId;

    @NotBlank(message = "Prompt : Empty title is invalid.")
    private String title;

    @NotBlank(message = "Prompt : Empty description is invalid.")
    private String description;

    @NotBlank(message = "Prompt : Empty blog content is invalid.")
    private String blogContent;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDateTime created;

    private Integer status;
}
