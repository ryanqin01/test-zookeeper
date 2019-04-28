package com.opentext.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZookeeperSample implements Watcher {

	private ZooKeeper zk;

	public static void main(String args[]) throws Exception {
		new ZookeeperSample().run();
	}

	private void run() throws Exception {
		zk = new ZooKeeper("localhost:2181", 5000, this);

		zk.create("/original", "otest".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		System.out.println(new String(zk.getData("/original", false, new Stat())));
		System.out.println(zk.exists("/original", false) != null);
		zk.setData("/original", "newdata".getBytes(), -1);
		System.out.println(new String(zk.getData("/original", false, new Stat())));
		zk.delete("/original", -1);
		System.out.println(zk.exists("/original", false) != null);

		zk.close();
	}

	@Override
	public void process(WatchedEvent event) {
	}
}
