package com.broada.uyconf.client.scan.inner.statically.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.broada.uyconf.client.common.annotations.UyconfItem;
import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.common.model.UyconfCenterBaseModel;
import com.broada.uyconf.client.common.model.UyconfCenterItem;
import com.broada.uyconf.client.config.UyClientSysConfig;
import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;
import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;
import com.broada.uyconf.client.support.utils.MethodUtils;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.core.common.path.UyconfWebPathMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.scan.inner.statically.StaticScannerMgr;

/**
 * 配置项的静态扫描 
 */
public class StaticScannerItemMgrImpl extends StaticScannerMgrImplBase implements StaticScannerMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(StaticScannerItemMgrImpl.class);

    @Override
    public void scanData2Store(ScanStaticModel scanModel) {

        // 转换配置项
        List<UyconfCenterBaseModel> uyconfCenterItems = getUyconfItems(scanModel);
        UyconfStoreProcessorFactory.getUyconfStoreItemProcessor().transformScanData(uyconfCenterItems);

    }

    /**
     *
     */
    @Override
    public void exclude(Set<String> keySet) {
        UyconfStoreProcessorFactory.getUyconfStoreItemProcessor().exclude(keySet);
    }

    /**
     * 转换配置项
     */
    private static List<UyconfCenterBaseModel> getUyconfItems(ScanStaticModel scanModel) {

        List<UyconfCenterBaseModel> uyconfCenterItems = new ArrayList<UyconfCenterBaseModel>();

        Set<Method> methods = scanModel.getUyconfItemMethodSet();
        for (Method method : methods) {

            UyconfCenterItem uyconfCenterItem = transformScanItem(method);

            if (uyconfCenterItem != null) {
                uyconfCenterItems.add(uyconfCenterItem);
            }
        }

        return uyconfCenterItems;
    }

    /**
     * 转换配置项
     */
    private static UyconfCenterItem transformScanItem(Method method) {

        UyconfCenterItem uyconfCenterItem = new UyconfCenterItem();

        // class
        Class<?> cls = method.getDeclaringClass();

        // fields
        Field[] expectedFields = cls.getDeclaredFields();

        // field
        Field field = MethodUtils.getFieldFromMethod(method, expectedFields, UyConfigTypeEnum.ITEM);

        if (field == null) {
            return null;
        }

        // 获取标注
        UyconfItem uyconfItem = method.getAnnotation(UyconfItem.class);

        // 去掉空格
        String key = uyconfItem.key().replace(" ", "");

        // get setter method
        Method setterMethod = MethodUtils.getSetterMethodFromField(cls, field);
        uyconfCenterItem.setSetMethod(setterMethod);

        // field
        uyconfCenterItem.setField(field);

        // key
        uyconfCenterItem.setKey(key);

        // access
        field.setAccessible(true);

        // object
        uyconfCenterItem.setObject(null);

        // value
        if (Modifier.isStatic(field.getModifiers())) {
            try {
                uyconfCenterItem.setValue(field.get(null));
            } catch (Exception e) {
                LOGGER.error(e.toString());
                uyconfCenterItem.setValue(null);
            }
        } else {
            uyconfCenterItem.setValue(null);
        }

        //
        // uyConfCommonModel
        UyConfCommonModel uyConfCommonModel = makeUyConfCommonModel(uyconfItem.app(), uyconfItem.env(),
                uyconfItem.version());
        uyconfCenterItem.setUyConfCommonModel(uyConfCommonModel);

        // Uyconf-web url
        String url = UyconfWebPathMgr.getRemoteUrlParameter(UyClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION,
                uyConfCommonModel.getApp(),
                uyConfCommonModel.getVersion(),
                uyConfCommonModel.getEnv(), key,
                UyConfigTypeEnum.ITEM);
        uyconfCenterItem.setRemoteServerUrl(url);

        return uyconfCenterItem;
    }
}
