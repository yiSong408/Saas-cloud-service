package net.cloud.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import lombok.extern.slf4j.Slf4j;
import net.cloud.enums.BizCodeEnum;
import net.cloud.model.UserDO;
import net.cloud.request.UserLoginRequest;
import net.cloud.request.UserRegisterRequest;
import net.cloud.service.FileService;
import net.cloud.service.UserService;
import net.cloud.util.CommonUtil;
import net.cloud.util.JsonData;
import net.cloud.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Rongyi
 * @since 2024-08-13
 */
@Api(tags = "User module")
@Slf4j
@RestController
@RequestMapping("/api/user/v1/")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;

    @ApiOperation("Get user detail by ID")
    @GetMapping("/find/{user_id}")
    public Object detail(@ApiParam(value = "user ID", required = true)
                         @PathVariable("user_id") Long userId) {
        UserDO detail = userService.detail(userId);

        return JsonData.buildSuccess(detail);
    }

    @ApiOperation("Upload user avatar when register")
    @PostMapping("upload")
    public JsonData upload(@ApiParam(value = "upload avatar img", required = true)
                           @RequestPart(name = "file") MultipartFile file) {
        String imgUrl = fileService.uploadUserImg(file);
        return imgUrl != null ? JsonData.buildSuccess(imgUrl) : JsonData.buildResult(BizCodeEnum.UPLOAD_USER_IMG_FILE_FAIL);
    }

    @ApiOperation("User register")
    @PostMapping("register")
    public JsonData register(@ApiParam("User register object")
                             @RequestBody UserRegisterRequest registerRequest) {
        return userService.register(registerRequest);
    }
    @ApiOperation("User login")
    @PostMapping("login")
    public JsonData login(@ApiParam("User login object")
                          @RequestBody UserLoginRequest loginRequest, HttpServletRequest request){
        String ip = CommonUtil.getIpAddr(request);
        return userService.login(loginRequest, ip);
    }

    @ApiOperation("User detail info")
    @GetMapping("detail")
    public JsonData detail(){
        UserVO userVO = userService.getUserDetail();
        return JsonData.buildSuccess(userVO);
    }


}

