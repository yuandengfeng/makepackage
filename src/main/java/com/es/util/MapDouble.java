/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Administrator
 */
public class MapDouble {
    
    private HashMap<String,Double> maps = new HashMap<String, Double>();
	public void put(String key, Double value) {
		
		if (!maps.containsKey(key)) {
			maps.put(key,value);
		}else {
			double a=maps.get(key);
			maps.put(key,a+value);
		}

	}

	public Double get(String key){
		if(maps.containsKey(key)){
			return maps.get(key);
		}else{
			return 0.0;
		}
	}
	
	public Set<String> keySet(){
		return maps.keySet();
	}
	
   public void remove(Object key){
		   maps.remove(key);
   }


	/*
     * 将时间转换为时间戳
     */
	public static String dateToStamp(String s) throws ParseException{
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}
	/*
     * 将时间戳转换为时间
     */
	public static String stampToDate(String s){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	public static void main(String[] args) throws ParseException {

		

		for(int i=0;i<10;i++){
			if (i==0 || i==8)
				continue;
			System.out.println(i);
			System.out.println("jj");
			System.out.println("bb");
		}

	}

}
