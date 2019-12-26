package com.broada.uyconf.client.config;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.broada.uyconf.client.config.inner.UyInnerConfigAnnotation;
import com.broada.uyconf.client.support.UyconfAutowareConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.core.common.constants.Constants;

/**
 * Uyconf Client的用户配置文件
 *
 * @author wnb
 * 14-6-6
 */
public final class UyClientConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyClientConfig.class);

    protected static final UyClientConfig INSTANCE = new UyClientConfig();

    public static UyClientConfig getInstance() {
        return INSTANCE;
    }

    protected static final String filename = "uyconf.properties";

    // uyconf.properties 的路径 -D 传入
    private static final String UYCONF_CONF_FILE_PATH_ARG = "uyconf.conf";

    private boolean isLoaded = false;

    private UyClientConfig() {

    }

    public synchronized boolean isLoaded() {
        return isLoaded;
    }

    /**
     * load config normal
     *
     * @throws Exception
     */
    public synchronized void loadConfig(String filePath) throws Exception {

        if (isLoaded) {
            return;
        }

        String filePathInternal = filename;

        // 指定的路径
        if (filePath != null) {
            filePathInternal = filePath;
        }

        // -d 的路径
        // 优先使用 系统参数或命令行导入
        String uyconfFilePath = System.getProperty(UYCONF_CONF_FILE_PATH_ARG);
        if (uyconfFilePath != null) {
            filePathInternal = uyconfFilePath;
        }

        try {
            UyconfAutowareConfig.autowareConfig(INSTANCE, filePathInternal);
        } catch (Exception e) {
            LOGGER.warn("cannot find " + filePathInternal + ", using sys var or user input.");
        }

        // 使用system env 导入
        UyconfAutowareConfig.autowareConfigWithSystemEnv(INSTANCE);

        isLoaded = true;
    }

    /**
     * 配置文件服务器 HOST
     */
    public static final String CONF_SERVER_HOST_NAME = "uyconf.conf_server_host";
    @UyInnerConfigAnnotation(name = UyClientConfig.CONF_SERVER_HOST_NAME)
    public String CONF_SERVER_HOST;

    private List<String> hostList;

    /**
     * app
     *
     * @author
     * @since 1.0.0
     */
    public static final String APP_NAME = "uyconf.app";
    @UyInnerConfigAnnotation(name = UyClientConfig.APP_NAME)
    public String APP;

    /**
     * 版本
     *
     * @author
     * @since 1.0.0
     */
    public static final String VERSION_NAME = "uyconf.version";
    @UyInnerConfigAnnotation(name = UyClientConfig.VERSION_NAME, defaultValue = Constants.DEFAULT_VERSION)
    public String VERSION = Constants.DEFAULT_VERSION;

    /**
     * 主或备
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.maintype")
    public String MAIN_TYPE;

    /**
     * 部署环境
     *
     * @author
     * @since 1.0.0
     */
    public static final String ENV_NAME = "uyconf.env";
    @UyInnerConfigAnnotation(name = UyClientConfig.ENV_NAME, defaultValue = Constants.DEFAULT_ENV)
    public String ENV = Constants.DEFAULT_ENV;

    /**
     * 是否从云端下载配置
     *
     * @author
     * @since 1.0.0
     */
    private static final String ENABLE_REMOTE_CONF_NAME = "uyconf.enable.remote.conf";
    @UyInnerConfigAnnotation(name = UyClientConfig.ENABLE_REMOTE_CONF_NAME, defaultValue = "false")
    public boolean ENABLE_UYCONF = false;

    /**
     * 是否开启DEBUG模式: 默认不开启，
     * 1）true: 用于线下调试，当ZK断开与client连接后（如果设置断点，这个事件很容易就发生），ZK不会去重新建立连接。
     * 2）false: 用于线上，当ZK断开与client连接后，ZK会再次去重新建立连接。
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.debug", defaultValue = "false")
    public boolean DEBUG = false;

    /**
     * 忽略哪些分布式配置
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.ignore", defaultValue = "")
    public String IGNORE_UYCONF_LIST = "";
    private Set<String> ignoreUyconfKeySet = new HashSet<String>();

    /**
     * 获取远程配置 重试次数，默认是3次
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.conf_server_url_retry_times", defaultValue = "3")
    public int CONF_SERVER_URL_RETRY_TIMES = 3;

    /**
     * 用户指定的 下载文件夹, 远程文件下载后会放在这里
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.user_define_download_dir", defaultValue = "./uyconf/download")
    public String userDefineDownloadDir = "./uyconf/download";

    /**
     * 获取远程配置 重试时休眠时间，默认是5秒
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.conf_server_url_retry_sleep_seconds", defaultValue = "2")
    public int confServerUrlRetrySleepSeconds = 2;

    /**
     * 让下载文件夹放在 classpath目录 下
     *
     * @author
     * @since 1.0.0
     */
    @UyInnerConfigAnnotation(name = "uyconf.enable_local_download_dir_in_class_path", defaultValue = "true")
    public boolean enableLocalDownloadDirInClassPath = true;

    public List<String> getHostList() {
        return hostList;
    }

    public void setHostList(List<String> hostList) {
        this.hostList = hostList;
    }

    public Set<String> getIgnoreUyconfKeySet() {
        return ignoreUyconfKeySet;
    }

    public void setIgnoreUyconfKeySet(Set<String> ignoreUyconfKeySet) {
        this.ignoreUyconfKeySet = ignoreUyconfKeySet;
    }

}
