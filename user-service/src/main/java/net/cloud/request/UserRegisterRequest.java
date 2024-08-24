package net.cloud.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(value = "User register object", description = "User register object for request")
@Data
public class UserRegisterRequest {
    @ApiModelProperty(value = "username",example = "Johny123")
    private String name;
    @ApiModelProperty(value = "password",example = "123123")
    private String pwd;
    @ApiModelProperty(value = "avatar",example = "https://xdclass-user-service.s3.eu-west-2.amazonaws.com/user/2024/08/23/8ce88b4709564cda9c236cd1f6c9ecaf.jpg")
    private String headImg;
    @ApiModelProperty(value = "personal slogan",example = "Stay hungry, stay fool")
    private String slogan;
    @ApiModelProperty(value = "gender，0 is female, 1 is male",example = "0")
    private String sex;
    @ApiModelProperty(value = "email",example = "123@asd.com")
    private String mail;
    @ApiModelProperty(value = "verifying code",example = "123456")
    private String code;
}
