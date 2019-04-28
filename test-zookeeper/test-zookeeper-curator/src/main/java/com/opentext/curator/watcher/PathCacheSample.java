package com.opentext.curator.watcher;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;

import com.opentext.curator.Utils;

public class PathCacheSample {

	public static void main(String[] args) throws Exception {
		Utils.process((client) -> {
			client.create().forPath("/curator", "ctest".getBytes());
			PathChildrenCache cache = new PathChildrenCache(client, "/curator", true);
			cache.getListenable().addListener((c, event) -> {
				switch (event.getType()) {
					case INITIALIZED:
						System.out.println(event);
						break;
					case CHILD_ADDED:
						System.out.println(event);
						break;
					case CHILD_UPDATED:
						System.out.println(event);
						break;
					case CHILD_REMOVED:
						System.out.println(event);
						break;
					default:
						break;
				}
			});
			cache.start();

			client.create().forPath("/curator/child", "childdata".getBytes());
			client.create().forPath("/curator/child/gchild", "gchilddata".getBytes());
			client.setData().forPath("/curator", "newdata".getBytes());
			client.setData().forPath("/curator/child", "newchilddata".getBytes());
			client.delete().forPath("/curator/child/gchild");
			client.delete().forPath("/curator/child");
			client.delete().forPath("/curator");
			cache.close();
		});
	}
}
