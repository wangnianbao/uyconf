package com.broada.uyconf.web.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.broada.uyconf.web.test.service.config.dao.ConfigDaoTestCase;

/**
 * 
 * @author wnb
 * 14-1-14
 */
@RunWith(Suite.class)
@SuiteClasses({ ConfigDaoTestCase.class })
public class AllTestSuite {

}
