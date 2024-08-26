package net.cloud.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("User login object")
public class UserLoginRequest {
    @ApiModelProperty(value = "email",example = "1027881714@qq.com")
    private String email;
    @ApiModelProperty(value = "password",example = "123456")
    private String pwd;
}
