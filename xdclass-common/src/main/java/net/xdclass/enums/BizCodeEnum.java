package net.xdclass.enums;

import lombok.Getter;

import java.util.PrimitiveIterator;

public enum BizCodeEnum {
    // common
    OPS_REPEAT(110001, "Repeat operations"),
    // captcha
    CODE_TO_ERROR(240001, "Invalid number"),
    CODE_LIMITED(240002, "Code gap too short"),
    CODE_CAPTCHA(240101, "Captcha error"),
    // account
    ACCOUNT_REPEAT(250001, "Account existed"),
    ACCOUNT_UNREGISTER(250002, "Account not exist"),
    ACCOUNT_PWD_ERROR(250003, "Username or password incorrect");
    @Getter
    private String message;
    @Getter
    private int code;

    private BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
