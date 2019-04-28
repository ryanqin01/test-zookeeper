package com.opentext.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public final class Utils {

	private static CuratorFramework getClient() {
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").connectionTimeoutMs(60000)
						.sessionTimeoutMs(60000).retryPolicy(retryPolicy).build();
		return client;
	}

	private static void start(CuratorFramework client) {
		client.start();
	}

	private static void close(CuratorFramework client) {
		if (client != null)
			client.close();
	}

	public static void process(CustomProcessor<CuratorFramework> processer) throws Exception {
		CuratorFramework client = null;
		try {
			client = getClient();
			start(client);
			processer.process(client);
		} finally {
			close(client);
		}
	}

	public static void processWithNode(CustomProcessor<CuratorFramework> processor) throws Exception {
		process((client) -> {
			client.create().forPath("/curator", "ctest".getBytes());
			System.out.println("/curator created");
			processor.process(client);
			client.delete().forPath("/curator");
			System.out.println("/curator deleted");
		});
	}
}
