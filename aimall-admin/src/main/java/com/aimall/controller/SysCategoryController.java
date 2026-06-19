package com.aimall.controller;

import com.aimall.entity.po.SysCategory;
import com.aimall.entity.po.SysProductProperty;
import com.aimall.entity.query.SysCategoryQuery;
import com.aimall.entity.vo.ResponseVO;
import com.aimall.service.SysCategoryService;
import com.aimall.service.SysProductPropertyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 */
@RestController("sysCategoryController")
@RequestMapping("/sysCategory")
public class SysCategoryController extends ABaseController {

    @Resource
    private SysCategoryService sysCategoryService;

    @Resource
    private SysProductPropertyService sysProductPropertyService;

    /**
     * 鏍规嵁鏉′欢鍒嗛〉鏌ヨ
     */
    @RequestMapping("/loadCategory")
    public ResponseVO loadCategory(Boolean queryProperty) {
        SysCategoryQuery sysCategoryQuery = new SysCategoryQuery();
        sysCategoryQuery.setOrderBy("s.sort asc");
        if (queryProperty != null && queryProperty) {
            sysCategoryQuery.setOrderBy("s.sort asc,sp.property_sort asc");
        }
        sysCategoryQuery.setQueryProperty(queryProperty);
        sysCategoryQuery.setConvert2Tree(true);
        return getSuccessResponseVO(sysCategoryService.findListByParam(sysCategoryQuery));
    }

    @RequestMapping("/saveCategory")
    public ResponseVO saveCategory(SysCategory category) {
        sysCategoryService.saveCategoryInfo(category);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/delCategory")
    public ResponseVO delCategory(String categoryId) {
        sysCategoryService.delCategory(categoryId);
        return getSuccessResponseVO(null);
    }

    @RequestMapping("/changeCategorySort")
    public ResponseVO changeCategorySort(String categoryIds) {
        sysCategoryService.changeSort(categoryIds);
        return getSuccessResponseVO(null);
    }

    /**
     * 淇濆瓨鍟嗗搧灞炴€?
     *
     * @param productProperty
     * @return
     */
    @RequestMapping("/saveProductProperty")
    public ResponseVO saveProductProperty(SysProductProperty productProperty) {
        sysProductPropertyService.saveSysProductPropertyService(productProperty);
        return getSuccessResponseVO(null);
    }

    /**
     * 鍒犻櫎鍟嗗搧灞炴€?
     *
     * @param propertyId
     * @return
     */
    @RequestMapping("/delProductProperty")
    public ResponseVO delProductProperty(String propertyId) {
        sysProductPropertyService.deleteSysProductPropertyByPropertyId(propertyId);
        return getSuccessResponseVO(null);
    }
}
