package net.xdclass.util;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {
    private static final Pattern MAIL_PATTERN=Pattern.compile("^\\w+(-+.\\w+)*@\\w+(-.\\w+)*.\\w+(-.\\w+)*$");
    private static final Pattern PHONE_PATTERN=Pattern.compile("^(130-9|145|7|150|1|2|3|4|5|6|7|8|9|180|1|2|3|5|6|7|8|9)\\d{8}$");

    public static boolean isEmail(String email){
        if(Objects.isNull(email)||email.equals("")){
            return false;
        }
        Matcher matcher = MAIL_PATTERN.matcher(email);
        return matcher.matches();
    }
    public static boolean isPhoneNumber(String phone){
        if(Objects.isNull(phone)||phone.equals("")){
            return false;
        }
        Matcher matcher = PHONE_PATTERN.matcher(phone);
        return matcher.matches();
    }
}
