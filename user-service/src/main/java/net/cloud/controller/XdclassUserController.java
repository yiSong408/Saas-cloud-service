package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
<<<<<<< HEAD:xdclass-user-service/src/main/java/net/xdclass/controller/XdclassUserController.java
import lombok.Getter;
import net.xdclass.exception.BizException;
import net.xdclass.model.XdclassUserDO;
import net.xdclass.service.UserService;
import net.xdclass.util.JsonData;
=======
import net.cloud.enums.BizCodeEnum;
import net.cloud.model.XdclassUserDO;
import net.cloud.service.FileService;
import net.cloud.service.UserService;
import net.cloud.util.JsonData;
>>>>>>> 5313def (rename files):user-service/src/main/java/net/cloud/controller/XdclassUserController.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-08-13
 */
@Api(tags = "User module")
@RestController
@RequestMapping("/api/user/v1/")
public class XdclassUserController {
    @Autowired
    private UserService userService;

    @ApiOperation("Get user detail by ID")
    @GetMapping("/find/{user_id}")
    public Object detail(@ApiParam(value = "user ID", required = true)
                         @PathVariable("user_id") Long userId) {
        XdclassUserDO detail = userService.detail(userId);

        return JsonData.buildSuccess(detail);
    }
}

