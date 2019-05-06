package cn.ryan.curator.acl;

import java.util.ArrayList;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import cn.ryan.curator.Utils;

public class ACLSample {

	private static String aclString = "superuser:password";

	private static List<ACL> aclList = new ArrayList<ACL>();

	public static void main(String[] args) throws Exception {
		Utils.process((client) -> {
			ACL acl = new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest(aclString)));
			aclList.add(acl);
			client.create().withACL(aclList).forPath("/curator", "ctest".getBytes());

			withoutACL();
			withACL();

			client.delete().forPath("/curator");
		});

	}

	private static void withoutACL() {
		try {
			Utils.process((client) -> {
				client.setData().forPath("/curator", "newdata".getBytes());
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void withACL() {
		try {
			CuratorFramework client = CuratorFrameworkFactory.builder().connectString("localhost:2181").connectionTimeoutMs(60000)
							.sessionTimeoutMs(60000).retryPolicy(new ExponentialBackoffRetry(1000, 3)).authorization("digest", aclString.getBytes())
							.build();
			client.start();
			client.setData().forPath("/curator", "newdata".getBytes());
			System.out.println(new String(client.getData().forPath("/curator")));
			client.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
