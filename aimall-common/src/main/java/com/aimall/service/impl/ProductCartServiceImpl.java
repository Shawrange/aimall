package com.aimall.service.impl;

import com.aimall.constants.Constants;
import com.aimall.entity.enums.PageSize;
import com.aimall.entity.enums.ProductStatusEnum;
import com.aimall.entity.po.ProductCart;
import com.aimall.entity.po.ProductInfo;
import com.aimall.entity.po.ProductPropertyValue;
import com.aimall.entity.po.ProductSku;
import com.aimall.entity.query.*;
import com.aimall.entity.query.*;
import com.aimall.entity.vo.PaginationResultVO;
import com.aimall.entity.vo.ProductSkuProperDataVO;
import com.aimall.entity.vo.ProductSkuVO;
import com.aimall.exception.BusinessException;
import com.aimall.mappers.ProductCartMapper;
import com.aimall.mappers.ProductInfoMapper;
import com.aimall.mappers.ProductPropertyValueMapper;
import com.aimall.mappers.ProductSkuMapper;
import com.aimall.service.ProductCartService;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 璐墿杞?涓氬姟鎺ュ彛瀹炵幇
 */
@Service("productCartService")
public class ProductCartServiceImpl implements ProductCartService {

    @Resource
    private ProductCartMapper<ProductCart, ProductCartQuery> productCartMapper;

    @Resource
    private ProductInfoMapper<ProductInfo, ProductInfoQuery> productInfoMapper;

    @Resource
    private ProductSkuMapper<ProductSku, ProductSkuQuery> productSkuMapper;

    @Resource
    private ProductPropertyValueMapper<ProductPropertyValue, ProductPropertyValueQuery> productPropertyValueMapper;

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public List<ProductCart> findListByParam(ProductCartQuery param) {
        return this.productCartMapper.selectList(param);
    }

    /**
     * 鏍规嵁鏉′欢鏌ヨ鍒楄〃
     */
    @Override
    public Integer findCountByParam(ProductCartQuery param) {
        return this.productCartMapper.selectCount(param);
    }

