package net.cloud.exception;

import lombok.extern.slf4j.Slf4j;
import net.cloud.util.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData handler(Exception e) {
        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            log.error("Service exception", e);
            return JsonData.buildError(bizException.getCode(),bizException.getMessage());
        } else {
            log.error("System error", e);
            return JsonData.buildError("Unknown exception");
        }
    }
}
