package com.uttara.taskManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class TaskModel {
	public boolean doesCatExists(String catName){
		String path = Constants.PATH+catName+".todo";
		return new File(path).exists();			
	}
	
	
	public boolean doesTaskExist(String taskName, String catName) throws IOException {
		String path = Constants.PATH+catName+".todo";
		BufferedReader br = null;
		System.out.println("hello");
		String line;
		File file = new File(path);
		if(file.exists()){
		try{
			br = new BufferedReader(new FileReader(path));
			while((line = br.readLine())!= null){
				String[] sa = line.split(":");
				if(sa[0].equals(taskName))
					return true;
			}
		}catch(IOException e){
			throw e;
		}
		finally{
			if(br != null){
				try{
					br.close();
				}catch(IOException e){
					throw e;
				}
			}
		}
	}
	return false;
	}
	
	
	public String createCategory(String catName) throws IOException {
		String path = Constants.PATH+catName+".todo";
		String msg ="";
		File cat = new File(path);
		if(cat.createNewFile())
			msg = Constants.SUCCESS;
		else
			msg = "Something went wrong while creating "+catName;
		return msg;
	}

	
	public String addTask(TaskBean tb, String catName) {
		String msg = tb.validate();
		String path = Constants.PATH+catName+".todo";
		String tags ="";
		LinkedHashSet<String> taskTags;
		List<String> taskList = new ArrayList<String>();
		if(msg.equals(Constants.SUCCESS)){
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				taskTags = tb.getTags();
				int i =0;
				for(String s:taskTags){
					tags = tags+s;
					
					if(i != (taskTags.size()-1))
						tags = tags+",";
					i++;
				}
				String data = tb.getName()+":"+tb.getDesc()+":"+formatter.format(tb.getCr_dt())+":"+formatter.format(tb.getEnd_dt())+":"+tb.getPriority()+":"+tb.getStatus()+":"+tags;
				taskList.add(data);
				return updateTaskInFile(taskList, path,true);
		}
		else
			return msg;
	}
	
private String updateTaskInFile(List<String> al,String pathName,boolean append){
		
		String msg ="";
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(pathName,append));
			for(String s:al){
				bw.write(s);
				bw.newLine();
			}
			
		}catch(IOException e){
			msg = msg+e.getMessage();
			e.printStackTrace();
			return msg;
		}
		finally{
			if(bw != null){
				try{
					bw.close();
				}catch(IOException e){
					msg = msg+e.getMessage();
					e.printStackTrace();
					return msg;
				}
			}
		}
		if(msg.equals(""))
			msg = Constants.SUCCESS;
		return msg;
		
	}


		public String modifyTask(TaskBean tb, TaskBean oldTB, String catName) {
			String msg = "";
			if(tb.validate().equals(Constants.SUCCESS)){
			String path = Constants.PATH+catName+".todo";
			BufferedReader br = null;
			String[] sa = null;
			LinkedHashSet<String> taskTags = new LinkedHashSet<String>();
			String tags = "";
			taskTags = tb.getTags();
			int i =0;
			for(String s:taskTags){
				tags = tags+s;
				if(i != (taskTags.size()-1))
					tags = tags+",";
				i++;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String data = tb.getName()+":"+tb.getDesc()+":"+formatter.format(tb.getCr_dt())+":"+formatter.format(tb.getEnd_dt())+":"+tb.getPriority()+":"+tb.getStatus()+":"+tags;
			String line;
			List<String> contentList = new ArrayList<String>();
			try{
				br = new BufferedReader(new FileReader(path));
				while((line = br.readLine()) != null){
					sa = line.split(":");
					if(sa[0].equals(oldTB.getName())){
						contentList.add(data);	
					}
					else
						contentList.add(line);//here i changed
				}
			}catch(IOException e){
				msg = msg+e.getMessage();
				e.printStackTrace();
				return msg;
			}
			finally{
				if(br != null){
					try{
						br.close();
					}catch(IOException e){
						msg = msg+e.getMessage();
						e.printStackTrace();
						return msg;
					}
				}
			}
			msg = updateTaskInFile(contentList,path,false);
			}
			return msg;
		}

		public TaskBean getTask(String taskName, String catName) throws Exception {
			String path = Constants.PATH+catName+".todo";
			BufferedReader br = null;
			TaskBean tb = null;
			LinkedHashSet<String> hs = new LinkedHashSet<String>();
			String line;
			try{
				SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
				br = new BufferedReader(new FileReader(path));
				while((line = br.readLine())!= null){
					String[] sa = line.split(":");
					if(sa[0].equals(taskName)){
						String[] tags = sa[6].split(",");
						for(String s: tags){
							hs.add(s);
						}
						tb = new TaskBean(sa[0],sa[1],parser.parse(sa[2]),parser.parse(sa[3]),Integer.parseInt(sa[4]),sa[5],hs);
					}	
				}
			}catch(IOException e){
				throw e;
			}
			catch(ParseException e){
				throw e;
			}
			finally{
				if(br != null){
					try{
						br.close();
					}catch(IOException e){
						throw e;
					}
				}
			}
			return tb;
		}
		public String deleteTask(TaskBean tb, String catName) {
			String msg = "";
			String path = Constants.PATH+catName+".todo";
			BufferedReader br = null;
			String line;
			List<String> contentList = new ArrayList<String>();
			try{
				br = new BufferedReader(new FileReader(path));
				while((line = br.readLine()) != null){
					if(line.startsWith(tb.getName())){
						continue;
					}
					contentList.add(line);
				}
			}catch(IOException e){
				msg = msg+e.getMessage();
				e.printStackTrace();
				return msg;
			}
			finally{
				if(br != null){
					try{
						br.close();
					}catch(IOException e){
						msg = msg+e.getMessage();
						e.printStackTrace();
						return msg;
					}
				}
			}
			msg = updateTaskInFile(contentList,path,false);
			return msg;
		}
		
		public List<String> getCategoryList() {
			File curDir = new File(Constants.PATH);
			File[] filesList = curDir.listFiles();
			List<String> res = new ArrayList<String>();
			for(File f: filesList){
				if(f.getAbsolutePath().contains(".todo")){
					String[] sa = f.getName().split("\\.");
					res.add(sa[0]);
				}
			}
			return res;
		}

		
		public List<TaskBean> getTaskList(String catName, String option) throws Exception {
			String path = Constants.PATH+catName+".todo";
			BufferedReader br = null;
			TaskBean tb = null;
			List<TaskBean> res = new ArrayList<TaskBean>();
			LinkedHashSet<String> hs = new LinkedHashSet<String>();
			String line;
			try{
				SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
				br = new BufferedReader(new FileReader(path));
				while((line = br.readLine())!= null){
					String[] sa = line.split(":");
					
						String[] tags = sa[6].split(",");
						for(String s: tags){
							hs.add(s);
						}
						tb = new TaskBean(sa[0],sa[1],parser.parse(sa[2]),parser.parse(sa[3]),Integer.parseInt(sa[4]),sa[5],hs);
						res.add(tb);
					}	
			}catch(IOException e){
				throw e;
			}
			catch(ParseException e){
				throw e;
			}
			finally{
				if(br != null){
					try{
						br.close();
					}catch(IOException e){
						throw e;
					}
				}
			}
			if(option.equals(Constants.ALPHA)) {
				Collections.sort(res);
				return res;
			}
			else if (option.equals(Constants.DUEDATE)){
				TaskListBasedOnDueDate tld = new TaskListBasedOnDueDate();
				Collections.sort(res,tld);
				return res;
			}
			else if(option.equals(Constants.CREATEDDATE)){
				TaskListBasedOnCreatedDate tld = new TaskListBasedOnCreatedDate();
				Collections.sort(res,tld);
				return res;
			}
			else if(option.equals(Constants.LONGESTTIME)){
				TaskListBasedOnLongestTime tld = new TaskListBasedOnLongestTime();
				Collections.sort(res,tld);
				return res;
			}
			return res;
		}
		public void  deleteCategory(String cat)
		{
			String path = Constants.PATH+cat+".todo";
			File f=new File(path);
			f.delete();
		}
		

}


	

