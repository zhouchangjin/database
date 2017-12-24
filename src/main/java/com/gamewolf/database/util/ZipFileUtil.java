package com.gamewolf.database.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipFileUtil {
	
	  public static String readZipFile(String file) throws Exception {
		  String separator = System.getProperty("line.separator", "\n");
          ZipFile zf = new ZipFile(file);  
          InputStream in = new BufferedInputStream(new FileInputStream(file));  
          ZipInputStream zin = new ZipInputStream(in);  
          ZipEntry ze; 
          StringBuffer sb=new StringBuffer();
          while ((ze = zin.getNextEntry()) != null) {  
        	  zin.closeEntry();
              if (ze.isDirectory()) {
              } else {
            	  if(ze.getName().endsWith(".xml")){
                      System.err.println("file - " + ze.getName() + " : "  
                              + ze.getSize() + " bytes");  
                      long size = ze.getSize();  
                      if (size > 0) {  
                          BufferedReader br = new BufferedReader(  
                                  new InputStreamReader(zf.getInputStream(ze)));  
                          String line;  
                          while ((line = br.readLine()) != null) {  
                              //System.out.println(line);
                              sb.append(line);
                              sb.append(separator);
                          }  
                          br.close();  
                      }
                      break;
            	  }
              }  
          }  
          zf.close();
          zin.closeEntry();
          zin.close();
          in.close();
          return sb.toString();
      } 

}
