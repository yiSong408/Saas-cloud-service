package net.cloud.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import net.cloud.constant.CacheKey;
import net.cloud.enums.BizCodeEnum;
import net.cloud.exception.BizException;
import net.cloud.interceptor.LoginInterceptor;
import net.cloud.model.LoginUser;
import net.cloud.request.CartItemRequest;
import net.cloud.service.CartService;
import net.cloud.service.ProductService;
import net.cloud.vo.CartItemVO;
import net.cloud.vo.ProductVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductService productService;

    @Override
    public void addToCart(CartItemRequest cartItemRequest) {
        long productId = cartItemRequest.getProductId();
        Integer amount = cartItemRequest.getAmount();
        BoundHashOperations<String, Object, Object> myCart = getMyCartOps();
        Object cacheObj = myCart.get(Long.toString(productId));
        String result = "";
        if (cacheObj != null) {
            result = (String) cacheObj;
        }
        if (StringUtils.isBlank(result)) {
            CartItemVO cartItemVO = new CartItemVO();
            ProductVO productDetail = productService.getProductDetail(productId);
            if (productDetail == null) {
                throw new BizException(BizCodeEnum.CART_FAILURE);
            }
            cartItemVO.setProductId(productDetail.getId());
            cartItemVO.setAmount(amount);
            cartItemVO.setProductTitle(productDetail.getTitle());
            cartItemVO.setProductImg(productDetail.getCoverImg());
            cartItemVO.setSinglePrice(productDetail.getPrice());

            myCart.put(Long.toString(productId), JSON.toJSONString(cartItemVO));
        } else {
            CartItemVO cartItemVO = JSON.parseObject(result, CartItemVO.class);
            cartItemVO.setAmount(cartItemVO.getAmount() + amount);
            myCart.put(Long.toString(productId), JSON.toJSONString(cartItemVO));
        }
    }

    @Override
    public void clearCart() {
        redisTemplate.delete(getCartKey());
    }

    private BoundHashOperations<String, Object, Object> getMyCartOps() {
        String cartKey = getCartKey();
        return redisTemplate.boundHashOps(cartKey);
    }

    private String getCartKey() {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
        String cacheKey = String.format(CacheKey.CART_KEY, loginUser.getId());
        return cacheKey;
    }
}
