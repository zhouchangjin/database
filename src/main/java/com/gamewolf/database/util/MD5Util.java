package com.gamewolf.database.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	public static String MD5(String str){
		 byte [] buf = str.getBytes();
	        MessageDigest md5;
			try {
				md5 = MessageDigest.getInstance("MD5");
				 md5.update(buf);
			        byte [] tmp = md5.digest();
			        StringBuilder sb = new StringBuilder();
			        for (byte b:tmp) {
			        	String s=Integer.toHexString(b&0xff);
			        	if(s.length()==1){
			        		s="0"+s;
			        	}
			            sb.append(s);
			        }
			        return sb.toString();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "";

	}
	
	public static String MD5(String str,int length){
		return MD5(str).substring(0, length);
	}
	
	public  static void main(String args[]){
		String out=MD5("http://tieba.baidu.com/official/click/redirect?client_type=pc_web&tbjump=http%3A%2F%2Ftieba.baidu.com%2Fp%2F4438382774&ie=utf-8&task=%E5%9C%B0%E6%96%B9%E5%90%A7%E5%B9%BF%E5%91%8A_44441&ie=utf-8&locate=35&ie=utf-8&page=pt-pc-frsui&type=click&url=http%3A%2F%2Ftieba.baidu.com%2Ff%3Fie%3Dutf-8%26kw%3D%25E5%258E%25A6%25E9%2597%25A8%26fr%3Dsearch%3Fie%3Dutf-8%26kw%3D%25E5%258E%25A6%25E9%2597%25A8%26fr%3Dsearch&ie=utf-8&refer=&ie=utf-8&fid=21529&ie=utf-8&fname=%E5%8E%A6%E9%97%A8&ie=utf-8&uid=&uname=&is_new_user=&tid=&_t=1459995501&desc=35_FRS头部图文混排1_文字链接3&obj_id=44441",24);
		System.out.println(out);
		String s="http://zhidao.baidu.com/link?url=Vs9GOgaFLMlUQCzl2RLkWQ9K4meXZSr-26h9727YPtVIp8Lr_uBqlXb3mBizMcjQdN270OUewL0gL9MrN0J71QNpHGLNmS3iHxjHa7SICUK";
		System.out.println(s.length());
		
		String md5Str="wwww";
		System.out.println(MD5(md5Str));
	}

}
