package com.broada.uyconf.client.core.filetype;

import java.util.Map;

/**
 * 文件类型处理器
 *
 * @author wnb
 */
public interface UyconfFileTypeProcessor {

    /**
     * 输入文件名，返回其相应的k-v数据
     */
    Map<String, Object> getKvMap(String fileName) throws Exception;
}
