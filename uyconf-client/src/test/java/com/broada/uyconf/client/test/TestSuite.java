package com.broada.uyconf.client.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.broada.uyconf.client.test.config.ConfigMgrTestCase;
import com.broada.uyconf.client.test.core.DisconfCoreMgrTestCase;
import com.broada.uyconf.client.test.fetcher.FetcherMgrMgrTestCase;
import com.broada.uyconf.client.test.json.JsonTranslate;
import com.broada.uyconf.client.test.scan.ScanMgrTestCase;
import com.broada.uyconf.client.test.scan.inner.ScanPackTestCase;
import com.broada.uyconf.client.test.watch.WatchMgrTestCase;

@RunWith(Suite.class)
@SuiteClasses({ScanPackTestCase.class, DisconfMgrTestCase.class, WatchMgrTestCase.class, ScanMgrTestCase.class,
                  JsonTranslate.class, FetcherMgrMgrTestCase.class, DisconfCoreMgrTestCase.class,
                  ConfigMgrTestCase.class})
public class TestSuite {

}