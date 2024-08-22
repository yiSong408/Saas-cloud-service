package net.xdclass.service.Impl;

import lombok.extern.slf4j.Slf4j;
import net.xdclass.enums.BizCodeEnum;
import net.xdclass.enums.SentCodeEnum;
import net.xdclass.component.MailService;
import net.xdclass.service.NotifyService;
import net.xdclass.util.CheckUtil;
import net.xdclass.util.CommonUntil;
import net.xdclass.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
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
