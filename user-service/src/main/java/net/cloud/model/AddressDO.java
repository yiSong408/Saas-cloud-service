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
@TableName("xdclass_address")
public class AddressDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * if is default address:0No;1Yes
     */
    private Integer defaultStatus;

    private String receiveName;

    private String phone;

    private String province;

    private String city;

    private String region;

    private String detailedAddress;

    private Date createTime;


}
