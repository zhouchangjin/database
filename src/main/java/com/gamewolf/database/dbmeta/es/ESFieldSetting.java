package com.gamewolf.database.dbmeta.es;

public class ESFieldSetting {
	public ESFieldType type;
	public ESAnalyzerType searchAnalyzer;
	public ESAnalyzerType analyzer;
	

	public String getAnalyzer() {
		if(analyzer!=null){
			return analyzer.toString();
		}else{
			return null;
		}
		
	}

	public void setAnalyzer(ESAnalyzerType analyzer) {
		this.analyzer = analyzer;
	}

	public String getType() {
		return type.toString();
	}
	
	public void setType(ESFieldType type) {
		this.type = type;
	}
	

	
	public String getSearchAnalyzer() {
		if(searchAnalyzer!=null){
			return searchAnalyzer.toString();
		}else{
			return null;
		}
		
	}
	public void setSearchAnalyzer(ESAnalyzerType searchAnalyzer) {
		this.searchAnalyzer = searchAnalyzer;
	}

}
