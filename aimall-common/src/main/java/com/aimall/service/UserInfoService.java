package com.aimall.service;

import com.aimall.entity.dto.TokenUserInfoDTO;
import com.aimall.entity.po.UserInfo;
import com.aimall.entity.query.UserInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;

import java.util.List;


/**
 * 鐢ㄦ埛淇℃伅 涓氬姟鎺ュ彛
 */
public interface UserInfoService {

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    List<UserInfo> findListByParam(UserInfoQuery param);

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    Integer findCountByParam(UserInfoQuery param);

    /**
     * 鍒嗛〉鏌ヨ
     */
    PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param);

    /**
     * 鏂板
     */
    Integer add(UserInfo bean);

    /**
     * 鎵归噺鏂板
     */
    Integer addBatch(List<UserInfo> listBean);

    /**
     * 鎵归噺鏂板/淇敼
     */
    Integer addOrUpdateBatch(List<UserInfo> listBean);

    /**
     * 澶氭潯浠舵洿鏂?
     */
    Integer updateByParam(UserInfo bean, UserInfoQuery param);

    /**
     * 澶氭潯浠跺垹闄?
     */
    Integer deleteByParam(UserInfoQuery param);

    /**
     * 鏍规嵁UserId鏌ヨ瀵硅薄
     */
    UserInfo getUserInfoByUserId(String userId);


    /**
     * 鏍规嵁UserId淇敼
     */
    Integer updateUserInfoByUserId(UserInfo bean, String userId);


    /**
     * 鏍规嵁UserId鍒犻櫎
     */
    Integer deleteUserInfoByUserId(String userId);


    /**
     * 鏍规嵁Email鏌ヨ瀵硅薄
     */
    UserInfo getUserInfoByEmail(String email);


    /**
     * 鏍规嵁Email淇敼
     */
    Integer updateUserInfoByEmail(UserInfo bean, String email);


    /**
     * 鏍规嵁Email鍒犻櫎
     */
    Integer deleteUserInfoByEmail(String email);


    /**
     * 鏍规嵁NickName鏌ヨ瀵硅薄
     */
    UserInfo getUserInfoByNickName(String nickName);


    /**
     * 鏍规嵁NickName淇敼
     */
    Integer updateUserInfoByNickName(UserInfo bean, String nickName);


    /**
     * 鏍规嵁NickName鍒犻櫎
     */
    Integer deleteUserInfoByNickName(String nickName);

    TokenUserInfoDTO login(String email, String password, String ip);

    void register(String email, String nickName, String password);

    void updateUserInfo(UserInfo userInfo, TokenUserInfoDTO tokenUserInfoDto);

     void updatePassword(String userId, String oldPassword, String newPassword);
}
