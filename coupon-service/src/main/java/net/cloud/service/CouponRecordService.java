package net.cloud.service;

import net.cloud.vo.CouponRecordVO;

import java.util.Map;

public interface CouponRecordService {
    Map<String, Object> pageData(int page, int size);

    CouponRecordVO findById(long recordId);
}
