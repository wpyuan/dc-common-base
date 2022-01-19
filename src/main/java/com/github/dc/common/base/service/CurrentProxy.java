package com.github.dc.common.base.service;

import org.springframework.aop.framework.AopContext;

/**
 * <p>
 *     获取当前代理
 * </p>
 *
 * @author wangpeiyuan
 * @date 2022/1/17 9:39
 */
public interface CurrentProxy<T> {

    /**
     * 当前代理对象
     * @return
     */
    default T _this() {
        return (T) AopContext.currentProxy();
    }
}
