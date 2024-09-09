package net.cloud.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import net.cloud.interceptor.LoginInterceptor;
import net.cloud.mapper.AddressMapper;
import net.cloud.model.AddressDO;
import net.cloud.model.LoginUser;
import net.cloud.request.AddressAddRequest;
import net.cloud.service.AddressService;
import net.cloud.vo.AddressVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressMapper addressMapper;

    @Override
    public void add(AddressAddRequest addressAddRequest) {
        AddressDO addressDO = new AddressDO();
        addressDO.setCreateTime(new Date());
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        addressDO.setUserId(loginUser.getId());
        BeanUtils.copyProperties(addressAddRequest, addressDO);
        if (addressAddRequest.getDefaultStatus() == 1) {
            AddressDO defaultAddress = addressMapper.selectOne(new QueryWrapper<AddressDO>()
                    .eq("user_id", addressDO.getUserId())
                    .eq("default_status", addressDO.getDefaultStatus()));
            if (defaultAddress != null) {
                defaultAddress.setDefaultStatus(0);
                addressMapper.update(defaultAddress, new QueryWrapper<AddressDO>().eq("id", defaultAddress.getId()));
            }
        }
        int rows = addressMapper.insert(addressDO);
        log.info("number of insert address: {}, detailed object: {}", rows, addressDO);
    }

    @Override
    public AddressVO get(int addressId) {
        AddressDO addressDO = addressMapper.selectOne(new QueryWrapper<AddressDO>().eq("id", addressId));
        if(addressDO==null){
            return null;
        }
        AddressVO addressVO = new AddressVO();
        BeanUtils.copyProperties(addressDO,addressVO);
        return addressVO;
    }

    @Override
    public int delete(int addressId) {
        int row = addressMapper.delete(new QueryWrapper<AddressDO>().eq("id", addressId));
        return row;
    }
}
