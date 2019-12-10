package com.broada.uyconf.client.store.processor.impl;

import static com.broada.uyconf.client.store.inner.DisconfCenterStore.getInstance;

import com.broada.uyconf.client.common.update.IDisconfUpdatePipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broada.uyconf.client.store.DisconfStorePipelineProcessor;

/**
 *
 */
public class DisconfStorePipelineProcessorImpl implements DisconfStorePipelineProcessor {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfStorePipelineProcessorImpl.class);

    @Override
    public void setDisconfUpdatePipeline(IDisconfUpdatePipeline iDisconfUpdatePipeline) {

        getInstance().setiDisconfUpdatePipeline(iDisconfUpdatePipeline);
    }
}
