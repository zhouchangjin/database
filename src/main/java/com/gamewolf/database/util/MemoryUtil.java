package com.gamewolf.database.util;

public class MemoryUtil {
	
	public static long begin=0;
	
	public static String getCurrentUsedMemory(){
		long current=Runtime.getRuntime().totalMemory();
		return (current-begin)/1024/1024+"M";
	}



}
