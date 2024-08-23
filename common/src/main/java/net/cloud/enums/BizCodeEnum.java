package net.cloud.enums;

import lombok.Getter;

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
    ACCOUNT_PWD_ERROR(250003, "Username or password incorrect"),
    UPLOAD_USER_IMG_FILE_FAIL(600101, "Fail to upload user avtar");
    @Getter
    private String message;
    @Getter
    private int code;

    private BizCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
