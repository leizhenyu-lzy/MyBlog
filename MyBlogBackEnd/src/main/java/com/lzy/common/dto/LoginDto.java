package com.lzy.common.dto;

import java.io.Serializable;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class LoginDto implements Serializable
{
    @NotBlank(message = "Login Prompt : Username can't be empty.")
    private String username;

    @NotBlank(message = "Login Prompt : Password can't be empty.")
    private String password;
}
