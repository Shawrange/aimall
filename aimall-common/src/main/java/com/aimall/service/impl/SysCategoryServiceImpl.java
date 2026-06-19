package com.aimall.service.impl;

import com.aimall.component.RedisComponent;
import com.aimall.constants.Constants;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.po.SysCategory;
import com.aimall.entity.query.SimplePage;
import com.aimall.entity.query.SysCategoryQuery;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.mappers.SysCategoryMapper;
import com.aimall.service.SysCategoryService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * 涓氬姟鎺ュ彛瀹炵幇
 */
@Service("sysCategoryService")
public class SysCategoryServiceImpl implements SysCategoryService {

    @Resource
    private SysCategoryMapper<SysCategory, SysCategoryQuery> sysCategoryMapper;

    @Resource
    private RedisComponent redisComponent;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<SysCategory> findListByParam(SysCategoryQuery param) {
        List<SysCategory> sysCategoryList = this.sysCategoryMapper.selectList(param);
        if (param.getConvert2Tree()) {
            sysCategoryList = convertLine2Tree(sysCategoryList, Constants.ZERO_STR);
        }
        return sysCategoryList;
    }

    private List<SysCategory> convertLine2Tree(List<SysCategory> dataList, String pid) {
        List<SysCategory> children = new ArrayList();
        for (SysCategory m : dataList) {
            if (m.getCategoryId() != null && m.getpCategoryId() != null && m.getpCategoryId().equals(pid)) {
                m.setChildren(convertLine2Tree(dataList, m.getCategoryId()));
                children.add(m);
            }
        }
        return children;
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(SysCategoryQuery param) {
        return this.sysCategoryMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<SysCategory> findListByPage(SysCategoryQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<SysCategory> list = this.findListByParam(param);
        PaginationResultVO<SysCategory> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(SysCategory bean) {
        return this.sysCategoryMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<SysCategory> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sysCategoryMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<SysCategory> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.sysCategoryMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(SysCategory bean, SysCategoryQuery param) {
        StringTools.checkParam(param);
        return this.sysCategoryMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(SysCategoryQuery param) {
        StringTools.checkParam(param);
        return this.sysCategoryMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁CategoryId鑾峰彇瀵硅薄
     */
    @Override
    public SysCategory getSysCategoryByCategoryId(String categoryId) {
        return this.sysCategoryMapper.selectByCategoryId(categoryId);
    }

    /**
     * 鏍规嵁CategoryId淇敼
     */
    @Override
    public Integer updateSysCategoryByCategoryId(SysCategory bean, String categoryId) {
        return this.sysCategoryMapper.updateByCategoryId(bean, categoryId);
    }

    /**
     * 鏍规嵁CategoryId鍒犻櫎
     */
    @Override
    public Integer deleteSysCategoryByCategoryId(String categoryId) {
        return this.sysCategoryMapper.deleteByCategoryId(categoryId);
    }


    @Override
    public void saveCategoryInfo(SysCategory bean) {
        if (bean.getCategoryId() == null) {
            bean.setCategoryId(StringTools.getRandomNumber(5));
            Integer maxSort = this.sysCategoryMapper.selectMaxSort(bean.getpCategoryId());
            bean.setSort(maxSort + 1);
            this.sysCategoryMapper.insert(bean);
        } else {
            this.sysCategoryMapper.updateByCategoryId(bean, bean.getCategoryId());
        }
        //鍒锋柊缂撳瓨
        save2Redis();
    }

    @Override
    public void delCategory(String categoryId) {
        SysCategoryQuery categoryInfoQuery = new SysCategoryQuery();
        categoryInfoQuery.setCategoryIdOrPCategoryId(categoryId);
        sysCategoryMapper.deleteByParam(categoryInfoQuery);
        //鍒锋柊缂撳瓨
        save2Redis();
    }


    @Override
    public void changeSort(String categoryIds) {
        String[] categoryIdArray = categoryIds.split(",");
        List<SysCategory> categoryInfoList = new ArrayList<>();
        Integer sort = 1;
        for (String categoryId : categoryIdArray) {
            SysCategory categoryInfo = new SysCategory();
            categoryInfo.setCategoryId(categoryId);
            categoryInfo.setSort(sort++);
            categoryInfoList.add(categoryInfo);
        }
        this.sysCategoryMapper.updateSortBatch(categoryInfoList);
        save2Redis();
    }

    private void save2Redis() {
        SysCategoryQuery categoryInfoQuery = new SysCategoryQuery();
        categoryInfoQuery.setOrderBy("sort asc");
        List<SysCategory> categoryInfoList = findListByParam(categoryInfoQuery);
        redisComponent.saveCategoryList(categoryInfoList);
    }

    @Override
    public List<SysCategory> getAllCategoryList() {
        List<SysCategory> categoryInfoList = redisComponent.getCategoryList();
        if (categoryInfoList == null || categoryInfoList.isEmpty()) {
            save2Redis();
        }
        categoryInfoList = redisComponent.getCategoryList();
        return redisComponent.getCategoryList();
    }
}
