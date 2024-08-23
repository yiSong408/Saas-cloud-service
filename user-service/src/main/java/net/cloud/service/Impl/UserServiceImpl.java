package net.cloud.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.cloud.mapper.XdclassUserMapper;
import net.cloud.model.XdclassUserDO;
import net.cloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private XdclassUserMapper userMapper;
    @Override
    public XdclassUserDO detail(Long id) {
        XdclassUserDO userDO = userMapper.selectOne(new QueryWrapper<XdclassUserDO>().eq("id", id));
        return userDO;
    }
}
