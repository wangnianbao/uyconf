package com.broada.uyconf.core.test.zookeeper;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;

import com.broada.uyconf.core.common.zookeeper.inner.ResilientActiveKeyValueStore;

/**
 * 更新 结点
 *
 * @author wnb
 *
 */
public class UpdateNode {

    public static String hosts = "10.48.57.42:8581,10.48.57.42:8582,10.48.57.42:8583";

    public static String uyconfFileNode = "/uyconf/uyconf_demo_1_0_0_0_rd/file/redis.properties";

    private ResilientActiveKeyValueStore store;
    private Random random = new Random();

    public UpdateNode(String hosts) throws IOException, InterruptedException {
        store = new ResilientActiveKeyValueStore(true);
        store.connect(hosts);
    }

    public void run() throws InterruptedException, KeeperException {

        String value = random.nextInt(100) + "";
        store.write(uyconfFileNode, value);
        System.out.printf("Set %s to %s\n", uyconfFileNode, value);
        TimeUnit.SECONDS.sleep(random.nextInt(5));
    }

    public static void main(String[] args) throws Exception {

        UpdateNode updateNode = new UpdateNode(hosts);
        updateNode.run();
    }
}
