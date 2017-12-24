package com.gamewolf.database.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import com.gamewolf.database.dbconnector.ClientManager;
import com.gamewolf.database.dbconnector.ClientProxy;
import com.gamewolf.database.dbconnector.ConnectionMsg;
import com.gamewolf.database.dbconnector.ElasticSearchClientProxy;
import com.gamewolf.database.dbsource.ElasticSearchDataSource;
import com.gamewolf.database.util.JSONUtil;




public class ElasticSearchSourceHandler implements IDatasourceHandler<ElasticSearchDataSource>{
	public static int PREVIEWSIZE=10;
	XContentBuilder jsonBuilder;
	ElasticSearchDataSource datasource;
	ClientProxy clientProxy;
	Client client;
	
	@Override
	public void setDatasource(ElasticSearchDataSource datasource) {
		// TODO Auto-generated method stub
		this.datasource=datasource;
	}

	@Override
	public ElasticSearchDataSource getDatasource() {
		// TODO Auto-generated method stub
		return datasource;
	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		if(datasource.testConnection().getErrorNum().equals(ConnectionMsg.SUCCESS_NUM)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean initialize() {
		// TODO Auto-generated method stub
		clientProxy=ClientManager.getClient(datasource);
		if(clientProxy instanceof ElasticSearchClientProxy) {
			client=((ElasticSearchClientProxy) clientProxy).getClient();
		}
		return true;
	}

	@Override
	public boolean close() {
		// TODO Auto-generated method stub
		if(client!=null){
			client.close();
		}
		
		return true;
	}

	@Override
	public List<Object> preview() {
		// TODO Auto-generated method stub
		SearchResponse searchResponse = client.prepareSearch(datasource.getIndexName()).setTypes(datasource.getTypeName())
		        .setFrom(0).setSize(PREVIEWSIZE)
		        .execute()
		        .actionGet();
		        SearchHits hits = searchResponse.getHits();
		        List<Object> list=new ArrayList<Object>();
		        for(int i=0;i<hits.getHits().length;i++){
		        	list.add(hits.getAt(i).getSource());
		        }
		        return list;
	}
	
	
	public Page<Map<String, Object>> rangeQuery(String field,Object from,Object end,int currentPage,int pageSize){
		
		QueryBuilder qb = QueryBuilders.rangeQuery(field).gte(from).lte(end);
		int skip=(currentPage-1)*pageSize;
		SearchResponse searchResponse = client.prepareSearch(datasource.getIndexName()).setTypes(datasource.getTypeName()).setQuery(qb)
		        .setFrom(skip).setSize(pageSize)
		        .execute()
		        .actionGet();
		        SearchHits hits = searchResponse.getHits();
		       long totalRecord=hits.getTotalHits();
		       int totalpage= (int)totalRecord/pageSize+1;
		       Page<Map<String, Object>> pageObj= new Page<Map<String, Object>>(totalRecord,totalpage,currentPage,pageSize);
		       List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		       for(int i=0;i<hits.getHits().length;i++){
		        	list.add(hits.getAt(i).getSource());
		       }
		       pageObj.setList(list);
		       return pageObj;
	}
	
	public Page<Map<String, Object>> textQuery(String field,String text,int currentPage,int pageSize){
		
		QueryBuilder qb = QueryBuilders.termQuery(field, text);
		int skip=(currentPage-1)*pageSize;
		SearchResponse searchResponse = client.prepareSearch(datasource.getIndexName()).setTypes(datasource.getTypeName()).setQuery(qb)
		        .setFrom(skip).setSize(pageSize)
		        .execute()
		        .actionGet();
		        SearchHits hits = searchResponse.getHits();
		       long totalRecord=hits.getTotalHits();
		       int totalpage= (int)totalRecord/pageSize+1;
		       Page<Map<String, Object>> pageObj= new Page<Map<String, Object>>(totalRecord,totalpage,currentPage,pageSize);
		       List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		       for(int i=0;i<hits.getHits().length;i++){
		        	list.add(hits.getAt(i).getSource());
		       }
		       pageObj.setList(list);
		       return pageObj;
	}
	
	public Page<Map<String, Object>> multiQuery(QueryBuilder qb,int currentPage,int pageSize){
		int skip=(currentPage-1)*pageSize;
		SearchResponse searchResponse = client.prepareSearch(datasource.getIndexName()).setTypes(datasource.getTypeName()).setQuery(qb)
		        .setFrom(skip).setSize(pageSize)
		        .execute()
		        .actionGet();
		        SearchHits hits = searchResponse.getHits();
		       long totalRecord=hits.getTotalHits();
		       int totalpage= (int)totalRecord/pageSize+1;
		       Page<Map<String, Object>> pageObj= new Page<Map<String, Object>>(totalRecord,totalpage,currentPage,pageSize);
		       List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		       for(int i=0;i<hits.getHits().length;i++){
		        	list.add(hits.getAt(i).getSource());
		       }
		       pageObj.setList(list);
		       return pageObj;
	}

	@Override
	public void retrieveRows(IRowRetrieveCallback callback) {
		// TODO Auto-generated method stub
		
	}
	
	public void insertObject(Object t,String idField){

			IndexRequestBuilder requestBuilder = client.prepareIndex(datasource.getIndexName(), datasource.getTypeName()).setRefresh(true);
			try {
				requestBuilder
				.setId(BeanUtils.getProperty(t, idField))
				.setSource(JSONUtil.getJson(t))
				.execute()
				.actionGet();
			} catch (IllegalAccessException  e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
