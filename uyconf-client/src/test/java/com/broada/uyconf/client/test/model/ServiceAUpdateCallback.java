package com.broada.uyconf.client.test.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.broada.uyconf.client.common.annotations.UyconfUpdateService;
import com.broada.uyconf.client.common.update.IUyconfUpdate;

/**
 * 分布式配置服务回调函数<br/>
 * <p/>
 * 1. 使用了分布式配置文件 @UyconfUpdateService
 *
 * @author wnb
 * 14-5-22
 */
@Service("serviceAUpdateCallback")
@UyconfUpdateService(classes = {ConfA.class}, itemKeys = {ServiceA.keyA})
public class ServiceAUpdateCallback implements IUyconfUpdate {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceAUpdateCallback.class);

    @Autowired
    private ServiceA serviceA;

    /**
     *
     */
    @Override
    public void reload() throws Exception {

        LOGGER.info(String.valueOf(serviceA.calcMoneyA()));
    }

}
