package com.broada.uyconf.core.test;

import com.broada.uyconf.core.test.path.DisconfWebPathMgrTestCase;
import com.broada.uyconf.core.test.restful.RestfulMgrTestCase;
import com.broada.uyconf.core.test.zookeeper.ZookeeperMgrTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.broada.uyconf.core.test.path.ZooPathMgrTestCase;
import com.broada.uyconf.core.test.utils.MyStringUtilsTestCase;

/**
 * @author liaoqiqi
 * @version 2014-7-30
 */
@RunWith(Suite.class)
@SuiteClasses({ DisconfWebPathMgrTestCase.class, ZooPathMgrTestCase.class, RestfulMgrTestCase.class,
                  ZookeeperMgrTest.class, MyStringUtilsTestCase.class})
public class TestSuite {

}