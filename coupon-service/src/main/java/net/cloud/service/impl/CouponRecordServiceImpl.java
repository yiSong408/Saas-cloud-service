package net.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.cloud.interceptor.LoginInterceptor;
import net.cloud.mapper.CouponRecordMapper;
import net.cloud.model.CouponRecordDO;
import net.cloud.model.LoginUser;
import net.cloud.service.CouponRecordService;
import net.cloud.vo.CouponRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CouponRecordServiceImpl implements CouponRecordService {
    @Autowired
    private CouponRecordMapper couponRecordMapper;

    @Override
    public Map<String, Object> pageData(int page, int size) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        Page<CouponRecordDO> pageInfo = new Page<>(page, size);
        Page<CouponRecordDO> recordDOPage = couponRecordMapper.selectPage(pageInfo,
                new QueryWrapper<CouponRecordDO>().eq("user_id", loginUser.getId())
                        .orderByDesc("create_time"));
        Map<String, Object> map = new HashMap<>();
        map.put("total_record", recordDOPage.getTotal());
        map.put("total_page", recordDOPage.getPages());
        map.put("current_data", recordDOPage.getRecords().stream()
                .map(obj -> {
                    CouponRecordVO couponRecordVO = new CouponRecordVO();
                    BeanUtils.copyProperties(obj, couponRecordVO);
                    return couponRecordVO;
                }).collect(Collectors.toList()));
        return map;
    }

    @Override
    public CouponRecordVO findById(long recordId) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        CouponRecordDO couponRecordDO = couponRecordMapper.selectOne(new QueryWrapper<CouponRecordDO>()
                .eq("id", recordId).eq("user_id", loginUser.getId()));
        if (couponRecordDO == null) {
            return null;
        }
        CouponRecordVO couponRecordVO = new CouponRecordVO();
        BeanUtils.copyProperties(couponRecordDO, couponRecordVO);
        return couponRecordVO;
    }
}
