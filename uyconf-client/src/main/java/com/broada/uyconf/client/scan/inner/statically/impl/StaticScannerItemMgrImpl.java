package com.broada.uyconf.client.scan.inner.statically.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.broada.uyconf.client.common.annotations.DisconfItem;
import com.broada.uyconf.client.common.model.DisConfCommonModel;
import com.broada.uyconf.client.common.model.DisconfCenterBaseModel;
import com.broada.uyconf.client.common.model.DisconfCenterItem;
import com.broada.uyconf.client.config.DisClientSysConfig;
import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;
import com.broada.uyconf.client.store.DisconfStoreProcessorFactory;
import com.broada.uyconf.client.support.utils.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.scan.inner.statically.StaticScannerMgr;
import com.broada.uyconf.core.common.constants.DisConfigTypeEnum;
import com.broada.uyconf.core.common.path.DisconfWebPathMgr;

/*
 * 配置项的静态扫描 
 */
public class StaticScannerItemMgrImpl extends StaticScannerMgrImplBase implements StaticScannerMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(StaticScannerItemMgrImpl.class);

    @Override
    public void scanData2Store(ScanStaticModel scanModel) {

        // 转换配置项
        List<DisconfCenterBaseModel> disconfCenterItems = getDisconfItems(scanModel);
        DisconfStoreProcessorFactory.getDisconfStoreItemProcessor().transformScanData(disconfCenterItems);

    }

    /**
     *
     */
    @Override
    public void exclude(Set<String> keySet) {
        DisconfStoreProcessorFactory.getDisconfStoreItemProcessor().exclude(keySet);
    }

    /**
     * 转换配置项
     */
    private static List<DisconfCenterBaseModel> getDisconfItems(ScanStaticModel scanModel) {

        List<DisconfCenterBaseModel> disconfCenterItems = new ArrayList<DisconfCenterBaseModel>();

        Set<Method> methods = scanModel.getDisconfItemMethodSet();
        for (Method method : methods) {

            DisconfCenterItem disconfCenterItem = transformScanItem(method);

            if (disconfCenterItem != null) {
                disconfCenterItems.add(disconfCenterItem);
            }
        }

        return disconfCenterItems;
    }

    /**
     * 转换配置项
     */
    private static DisconfCenterItem transformScanItem(Method method) {

        DisconfCenterItem disconfCenterItem = new DisconfCenterItem();

        // class
        Class<?> cls = method.getDeclaringClass();

        // fields
        Field[] expectedFields = cls.getDeclaredFields();

        // field
        Field field = MethodUtils.getFieldFromMethod(method, expectedFields, DisConfigTypeEnum.ITEM);

        if (field == null) {
            return null;
        }

        // 获取标注
        DisconfItem disconfItem = method.getAnnotation(DisconfItem.class);

        // 去掉空格
        String key = disconfItem.key().replace(" ", "");

        // get setter method
        Method setterMethod = MethodUtils.getSetterMethodFromField(cls, field);
        disconfCenterItem.setSetMethod(setterMethod);

        // field
        disconfCenterItem.setField(field);

        // key
        disconfCenterItem.setKey(key);

        // access
        field.setAccessible(true);

        // object
        disconfCenterItem.setObject(null);

        // value
        if (Modifier.isStatic(field.getModifiers())) {
            try {
                disconfCenterItem.setValue(field.get(null));
            } catch (Exception e) {
                LOGGER.error(e.toString());
                disconfCenterItem.setValue(null);
            }
        } else {
            disconfCenterItem.setValue(null);
        }

        //
        // disConfCommonModel
        DisConfCommonModel disConfCommonModel = makeDisConfCommonModel(disconfItem.app(), disconfItem.env(),
                disconfItem.version());
        disconfCenterItem.setDisConfCommonModel(disConfCommonModel);

        // Disconf-web url
        String url = DisconfWebPathMgr.getRemoteUrlParameter(DisClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION,
                disConfCommonModel.getApp(),
                disConfCommonModel.getVersion(),
                disConfCommonModel.getEnv(), key,
                DisConfigTypeEnum.ITEM);
        disconfCenterItem.setRemoteServerUrl(url);

        return disconfCenterItem;
    }
}
