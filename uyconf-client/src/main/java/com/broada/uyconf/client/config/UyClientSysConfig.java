package com.broada.uyconf.client.config;

import com.broada.uyconf.client.config.inner.UyInnerConfigAnnotation;
import com.broada.uyconf.client.support.UyconfAutowareConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Uyconf 系统自带的配置
 *
 * @author wnb
 *
 */
public class UyClientSysConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyClientSysConfig.class);

    protected static final UyClientSysConfig INSTANCE = new UyClientSysConfig();

    public static UyClientSysConfig getInstance() {
        return INSTANCE;
    }

    protected static final String filename = "uyconf_sys.properties";

    private boolean isLoaded = false;

    private UyClientSysConfig() {

    }

    public synchronized boolean isLoaded() {
        return isLoaded;
    }

    /**
     * load config normal
     */
    public synchronized void loadConfig(String filePath) throws Exception {

        if (isLoaded) {
            return;
        }

        String filePathInternal = filename;

        if (filePath != null) {

            filePathInternal = filePath;
        }

        UyconfAutowareConfig.autowareConfig(INSTANCE, filePathInternal);

        isLoaded = true;
    }

    /**
     * STORE URL
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.conf_server_store_action")
    public String CONF_SERVER_STORE_ACTION;

    /**
     * STORE URL
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.conf_server_zoo_action")
    public String CONF_SERVER_ZOO_ACTION;

    /**
     * 获取远程主机个数的URL
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.conf_server_master_num_action")
    public String CONF_SERVER_MASTER_NUM_ACTION;

    /**
     * 下载文件夹, 远程文件下载后会放在这里
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.local_download_dir")
    public String LOCAL_DOWNLOAD_DIR;

}
