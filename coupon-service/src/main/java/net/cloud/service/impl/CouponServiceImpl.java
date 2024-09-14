package net.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.cloud.enums.BizCodeEnum;
import net.cloud.enums.CouponCategoryEnum;
import net.cloud.enums.CouponPublishEnum;
import net.cloud.enums.CouponStateEnum;
import net.cloud.exception.BizException;
import net.cloud.interceptor.LoginInterceptor;
import net.cloud.mapper.CouponMapper;
import net.cloud.mapper.CouponRecordMapper;
import net.cloud.model.CouponDO;
import net.cloud.model.CouponRecordDO;
import net.cloud.model.LoginUser;
import net.cloud.service.CouponService;
import net.cloud.util.CommonUtil;
import net.cloud.util.JsonData;
import net.cloud.vo.CouponVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CouponServiceImpl implements CouponService {
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CouponRecordMapper couponRecordMapper;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public Map<String, Object> getCouponPageData(int page, int size) {
        Page<CouponDO> pageInfo = new Page<>(page, size);
        IPage<CouponDO> couponDOPage = couponMapper.selectPage(pageInfo, new QueryWrapper<CouponDO>().eq("publish", CouponPublishEnum.PUBLISH)
                .eq("category", CouponCategoryEnum.PROMOTION).orderByDesc("create_time"));
        Map<String, Object> map = new HashMap<>(3);
        map.put("total_record", couponDOPage.getTotal());
        map.put("total_page", couponDOPage.getPages());
        map.put("current_data", couponDOPage.getRecords().stream().map(obj -> {
            CouponVO couponVO = new CouponVO();
            BeanUtils.copyProperties(obj, couponVO);
            return couponVO;
        }).collect(Collectors.toList()));
        return map;
    }
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public JsonData addCoupon(long couponId, CouponCategoryEnum category) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String lockKey = "lock:coupon:" + couponId;
        RLock rLock = redissonClient.getLock(lockKey);
        rLock.lock();
        try {
            CouponDO couponDO = couponMapper.selectOne(new QueryWrapper<CouponDO>().eq("id", couponId).eq("category", category.name()));

            this.checkCoupon(couponDO, loginUser.getId());

            CouponRecordDO couponRecordDO = new CouponRecordDO();
            BeanUtils.copyProperties(couponDO, couponRecordDO);
            couponRecordDO.setCreateTime(new Date());
            couponRecordDO.setUseState(CouponStateEnum.NEW.name());
            couponRecordDO.setUserId(loginUser.getId());
            couponRecordDO.setUserName(loginUser.getName());
            couponRecordDO.setCouponId(couponId);
            couponRecordDO.setId(null);

            int rows = couponMapper.reduceStock(couponId);
            if (rows == 1) {
                couponRecordMapper.insert(couponRecordDO);
            } else {
                throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
            }
        } finally {
            rLock.unlock();
        }
        return JsonData.buildSuccess();
    }

    private void checkCoupon(CouponDO couponDO, long userId) {
        if (couponDO == null) {
            throw new BizException(BizCodeEnum.COUPON_NOT_EXIST);
        }
        if (!couponDO.getPublish().equals(CouponPublishEnum.PUBLISH.name())) {
            throw new BizException(BizCodeEnum.COUPON_NOT_EXIST);
        }
        if (couponDO.getStock() <= 0) {
            throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }
        long time = CommonUtil.getCurrentTimestamp();
        long startTime = couponDO.getStartTime().getTime();
        long endTime = couponDO.getEndTime().getTime();
        if (startTime > time || time > endTime) {
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_TIME);
        }
        Integer selectedUserCount = couponRecordMapper.selectCount(new QueryWrapper<CouponRecordDO>().eq("user_id", userId).eq("coupon_id", couponDO.getId()));
        if (selectedUserCount >= couponDO.getUserLimit()) {
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_LIMIT);
        }
    }
}
