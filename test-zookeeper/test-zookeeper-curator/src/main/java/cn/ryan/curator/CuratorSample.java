package cn.ryan.curator;

public class CuratorSample {

	public static void main(String args[]) throws Exception {

		Utils.process((client) -> {
			client.create().forPath("/curator", "ctest".getBytes());
			System.out.println(new String(client.getData().forPath("/curator")));
			System.out.println(client.checkExists().forPath("/curator") != null);
			client.setData().forPath("/curator", "newdata".getBytes());
			System.out.println(new String(client.getData().forPath("/curator")));
			client.delete().forPath("/curator");
			System.out.println(client.checkExists().forPath("/curator") != null);
		});
	}
}
