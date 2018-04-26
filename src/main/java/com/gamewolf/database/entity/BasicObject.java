package com.gamewolf.database.entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import com.alibaba.fastjson.JSON;

public class BasicObject implements JSONEntity, XMLEntity {
	
	public JSONVendor jsonVendor;
	public XMLVendor xmlVendor;
	public Object baseObject;
	HashMap<String,Object> properties;
	public static String outputSwitch="JSON";
	
	public BasicObject(Object baseObject,JSONVendor jsonVendor,XMLVendor xmlVendor) {

		properties=new HashMap<String,Object>();
		this.jsonVendor=jsonVendor;
		this.xmlVendor=xmlVendor;
		
	}
	
	public BasicObject() {
		properties=new HashMap<String,Object>();
	}
	
	public String toString() {
		if(outputSwitch.equals("JSON")) {
			return toJSONString();
		}else {
			return toJSONString();
		}
	}

	@Override
	public String toXMLString() {
		// TODO Auto-generated method stub
		return xmlVendor.fromObject(this);
	}

	@Override
	public String toJSONString() {
		// TODO Auto-generated method stub
		if(jsonVendor==null) {
			jsonVendor=new JSONVendor() {
				
				@Override
				public String fromObject(Object obj) {
					return JSON.toJSONString(properties);
				}
			};
		}
		return jsonVendor.fromObject(this);
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
	
	public BasicObject set(String name,Object value) {
		this.properties.put(name, value);
		return this;
	}
	
	public Object get(String name) {
		if(this.properties.containsKey(name)) {
			return this.properties.get(name);
		}else {
			try {
				Method m=this.baseObject.getClass().getMethod("get"+name.substring(0,1).toUpperCase()+name.substring(1));
				Object value= m.invoke(this.baseObject);
				this.properties.put(name, value);
				return value;
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				return null;
			} catch (SecurityException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return null;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return null;
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	public BasicObject build(){
		
		Method[] ms=this.baseObject.getClass().getMethods();
		for(Method m:ms) {
			if(m.getName().startsWith("get") && m.getParameterCount()==0) {
				String propertyName=m.getName().substring(3, 4).toLowerCase()+m.getName().substring(4);
				Object value;
				try {
					value = m.invoke(baseObject);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					continue;
				}
				properties.put(propertyName, value);
			}
		}
		return this;
	}
	
	
	public static void main(String args[]) {

	}
	

}
