/**
 * adx-common#com.baidu.ub.common.generic.dao.operator.Order.java
 * 下午2:53:44 created by jay
 */
package com.broada.unbiz.common.genericdao.operator;

/**
 * 排序
 *
 * @author jay
 */
public class Order implements Pair {

    private String column;

    private boolean asc;

    /**
     * @param column
     * @param asc
     */
    public Order(String column, boolean asc) {
        this.column = column;
        this.asc = asc;
    }

    public boolean isAsc() {
        return asc;
    }

    public String getColumn() {
        return column;
    }

    public Object getValue() {
        return asc;
    }

}
