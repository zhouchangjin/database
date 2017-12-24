
package com.gamewolf.database.dbconnector;

import java.util.HashMap;

import com.gamewolf.database.dbsource.AbstractDataSource;


public class ClientManager {
	
	public static HashMap<String,ClientProxy> clientMap=new HashMap<String, ClientProxy>();
	
	public static ClientProxy getClient(AbstractDataSource datasource){
		
		if(!clientMap.containsKey(datasource.getConnectionStr())){
			setClient(datasource);
		}
		return clientMap.get(datasource.getConnectionStr());
	}
	
	public static ClientProxy setClient(AbstractDataSource datasource){
		ClientProxyFactory factory=new ClientProxyFactory();
		ClientProxy proxy=factory.createClientByType(datasource);
		return clientMap.put(datasource.getConnectionStr(), proxy);
	}

}
