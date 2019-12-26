package com.broada.uyconf.client.store.processor.impl;

import static com.broada.uyconf.client.store.inner.UyconfCenterStore.getInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.client.addons.properties.ReloadConfigurationMonitor;
import com.broada.uyconf.client.common.constants.SupportFileTypeEnum;
import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.common.model.UyconfCenterBaseModel;
import com.broada.uyconf.client.common.model.UyconfCenterFile;
import com.broada.uyconf.client.common.update.IUyconfUpdate;
import com.broada.uyconf.client.store.processor.model.UyconfValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.store.UyconfStoreProcessor;

/**
 * 配置文件仓库实现器
 *
 * @author wnb
 *
 */
public class UyconfStoreFileProcessorImpl implements UyconfStoreProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfStoreFileProcessorImpl.class);

    /**
     *
     */
    @Override
    public void addUpdateCallbackList(String keyName, List<IUyconfUpdate> iUyconfUpdateList) {

        if (getInstance().getConfFileMap().containsKey(keyName)) {

            getInstance().getConfFileMap().get(keyName).getUyconfCommonCallbackModel().getUyconfConfUpdates()
                    .addAll(iUyconfUpdateList);
        }
    }

    /**
     *
     */
    @Override
    public List<IUyconfUpdate> getUpdateCallbackList(String keyName) {

        if (getInstance().getConfFileMap().containsKey(keyName)) {

            return getInstance().getConfFileMap().get(keyName).getUyconfCommonCallbackModel().getUyconfConfUpdates();
        }

        return new ArrayList<IUyconfUpdate>();
    }

    /**
     *
     */
    @Override
    public UyConfCommonModel getCommonModel(String keyName) {

        UyconfCenterFile uyconfCenterFile = getInstance().getConfFileMap().get(keyName);

        // 校验是否存在
        if (uyconfCenterFile == null) {
            LOGGER.error("cannot find " + keyName + " in store....");
            return null;
        }

        return uyconfCenterFile.getUyConfCommonModel();
    }

    /**
     *
     */
    @Override
    public boolean hasThisConf(String keyName) {

        // 配置文件
        if (!getInstance().getConfFileMap().containsKey(keyName)) {
            return false;
        }
        return true;
    }

    /**
     *
     */
    @Override
    public void inject2Instance(Object object, String fileName) {

        UyconfCenterFile uyconfCenterFile = getInstance().getConfFileMap().get(fileName);

        // 校验是否存在
        if (uyconfCenterFile == null) {
            LOGGER.error("cannot find " + fileName + " in store....");
            return;
        }

        //
        // 静态类
        //
        if (object != null) {
            // 设置object
            uyconfCenterFile.setObject(object);
        }

        // 根据类型设置值
        //
        // 注入实体
        //
        Map<String, UyconfCenterFile.FileItemValue> keMap = uyconfCenterFile.getKeyMaps();
        for (String fileItem : keMap.keySet()) {

            // 根据类型设置值
            try {

                //
                // 静态类
                //
                if (object == null) {

                    if (keMap.get(fileItem).isStatic()) {
                        LOGGER.debug(fileItem + " is a static field. ");
                        keMap.get(fileItem).setValue4StaticFileItem(keMap.get(fileItem).getValue());
                    }

                    //
                    // 非静态类
                    //
                } else {

                    LOGGER.debug(fileItem + " is a non-static field. ");

                    if (keMap.get(fileItem).getValue() == null) {

                        // 如果仓库值为空，则实例 直接使用默认值
                        Object defaultValue = keMap.get(fileItem).getFieldDefaultValue(object);
                        keMap.get(fileItem).setValue(defaultValue);

                    } else {

                        // 如果仓库里的值为非空，则实例使用仓库里的值
                        keMap.get(fileItem).setValue4FileItem(object, keMap.get(fileItem).getValue());
                    }
                }

            } catch (Exception e) {
                LOGGER.error("inject2Instance fileName " + fileName + " " + e.toString(), e);
            }
        }
    }

    /**
     *
     */
    @Override
    public Object getConfig(String fileName, String keyName) {

        UyconfCenterFile uyconfCenterFile = getInstance().getConfFileMap().get(fileName);

        // 校验是否存在
        if (uyconfCenterFile == null) {
            LOGGER.debug("cannot find " + fileName + " in store....");
            return null;
        }

        if (uyconfCenterFile.getKeyMaps().get(keyName) == null) {
            LOGGER.debug("cannot find " + fileName + ", " + keyName + " in store....");
            return null;
        }

        return uyconfCenterFile.getKeyMaps().get(keyName).getValue();
    }

    /**
     *
     */
    @Override
    public void inject2Store(String fileName, UyconfValue uyconfValue) {

        UyconfCenterFile uyconfCenterFile = getInstance().getConfFileMap().get(fileName);

        // 校验是否存在
        if (uyconfCenterFile == null) {
            LOGGER.error("cannot find " + fileName + " in store....");
            return;
        }

        if (uyconfValue == null || uyconfValue.getFileData() == null) {
            LOGGER.error("value is null for {}", fileName);
            return;
        }

        // 存储
        Map<String, UyconfCenterFile.FileItemValue> keMap = uyconfCenterFile.getKeyMaps();
        if (keMap.size() > 0) {
            for (String fileItem : keMap.keySet()) {

                Object object = uyconfValue.getFileData().get(fileItem);
                if (object == null) {
                    LOGGER.error("cannot find {} to be injected. file content is: {}", fileItem,
                            uyconfValue.getFileData().toString());
                    continue;
                }

                // 根据类型设置值
                try {

                    Object value = keMap.get(fileItem).getFieldValueByType(object);
                    keMap.get(fileItem).setValue(value);

                } catch (Exception e) {
                    LOGGER.error("inject2Store filename: " + fileName + " " + e.toString(), e);
                }
            }
        }

        // 使用过 XML式配置
        if (uyconfCenterFile.isTaggedWithNonAnnotationFile()) {

            if (uyconfCenterFile.getSupportFileTypeEnum().equals(SupportFileTypeEnum.PROPERTIES)) {
                // 如果是采用XML进行配置的，则需要利用spring的reload将数据reload到bean里
                ReloadConfigurationMonitor.reload();
            }
            uyconfCenterFile.setAdditionalKeyMaps(uyconfValue.getFileData());
        }
    }

    /**
     *
     */
    @Override
    public void transformScanData(List<UyconfCenterBaseModel> uyconfCenterBaseModels) {

        for (UyconfCenterBaseModel uyconfCenterFile : uyconfCenterBaseModels) {
            transformScanData(uyconfCenterFile);
        }
    }

    /**
     *
     */
    @Override
    public void transformScanData(UyconfCenterBaseModel uyconfCenterBaseModel) {
        getInstance().storeOneFile(uyconfCenterBaseModel);
    }

    /**
     *
     */
    @Override
    public UyconfCenterBaseModel getConfData(String key) {

        if (getInstance().getConfFileMap().containsKey(key)) {

            return getInstance().getConfFileMap().get(key);

        } else {

            return null;
        }
    }

    /**
     *
     */
    @Override
    public Set<String> getConfKeySet() {
        return getInstance().getConfFileMap().keySet();
    }

    /**
     *
     */
    @Override
    public String confToString() {

        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("\n");
        Map<String, UyconfCenterFile> disMap = getInstance().getConfFileMap();
        for (String file : disMap.keySet()) {
            sBuffer.append("uyconf-file:\t" + file + "\t");

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
            getInstance().excludeOneFile(key);
        }
    }
}
