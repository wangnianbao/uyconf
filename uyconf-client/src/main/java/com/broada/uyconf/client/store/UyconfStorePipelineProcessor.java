package com.broada.uyconf.client.store;

import com.broada.uyconf.client.common.update.IUyconfUpdatePipeline;

/**
 * @author wnb
 */
public interface UyconfStorePipelineProcessor {

    void setUyconfUpdatePipeline(IUyconfUpdatePipeline iUyconfUpdatePipeline);
}
