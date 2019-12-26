package com.broada.uyconf.core.common.restful.core;

/**
 * 一个可重试可执行方法
 *
 * @author wnb
 *
 */
public interface UnreliableInterface {

    <T> T call() throws Exception;

}
