package com.broada.uyconf.client.scan.inner.statically.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.broada.uyconf.client.common.constants.SupportFileTypeEnum;
import com.broada.uyconf.client.common.model.UyConfCommonModel;
import com.broada.uyconf.client.common.model.UyconfCenterBaseModel;
import com.broada.uyconf.client.common.model.UyconfCenterFile;
import com.broada.uyconf.client.config.UyClientSysConfig;
import com.broada.uyconf.client.scan.inner.statically.StaticScannerMgr;
import com.broada.uyconf.client.scan.inner.statically.model.ScanStaticModel;
import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;
import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;
import com.broada.uyconf.core.common.path.UyconfWebPathMgr;

/**
 * 非注解配置文件的扫描器
 */
public class StaticScannerNonAnnotationFileMgrImpl extends StaticScannerMgrImplBase implements StaticScannerMgr {

    /**
     *
     */
    @Override
    public void scanData2Store(ScanStaticModel scanModel) {

        //
        //
        //
        List<UyconfCenterBaseModel> uyconfCenterBaseModels = getUyconfCenterFiles(scanModel.getJustHostFiles());

        UyconfStoreProcessorFactory.getUyconfStoreFileProcessor().transformScanData(uyconfCenterBaseModels);
    }

    /**
     *
     */
    public static void scanData2Store(String fileName) {

        UyconfCenterBaseModel uyconfCenterBaseModel =
                StaticScannerNonAnnotationFileMgrImpl.getUyconfCenterFile(fileName);

        UyconfStoreProcessorFactory.getUyconfStoreFileProcessor().transformScanData(uyconfCenterBaseModel);
    }

    /**
     *
     */
    @Override
    public void exclude(Set<String> keySet) {
        UyconfStoreProcessorFactory.getUyconfStoreFileProcessor().exclude(keySet);
    }

    /**
     *
     */
    public static List<UyconfCenterBaseModel> getUyconfCenterFiles(Set<String> fileNameList) {

        List<UyconfCenterBaseModel> uyconfCenterFiles = new ArrayList<UyconfCenterBaseModel>();

        for (String fileName : fileNameList) {

            uyconfCenterFiles.add(getUyconfCenterFile(fileName));
        }

        return uyconfCenterFiles;
    }

    /**
     *
     */
    public static UyconfCenterBaseModel getUyconfCenterFile(String fileName) {

        UyconfCenterFile uyconfCenterFile = new UyconfCenterFile();

        fileName = fileName.trim();

        //
        // file name
        uyconfCenterFile.setFileName(fileName);

        // 非注解式
        uyconfCenterFile.setIsTaggedWithNonAnnotationFile(true);

        // file type
        uyconfCenterFile.setSupportFileTypeEnum(SupportFileTypeEnum.getByFileName(fileName));

        //
        // uyConfCommonModel
        UyConfCommonModel uyConfCommonModel = makeUyConfCommonModel("", "", "");
        uyconfCenterFile.setUyConfCommonModel(uyConfCommonModel);

        // Remote URL
        String url = UyconfWebPathMgr.getRemoteUrlParameter(UyClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION,
                uyConfCommonModel.getApp(),
                uyConfCommonModel.getVersion(),
                uyConfCommonModel.getEnv(),
                uyconfCenterFile.getFileName(),
                UyConfigTypeEnum.FILE);
        uyconfCenterFile.setRemoteServerUrl(url);

        return uyconfCenterFile;
    }

}
