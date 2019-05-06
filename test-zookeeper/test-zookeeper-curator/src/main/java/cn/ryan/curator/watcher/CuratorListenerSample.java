package cn.ryan.curator.watcher;

import org.apache.curator.framework.api.CuratorListener;

import cn.ryan.curator.Utils;

public class CuratorListenerSample {

	public static void main(String[] args) throws Exception {
		Utils.process((client) -> {
			client.create().forPath("/curator", "ctest".getBytes());
			CuratorListener listener = (c, we) -> {
				System.out.println(we);
			};

			client.getCuratorListenable().addListener(listener);
			client.getData().forPath("/curator");
			client.getData().inBackground().forPath("/curator");
			client.setData().forPath("/curator", "newdata".getBytes());
			client.getData().inBackground().forPath("/curator");
			client.setData().inBackground().forPath("/curator", "newdata2".getBytes());
			client.delete().forPath("/curator");
			client.create().forPath("/curator", "ctest".getBytes());
			client.delete().inBackground().forPath("/curator");

		});
	}

}
