package com.broada.uyconf.web.service.zookeeper.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 每个配置对应的数据
 *
 * @author wnb
 *
 */
public class ZkUyconfData {

    /**
     * 每个配置对应一个实例的数据
     *
     * @author wnb
     *
     */
    public static class ZkUyconfDataItem {

        // 所在机器
        private String machine = "";

        // 值
        private String value = "";

        private List<String> errorList = new ArrayList<String>();

        public String getMachine() {
            return machine;
        }

        public void setMachine(String machine) {
            this.machine = machine;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public List<String> getErrorList() {
            return errorList;
        }

        public void setErrorList(List<String> errorList) {
            this.errorList = errorList;
        }

        @Override
        public String toString() {
            return "ZkUyconfDataItem [machine=" + machine + ", value=" + value + ", errorList=" + errorList + "]";
        }

    }

    /*
     * 
     */
    private List<ZkUyconfDataItem> data = new ArrayList<ZkUyconfDataItem>();

    private String key;

    public List<ZkUyconfDataItem> getData() {
        return data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setData(List<ZkUyconfDataItem> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ZkUyconfData [data=" + data + ", key=" + key + "]";
    }

}
