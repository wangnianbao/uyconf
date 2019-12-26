package com.broada.uyconf.client.core.filetype;

import java.util.HashMap;
import java.util.Map;

import com.broada.uyconf.client.common.constants.SupportFileTypeEnum;
import com.broada.uyconf.client.core.filetype.impl.UyconfAnyFileProcessorImpl;
import com.broada.uyconf.client.core.filetype.impl.UyconfXmlProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.core.filetype.impl.UyconfPropertiesProcessorImpl;

/**
 * @author wnb
 */
public class FileTypeProcessorUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(FileTypeProcessorUtils.class);

    /**
     * 输入文件名，返回其相应的k-v数据
     */
    public static Map<String, Object> getKvMap(SupportFileTypeEnum supportFileTypeEnum, String fileName)
        throws Exception {

        UyconfFileTypeProcessor uyconfFileTypeProcessor;

        //
        // 获取数据
        //
        Map<String, Object> dataMap;

        if (supportFileTypeEnum.equals(SupportFileTypeEnum.PROPERTIES)) {

            uyconfFileTypeProcessor = new UyconfPropertiesProcessorImpl();

        } else if (supportFileTypeEnum.equals(SupportFileTypeEnum.XML)) {

            uyconfFileTypeProcessor = new UyconfXmlProcessorImpl();

        } else {

            uyconfFileTypeProcessor = new UyconfAnyFileProcessorImpl();
        }

        dataMap = uyconfFileTypeProcessor.getKvMap(fileName);

        if (dataMap == null) {
            dataMap = new HashMap<String, Object>();
        }

        //
        // 进行数据过滤
        //
        for (String key : dataMap.keySet()) {

            if (key == null) {
                continue;
            }

            LOGGER.debug(key + "\t" + dataMap.get(key));
        }

        return dataMap;
    }
}
