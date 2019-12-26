package com.broada.dsp.common.listener;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

public class ContextListener extends ContextLoaderListener {

    private static final Logger logger = LoggerFactory.getLogger(ContextListener.class);

    private static boolean isInitialized = false;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        super.contextDestroyed(arg0);
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        super.contextInitialized(arg0);

        logger.info("start to load ContextListener");

        isInitialized = true;
        logger.info("end of ContextListener");
    }

    public static boolean isInitialized() {
        return isInitialized;
    }

}
