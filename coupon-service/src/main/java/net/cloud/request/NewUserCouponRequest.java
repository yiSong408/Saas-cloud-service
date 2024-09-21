package net.cloud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class NewUserCouponRequest {
    @ApiModelProperty(value = "user id",example = "3")
    @JsonProperty("user_id")
    private Long userId;
    @ApiModelProperty(value = "user name",example = "Rongyi")
    private String name;
}
