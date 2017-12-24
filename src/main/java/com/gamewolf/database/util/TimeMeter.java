package com.gamewolf.database.util;

import java.util.ArrayList;
import java.util.Date;

public class TimeMeter {
	
	Date startTime;
	String lastFlag;
    ArrayList<String> flagList=new ArrayList<String>();
    ArrayList<Integer> log=new ArrayList<Integer>();
    ArrayList<Date> dates=new ArrayList<Date>();
	
	public TimeMeter(){
		this.startTime=new Date();
		flagList.add("Begin");
		log.add(0);
		dates.add(startTime);
	}
	
	public void  setFlag(String flagName){
		Date current=new Date();
		int index=flagList.size()-1;
		this.lastFlag=flagList.get(index);
		flagList.add(flagName);
		Date last=dates.get(dates.size()-1);
		int time=(int)(current.getTime()-last.getTime());
		System.out.println("from "+this.lastFlag+" to "+flagName+" cost "+time+" ms");
		dates.add(current);
		log.add(time);
	}

}
