package com.opentext.curator.watcher;

import org.apache.zookeeper.Watcher;

import com.opentext.curator.Utils;

public class ZookeeperWatcherSample {

	public static void main(String args[]) throws Exception {
		Utils.processWithNode((client) -> {
			byte[] content = client.getData().usingWatcher((Watcher)(we) -> {
				System.out.println(we);
			}).forPath("/curator");
			System.out.println(new String(content));
			client.setData().forPath("/curator", "change1".getBytes());
			client.setData().forPath("/curator", "change2".getBytes());
		});
	}

}
