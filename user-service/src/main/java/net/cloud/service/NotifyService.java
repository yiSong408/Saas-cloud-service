package net.cloud.service;

import net.cloud.enums.SentCodeEnum;
import net.cloud.util.JsonData;

public interface NotifyService {
    JsonData sendCode(SentCodeEnum sentCodeEnum, String to);
}

