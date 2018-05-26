package com.gamewolf.database.dbsource;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.action.admin.indices.stats.IndicesStatsRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.NoNodeAvailableException;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.gamewolf.database.dbconnector.ConnectionMsg;

public class ElasticSearchDataSource  extends AbstractDataSource{
	String host;
	Integer port;
	String indexName;
	String typeName;
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	
	@Override
	public ConnectionMsg testConnection() {
		// TODO Auto-generated method stub
		Client client=null;
		try {
			client=  new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new TransportAddress(InetAddress.getByName(host), 9300));
			//TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
			ConnectionMsg msg=new ConnectionMsg();
			msg.setErrorNum(ConnectionMsg.SUCCESS_NUM);
			msg.setErrorMsg("Successfull connected to ES");
			try{
				IndicesStatsRequest request=new IndicesStatsRequest();
				request.search();
				client.admin().indices().stats(request);
				client.close();
			}catch(Exception e){
				if (e instanceof NoNodeAvailableException){
					msg.setErrorNum("101");
					msg.setErrorMsg("No node available:"+e.getLocalizedMessage());
				}else{
					msg.setErrorNum("201");
					msg.setErrorMsg("Unknown Error type:"+e.getLocalizedMessage());
				}
				
			}
			
			return msg;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			ConnectionMsg msg=new ConnectionMsg();
			msg.setErrorNum("102");
			msg.setErrorMsg(e.getLocalizedMessage());
			return msg;
		}
	}
	@Override
	public String getConnectionStr() {
		// TODO Auto-generated method stub
		return "es://"+this.host+":"+this.port+"";
	}

}
