package com.opentext.curator.watcher;

import org.apache.curator.framework.recipes.cache.NodeCache;

import com.opentext.curator.Utils;

public class NodeCacheSample {

	public static void main(String[] args) throws Exception {
		Utils.process((client) -> {
			client.create().forPath("/curator", "ctest".getBytes());
			NodeCache cache = new NodeCache(client, "/curator", false);
			cache.start(false);
			cache.getListenable().addListener(() -> {
				if (cache.getCurrentData() != null) {
					System.out.println("Path: " + cache.getCurrentData().getPath());
					System.out.println("Data: " + new String(cache.getCurrentData().getData()));
				} else {
					System.out.println("Current data is null");
				}
			});

			client.getData().forPath("/curator");
			client.setData().forPath("/curator", "newdata".getBytes());

			client.delete().forPath("/curator");
			cache.close();
		});
	}
}
