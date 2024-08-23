package net.cloud.service.Impl;

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

@Service
@Slf4j
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    private MailService mailService;

    private static final String SUBJECT = "Verifying code";
    private static final String CONTENT = "Hello. Your verify code is %s.";

    @Override
    public JsonData sendCode(SentCodeEnum sentCodeEnum, String to) {
        if (CheckUtil.isEmail(to)) {
            String randomCode = CommonUntil.getRandomCode(6);
            mailService.sendMail(to, SUBJECT, String.format(CONTENT, randomCode));
            return JsonData.buildSuccess();
        } else if (CheckUtil.isPhoneNumber(to)) {

        }
        return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
    }
}
