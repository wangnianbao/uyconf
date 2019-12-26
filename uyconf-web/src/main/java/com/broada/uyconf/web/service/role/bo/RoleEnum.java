package com.broada.uyconf.web.service.role.bo;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色分配
 *
 * @author wnb
 *
 */
public enum RoleEnum {

    NORMAL(1), ADMIN(2), READ_ADMIN(3);
    private static final Map<Integer, RoleEnum> intToEnum = new HashMap<Integer, RoleEnum>();

    static {
        for (RoleEnum roleEnum : values()) {
            intToEnum.put(roleEnum.value, roleEnum);
        }
    }

    private final int value;

    RoleEnum(int value) {
        this.value = value;
    }

    public static RoleEnum fromInt(int symbol) {
        return intToEnum.get(symbol);
    }

    public int getValue() {
        return value;
    }
}
