package com.gamewolf.database.entity;

public class BasicObject implements JSONEntity, XMLEntity {
	
	public JSONVendor jsonVendor;
	public XMLVendor xmlVendor;
	
	public void BasicObject(JSONVendor jsonVendor,XMLVendor xmlVendor) {
		this.jsonVendor=jsonVendor;
		this.xmlVendor=xmlVendor;
	}

	@Override
	public String toXMLString() {
		// TODO Auto-generated method stub
		return jsonVendor.fromObject(this);
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		return xmlVendor.fromObject(this);
	}

}
