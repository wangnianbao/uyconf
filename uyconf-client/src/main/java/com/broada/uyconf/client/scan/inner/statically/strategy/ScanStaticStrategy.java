package com.broada.uyconf.client.scan.inner.statically.strategy;

import java.util.List;

import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;

/**
 * 扫描静态注解，并且进行分析整合数据
 *
 * @author wnb
 *
 */
public interface ScanStaticStrategy {

    ScanStaticModel scan(List<String> packNameList);
}
