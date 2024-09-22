package net.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.cloud.interceptor.LoginInterceptor;
import net.cloud.mapper.ProductMapper;
import net.cloud.model.LoginUser;
import net.cloud.model.ProductDO;
import net.cloud.service.ProductService;
import net.cloud.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Override
    public Map<String, Object> pageList(int page, int size) {
        Page<ProductDO> pageInfo = new Page<>(page, size);
        Page<ProductDO> productDOPage = productMapper.selectPage(pageInfo, null);
        Map<String, Object> map = new HashMap<>();
        map.put("total_record", productDOPage.getTotal());
        map.put("total_page", productDOPage.getPages());
        map.put("current_data", productDOPage.getRecords().stream()
                .map(this::beanProcess).collect(Collectors.toList()));
        return map;

    }

    @Override
    public ProductVO getProductDetail(long productId) {
        ProductDO productDO = productMapper.selectById(productId);
        return beanProcess(productDO);
    }

    @Override
    public List<ProductVO> findProductByIdBatch(List<Long> productIdList) {
        List<ProductDO> productDOList = productMapper.selectBatchIds(productIdList);
        List<ProductVO> list = productDOList.stream().map(obj -> {
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(obj, productVO);
            return productVO;
        }).collect(Collectors.toList());
        return list;
    }

    private ProductVO beanProcess(ProductDO productDO){
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(productDO, productVO);
        productVO.setStock(productDO.getStock()-productDO.getLockStock());
        return productVO;
    }
}
