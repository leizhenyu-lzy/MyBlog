package com.lzy.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
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
@TableName(value = "users",schema = "blogschema")
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    @NotBlank(message = "Prompt : Empty username is invalid.")
    private String username;

    private String avatar;

    @NotBlank(message = "Prompt : Empty email address is invalid.")
    @Email(message = "Prompt : Wrong email address format.")
    private String email;

    private String userPassword;

    private Long status;

    private LocalDateTime created;

    private LocalDateTime lastLogin;

}
