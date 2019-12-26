package com.broada.uyconf.client.config.inner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.common.model.InstanceFingerprint;

/**
 * 一些通用的数据
 *
 * @author wnb
 * 14-7-1
 */
public class UyClientComConfig {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyClientComConfig.class);

    protected static final UyClientComConfig INSTANCE = new UyClientComConfig();

    public static UyClientComConfig getInstance() {
        return INSTANCE;
    }

    private UyClientComConfig() {

        initInstanceFingerprint();
    }

    /**
     * 初始化实例指纹<br/>
     * 以IP和PORT为指紋，如果找不到则以本地IP为指纹
     */
    private void initInstanceFingerprint() {

        Properties properties = System.getProperties();

        int port = 0;

        // get host
        String host = properties.getProperty("VCAP_APP_HOST");
        if (host == null) {

            InetAddress addr;
            try {
                addr = InetAddress.getLocalHost();
                host = addr.getHostName();
            } catch (UnknownHostException e) {
                LOGGER.info("");
            }

        } else {
            // get port
            try {
                port = Integer.parseInt(properties.getProperty("VCAP_APP_HOST"));
            } catch (Exception e) {
                LOGGER.info("");
            }
        }

        instanceFingerprint = new InstanceFingerprint(host, port, UUID.randomUUID().toString());
    }

    private InstanceFingerprint instanceFingerprint;

    /**
     * 获取指纹
     */
    public String getInstanceFingerprint() {
        return instanceFingerprint.getHost() + "_" + String.valueOf(instanceFingerprint.getPort()) + "_" +
                instanceFingerprint.getUuid();
    }
}
