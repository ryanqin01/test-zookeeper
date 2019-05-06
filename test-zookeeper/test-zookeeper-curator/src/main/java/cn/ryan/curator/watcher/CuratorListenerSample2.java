package cn.ryan.curator.watcher;

import org.apache.curator.framework.api.CuratorListener;

import cn.ryan.curator.Utils;

public class CuratorListenerSample2 {

	public static void main(String[] args) throws Exception {
		Utils.process((client) -> {
			client.create().forPath("/curator", "ctest".getBytes());
			CuratorListener listener = (c, we) -> {
				System.out.println(we);
			};

			client.getCuratorListenable().addListener(listener);
			Thread.sleep(30000);
		});
	}

}
