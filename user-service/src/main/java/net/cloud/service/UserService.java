package net.cloud.service;

import net.cloud.model.UserDO;
import net.cloud.request.UserRegisterRequest;
import net.cloud.util.JsonData;

public interface UserService {

    UserDO detail(Long id);

    JsonData register(UserRegisterRequest registerRequest);
}
