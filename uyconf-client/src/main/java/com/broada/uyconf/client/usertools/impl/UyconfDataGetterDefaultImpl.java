package com.broada.uyconf.client.usertools.impl;

import java.util.HashMap;
import java.util.Map;

import com.broada.uyconf.client.common.model.UyconfCenterFile;
import com.broada.uyconf.client.common.model.UyconfCenterItem;
import com.broada.uyconf.client.store.UyconfStoreProcessor;
import com.broada.uyconf.client.store.UyconfStoreProcessorFactory;
import com.broada.uyconf.client.usertools.IUyconfDataGetter;

/**
 * Created by wnb on 16/5/28.
 */
public class UyconfDataGetterDefaultImpl implements IUyconfDataGetter {

    @Override
    public Map<String, Object> getByFile(String fileName) {

        UyconfStoreProcessor uyconfStoreProcessor =
                UyconfStoreProcessorFactory.getUyconfStoreFileProcessor();

        UyconfCenterFile uyconfCenterFile = (UyconfCenterFile) uyconfStoreProcessor.getConfData(fileName);
        if (uyconfCenterFile == null) {
            return new HashMap<String, Object>();
        }

        return uyconfCenterFile.getKV();
    }

    @Override
    public Object getByFileItem(String fileName, String fileItem) {

        Map<String, Object> map = getByFile(fileName);

        if (map.containsKey(fileItem)) {

            return map.get(fileItem);
        }
        return null;
    }

    @Override
    public Object getByItem(String itemName) {

        UyconfStoreProcessor uyconfStoreProcessor =
                UyconfStoreProcessorFactory.getUyconfStoreItemProcessor();

        UyconfCenterItem uyconfCenterItem = (UyconfCenterItem) uyconfStoreProcessor.getConfData(itemName);

        if (uyconfCenterItem == null) {
            return null;
        }

        return uyconfCenterItem.getValue();
    }
}
