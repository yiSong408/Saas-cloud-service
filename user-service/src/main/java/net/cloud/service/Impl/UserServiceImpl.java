package net.cloud.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.cloud.enums.BizCodeEnum;
import net.cloud.enums.SentCodeEnum;
import net.cloud.mapper.UserMapper;
import net.cloud.model.UserDO;
import net.cloud.request.UserRegisterRequest;
import net.cloud.service.NotifyService;
import net.cloud.service.UserService;
import net.cloud.util.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotifyService notifyService;

    @Override
    public UserDO detail(Long id) {
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("id", id));
        return userDO;
    }

    /***
     * 1. code verify
     * 2. secret encrypt
     * 3. check username unique
     * 4. insert into db
     * 5. credit send
     * @param registerRequest
     * @return
     */
    @Override
    public JsonData register(UserRegisterRequest registerRequest) {
//        check code
        boolean checkCode = false;
        if (StringUtils.isNotBlank(registerRequest.getMail())) {
            checkCode = notifyService.checkCode(SentCodeEnum.USER_REGISTER, registerRequest.getMail(), registerRequest.getCode());
        }
        if(!checkCode){
            return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(registerRequest,userDO);
        userDO.setCreateTime(new Date());
        userDO.setSlogan("Default slogan");
//        set password TODO
//        userDO.setPwd();
//        check if account is unique TODO
        if(checkUnique(userDO.getMail())){
            int rows = userMapper.insert(userDO);
            log.info("rows:{}, register succeed:{}",rows,userDO.toString());
//        send credit
            userRegisterInitCredit(userDO);
            return JsonData.buildSuccess();
        }else{
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
    }
    private boolean checkUnique(String mail){

    }

    private void userRegisterInitCredit(UserDO userDO){

    }
}
