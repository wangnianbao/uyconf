package com.broada.uyconf.web.test.common;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

/**
 * 
 * @author wnb
 *
 */
public class BaseTestBeforeClass extends AbstractTestExecutionListener {

    /**
     * The default implementation is <em>empty</em>. Can be overridden by
     * subclasses as necessary.
     */
    public void prepareTestInstance(TestContext testContext) throws Exception {

    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }

}
