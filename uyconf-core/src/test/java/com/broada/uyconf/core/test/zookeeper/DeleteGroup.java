package com.broada.uyconf.core.test.zookeeper;

import java.util.List;

import org.apache.zookeeper.KeeperException;

import com.broada.uyconf.core.common.zookeeper.inner.ConnectionWatcher;

/**
 * 递归进行删除
 *
 * @author wnb
 *
 */
public class DeleteGroup extends ConnectionWatcher {

    public static String hosts = "127.0.0.1:8581,127.0.0.1:8582,127.0.0.1:8583";

    /**
     * @param
     */
    public DeleteGroup() {
        super(true);
    }

    public void delete(String groupName) throws KeeperException, InterruptedException {

        String path = groupName;

        try {
            List<String> children = zk.getChildren(path, false);
            if (children.size() == 0) {
                System.out.println("delete: " + path);
                zk.delete(path, -1);
                return;
            }

            for (String child : children) {
                System.out.println("delete: " + path + "/" + child);
                delete(path + "/" + child);
            }

        } catch (KeeperException.NoNodeException e) {
            System.out.printf("Group %s does not exist\n", groupName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws Exception {

        DeleteGroup deleteGroup = new DeleteGroup();
        deleteGroup.connect(hosts);
        deleteGroup.delete("/uyconf");
        deleteGroup.close();
    }
}
// ^^ DeleteGroup
