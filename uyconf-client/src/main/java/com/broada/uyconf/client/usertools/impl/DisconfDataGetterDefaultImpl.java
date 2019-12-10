package com.broada.uyconf.client.usertools.impl;

import java.util.HashMap;
import java.util.Map;

import com.broada.uyconf.client.common.model.DisconfCenterFile;
import com.broada.uyconf.client.common.model.DisconfCenterItem;
import com.broada.uyconf.client.store.DisconfStoreProcessor;
import com.broada.uyconf.client.store.DisconfStoreProcessorFactory;
import com.broada.uyconf.client.usertools.IDisconfDataGetter;

/**
 * Created by knightliao on 16/5/28.
 */
public class DisconfDataGetterDefaultImpl implements IDisconfDataGetter {

    @Override
    public Map<String, Object> getByFile(String fileName) {

        DisconfStoreProcessor disconfStoreProcessor =
                DisconfStoreProcessorFactory.getDisconfStoreFileProcessor();

        DisconfCenterFile disconfCenterFile = (DisconfCenterFile) disconfStoreProcessor.getConfData(fileName);
        if (disconfCenterFile == null) {
            return new HashMap<String, Object>();
        }

        return disconfCenterFile.getKV();
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

        DisconfStoreProcessor disconfStoreProcessor =
                DisconfStoreProcessorFactory.getDisconfStoreItemProcessor();

        DisconfCenterItem disconfCenterItem = (DisconfCenterItem) disconfStoreProcessor.getConfData(itemName);

        if (disconfCenterItem == null) {
            return null;
        }

        return disconfCenterItem.getValue();
    }
}
