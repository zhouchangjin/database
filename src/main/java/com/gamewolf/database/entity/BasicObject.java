package com.gamewolf.database.entity;

public class BasicObject implements JSONEntity, XMLEntity {
	
	public JSONVendor jsonVendor;
	public XMLVendor xmlVendor;
	public Object baseObject;
	
	public BasicObject(Object baseObject,JSONVendor jsonVendor,XMLVendor xmlVendor) {
		this.jsonVendor=jsonVendor;
		this.xmlVendor=xmlVendor;
	}
	
	private BasicObject() {
		
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
	
	public static BasicObject buildBasicObject() {
		BasicObject obj=new BasicObject();
		return obj;
	}
	
	public BasicObject setJSONVendor(JSONVendor vendor) {
		this.jsonVendor=vendor;
		return this;
	}
	
	public BasicObject setXMLVendor(XMLVendor vendor) {
		this.xmlVendor=vendor;
		return this;
	}
	
	
	public BasicObject setBaseObject(Object obj) {
		this.baseObject=obj;
		return this;
	}
	

}
