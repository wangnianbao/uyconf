package com.broada.uyconf.core.common.constants;

/**
 * 配置类型(配置文件,配置项)
 *
 * @author wnb
 *
 */
public enum UyConfigTypeEnum {

    FILE(0, "配置文件"), ITEM(1, "配置项");

    private int type = 0;
    private String modelName = null;

    private UyConfigTypeEnum(int type, String modelName) {
        this.type = type;
        this.modelName = modelName;
    }

    public static UyConfigTypeEnum getByType(int type) {

        int index = 0;
        for (UyConfigTypeEnum uyConfigTypeEnum : UyConfigTypeEnum.values()) {

            if (type == index) {
                return uyConfigTypeEnum;
            }

            index++;
        }

        return null;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
