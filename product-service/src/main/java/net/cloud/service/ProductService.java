package net.cloud.service;

import net.cloud.vo.ProductVO;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Map<String, Object> pageList(int page, int size);

    ProductVO getProductDetail(long productId);

    List<ProductVO> findProductByIdBatch(List<Long> productIdList);
}
