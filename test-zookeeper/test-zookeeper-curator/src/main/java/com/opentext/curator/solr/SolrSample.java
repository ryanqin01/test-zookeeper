package com.opentext.curator.solr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.CollectionAdminRequest;

public class SolrSample {

	public static void main(String args[]) throws Exception {
		List<String> zkHosts = new ArrayList<>();
		zkHosts.add("localhost:9983");
		Optional<String> zkChroot = Optional.of("/");
		CloudSolrClient client = new CloudSolrClient.Builder(zkHosts, zkChroot).build();
		CollectionAdminRequest.Create request = CollectionAdminRequest.createCollection("demo2", "_default", 1, 1);
		client.request(request);
		client.close();
	}
}
