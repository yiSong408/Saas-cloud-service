package net.cloud.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginUser {
    private Long id;
    private String name;
    @JsonProperty("head_img")
    private String headImg;
    private String mail;
    @JsonProperty("ip_addr")
    private String ipAddr;

}
