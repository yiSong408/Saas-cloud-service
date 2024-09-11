package net.cloud.service;

import net.cloud.request.AddressAddRequest;
import net.cloud.vo.AddressVO;

import java.util.List;

public interface AddressService {

    void add(AddressAddRequest addressAddRequest);

    AddressVO get(int addressId);

    int delete(int addressId);

    List<AddressVO> findAllUserAddress();
}