    /**
     * 鍒嗛〉鏌ヨ鏂规硶
     */
    @Override
    public PaginationResultVO<ProductCart> findListByPage(ProductCartQuery param) {
        int count = this.findCountByParam(param);
        int pageSize = param.getPageSize() == null ? PageSize.SIZE15.getSize() : param.getPageSize();

        SimplePage page = new SimplePage(param.getPageNo(), count, pageSize);
        param.setSimplePage(page);
        List<ProductCart> list = this.findListByParam(param);
        PaginationResultVO<ProductCart> result = new PaginationResultVO(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
        return result;
    }

    /**
     * 鏂板
     */
    @Override
    public Integer add(ProductCart bean) {
        return this.productCartMapper.insert(bean);
    }

    /**
     * 鎵归噺鏂板
     */
    @Override
    public Integer addBatch(List<ProductCart> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.productCartMapper.insertBatch(listBean);
    }

    /**
     * 鎵归噺鏂板鎴栬€呬慨鏀?
     */
    @Override
    public Integer addOrUpdateBatch(List<ProductCart> listBean) {
        if (listBean == null || listBean.isEmpty()) {
            return 0;
        }
        return this.productCartMapper.insertOrUpdateBatch(listBean);
    }

    /**
     * 澶氭潯浠舵洿鏂?
     */
    @Override
    public Integer updateByParam(ProductCart bean, ProductCartQuery param) {
        StringTools.checkParam(param);
        return this.productCartMapper.updateByParam(bean, param);
    }

    /**
     * 澶氭潯浠跺垹闄?
     */
    @Override
    public Integer deleteByParam(ProductCartQuery param) {
        StringTools.checkParam(param);
        return this.productCartMapper.deleteByParam(param);
    }

    /**
     * 鏍规嵁CartId鑾峰彇瀵硅薄
     */
    @Override
    public ProductCart getProductCartByCartId(String cartId) {
        return this.productCartMapper.selectByCartId(cartId);
    }

    /**
     * 鏍规嵁CartId淇敼
     */
    @Override
    public Integer updateProductCartByCartId(ProductCart bean, String cartId) {
        return this.productCartMapper.updateByCartId(bean, cartId);
    }

    /**
     * 鏍规嵁CartId鍒犻櫎
     */
    @Override
    public Integer deleteProductCartByCartId(String cartId) {
        return this.productCartMapper.deleteByCartId(cartId);
    }

    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId鑾峰彇瀵硅薄
     */
    @Override
    public ProductCart getProductCartByProductIdAndPropertyValueIdHashAndUserId(String productId, String propertyValueIdHash, String userId) {
        return this.productCartMapper.selectByProductIdAndPropertyValueIdHashAndUserId(productId, propertyValueIdHash, userId);
    }

    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId淇敼
     */
    @Override
    public Integer updateProductCartByProductIdAndPropertyValueIdHashAndUserId(ProductCart bean, String productId, String propertyValueIdHash, String userId) {
        return this.productCartMapper.updateByProductIdAndPropertyValueIdHashAndUserId(bean, productId, propertyValueIdHash, userId);
    }

    /**
     * 鏍规嵁ProductIdAndPropertyValueIdHashAndUserId鍒犻櫎
     */
    @Override
    public Integer deleteProductCartByProductIdAndPropertyValueIdHashAndUserId(String productId, String propertyValueIdHash, String userId) {
        return this.productCartMapper.deleteByProductIdAndPropertyValueIdHashAndUserId(productId, propertyValueIdHash, userId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add2Cart(ProductCart cart) {
        Date curDate = new Date();
        //鏇存柊鍚岀被鍨嬬殑鍟嗗搧鏃堕棿
        ProductCartQuery query = new ProductCartQuery();
        query.setProductId(cart.getProductId());
        query.setUserId(cart.getUserId());

        ProductCart productCart = new ProductCart();
        productCart.setLastUpdateTime(new Date(curDate.getTime() - 1000));
        this.productCartMapper.updateByParam(productCart, query);

        ProductSku sku = this.productSkuMapper.selectByProductIdAndPropertyValueIdHash(cart.getProductId(), StringTools.encodeByMD5(cart.getPropertyValueIds()));
        if (sku == null) {
            throw new BusinessException("鍟嗗搧涓嶅瓨鍦?);
        }
        ProductCart dbCart = this.productCartMapper.selectByProductIdAndPropertyValueIdHashAndUserId(cart.getProductId(),
                StringTools.encodeByMD5(cart.getPropertyValueIds()), cart.getUserId());
        if (dbCart == null) {
            String cartId = StringTools.getRandomString(Constants.LENGTH_15);
            cart.setCartId(cartId);
            cart.setPropertyValueIdHash(StringTools.encodeByMD5(cart.getPropertyValueIds()));
            cart.setLastUpdateTime(curDate);
            cart.setCreateTime(curDate);
            this.add(cart);
        } else {
            this.productCartMapper.updateCartBuyCount(dbCart.getCartId(), cart.getBuyCount());
        }
    }

    @Override
    public PaginationResultVO<ProductSkuVO> loadProductCart(ProductCartQuery query) {
        PaginationResultVO<ProductCart> resultVO = this.findListByPage(query);
        List<ProductCart> list = resultVO.getList();
        if (list.isEmpty()) {
            return new PaginationResultVO<>(0, query.getPageSize(), query.getPageNo(), 0, new ArrayList<>());
        }
        List<String> productIdList = list.stream().map(ProductCart::getProductId).toList();
        //鏌ヨ鍟嗗搧淇℃伅
        ProductInfoQuery productInfoQuery = new ProductInfoQuery();
        productInfoQuery.setProductIdList(productIdList);
        List<ProductInfo> productInfoList = productInfoMapper.selectList(productInfoQuery);

        Map<String, ProductInfo> tempProductInfoMap = productInfoList.stream().collect(Collectors.toMap(item -> item.getProductId(), Function.identity(), (data1,
                                                                                                                                                           data2) -> data2));

        //鏌ヨ鍟嗗搧灞炴€т俊鎭?
        ProductPropertyValueQuery productPropertyValueQuery = new ProductPropertyValueQuery();
        productPropertyValueQuery.setProductIdList(productIdList);
        List<ProductPropertyValue> productPropertyValueList = productPropertyValueMapper.selectList(productPropertyValueQuery);
        Map<String, ProductPropertyValue> productPropertyValueMap =
                productPropertyValueList.stream().collect(Collectors.toMap(item -> item.getProductId() + item.getPropertyValueId(), Function.identity(),
                        (data1, data2) -> data2));
        //鏌ヨsku淇℃伅
        ProductSkuQuery productSkuQuery = new ProductSkuQuery();
        productSkuQuery.setProductIdList(productIdList);
        List<ProductSku> productSkuList = productSkuMapper.selectList(productSkuQuery);
        Map<String, ProductSku> productSkuMap = productSkuList.stream().collect(Collectors.toMap(item -> item.getProductId() + item.getPropertyValueIds(),
                Function.identity(), (data1, data2) -> data2));


        List<ProductSkuVO> productSkuVOList = new ArrayList<>();
        for (ProductCart cart : list) {
            ProductSkuVO prproductSkuVO = new ProductSkuVO();
            prproductSkuVO.setCartId(cart.getCartId());

            String propertyValueIds = cart.getPropertyValueIds();
            String[] propertyValueIdArray = propertyValueIds.split("-");
            List<ProductSkuProperDataVO> propertyData = new ArrayList<>();

            String cover = null;
            for (String propertyValueId : propertyValueIdArray) {
                ProductSkuProperDataVO productSkuProperDataVO = new ProductSkuProperDataVO();
                ProductPropertyValue productPropertyValue = productPropertyValueMap.get(cart.getProductId() + propertyValueId);
                if (productPropertyValue == null) {
                    continue;
                }
                productSkuProperDataVO.setPropertyName(productPropertyValue.getPropertyName());
                productSkuProperDataVO.setPropertyValue(productPropertyValue.getPropertyValue());
                propertyData.add(productSkuProperDataVO);

                if (cover == null && !StringTools.isEmpty(productPropertyValue.getPropertyCover())) {
                    cover = productPropertyValue.getPropertyCover();
                }
            }
            prproductSkuVO.setPropertyData(propertyData);
            ProductInfo productInfo = tempProductInfoMap.get(cart.getProductId());


            ProductSku productSku = productSkuMap.get(cart.getProductId() + cart.getPropertyValueIds());

            //璁剧疆灏侀潰
            cover = cover == null ? productInfo.getCover().split(",")[0] : cover;
            prproductSkuVO.setProductId(cart.getProductId());
            prproductSkuVO.setProductName(productInfo.getProductName());

            prproductSkuVO.setPrice(productSku.getPrice());
            prproductSkuVO.setStock(productSku.getStock());

            prproductSkuVO.setPropertyValueIds(cart.getPropertyValueIds());
            prproductSkuVO.setPropertyValueIdHash(StringTools.encodeByMD5(cart.getPropertyValueIds()));
            prproductSkuVO.setBuyCount(cart.getBuyCount());
            prproductSkuVO.setProductCover(cover);

            prproductSkuVO.setProductOnSale(ProductStatusEnum.ON_SALE.getStatus().equals(productInfo.getStatus()));
            productSkuVOList.add(prproductSkuVO);
        }
        return new PaginationResultVO<>(resultVO.getTotalCount(), resultVO.getPageSize(), resultVO.getPageNo(), resultVO.getPageTotal(), productSkuVOList);
    }
}
