package com.broada.uyconf.client.common.update;

/**
 * 当配置更新 时，用户可以实现此接口，用以来实现回调函数
 *
 * @author wnb
 *
 */
public interface IUyconfUpdate {

    void reload() throws Exception;
}
