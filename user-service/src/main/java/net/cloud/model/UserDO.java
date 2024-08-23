package net.cloud.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author Rongyi
 * @since 2024-08-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("xdclass_user")
public class UserDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String pwd;

    private String headImg;

    private String slogan;

    /**
     * 0femal, 1male
     */
    private Integer sex;

    private Integer points;

    private Date createTime;

    private String mail;

    private String secret;


}
