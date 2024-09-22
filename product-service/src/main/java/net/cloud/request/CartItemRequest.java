package net.cloud.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class CartItemRequest {
    @ApiModelProperty(value = "product id",example = "1")
    @JsonProperty("product_id")
    private long productId;
    @ApiModelProperty(value = "amount of product", example = "1")
    private Integer amount;
}
