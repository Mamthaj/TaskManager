package com.uttara.taskManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
	public static final boolean PRINTTOMONITOR = true;
	private static Logger obj = null;
	
	private Logger(){
		
	}
	
	public synchronized static Logger getInstance(){
		if(obj == null)
			obj = new Logger();
		return obj;
		
	}
	
	public void log(String data){
		BufferedWriter bw = null;
		Date d1 = new Date();
		try{
			if(PRINTTOMONITOR){
				System.out.println(data+" "+d1.toString());
			}
			
			bw = new BufferedWriter(new FileWriter("C://Users//I506676//Desktop//LogFile.txt"));
			bw.write(data);
			bw.write(d1.toString());
			bw.newLine();
			
		}catch(IOException e){
			Logger.getInstance().log(e.getMessage());
		}
		finally{
			if(bw != null)
			try{
				bw.close();
				}catch(IOException e){
					Logger.getInstance().log(e.getMessage());
				}
			}
		
	}
	
}
