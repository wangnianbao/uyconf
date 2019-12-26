package com.broada.uyconf.client.support.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.broada.uyconf.client.common.annotations.UyconfFile;
import org.reflections.Reflections;
import org.reflections.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.common.annotations.UyconfItem;
import com.google.common.collect.Multimap;

/**
 * 扫描打印器
 *
 * @author wnb
 * 14-6-9
 */
public class ScanPrinterUtils {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ScanPrinterUtils.class);

    /**
     * 打印出StoreMap的数据
     */
    public static void printStoreMap(Reflections reflections) {

        LOGGER.info("Now we will print store map......");

        Store store = reflections.getStore();
        Map<String/* indexName */, Multimap<String, String>> storeMap = store.getStoreMap();
        for (String indexName : storeMap.keySet()) {

            LOGGER.info("====================================");
            LOGGER.info("indexName:" + indexName);

            Multimap<String, String> multimap = storeMap.get(indexName);

            for (String firstName : multimap.keySet()) {
                Collection<String> lastNames = multimap.get(firstName);
                LOGGER.info("\t\t" + firstName + ": " + lastNames);
            }

        }
    }

    /**
     *
     */
    public static void printFileItem(Set<Field> uyconfFileItemSet) {

        for (Field item : uyconfFileItemSet) {

            LOGGER.info(item.toString());
        }
    }

    /**
     *
     */
    public static void printFileItemMethod(Set<Method> uyconfFileItemSet) {

        for (Method item : uyconfFileItemSet) {

            LOGGER.info(item.toString());
        }
    }

    /**
     *
     */
    public static void printFile(Set<Class<?>> classdata) {

        for (Class<?> item : classdata) {

            LOGGER.info(item.toString());
            UyconfFile uyconfFile = item.getAnnotation(UyconfFile.class);
            LOGGER.info("\tfile name: " + uyconfFile.filename());
            LOGGER.info("\tenv: " + uyconfFile.env());
            LOGGER.info("\tversion: " + uyconfFile.env());
        }
    }

    /**
     *
     */
    public static void printFileMap(Map<Class<?>, Set<Field>> uyconfFileItemMap) {

        for (Class<?> thisClass : uyconfFileItemMap.keySet()) {

            LOGGER.info(thisClass + " -> ");
            Set<Field> fields = uyconfFileItemMap.get(thisClass);
            for (Field field : fields) {
                LOGGER.info("\t\t" + field.toString());
            }
        }
    }

    /**
     *
     */
    public static void printItem(Set<Field> af1) {

        for (Field item : af1) {

            LOGGER.info(item.toString());
            UyconfItem uyconfItem = item.getAnnotation(UyconfItem.class);
            LOGGER.info("\tkey: " + uyconfItem.key());
            LOGGER.info("\tenv: " + uyconfItem.env());
            LOGGER.info("\tversion: " + uyconfItem.version());

        }
    }

    /**
     *
     */
    public static void printActiveBackup(Set<Class<?>> classdata) {

        for (Class<?> item : classdata) {

            LOGGER.info(item.toString());
        }
    }

    /**
     *
     */
    public static void printUpdateFile(Set<Class<?>> classdata) {

        for (Class<?> item : classdata) {

            LOGGER.info(item.toString());
        }
    }
}
