package cn.ryan.curator.acl;

import java.security.NoSuchAlgorithmException;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class PasswordGenerator {

	private static String aclString = "user1:12345";

	public static void main(String[] args) throws NoSuchAlgorithmException {
		System.out.println(DigestAuthenticationProvider.generateDigest(aclString));
	}

}
