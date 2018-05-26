package com.gamewolf.database.dbconnector;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

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
				client= new PreBuiltTransportClient(Settings.EMPTY)
		                .addTransportAddress(new TransportAddress(ipAddress1, 9300));
				
				//ransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(ipAddress1, datasource.getPort()));
			} catch (UnknownHostException e) {
				
			}
	        

	        		
		}
	}

	public Client getClient() {
		return client;
	}

	

}
