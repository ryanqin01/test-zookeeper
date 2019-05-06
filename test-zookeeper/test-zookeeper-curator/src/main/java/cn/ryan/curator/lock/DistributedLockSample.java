package cn.ryan.curator.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import cn.ryan.curator.Utils;

public class DistributedLockSample {

	private static ExecutorService executor = Executors.newFixedThreadPool(2);

	public static void main(String[] args) throws Exception {
		executor.submit(() -> {
			try {
				Utils.process((client) -> {
					client.create().forPath("/curator", "ctest".getBytes());
					InterProcessMutex lock = new InterProcessMutex(client, "/curator");
					lock.acquire();
					Thread.sleep(20000);
					lock.release();
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		executor.submit(() -> {
			try {
				Utils.process((client) -> {
					Thread.sleep(2000);
					long starttime = System.currentTimeMillis();
					InterProcessMutex lock = new InterProcessMutex(client, "/curator");
					lock.acquire();
					long endtime = System.currentTimeMillis();
					System.out.println("Total wait time: " + (endtime - starttime) / 1000 + " s.");
					client.delete().deletingChildrenIfNeeded().forPath("/curator");
					lock.release();
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		executor.shutdown();
	}
}
