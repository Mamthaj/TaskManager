package com.uttara.taskManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskUtil {

	
		public static String catNameValidate(String catName){
			if(catName == null || catName.trim().equals(""))
				return "You cannot pass null or empty category";
			if(catName.split(" ").length > 1)
				return "You have to pass single word for category";
			if(!Character.isLetter(catName.charAt(0)))
					return "You have to pass category name which starts with a letter";
			for(int i = 0;i<catName.length();i++){
				if(!Character.isDigit(catName.charAt(i)) && !Character.isLetter(catName.charAt(i)))
					return "Category should be alphanumeric";
			}
			
			return Constants.SUCCESS;
		}
		
		public static String taskNameValidation(String taskName){
			if(taskName == null || taskName.trim().equals(""))
				return "You cannot pass null or empty category";
			else
				return Constants.SUCCESS;
			
		}
		
		public static String getCurrentDate(){
			Date date=new Date();
	        DateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
	        String formattedDate=dateFormat.format(date);
	        return formattedDate;
		}
		public static boolean validateTaskPriority(int taskPriority){
			if(taskPriority >= 0  && taskPriority <= 10)
				return true;
			else
				return false;
		}
}
