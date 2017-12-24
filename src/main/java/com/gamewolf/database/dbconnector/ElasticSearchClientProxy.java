package com.gamewolf.database.dbconnector;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.gamewolf.database.dbsource.AbstractDataSource;
import com.gamewolf.database.dbsource.ElasticSearchDataSource;

public class ElasticSearchClientProxy extends ClientProxy{
	
	Client client;

	public ElasticSearchClientProxy(AbstractDataSource abstractDatasource) {
		super(abstractDatasource);
		if(abstractDatasource instanceof ElasticSearchDataSource){
			ElasticSearchDataSource datasource=(ElasticSearchDataSource)abstractDatasource;
			InetAddress ipAddress1 = null;
			try {
				ipAddress1 = InetAddress.getByName(datasource.getHost());
			} catch (UnknownHostException e) {
				
			}
	        client=  TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(ipAddress1, datasource.getPort()));
		}
	}

	public Client getClient() {
		return client;
	}

	

}
