package com.broada.uyconf.client.support.registry;

import org.springframework.context.ApplicationContext;

import com.broada.uyconf.client.support.registry.impl.SpringRegistry;

/**
 * Created by wnb miniking
 */
public class RegistryFactory {

    /**
     *
     */
    public static Registry getSpringRegistry(ApplicationContext applicationContext) throws Exception {

        SpringRegistry registry = new SpringRegistry();
        registry.setApplicationContext(applicationContext);

        return registry;
    }
}
