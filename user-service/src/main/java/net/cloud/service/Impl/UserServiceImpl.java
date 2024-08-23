package net.cloud.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.cloud.mapper.UserMapper;
import net.cloud.model.UserDO;
import net.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDO detail(Long id) {
        UserDO userDO = userMapper.selectOne(new QueryWrapper<UserDO>().eq("id", id));
        return userDO;
    }
}
