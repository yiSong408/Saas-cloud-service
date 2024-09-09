package net.cloud.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("Object for adding address")
@Data
public class AddressAddRequest {
    @ApiModelProperty(value = "default status, 0No; 1Yes", example = "0")
    @JsonProperty("default_status")
    private Integer defaultStatus;
    @ApiModelProperty(value = "name of the receiver", example = "Yi")
    @JsonProperty("receive_name")
    private String receiveName;
    @ApiModelProperty(value = "phone number", example = "1231231234")
    private String phone;
    @ApiModelProperty(value = "province of address", example = "jiangsu")
    private String province;
    @ApiModelProperty(value = "city of address", example = "nantong")
    private String city;
    @ApiModelProperty(value = "region of address", example = "chongchuan")
    private String region;
    @JsonProperty("detailed_address")
    @ApiModelProperty(value = "detail address", example = "yujinchen")
    private String detailedAddress;

}
