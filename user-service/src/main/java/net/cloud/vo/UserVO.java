package net.cloud.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserVO {
    private Long id;

    private String name;
    @JsonProperty("head_img")
    private String headImg;

    private String slogan;

    /**
     * 0femal, 1male
     */
    private Integer sex;

    private Integer points;

    private String mail;
}
