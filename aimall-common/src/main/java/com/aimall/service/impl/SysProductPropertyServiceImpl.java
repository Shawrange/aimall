package com.aimall.service.impl;

import com.aimall.constants.Constants;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.po.SysProductProperty;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.query.SysProductPropertyQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.mappers.SysProductPropertyMapper;
import com.aimall.service.SysProductPropertyService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("sysProductPropertyService")
public class SysProductPropertyServiceImpl implements SysProductPropertyService {

    @Resource
    private SysProductPropertyMapper<SysProductProperty, SysProductPropertyQuery> sysProductPropertyMapper;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<SysProductProperty> findListByParam(SysProductPropertyQuery param) {
        return this.sysProductPropertyMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(SysProductPropertyQuery param) {
        return this.sysProductPropertyMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<SysProductProperty> findListByPage(SysProductPropertyQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<SysProductProperty> list = this.findListByParam(param);
        PaginationResultVO<SysProductProperty> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(SysProductProperty bean) {
        return this.sysProductPropertyMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<SysProductProperty> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sysProductPropertyMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<SysProductProperty> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sysProductPropertyMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(SysProductProperty bean, SysProductPropertyQuery param) {
        StringTools.checkParam(param);
        return this.sysProductPropertyMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(SysProductPropertyQuery param) {
        StringTools.checkParam(param);
        return this.sysProductPropertyMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁PropertyId鑾峰彇瀵硅薄
     */
    @Override
    public SysProductProperty getSysProductPropertyByPropertyId(String propertyId) {
        return this.sysProductPropertyMapper.selectByPropertyId(propertyId);
    }

    /**
     * 鏍规嵁PropertyId淇敼
     */
    @Override
    public Integer updateSysProductPropertyByPropertyId(SysProductProperty bean, String propertyId) {
        return this.sysProductPropertyMapper.updateByPropertyId(bean, propertyId);
    }

    /**
     * 鏍规嵁PropertyId鍒犻櫎
     */
    @Override
    public Integer deleteSysProductPropertyByPropertyId(String propertyId) {
        return this.sysProductPropertyMapper.deleteByPropertyId(propertyId);
    }

    @Override
    public void saveSysProductPropertyService(SysProductProperty productProperty) {
        if (StringTools.isEmpty(productProperty.getPropertyId())) {
            productProperty.setPropertyId(StringTools.getRandomNumber(Constants.LENGTH_5));
            productProperty.setPropertySort(this.sysProductPropertyMapper.selectMaxSort(productProperty.getCategoryId()) + 1);
            this.sysProductPropertyMapper.insert(productProperty);
        } else {
            this.sysProductPropertyMapper.updateByPropertyId(productProperty, productProperty.getPropertyId());
        }
    }

    @Override
    public void deleteSysProductPropertyService(String propertyId) {
        sysProductPropertyMapper.deleteByPropertyId(propertyId);
    }
}
