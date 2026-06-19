package com.aimall.controller;

import com.aimall.annotation.GlobalInterceptor;
import com.aimall.entity.dto.TokenUserInfoDTO;
import com.aimall.entity.po.UserAddress;
import com.aimall.entity.query.UserAddressQuery;
import com.aimall.entity.valid.CreateGroup;
import com.aimall.entity.valid.UpdateGroup;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.service.UserAddressService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 */
@RestController("userAddressController")
@RequestMapping("/userAddress")
@Validated
public class UserAddressController extends ABaseController {

    @Resource
    private UserAddressService userAddressService;

    /**
     * 鏍规嵁鏉′欢鍒嗛〉鏌ヨ
     */
    @RequestMapping("/loadDataList")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO loadDataList() {
        UserAddressQuery query = new UserAddressQuery();
        query.setUserId(getTokenUserInfo().getUserId());
        query.setOrderBy("default_type desc");
        return getSuccessResponseVO(userAddressService.findListByParam(query));
    }

    @RequestMapping("/addAddress")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO addAddress(@Validated(CreateGroup.class) UserAddress userAddress) {
        userAddress.setUserId(getTokenUserInfo().getUserId());
        userAddressService.saveAdderss(userAddress);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/updateAddress")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO updateAddress(@Validated(UpdateGroup.class) UserAddress userAddress) {
        userAddress.setUserId(getTokenUserInfo().getUserId());
        userAddressService.saveAdderss(userAddress);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delAddress")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO delAddress(@NotEmpty String addressId) {
        TokenUserInfoDTO tokenUserInfoDTO = getTokenUserInfo();
        UserAddressQuery userAddressQuery = new UserAddressQuery();
        userAddressQuery.setUserId(tokenUserInfoDTO.getUserId());
        userAddressQuery.setAddressId(addressId);
        userAddressService.deleteByParam(userAddressQuery);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/updateDefault")
    @GlobalInterceptor(checkLogin = true)
    public ResponseVO updateDefault(@NotEmpty String addressId) {
        TokenUserInfoDTO tokenUserInfoDTO = getTokenUserInfo();
        userAddressService.updateDefaultAddress(addressId, tokenUserInfoDTO.getUserId());
        return getSuccessResponseVO(null);
    }
}
