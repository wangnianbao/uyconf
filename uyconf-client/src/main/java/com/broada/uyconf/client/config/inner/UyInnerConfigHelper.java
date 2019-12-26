package com.broada.uyconf.client.config.inner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.broada.uyconf.client.config.UyClientConfig;
import com.broada.uyconf.client.support.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.config.UyClientSysConfig;
import com.broada.uyconf.core.common.utils.ClassLoaderUtil;
import com.broada.uyconf.core.common.utils.OsUtil;

/**
 * 用户配置、系统配置 的校验
 *
 * @author wnb
 *
 */
public class UyInnerConfigHelper {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyInnerConfigHelper.class);

    /**
     * @throws Exception
     * @Description: 校验用户配置文件是否正常
     * @author wnb
     *  2013-6-13
     */
    public static void verifyUserConfig() throws Exception {

        //
        // 服务器相关
        //

        //
        // 服务器地址

        if (StringUtils.isEmpty(UyClientConfig.getInstance().CONF_SERVER_HOST)) {

            throw new Exception("settings: " + UyClientConfig.CONF_SERVER_HOST_NAME + " cannot find");
        }

        UyClientConfig.getInstance()
                .setHostList(StringUtil.parseStringToStringList(UyClientConfig.getInstance().CONF_SERVER_HOST, ","));
        LOGGER.info(
                "SERVER " + UyClientConfig.CONF_SERVER_HOST_NAME + ": " + UyClientConfig.getInstance().getHostList());

        //
        // 版本

        if (StringUtils.isEmpty(UyClientConfig.getInstance().VERSION)) {

            throw new Exception("settings: " + UyClientConfig.VERSION_NAME + " cannot find");
        }
        LOGGER.info("SERVER " + UyClientConfig.VERSION_NAME + ": " + UyClientConfig.getInstance().VERSION);

        //
        // APP名

        if (StringUtils.isEmpty(UyClientConfig.getInstance().APP)) {

            throw new Exception("settings: " + UyClientConfig.APP_NAME + " cannot find");
        }
        LOGGER.info("SERVER " + UyClientConfig.APP_NAME + ": " + UyClientConfig.getInstance().APP);

        //
        // 环境

        if (StringUtils.isEmpty(UyClientConfig.getInstance().ENV)) {

            throw new Exception("settings: " + UyClientConfig.ENV_NAME + "  cannot find");
        }
        LOGGER.info("SERVER " + UyClientConfig.ENV_NAME + ": " + UyClientConfig.getInstance().ENV);

        //
        // 是否使用远程的配置
        LOGGER.info("SERVER uyconf.enable.remote.conf: " + UyClientConfig.getInstance().ENABLE_UYCONF);

        //
        // debug mode
        LOGGER.info("SERVER uyconf.debug: " + UyClientConfig.getInstance().DEBUG);

        // 用户下载文件夹
        if (!StringUtils.isEmpty(UyClientConfig.getInstance().userDefineDownloadDir)) {
            OsUtil.makeDirs(UyClientConfig.getInstance().userDefineDownloadDir);
            LOGGER.info("SERVER uyconf.user_define_download_dir: " + UyClientConfig.getInstance()
                    .userDefineDownloadDir);
        }

        //
        // 忽略哪些分布式配置
        //
        List<String> ignoreUyconfList =
                StringUtil.parseStringToStringList(UyClientConfig.getInstance().IGNORE_UYCONF_LIST, ",");
        Set<String> keySet = new HashSet<String>();
        if (ignoreUyconfList != null) {
            for (String ignoreData : ignoreUyconfList) {
                keySet.add(ignoreData.trim());
            }
        }
        UyClientConfig.getInstance().setIgnoreUyconfKeySet(keySet);
        LOGGER.info("SERVER uyconf.ignore: " + UyClientConfig.getInstance().getIgnoreUyconfKeySet());

        // 重试
        LOGGER.debug("SERVER uyconf.conf_server_url_retry_times: " + UyClientConfig
                .getInstance().CONF_SERVER_URL_RETRY_TIMES);

        LOGGER.debug("SERVER uyconf.conf_server_url_retry_sleep_seconds: " +
                UyClientConfig.getInstance().confServerUrlRetrySleepSeconds);

        LOGGER.debug("SERVER uyconf.enable_local_download_dir_in_class_path: " + UyClientConfig
                .getInstance().enableLocalDownloadDirInClassPath);
        // 是否将文件放在classpath目录下
        if (UyClientConfig.getInstance().enableLocalDownloadDirInClassPath) {

            String classpath = ClassLoaderUtil.getClassPath();

            if (classpath.isEmpty()) {
                LOGGER.warn("CLASSPATH is null. we will not transfer your config file to classpath in the following");
            } else {
                LOGGER.debug("classpath: " + classpath);
            }
        }
    }

    /**
     * @throws Exception
     * @Description: 校验系统配置文件是否正常
     *  2013-6-13
     */
    public static void verifySysConfig() throws Exception {

        //
        // 服务器相关
        //

        // CONF_SERVER_STORE_ACTION
        if (StringUtils.isEmpty(UyClientSysConfig.getInstance().CONF_SERVER_STORE_ACTION)) {

            throw new Exception("settings: CONF_SERVER_STORE_ACTION cannot find");
        }
        LOGGER.debug("SERVER uyconf.conf_server_store_action: " + UyClientSysConfig
                .getInstance().CONF_SERVER_STORE_ACTION);

        // CONF_SERVER_ZOO_ACTION
        if (StringUtils.isEmpty(UyClientSysConfig.getInstance().CONF_SERVER_ZOO_ACTION)) {

            throw new Exception("settings: CONF_SERVER_ZOO_ACTION cannot find");
        }
        LOGGER.debug(
                "SERVER uyconf.conf_server_zoo_action: " + UyClientSysConfig.getInstance().CONF_SERVER_ZOO_ACTION);

        // CONF_SERVER_MASTER_NUM_ACTION
        if (StringUtils.isEmpty(UyClientSysConfig.getInstance().CONF_SERVER_MASTER_NUM_ACTION)) {

            throw new Exception("settings: CONF_SERVER_MASTER_NUM_ACTION  cannot find");
        }
        LOGGER.debug("SERVER uyconf.conf_server_master_num_action: " +
                UyClientSysConfig.getInstance().CONF_SERVER_MASTER_NUM_ACTION);

        //
        // 本地相关
        //

        if (StringUtils.isEmpty(UyClientSysConfig.getInstance().LOCAL_DOWNLOAD_DIR)) {
            throw new Exception("settings: LOCAL_TMP_DIR cannot find");
        }

        // LOCAL_DOWNLOAD_DIR
        LOGGER.debug("SERVER uyconf.local_download_dir: " + UyClientSysConfig.getInstance().LOCAL_DOWNLOAD_DIR);
        OsUtil.makeDirs(UyClientSysConfig.getInstance().LOCAL_DOWNLOAD_DIR);
    }

}
