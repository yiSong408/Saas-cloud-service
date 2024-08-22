package net.xdclass.service;

import net.xdclass.enums.SentCodeEnum;
import net.xdclass.util.JsonData;

public interface NotifyService {
    JsonData sendCode(SentCodeEnum sentCodeEnum, String to);
}

