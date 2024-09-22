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
import net.cloud.vo.CartVO;
import net.cloud.vo.ProductVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public CartVO listAll() {
        List<CartItemVO> cartItemList = buildCartItemList(false);
        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemList);
        return cartVO;
    }

    @Override
    public void deleteItem(long productId) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        myCartOps.delete(Long.toString(productId));
    }

    @Override
    public void updateToCart(CartItemRequest cartItemRequest) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        Object obj = myCartOps.get(Long.toString(cartItemRequest.getProductId()));
        if (obj == null) {
            throw new BizException(BizCodeEnum.CART_FAILURE);
        }
        CartItemVO cartItemVO = JSON.parseObject((String) obj, CartItemVO.class);
        cartItemVO.setAmount(cartItemRequest.getAmount());
        myCartOps.put(Long.toString(cartItemRequest.getProductId()), JSON.toJSONString(cartItemVO));
    }

    private List<CartItemVO> buildCartItemList(boolean latestPrice) {
        BoundHashOperations<String, Object, Object> myCartOps = getMyCartOps();
        List<Object> objectList = myCartOps.values();
        List<CartItemVO> cartItemList = new ArrayList<>();
        List<Long> productIdList = new ArrayList<>();

        for (Object item : objectList) {
            CartItemVO cartItemVO = JSON.parseObject((String) item, CartItemVO.class);
            cartItemList.add(cartItemVO);
            productIdList.add(cartItemVO.getProductId());
        }
        if (latestPrice) {
            // get latest price
            setProductLatestPrice(cartItemList, productIdList);
        }
        return cartItemList;
    }

    private void setProductLatestPrice(List<CartItemVO> cartItemList, List<Long> productIdList) {
        List<ProductVO> productVOList = productService.findProductByIdBatch(productIdList);
        Map<Long, ProductVO> map = productVOList.stream().collect(Collectors.toMap(ProductVO::getId, Function.identity()));
        cartItemList.stream().forEach(item -> {
            ProductVO productVO = map.get(item.getProductId());
            item.setProductTitle(productVO.getTitle());
            item.setProductImg(productVO.getCoverImg());
            item.setSinglePrice(productVO.getPrice());
        });
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
