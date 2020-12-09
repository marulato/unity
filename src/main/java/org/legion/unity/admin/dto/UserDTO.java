package org.legion.unity.admin.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel
public class UserDTO implements Serializable {

    @ApiModelProperty(value = "用户名", example = "admin", required = true)
    private String username;
    @ApiModelProperty(value = "密码", example = "admin123", required = true)
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
