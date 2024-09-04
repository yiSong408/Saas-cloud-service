package net.cloud.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.cloud.enums.BizCodeEnum;
import net.cloud.enums.SentCodeEnum;
import net.cloud.interceptor.LoginInterceptor;
import net.cloud.mapper.UserMapper;
import net.cloud.model.LoginUser;
import net.cloud.model.UserDO;
import net.cloud.request.UserLoginRequest;
import net.cloud.request.UserRegisterRequest;
import net.cloud.service.NotifyService;
import net.cloud.service.UserService;
import net.cloud.util.CommonUtil;
import net.cloud.util.JsonData;
import net.cloud.util.JwtUtil;
import net.cloud.vo.UserVO;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        if (!checkCode) {
            return JsonData.buildResult(BizCodeEnum.CODE_TO_ERROR);
        }

        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(registerRequest, userDO);
        userDO.setCreateTime(new Date());
        userDO.setSlogan("Default slogan");
//        set password
        userDO.setSecret("$1$" + CommonUtil.getRandomString(8));
        String cryptPwd = Md5Crypt.md5Crypt(registerRequest.getPwd().getBytes(), userDO.getSecret());
        userDO.setPwd(cryptPwd);

//        check if account is unique TODO
        if (checkUnique(userDO.getMail())) {
            int rows = userMapper.insert(userDO);
            log.info("rows:{}, register succeed:{}", rows, userDO.toString());
//        send credit
            userRegisterInitCredit(userDO);
            return JsonData.buildSuccess();
        } else {
            return JsonData.buildResult(BizCodeEnum.ACCOUNT_REPEAT);
        }
    }

    @Override
    public JsonData login(UserLoginRequest loginRequest, String ipAddr) {
        List<UserDO> list = userMapper.selectList(new QueryWrapper<UserDO>().eq("mail", loginRequest.getEmail()));
        if(list!=null && list.size()==1){
            UserDO userDO = list.get(0);
            String encodedPwd = Md5Crypt.md5Crypt(loginRequest.getPwd().getBytes(), userDO.getSecret());
            if(encodedPwd.equals(userDO.getPwd())){
                // login success, generate token
                LoginUser loginUser = new LoginUser();
                BeanUtils.copyProperties(userDO,loginUser);
                loginUser.setIpAddr(ipAddr);
                String token = JwtUtil.generateJsonWebToken(loginUser);

                return JsonData.buildSuccess(token);
            }else{
                return JsonData.buildResult(BizCodeEnum.ACCOUNT_PWD_ERROR);
            }
        }else{
         return JsonData.buildResult(BizCodeEnum.ACCOUNT_UNREGISTER);
        }
    }

    @Override
    public UserVO getUserDetail() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        UserDO userDO = userMapper.selectById(loginUser.getId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO,userVO);
        return userVO;
    }

    private boolean checkUnique(String mail) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<UserDO>().eq("mail", mail);
        List<UserDO> list = userMapper.selectList(queryWrapper);
        return list.size() < 1;
    }

    private void userRegisterInitCredit(UserDO userDO) {

    }
}
