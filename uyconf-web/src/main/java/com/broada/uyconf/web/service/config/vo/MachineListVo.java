package com.broada.uyconf.web.service.config.vo;

import java.util.List;

import com.broada.uyconf.web.service.zookeeper.dto.ZkDisconfData;

/**
 * @author knightliao
 */
public class MachineListVo {

    private List<ZkDisconfData.ZkDisconfDataItem> datalist;
    private int errorNum = 0;
    private int machineSize;

    public int getMachineSize() {
        return machineSize;
    }

    public void setMachineSize(int machineSize) {
        this.machineSize = machineSize;
    }

    public List<ZkDisconfData.ZkDisconfDataItem> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<ZkDisconfData.ZkDisconfDataItem> datalist) {
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
