/**
 * unbiz-genericdao#com.baidu.unbiz.common.genericdao.sequence.SequenceGenerator.java
 * 上午11:15:18 created by jay
 */
package com.broada.unbiz.common.genericdao.sequence;

/**
 * id生成器
 *
 * @author jay
 */
public interface SequenceGenerator {

    /**
     * @param sequenceName
     *
     * @return 上午11:27:22 created by jay
     */
    Integer getIntKey(String sequenceName);

    /**
     * @param sequenceName
     *
     * @return 上午11:27:34 created by jay
     */
    Long getKey(String sequenceName);

    /**
     * @param sequenceName
     * @param size         上午11:30:02 created by jay
     */
    void initialSequence(String sequenceName, int size);

}
