package net.cloud.model;

import java.math.BigDecimal;
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
 * @since 2024-09-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("coupon")
public class CouponDO implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Type of coupon [NEW_USER, TASK, PROMOTION]
     */
    private String category;

    /**
     * Status [PUBLISH, DRAFT, OFFLINE]
     */
    private String publish;

    private String couponImg;

    private String couponTitle;

    private BigDecimal price;

    private Integer userLimit;

    private Date startTime;

    private Date endTime;

    private Integer publishCount;

    private Integer stock;

    private Date createTime;

    private BigDecimal conditionPrice;


}
