package com.aimall.service.impl;

import com.aimall.constants.Constants;
import com.aimall.entity.dto.TokenUserInfoDTO;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.enums.ResponseCodeEnum;
import com.aimall.entity.enums.UserSexEnum;
import com.aimall.entity.enums.UserStatusEnum;
import com.aimall.entity.po.UserInfo;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.query.UserInfoQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.exception.BusinessException;
import com.aimall.mappers.UserInfoMapper;
import com.aimall.component.RedisComponent;
import com.aimall.service.UserInfoService;
import com.aimall.utils.CopyTools;
import com.aimall.utils.PasswordUtils;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 鐢ㄦ埛淇℃伅 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoMapper<UserInfo, UserInfoQuery> userInfoMapper;

    @Resource
    private RedisComponent redisComponent;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<UserInfo> findListByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(UserInfoQuery param) {
        return this.userInfoMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<UserInfo> findListByPage(UserInfoQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserInfo> list = this.findListByParam(param);
        PaginationResultVO<UserInfo> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(UserInfo bean) {
        return this.userInfoMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<UserInfo> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userInfoMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(UserInfo bean, UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(UserInfoQuery param) {
        StringTools.checkParam(param);
        return this.userInfoMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁UserId鑾峰彇瀵硅薄
     */
    @Override
    public UserInfo getUserInfoByUserId(String userId) {
        return this.userInfoMapper.selectByUserId(userId);
    }

    /**
     * 鏍规嵁UserId淇敼
     */
    @Override
    public Integer updateUserInfoByUserId(UserInfo bean, String userId) {
        return this.userInfoMapper.updateByUserId(bean, userId);
    }

    /**
     * 鏍规嵁UserId鍒犻櫎
     */
    @Override
    public Integer deleteUserInfoByUserId(String userId) {
        return this.userInfoMapper.deleteByUserId(userId);
    }

    /**
     * 鏍规嵁Email鑾峰彇瀵硅薄
     */
    @Override
    public UserInfo getUserInfoByEmail(String email) {
        return this.userInfoMapper.selectByEmail(email);
    }

    /**
     * 鏍规嵁Email淇敼
     */
    @Override
    public Integer updateUserInfoByEmail(UserInfo bean, String email) {
        return this.userInfoMapper.updateByEmail(bean, email);
    }

    /**
     * 鏍规嵁Email鍒犻櫎
     */
    @Override
    public Integer deleteUserInfoByEmail(String email) {
        return this.userInfoMapper.deleteByEmail(email);
    }

    /**
     * 鏍规嵁NickName鑾峰彇瀵硅薄
     */
    @Override
    public UserInfo getUserInfoByNickName(String nickName) {
        return this.userInfoMapper.selectByNickName(nickName);
    }

    /**
     * 鏍规嵁NickName淇敼
     */
    @Override
    public Integer updateUserInfoByNickName(UserInfo bean, String nickName) {
        return this.userInfoMapper.updateByNickName(bean, nickName);
    }

    /**
     * 鏍规嵁NickName鍒犻櫎
     */
    @Override
    public Integer deleteUserInfoByNickName(String nickName) {
        return this.userInfoMapper.deleteByNickName(nickName);
    }


    @Override
    public TokenUserInfoDTO login(String email, String password, String ip) {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        if (null == userInfo || !PasswordUtils.matches(password, userInfo.getPassword())) {
            throw new BusinessException("璐﹀彿鎴栬€呭瘑鐮侀敊璇?);
        }
        if (UserStatusEnum.DISABLE.getStatus().equals(userInfo.getStatus())) {
            throw new BusinessException("璐﹀彿宸茬鐢?);
        }
        UserInfo updateInfo = new UserInfo();
        updateInfo.setLastLoginTime(new Date());
        updateInfo.setLastLoginIp(ip);
        if (PasswordUtils.shouldUpgrade(userInfo.getPassword())) {
            updateInfo.setPassword(PasswordUtils.hash(password));
        }
        this.userInfoMapper.updateByUserId(updateInfo, userInfo.getUserId());

        TokenUserInfoDTO tokenUserInfoDto = CopyTools.copy(userInfo, TokenUserInfoDTO.class);
        redisComponent.saveTokenInfo(tokenUserInfoDto);
        return tokenUserInfoDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String email, String nickName, String password) {
        UserInfo userInfo = this.userInfoMapper.selectByEmail(email);
        if (null != userInfo) {
            throw new BusinessException("閭璐﹀彿宸茬粡瀛樺湪");
        }
        UserInfo nickNameUser = this.userInfoMapper.selectByNickName(nickName);
        if (null != nickNameUser) {
            throw new BusinessException("鏄电О宸茬粡瀛樺湪");
        }
        String userId = StringTools.getRandomNumber(Constants.LENGTH_10);
        userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setNickName(nickName);
        userInfo.setEmail(email);
        userInfo.setPassword(PasswordUtils.hash(password));
        userInfo.setJoinTime(new Date());
        userInfo.setStatus(UserStatusEnum.ENABLE.getStatus());
        userInfo.setSex(UserSexEnum.SECRECY.getType());
        this.userInfoMapper.insert(userInfo);
    }

    @Override
    @Transactional
    public void updateUserInfo(UserInfo userInfo, TokenUserInfoDTO tokenUserInfoDto) {
        UserInfo dbInfo = this.userInfoMapper.selectByUserId(userInfo.getUserId());
        this.userInfoMapper.updateByUserId(userInfo, userInfo.getUserId());

        Boolean updateTokenInfo = false;
        if (!userInfo.getAvatar().equals(tokenUserInfoDto.getAvatar())) {
            tokenUserInfoDto.setAvatar(userInfo.getAvatar());
            updateTokenInfo = true;
        }
        if (!tokenUserInfoDto.getNickName().equals(userInfo.getNickName())) {
            tokenUserInfoDto.setNickName(userInfo.getNickName());
            updateTokenInfo = true;
        }
        if (updateTokenInfo) {
            redisComponent.updateTokenInfo(tokenUserInfoDto);
        }
    }

    @Override
    public void updatePassword(String userId, String oldPassword, String newPassword) {
        UserInfo userInfo = this.userInfoMapper.selectByUserId(userId);
        if (null == userInfo) {
            throw new BusinessException(ResponseCodeEnum.CODE_600);
        }
        if (!PasswordUtils.matches(oldPassword, userInfo.getPassword())) {
            throw new BusinessException("鍘熷瀵嗙爜閿欒");
        }
        UserInfo updateInfo = new UserInfo();
        updateInfo.setPassword(PasswordUtils.hash(newPassword));
        this.userInfoMapper.updateByUserId(updateInfo, userId);
    }
}
