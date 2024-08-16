package net.xdclass.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.xdclass.enums.BizCodeEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    private int code; // 0 success
    private Object data;
    private String msg;

    public static JsonData buildSuccess() {
        return new JsonData(0, null, null);
    }

    public static JsonData buildSuccess(Object data) {
        return new JsonData(0, data, null);
    }

    public static JsonData buildError(String msg) {
        return new JsonData(-1, null, msg);
    }

    public static JsonData buildError(Integer code, String msg) {
        return new JsonData(code, null, msg);
    }

    public static JsonData buildResult(BizCodeEnum codeEnum) {
        return JsonData.buildError(codeEnum.getCode(), codeEnum.getMessage());
    }
}
