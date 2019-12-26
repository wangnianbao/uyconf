package com.broada.unbiz.common.genericdao.bo;

/**
 * @version create
 */
public enum InsertOption {

    LOW_PRIORITY, DELAYED, HIGH_PRIORITY, IGNORE;

    @Override
    public String toString() {
        return this.name().toLowerCase();
    }

}
