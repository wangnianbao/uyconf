package com.broada.uyconf.client.scan.inner.statically.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.client.common.annotations.UyconfFile;
import com.broada.uyconf.client.common.annotations.UyconfFileItem;
import com.broada.uyconf.client.common.constants.SupportFileTypeEnum;
import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.common.model.UyconfCenterBaseModel;
import com.broada.uyconf.client.common.model.UyconfCenterFile;
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
 * 配置文件的静态扫描
 *
 * @author wnb
 * 14-9-9
 */
public class StaticScannerFileMgrImpl extends StaticScannerMgrImplBase implements StaticScannerMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(StaticScannerFileMgrImpl.class);

    /**
     *
     */
    @Override
    public void scanData2Store(ScanStaticModel scanModel) {

        // 转换配置文件
        List<UyconfCenterBaseModel> uyconfCenterFiles = getUyconfFiles(scanModel);
        UyconfStoreProcessorFactory.getUyconfStoreFileProcessor().transformScanData(uyconfCenterFiles);
    }

    /**
     *
     */
    @Override
    public void exclude(Set<String> keySet) {
        UyconfStoreProcessorFactory.getUyconfStoreFileProcessor().exclude(keySet);
    }

    /**
     * 获取配置文件数据
     */
    private static List<UyconfCenterBaseModel> getUyconfFiles(ScanStaticModel scanModel) {

        List<UyconfCenterBaseModel> uyconfCenterFiles = new ArrayList<UyconfCenterBaseModel>();

        Set<Class<?>> classSet = scanModel.getUyconfFileClassSet();
        for (Class<?> uyconfFile : classSet) {

            Set<Method> methods = scanModel.getUyconfFileItemMap().get(uyconfFile);
            if (methods == null) {
                continue;
            }

            UyconfCenterFile uyconfCenterFile = transformScanFile(uyconfFile, methods);

            uyconfCenterFiles.add(uyconfCenterFile);
        }

        return uyconfCenterFiles;
    }

    /**
     * 转换配置文件
     */
    private static UyconfCenterFile transformScanFile(Class<?> uyconfFileClass, Set<Method> methods) {

        UyconfCenterFile uyconfCenterFile = new UyconfCenterFile();

        //
        // class
        uyconfCenterFile.setCls(uyconfFileClass);

        UyconfFile uyconfFileAnnotation = uyconfFileClass.getAnnotation(UyconfFile.class);

        //
        // file name
        uyconfCenterFile.setFileName(uyconfFileAnnotation.filename());

        // config file target dir path
        uyconfCenterFile.setTargetDirPath(uyconfFileAnnotation.targetDirPath().trim());

        // file type
        uyconfCenterFile.setSupportFileTypeEnum(SupportFileTypeEnum.getByFileName(uyconfFileAnnotation.filename()));

        //
        // uyConfCommonModel
        UyConfCommonModel uyConfCommonModel =
                makeUyConfCommonModel(uyconfFileAnnotation.app(), uyconfFileAnnotation.env(), uyconfFileAnnotation
                        .version());
        uyconfCenterFile.setUyConfCommonModel(uyConfCommonModel);

        // Remote URL
        String url = UyconfWebPathMgr.getRemoteUrlParameter(UyClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION,
                uyConfCommonModel.getApp(),
                uyConfCommonModel.getVersion(),
                uyConfCommonModel.getEnv(),
                uyconfCenterFile.getFileName(),
                UyConfigTypeEnum.FILE);
        uyconfCenterFile.setRemoteServerUrl(url);

        // fields
        Field[] expectedFields = uyconfFileClass.getDeclaredFields();

        //
        // KEY & VALUE
        //
        Map<String, UyconfCenterFile.FileItemValue> keyMaps = new HashMap<String, UyconfCenterFile.FileItemValue>();

        for (Method method : methods) {

            // 获取指定的域
            Field field = MethodUtils.getFieldFromMethod(method, expectedFields, UyConfigTypeEnum.FILE);
            if (field == null) {
                continue;
            }

            //
            UyconfFileItem uyconfFileItem = method.getAnnotation(UyconfFileItem.class);
            String keyName = uyconfFileItem.name();

            // access
            field.setAccessible(true);

            // get setter method
            Method setterMethod = MethodUtils.getSetterMethodFromField(uyconfFileClass, field);

            // static 则直接获取其值
            if (Modifier.isStatic(field.getModifiers())) {

                try {
                    UyconfCenterFile.FileItemValue fileItemValue = new UyconfCenterFile.FileItemValue(field.get(null), field, setterMethod);
                    keyMaps.put(keyName, fileItemValue);

                } catch (Exception e) {
                    LOGGER.error(e.toString());
                }

            } else {

                // 非static则为Null, 这里我们没有必要获取其Bean的值
                UyconfCenterFile.FileItemValue fileItemValue = new UyconfCenterFile.FileItemValue(null, field, setterMethod);
                keyMaps.put(keyName, fileItemValue);
            }
        }

        // 设置
        uyconfCenterFile.setKeyMaps(keyMaps);

        return uyconfCenterFile;
    }

}
