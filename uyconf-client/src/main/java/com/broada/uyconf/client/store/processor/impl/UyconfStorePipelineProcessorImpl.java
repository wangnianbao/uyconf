package com.broada.uyconf.client.store.processor.impl;

import static com.broada.uyconf.client.store.inner.UyconfCenterStore.getInstance;

import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;
import com.broada.uyconf.client.store.UyconfStorePipelineProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wnb miniking
 */
public class UyconfStorePipelineProcessorImpl implements UyconfStorePipelineProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(UyconfStorePipelineProcessorImpl.class);

    @Override
    public void setUyconfUpdatePipeline(IUyconfUpdatePipeline iUyconfUpdatePipeline) {

        getInstance().setiUyconfUpdatePipeline(iUyconfUpdatePipeline);
    }
}
