package com.gamewolf.database.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	
	
	
	public static <T> void SerializeToFile(String filePath,T filter){
		File f=new File(filePath);
		try {
			FileOutputStream fos=new FileOutputStream(f);
			ObjectOutputStream os = new ObjectOutputStream(fos);  
			os.writeObject(filter);  
			os.flush();  
			os.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static <T> T loadFromFile(String filePath){
		FileInputStream fis;
		T vo=null;
		try {
			fis = new FileInputStream(new File(filePath));
			ObjectInputStream ois = new ObjectInputStream(fis);  
			vo = (T) ois.readObject();  
			ois.close();  
			fis.close(); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vo;
	}


}
