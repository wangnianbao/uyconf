package com.broada.uyconf.web.test.common;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 所有测试类的基类
 * 
 * @author wnb
 *
 */
@TestExecutionListeners(BaseTestBeforeClass.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager = "onedbTransactionManagerTest")
@ActiveProfiles({ "db-test" })
public class BaseTestCase extends AbstractTestCase {

}
