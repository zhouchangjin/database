package com.gamewolf.database.util;

import java.util.UUID;

public class UUIDUtil {
	
	public static String getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	public static void main(String args[]){
		System.out.println(UUIDUtil.getUUID());
	}

}
