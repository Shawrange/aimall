package com.aimall.controller;

import com.aimall.component.RedisComponent;
import com.aimall.entity.enums.ResponseCodeEnum;
import com.aimall.entity.enums.UserStatusEnum;
import com.aimall.entity.po.UserInfo;
import com.aimall.entity.query.UserInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.exception.BusinessException;
import com.aimall.service.UserInfoService;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Validated
public class UserController extends ABaseController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RedisComponent redisComponent;

    @RequestMapping("/loadUser")
    public ResponseVO loadUser(UserInfoQuery userInfoQuery) {
        userInfoQuery.setOrderBy("u.join_time desc");
        PaginationResultVO resultVO = userInfoService.findListByPage(userInfoQuery);
        return getSuccessResponseVO(resultVO);
    }


    @RequestMapping("/changeStatus")
    public ResponseVO changeStatus(@NotEmpty String userId, @NotNull Integer status) {
        UserStatusEnum userStatusEnum = UserStatusEnum.getByStatus(status);
        if (null == userStatusEnum) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setStatus(status);
        this.userInfoService.updateUserInfoByUserId(userInfo, userId);
        if (UserStatusEnum.DISABLE == userStatusEnum) {
            //强制退出
            redisComponent.forceLogout(userId);
        }
        return getSuccessResponseVO(null);
    }
}
