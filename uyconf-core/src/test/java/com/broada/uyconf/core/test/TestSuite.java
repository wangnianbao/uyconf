package com.broada.uyconf.core.test;

import com.broada.uyconf.core.test.path.UyconfWebPathMgrTestCase;
import com.broada.uyconf.core.test.restful.RestfulMgrTestCase;
import com.broada.uyconf.core.test.zookeeper.ZookeeperMgrTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.broada.uyconf.core.test.path.ZooPathMgrTestCase;
import com.broada.uyconf.core.test.utils.MyStringUtilsTestCase;

/**
 * @author wnb
 * 14-7-30
 */
@RunWith(Suite.class)
@SuiteClasses({ UyconfWebPathMgrTestCase.class, ZooPathMgrTestCase.class, RestfulMgrTestCase.class,
                  ZookeeperMgrTest.class, MyStringUtilsTestCase.class})
public class TestSuite {

}