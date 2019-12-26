package com.broada.uyconf.client.usertools;

import java.util.Map;

import com.broada.uyconf.client.usertools.impl.UyconfDataGetterDefaultImpl;

/**
 * Created by miniking
 */
public class UyconfDataGetter {

    private static IUyconfDataGetter iUyconfDataGetter = new UyconfDataGetterDefaultImpl();

    /**
     * 根据 分布式配置文件 获取该配置文件的所有数据，以 map形式呈现
     *
     * @param fileName
     *
     * @return
     */
    public static Map<String, Object> getByFile(String fileName) {
        return iUyconfDataGetter.getByFile(fileName);
    }

    /**
     * 获取 分布式配置文件 获取该配置文件 中 某个配置项 的值
     *
     * @param fileName
     * @param fileItem
     *
     * @return
     */
    public static Object getByFileItem(String fileName, String fileItem) {
        return iUyconfDataGetter.getByFileItem(fileName, fileItem);
    }

    /**
     * 根据 分布式配置 获取其值
     *
     * @param itemName
     *
     * @return
     */
    public static Object getByItem(String itemName) {
        return iUyconfDataGetter.getByItem(itemName);
    }
}
