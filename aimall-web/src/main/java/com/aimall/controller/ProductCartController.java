package com.aimall.controller;

import com.aimall.annotation.GlobalInterceptor;
import com.aimall.entity.po.ProductCart;
import com.aimall.entity.query.ProductCartQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.service.ProductCartService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 璐墿杞?Controller
 */
@RestController("productCartController")
@RequestMapping("/productCart")
public class ProductCartController extends ABaseController {

    @Resource
    private ProductCartService productCartService;

    /**
     * 鏍规嵁鏉′欢鍒嗛〉鏌ヨ
     */
    @RequestMapping("/loadProductCart")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadProductCart(Integer pageNo) {
        ProductCartQuery query = new ProductCartQuery();
        query.setUserId(getTokenUserInfo().getUserId());
        query.setPageNo(pageNo);
        PaginationResultVO resultVO = productCartService.loadProductCart(query);
        return getSuccessResponseVO(resultVO);
    }

    @RequestMapping("/add2Cart")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO add2Cart(ProductCart productCart) {
        productCart.setUserId(getTokenUserInfo().getUserId());
        productCartService.add2Cart(productCart);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/deleteCart")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO deleteCart(@NotEmpty String cartId) {
        ProductCartQuery productCartQuery = new ProductCartQuery();
        productCartQuery.setCartId(cartId);
        productCartQuery.setUserId(getTokenUserInfo().getUserId());
        productCartService.deleteByParam(productCartQuery);
        return getSuccessResponseVO(null);
    }
}
