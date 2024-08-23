package net.cloud.controller;

import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.cloud.enums.BizCodeEnum;
import net.cloud.enums.SentCodeEnum;
import net.cloud.service.NotifyService;
import net.cloud.util.CommonUntil;
import net.cloud.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Api(tags = "Notify module")
@Slf4j
@RestController
@RequestMapping("api/user/v1")
public class NotifyController {
    @Autowired
    private Producer captchaProducer;

    @Autowired
    private NotifyService notifyService;

    @Autowired
    private StringRedisTemplate redisTemplate;
    private static final long CAPTCHA_CODE_EXPIRED_TIME = 60 * 1000 * 10;

    @ApiOperation("Get image captcha")
    @GetMapping("captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        String content = captchaProducer.createText();
        log.info("captcha content: {}", content);

        redisTemplate.opsForValue().set(getCaptchaKey(request), content, CAPTCHA_CODE_EXPIRED_TIME, TimeUnit.MILLISECONDS);

        BufferedImage image = captchaProducer.createImage(content);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("=== Captcha error ===");
        }
    }

    @ApiOperation("Send verify code to email")
    @GetMapping("send_code")
    public JsonData sendRegisterCode(@RequestParam(value = "to") String to,
                                     @RequestParam(value = "captcha") String captcha,
                                     HttpServletRequest request) {
        String captchaKey = getCaptchaKey(request);
        String cacheCaptcha = redisTemplate.opsForValue().get(captchaKey);
        if (cacheCaptcha != null && captchaKey != null && captcha.equals(cacheCaptcha)) {
            redisTemplate.delete(captchaKey);
            JsonData jsonData = notifyService.sendCode(SentCodeEnum.USER_REGISTER, to);
            return jsonData;
        } else {
            return JsonData.buildResult(BizCodeEnum.CODE_CAPTCHA);
        }
    }

    private String getCaptchaKey(HttpServletRequest request) {
        String ipAddr = CommonUntil.getIpAddr(request);
        String header = request.getHeader("User-Agent");

        String key = "user-service:captcha:" + CommonUntil.MD5(ipAddr + header);
        return key;
    }

}
