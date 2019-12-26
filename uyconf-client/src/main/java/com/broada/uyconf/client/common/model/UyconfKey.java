package com.broada.uyconf.client.common.model;

import com.broada.uyconf.core.common.constants.UyConfigTypeEnum;

/**
 * 用于标识一个配置文件或配置项
 *
 * @author wnb
 * 14-8-4
 */
public class UyconfKey {

    public UyconfKey(UyConfigTypeEnum uyConfigTypeEnum, String key) {
        super();
        this.uyConfigTypeEnum = uyConfigTypeEnum;
        this.key = key;
    }

    /**
     * 类型
     */
    private UyConfigTypeEnum uyConfigTypeEnum;

    /**
     *  文件名或配置项名
     */
    private String key;

    public UyConfigTypeEnum getUyConfigTypeEnum() {
        return uyConfigTypeEnum;
    }

    public void setUyConfigTypeEnum(UyConfigTypeEnum uyConfigTypeEnum) {
        this.uyConfigTypeEnum = uyConfigTypeEnum;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "UyconfKey [uyConfigTypeEnum=" + uyConfigTypeEnum + ", key=" + key + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uyConfigTypeEnum == null) ? 0 : uyConfigTypeEnum.hashCode());
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        UyconfKey other = (UyconfKey) obj;
        if (uyConfigTypeEnum != other.uyConfigTypeEnum) {
            return false;
        }
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return true;
    }

}
