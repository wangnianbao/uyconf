package com.broada.uyconf.client.support.registry;

import java.util.List;

/**
 * Created by wnb miniking
 */
public interface Registry {

    /**
     * 查找Bean
     *
     * @param type 类型
     *
     * @return 可找到的Bean的实例列表
     */
    <T> List<T> findByType(Class<T> type, boolean newInstance);

    /**
     * 查找Bean
     *
     * @param type 类型
     *
     * @return 可找到的Bean的实例列表
     */
    <T> T getFirstByType(Class<T> type, boolean newInstance);

    /**
     * 查找Bean, 是否找proxy
     *
     * @param type 类型
     *
     * @return 可找到的Bean的实例列表
     */
    <T> T getFirstByType(Class<T> type, boolean newInstance, boolean withProxy);
}
