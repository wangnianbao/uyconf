package com.broada.uyconf.core.common.restful.retry;

import com.broada.uyconf.core.common.restful.core.UnreliableInterface;

/**
 * 重试的策略
 *
 * @author wnb
 *
 */
public interface RetryStrategy {

    <T> T retry(UnreliableInterface unreliableImpl, int retryTimes, int sleepSeconds) throws Exception;

}
