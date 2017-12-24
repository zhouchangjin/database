package com.gamewolf.database.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;



public class XmlUtil {
	
	public static String ConvertionJavaToXml(Object obj,Class clazz){
	    JAXBContext jaxbContext;
	    ByteArrayOutputStream bos=new ByteArrayOutputStream();
		try {
			jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();  
	           // output pretty printed  
	           jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
	            jaxbMarshaller.marshal(obj, bos);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		return bos.toString();
		
	}
	
	public static <T> T ConvertXmlFileToJava(String filePath,Class clazz){
	     try {
	    	 
	         File file = new File(filePath);
	         JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
	  
	         Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	         return (T)jaxbUnmarshaller.unmarshal(file);
	       } catch (JAXBException e) {
	         e.printStackTrace();
	       }
	     return null;
	}
	
	public static <T> T ConvertXmlStringToJava(String content,Class clazz){
	     try {
	    	 StringReader reader=new StringReader(content);
	         JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
	         Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	         return (T)jaxbUnmarshaller.unmarshal(reader);
	       } catch (JAXBException e) {
	         e.printStackTrace();
	       }
	     return null;
	}
	

}
