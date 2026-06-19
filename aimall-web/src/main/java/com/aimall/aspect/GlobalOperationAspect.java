package com.aimall.aspect;

import com.aimall.annotation.GlobalInterceptor;
import com.aimall.entity.dto.TokenUserInfoDTO;
import com.aimall.entity.enums.ResponseCodeEnum;
import com.aimall.exception.BusinessException;
import com.aimall.component.RedisComponent;
import com.aimall.utils.StringTools;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Component("operationAspect")
@Aspect
public class GlobalOperationAspect {

    @Resource
    private RedisComponent redisComponent;

    @Before("@annotation(com.aimall.annotation.GlobalInterceptor)")
    public void interceptorDo(JoinPoint point) {
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
        if (null == interceptor) {
            return;
        }
        /**
         * 鏍￠獙鐧诲綍
         */
        if (interceptor.checkLogin()) {
            checkLogin();
        }
    }

    //鏍￠獙鐧诲綍
    private void checkLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (StringTools.isEmpty(token)) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
        if (redisComponent.getTokenInfo(token) == null) {
            throw new BusinessException(ResponseCodeEnum.CODE_901);
        }
    }
}
