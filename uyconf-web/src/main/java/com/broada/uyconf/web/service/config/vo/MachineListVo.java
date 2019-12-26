package com.broada.uyconf.web.service.config.vo;

import java.util.List;

import com.broada.uyconf.web.service.zookeeper.dto.ZkUyconfData;

/**
 * @author wnb
 */
public class MachineListVo {

    private List<ZkUyconfData.ZkUyconfDataItem> datalist;
    private int errorNum = 0;
    private int machineSize;

    public int getMachineSize() {
        return machineSize;
    }

    public void setMachineSize(int machineSize) {
        this.machineSize = machineSize;
    }

    public List<ZkUyconfData.ZkUyconfDataItem> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<ZkUyconfData.ZkUyconfDataItem> datalist) {
        this.datalist = datalist;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    @Override
    public String toString() {
        return "MachineListVo [datalist=" + datalist + ", errorNum=" + errorNum + ", machineSize=" + machineSize + "]";
    }

}
