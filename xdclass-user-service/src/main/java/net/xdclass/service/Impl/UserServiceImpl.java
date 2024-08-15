package net.xdclass.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.xdclass.mapper.XdclassUserMapper;
import net.xdclass.model.XdclassUserDO;
import net.xdclass.service.UserService;
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
