package com.broada.uyconf.client.store.processor.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.common.model.UyconfCenterBaseModel;
import com.broada.uyconf.client.common.model.UyconfCenterItem;
import com.broada.uyconf.client.common.update.IUyconfUpdate;
import com.broada.uyconf.client.store.UyconfStoreProcessor;
import com.broada.uyconf.client.store.processor.model.UyconfValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.store.inner.UyconfCenterStore;

/**
 * 配置项仓库算子实现器
 *
 * @author wnb
 *
 */
public class UyconfStoreItemProcessorImpl implements UyconfStoreProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfStoreItemProcessorImpl.class);

    /**
     *
     */
    @Override
    public void addUpdateCallbackList(String keyName, List<IUyconfUpdate> iUyconfUpdateList) {

        if (UyconfCenterStore.getInstance().getConfItemMap().containsKey(keyName)) {

            UyconfCenterStore.getInstance().getConfItemMap().get(keyName).getUyconfCommonCallbackModel()
                    .getUyconfConfUpdates().addAll(iUyconfUpdateList);
        }

    }

    /**
     *
     */
    @Override
    public List<IUyconfUpdate> getUpdateCallbackList(String keyName) {

        if (UyconfCenterStore.getInstance().getConfItemMap().containsKey(keyName)) {

            return UyconfCenterStore.getInstance().getConfItemMap().get(keyName).getUyconfCommonCallbackModel()
                    .getUyconfConfUpdates();
        }

        return new ArrayList<IUyconfUpdate>();
    }

    /**
     *
     */
    @Override
    public UyConfCommonModel getCommonModel(String keyName) {

        UyconfCenterItem uyconfCenterItem = UyconfCenterStore.getInstance().getConfItemMap().get(keyName);

        // 校验是否存在
        if (uyconfCenterItem == null) {
            LOGGER.error("cannot find " + keyName + " in store....");
            return null;
        }

        return uyconfCenterItem.getUyConfCommonModel();
    }

    /**
     *
     */
    @Override
    public boolean hasThisConf(String keyName) {

        // 配置项
        return UyconfCenterStore.getInstance().getConfItemMap().containsKey(keyName);
    }

    /**
     *
     */
    @Override
    public void inject2Instance(Object object, String key) {

        UyconfCenterItem uyconfCenterItem = UyconfCenterStore.getInstance().getConfItemMap().get(key);

        // 校验是否存在
        if (uyconfCenterItem == null) {
            LOGGER.error("cannot find " + key + " in store....");
            return;
        }

        //
        // 非静态类
        //
        if (object != null) {
            uyconfCenterItem.setObject(object);
        }

        // 根据类型设置值
        //
        // 注入实体
        //
        try {

            if (object != null) {

                LOGGER.debug(uyconfCenterItem.getKey() + " is a non-static field. ");

                if (uyconfCenterItem.getValue() == null) {

                    // 如果仓库值为空，则实例 直接使用默认值
                    Object defaultValue = uyconfCenterItem.getFieldDefaultValue(object);
                    uyconfCenterItem.setValue(defaultValue);

                } else {

                    // 如果仓库里的值为非空，则实例使用仓库里的值
                    uyconfCenterItem.setValue4FileItem(object, uyconfCenterItem.getValue());
                }

            } else {

                //
                // 静态类
                //
                if (uyconfCenterItem.isStatic()) {
                    LOGGER.debug(uyconfCenterItem.getKey() + " is a static field. ");
                    uyconfCenterItem.setValue4StaticFileItem(uyconfCenterItem.getValue());
                }
            }

        } catch (Exception e) {
            LOGGER.error("inject2Instance key: " + key + " " + e.toString(), e);
        }
    }

    /**
     *
     */
    @Override
    public Object getConfig(String fileName, String keyName) {

        UyconfCenterItem uyconfCenterItem = UyconfCenterStore.getInstance().getConfItemMap().get(keyName);

        // 校验是否存在
        if (uyconfCenterItem == null) {
            LOGGER.debug("cannot find " + keyName + " in store....");
            return null;
        }

        return uyconfCenterItem.getValue();
    }

    /**
     *
     */
    @Override
    public void inject2Store(String key, UyconfValue uyconfValue) {

        UyconfCenterItem uyconfCenterItem = UyconfCenterStore.getInstance().getConfItemMap().get(key);

        // 校验是否存在
        if (uyconfCenterItem == null) {
            LOGGER.error("cannot find " + key + " in store....");
            return;
        }

        if (uyconfValue == null || uyconfValue.getValue() == null) {
            return;
        }

        // 根据类型设置值
        //
        // 注入仓库
        //
        try {

            Object newValue = uyconfCenterItem.getFieldValueByType(uyconfValue.getValue());
            uyconfCenterItem.setValue(newValue);

        } catch (Exception e) {
            LOGGER.error("key: " + key + " " + e.toString(), e);
        }

    }

    /**
     *
     */
    @Override
    public void transformScanData(List<UyconfCenterBaseModel> uyconfCenterBaseModels) {

        for (UyconfCenterBaseModel uyconfCenterItem : uyconfCenterBaseModels) {
            transformScanData(uyconfCenterItem);
        }
    }

    /**
     *
     */
    @Override
    public void transformScanData(UyconfCenterBaseModel uyconfCenterBaseModel) {
        UyconfCenterStore.getInstance().storeOneItem(uyconfCenterBaseModel);

    }

    /**
     *
     */
    @Override
    public UyconfCenterBaseModel getConfData(String key) {

        if (UyconfCenterStore.getInstance().getConfItemMap().containsKey(key)) {

            return UyconfCenterStore.getInstance().getConfItemMap().get(key);

        } else {

            return null;
        }
    }

    /**
     *
     */
    @Override
    public Set<String> getConfKeySet() {
        return UyconfCenterStore.getInstance().getConfItemMap().keySet();
    }

    /**
     *
     */
    @Override
    public String confToString() {

        StringBuilder sBuffer = new StringBuilder();
        sBuffer.append("\n");
        Map<String, UyconfCenterItem> disMap = UyconfCenterStore.getInstance().getConfItemMap();
        for (String file : disMap.keySet()) {
            sBuffer.append("disItem:\t" + file + "\t");

            if (LOGGER.isDebugEnabled()) {
                sBuffer.append(disMap.get(file).toString());
            } else {
                sBuffer.append(disMap.get(file).infoString());
            }
            sBuffer.append("\n");
        }

        return sBuffer.toString();
    }

    @Override
    public void exclude(Set<String> keySet) {
        for (String key : keySet) {
            UyconfCenterStore.getInstance().excludeOneItem(key);
        }
    }

}
