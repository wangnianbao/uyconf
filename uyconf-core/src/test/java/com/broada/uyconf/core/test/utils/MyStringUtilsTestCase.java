package com.broada.uyconf.core.test.utils;

import org.junit.Test;

import com.broada.uyconf.core.common.utils.MyStringUtils;

/**
 * MyStringUtilsTestCase
 *
 * @author wnb
 */
public class MyStringUtilsTestCase {

    @Test
    public void getRandomName() {

        System.out.println(MyStringUtils.getRandomName("abc.properties"));
    }
}
