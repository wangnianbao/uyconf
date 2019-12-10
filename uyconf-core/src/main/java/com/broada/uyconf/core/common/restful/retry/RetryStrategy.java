package com.broada.uyconf.core.common.restful.retry;

import com.broada.uyconf.core.common.restful.core.UnreliableInterface;

/**
 * 重试的策略
 *
 * @author liaoqiqi
 * @version 2014-6-10
 */
public interface RetryStrategy {

    <T> T retry(UnreliableInterface unreliableImpl, int retryTimes, int sleepSeconds) throws Exception;

}
