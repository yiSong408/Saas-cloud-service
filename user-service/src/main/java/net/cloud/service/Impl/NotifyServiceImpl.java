package net.cloud.service.Impl;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import net.cloud.constant.CacheKey;
import net.cloud.enums.BizCodeEnum;
import net.cloud.enums.SentCodeEnum;
import net.cloud.component.MailService;
import net.cloud.service.NotifyService;
import net.cloud.util.CheckUtil;
import net.cloud.util.CommonUntil;
import net.cloud.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private MailService mailService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String SUBJECT = "Verifying code";
    private static final String CONTENT = "Hello. Your verify code is %s.";
    private static final int CODE_EXPIRED = 1000 * 60 * 10;

    @Override
    public JsonData sendCode(SentCodeEnum sentCodeEnum, String to) {
        String cacheKey = String.format(CacheKey.CACHE_CODE_KEY, sentCodeEnum.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        // if not empty, check if it is in 60s
        if (StringUtils.isNotBlank(cacheValue)) {
            long ttl = Long.parseLong(cacheValue.split("_")[1]);
            if (CommonUntil.getCurrentTimestamp() - ttl < 1000 * 60) {
                log.info("repeat sent code in 60s");
                return JsonData.buildResult(BizCodeEnum.CODE_LIMITED);
            }
        }
        String randomCode = CommonUntil.getRandomCode(6);
        String value = randomCode + "_" + CommonUntil.getCurrentTimestamp();
        redisTemplate.opsForValue().set(cacheKey, value, CODE_EXPIRED, TimeUnit.MILLISECONDS);
        if (CheckUtil.isEmail(to)) {
            mailService.sendMail(to, SUBJECT, String.format(CONTENT, randomCode));
            return JsonData.buildSuccess();
        } else if (CheckUtil.isPhoneNumber(to)) {

        }
        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }

    @Override
    public boolean checkCode(SentCodeEnum sentCodeEnum, String to, String code) {
        String cacheKey = String.format(CacheKey.CACHE_CODE_KEY, sentCodeEnum.name(), to);
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        if (StringUtils.isNotBlank(cacheValue)) {
            String cacheCode = cacheValue.split("_")[0];
            if (cacheCode.equals(code)) {
                redisTemplate.delete(cacheKey);
                return true;
            }
        }
        return false;
    }
}
