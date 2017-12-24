
package com.gamewolf.database.dbconnector;

import com.gamewolf.database.dbsource.AbstractDataSource;

public class ClientProxy {
	AbstractDataSource datasource;
	public ClientProxy(AbstractDataSource abstractDatasource) {
		this.datasource=abstractDatasource;
	}

}
