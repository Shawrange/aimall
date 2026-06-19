package com.aimall.service.impl;

import com.aimall.constants.Constants;
import com.aimall.entity.enums.DefaultTypeEnum;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.po.UserAddress;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.query.UserAddressQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.mappers.UserAddressMapper;
import com.aimall.service.UserAddressService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserAddressMapper<UserAddress, UserAddressQuery> userAddressMapper;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<UserAddress> findListByParam(UserAddressQuery param) {
        return this.userAddressMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(UserAddressQuery param) {
        return this.userAddressMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<UserAddress> findListByPage(UserAddressQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<UserAddress> list = this.findListByParam(param);
        PaginationResultVO<UserAddress> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(UserAddress bean) {
        return this.userAddressMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<UserAddress> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userAddressMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<UserAddress> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.userAddressMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(UserAddress bean, UserAddressQuery param) {
        StringTools.checkParam(param);
        return this.userAddressMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(UserAddressQuery param) {
        StringTools.checkParam(param);
        return this.userAddressMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁AddressId鑾峰彇瀵硅薄
     */
    @Override
    public UserAddress getUserAddressByAddressId(String addressId) {
        return this.userAddressMapper.selectByAddressId(addressId);
    }

    /**
     * 鏍规嵁AddressId淇敼
     */
    @Override
    public Integer updateUserAddressByAddressId(UserAddress bean, String addressId) {
        return this.userAddressMapper.updateByAddressId(bean, addressId);
    }

    /**
     * 鏍规嵁AddressId鍒犻櫎
     */
    @Override
    public Integer deleteUserAddressByAddressId(String addressId) {
        return this.userAddressMapper.deleteByAddressId(addressId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDefaultAddress(String addressId, String userId) {

        restDefault(userId);

        UserAddress userAddress = new UserAddress();
        userAddress.setDefaultType(DefaultTypeEnum.DEFAULT.getType());

        UserAddressQuery userAddressQuery = new UserAddressQuery();
        userAddressQuery.setAddressId(addressId);
        userAddressQuery.setUserId(userId);
        this.userAddressMapper.updateByParam(userAddress, userAddressQuery);
    }

    private void restDefault(String userId) {
        UserAddress updateAddress = new UserAddress();
        updateAddress.setDefaultType(DefaultTypeEnum.NOT_DEFAULT.getType());

        UserAddressQuery query = new UserAddressQuery();
        query.setUserId(userId);
        this.userAddressMapper.updateByParam(updateAddress, query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAdderss(UserAddress userAddress) {
        if (DefaultTypeEnum.DEFAULT.getType().equals(userAddress.getDefaultType())) {
            restDefault(userAddress.getUserId());
        }
        if (StringTools.isEmpty(userAddress.getAddressId())) {
            userAddress.setAddressId(StringTools.getRandomString(Constants.LENGTH_15));
            this.userAddressMapper.insert(userAddress);
        } else {
            UserAddressQuery userAddressQuery = new UserAddressQuery();
            userAddressQuery.setAddressId(userAddress.getAddressId());
            userAddressQuery.setUserId(userAddress.getUserId());
            this.userAddressMapper.updateByParam(userAddress, userAddressQuery);
        }

    }
}
