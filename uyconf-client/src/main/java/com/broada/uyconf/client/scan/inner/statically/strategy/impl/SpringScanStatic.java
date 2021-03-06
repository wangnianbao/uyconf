package com.broada.uyconf.client.scan.inner.statically.strategy.impl;

import java.util.List;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;
import com.broada.uyconf.client.scan.inner.statically.strategy.ScanStaticStrategy;

/**
 *Created by miniking
 */
public class SpringScanStatic implements ScanStaticStrategy {

    private ApplicationContext context;
    private DefaultListableBeanFactory factory;

    /**
     * 构造函数
     */
    public SpringScanStatic(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public ScanStaticModel scan(List<String> packNameList) {

        factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        return null;
    }

}
