package demo.msa.msahello;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.sound.midi.SysexMessage;

import org.springframework.beans.factory.annotation.Value;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

/**
 * ZookeeperDemo
 */
public class ZookeeperDemo {

    private static String CONNECTION_STRING = "192.168.56.21:2181,192.168.56.22:2181,192.168.56.23:2181";
    private static int SESSION_TIMEOUT = 5000;

    private static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        System.out.println("begin");
        ZooKeeper zk = new ZooKeeper(
            CONNECTION_STRING,
            SESSION_TIMEOUT,
            new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    System.out.println("process begin");
                    if (event.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                    }
                    System.out.println("process end");
                }
            }
        );
        System.out.println("zookeeper end");
        latch.await();
        System.out.println(zk);
        System.out.println("zk print end");

        // printZkNodeSync(zk);
        // printZkNodeAsync(zk);
        createZkNodeSync(zk);

        // zk.getChildren("/", null, new AsyncCallback.ChildrenCallback() {

        //     @Override
        //     public void processResult(int rc, String path, Object ctx, List<String> children) {
        //         for (String node : children) {
        //             System.out.println(node);
        //         }
        //     }
        // }, new Object());
    }

    private static void printZkNodeSync(ZooKeeper zk) throws Exception{
        for (String node : zk.getChildren("/", null)) {
            System.out.println(node);
            System.out.println(zk.exists("/" + node, null));
        }
    }

    private static void createZkNodeSync(ZooKeeper zk) throws Exception{
        String name = zk.create("/foo3", "hello3".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        System.out.println("name: " + name);
        System.out.println("value: " + new String(zk.getData("/foo3", null, null)));
    }

    // private static void printZkNodeAsync(ZooKeeper zk) throws Exception {
    //     zk.getChildren("/", null, new AsyncCallback.ChildrenCallback(){
        
    //         @Override
    //         public void processResult(int rc, String path, Object ctx, List<String> children) {
    //             for (String node : children) {
    //                 System.out.println(node);
    //             }
    //         }
    //     });
    // }
}