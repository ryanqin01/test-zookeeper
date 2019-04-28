package com.opentext.curator.solr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.request.ConfigSetAdminRequest;
import org.apache.solr.common.util.NamedList;

public class SolrCleanConfigSets {

	public static void main(String[] args) throws Exception {
		List<String> zkHosts = new ArrayList<>();
		zkHosts.add("localhost:9983");
		Optional<String> zkChroot = Optional.of("/");
		CloudSolrClient client = new CloudSolrClient.Builder(zkHosts, zkChroot).build();
		ConfigSetAdminRequest.List request = new ConfigSetAdminRequest.List();
		NamedList<Object> response = client.request(request);
		@SuppressWarnings("unchecked")
		List<String> configsets = (List<String>)response.get("configSets");
		configsets.stream().filter(c -> !c.equalsIgnoreCase("_default")).forEach(c -> {
			ConfigSetAdminRequest.Delete drequest = new ConfigSetAdminRequest.Delete();
			drequest.setConfigSetName(c);
			try {
				client.request(drequest);
			} catch (SolrServerException | IOException e) {
				e.printStackTrace();
			}

		});
		client.close();
	}

}
