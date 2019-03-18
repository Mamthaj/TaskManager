
package com.uttara.taskManager;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Scanner;


public class StartApp {
	
	

	@SuppressWarnings("resource")
	public static void displayTaskMenu(String categoryName){
		Scanner sc3 = new Scanner(System.in);
		Scanner sc4 = new Scanner(System.in);
		int inp2 = 0;
		int inp3 = 0,inp4 =0,listOpt = 0;
		String taskName;
		String taskDesc;
		Date taskCr_Dt;
		String dueDate;
		Date taskEnd_Dt;
		int taskPriority;
		String taskStatus;
		String tag;
		String[] taskTags;
		String msg = "";
		LinkedHashSet<String> tagList = new LinkedHashSet<String>();
		SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy");
		TaskModel tm = new TaskModel();
		try{
		while(inp2 != 6){
        	System.out.println("Press 1 to add a task");
        	System.out.println("Press 2 to edit a task");
        	System.out.println("Press 3 to remove a task");
        	System.out.println("Press 4 to list the tasks");
        	System.out.println("Press 5 to search a task");
        	System.out.println("Press 6 to go back");
        	while(!sc3.hasNextInt()){
				System.out.println("Enter valid int value between 1 to 6");
				sc3.next();
			}
			inp2 =  sc3.nextInt();
        	switch(inp2){
        	case 1: System.out.println("Enter task name to be added to category "+categoryName);
        			taskName = sc4.nextLine();
        			while(tm.doesTaskExist(taskName, categoryName)){
        				System.out.println("Task already exists, please enter another task");
        				taskName = sc4.nextLine();
        			}
        			if(TaskUtil.taskNameValidation(taskName).equals(Constants.SUCCESS)){
		        			System.out.println("Enter task description for "+taskName);
		        			taskDesc = sc4.nextLine();
		        			taskCr_Dt = parser.parse(TaskUtil.getCurrentDate());
		        			System.out.println("Enter task due date in the format dd/MM/yyyy");
		        			dueDate = sc4.nextLine();
		        			taskEnd_Dt = parser.parse(dueDate);
		        			System.out.println("Enter task priority, 0 to 10 where 0 indicates low priority and 10 indicated high priority");
		        			while(!sc3.hasNextInt()){
		        				System.out.println("Enter valid task priority,it should be between 0 to 10");
		        				sc3.next();
		        			}
		        			taskPriority = sc3.nextInt();
		        			while(!(TaskUtil.validateTaskPriority(taskPriority))){
		        				System.out.println("Enter valid task priority,it should be between 0 to 10");
		        				taskPriority = sc3.nextInt();
		        			}
		        			taskStatus = Constants.NEWTASK;
		        			System.out.println("Enter tags for "+taskName+" task as comma separated words");
		        			tag = sc4.nextLine();
		        			taskTags = tag.split(",");
		        			for(String s:taskTags){
		        				tagList.add(s);
		        			}
		        			TaskBean tb = new TaskBean(taskName,taskDesc,taskCr_Dt,taskEnd_Dt,taskPriority,taskStatus,tagList);
		        			msg = tm.addTask(tb, categoryName);
		        			if(msg.equals(Constants.SUCCESS))
							{
								System.out.println(taskName+" task added successfully to "+categoryName);
							}
							else
								System.out.println(taskName+" could not get added successfully!! "+msg);
        			}
        			else{
        				System.out.println("Task name should not be null or blank, try again");
        			}
        			break;
        			
        	case 2: System.out.println("Enter task that you want to edit");
			taskName = sc4.nextLine();
			if(tm.doesTaskExist(taskName, categoryName)){
				TaskBean currTB = tm.getTask(taskName, categoryName);
				System.out.println(currTB);
				TaskBean newTB = new TaskBean(currTB.getName(),currTB.getDesc(),currTB.getCr_dt(),currTB.getEnd_dt(),currTB.getPriority(),currTB.getStatus(),currTB.getTags());
				inp3 = 0;
				while( inp3 != 7){
				
				System.out.println("Press 1 to edit task name");
				System.out.println("Press 2 to edit task description");
				System.out.println("Press 3 to edit task due date");
				System.out.println("Press 4 to edit priority");
				System.out.println("Press 5 to edit status");
				System.out.println("Press 6 to edit tags");
				System.out.println("Press 7 to update task and go back to previous menu");
				while(!sc3.hasNextInt()){
					System.out.println("Enter valid int value between 1 to 6");
					sc3.next();
				}
				inp3 =  sc3.nextInt();
				switch(inp3){
					case 1:System.out.println("Current task name is "+currTB.getName());
						   System.out.println("Enter new task name");
						   String tempName = sc4.nextLine();
						   while(tm.doesTaskExist(tempName, categoryName)){
							   System.out.println("Task already exists, enter another one");
							   tempName = sc4.nextLine();
						   }
						   newTB.setName(tempName);
						   break;
						   
					case 2: 	
							System.out.println("Current task's description is "+currTB.getDesc());
							System.out.println("Enter new description");
							newTB.setDesc(sc4.nextLine());
							break;
					case 3:
							System.out.println("Current task's due date is "+parser.format(currTB.getEnd_dt()));
							System.out.println("Enter task's new due date in dd/MM/yyyy format");
						
							newTB.setEnd_dt(parser.parse(sc4.nextLine()));
							break;
					case 4: 
							System.out.println("Current task's priority is "+currTB.getPriority());
							System.out.println("Enter task's new priority");
							newTB.setPriority(sc3.nextInt());
							break;
					case 5: 
							System.out.println("Current task's status is "+currTB.getStatus());
							System.out.println("Enter task's status, it can be either Pending or Completed");
							newTB.setStatus(sc4.nextLine());
							break;
					case 6: 
							System.out.println("Current task's tags: "+currTB.getTags().toString());
							System.out.println("Press 1 to edit a tag");
							System.out.println("Press 2 to delete a tag");
							while(!sc3.hasNextInt()){
								System.out.println("Enter valid int value between 1 to 6");
								sc3.next();
							}
							inp4 =  sc3.nextInt();
							String  oldTag;
							if(inp4 == 1){
								System.out.println("Enter a tag which you need to edit");
								oldTag = sc4.nextLine();
								while(!(newTB.getTags().remove(oldTag))){
									System.out.println(oldTag+" doesn't exist, enter existing tag which you need to edit");
								    oldTag = sc4.nextLine();
								}
								System.out.println("Enter new tag");
								String newTag = sc4.nextLine();
								newTB.getTags().add(newTag);	
							}
							else if(inp4 == 2)
							{
								System.out.println("Enter a tag that you want to remove");
								oldTag = sc4.nextLine();
								while(!(newTB.getTags().remove(oldTag)))
								{
									System.out.println(oldTag+" doesn't exist, enter existing tag which you need to edit");
								    oldTag = sc4.nextLine();
								}
							}
							break;
					case 7:  System.out.println(newTB);
					         System.out.println(currTB);
							 msg = tm.modifyTask(newTB, currTB, categoryName);
							 if(msg.equals(Constants.SUCCESS))
								 System.out.println("Task modified successfully!!");
							 else
								 System.out.println(msg);
							 break;
					default: System.out.println("Enter valid input please");
					         break;
				}
			}
		}
			else
				System.out.println(taskName+" doesn't exist in "+categoryName);
			break;
        	case 3:
        		
			        	System.out.println("Enter a task name which you want to delete");
			 	        taskName = sc4.nextLine();
			 	        int inp5 = 0;
			 	        while(!(tm.doesTaskExist(taskName, categoryName) || inp5 != 0)){
			 	        	System.out.println("You can enter only existing task or press 1 to abort");
			 	        	taskName = sc4.nextLine();
			 	        	if(taskName.equalsIgnoreCase("1"))
									inp5 = 1;
			 	        }
			 	        if(inp5 == 0){
			 	        	TaskBean delTB = tm.getTask(taskName, categoryName);
			 	        	msg = tm.deleteTask(delTB, categoryName);
			 	        
			 	        if(msg.equals(Constants.SUCCESS))
			 	        	 System.out.println("Task deleted successfully!!");
							else
								 System.out.println(msg);
			 	        }
			 	        break;
        	case 4:
		        		while(listOpt != 5){
		    		        List<TaskBean> taskList;
		    				System.out.println("Press 1 to list tasks based on alphabetical order of task name");
		    				System.out.println("Press 2 to list tasks based on due date of task");
		    				System.out.println("Press 3 to list tasks based on created date of task");
		    				System.out.println("Press 4 to list tasks based on longest time taking tasks");
		    				System.out.println("Press 5 to go back");
		    				listOpt = sc3.nextInt();
		    				switch(listOpt){
		    				case 1:taskList = tm.getTaskList(categoryName, Constants.ALPHA);
		    				       for(TaskBean t:taskList){
		    				    	   System.out.println(t);
		    				       }
		    				       break;
		    				case 2:taskList = tm.getTaskList(categoryName, Constants.DUEDATE);
			 				       for(TaskBean t:taskList){
							    	   System.out.println(t);
							       }
			 				       break;
		    				case 3:taskList = tm.getTaskList(categoryName, Constants.CREATEDDATE);
			 				       for(TaskBean t:taskList){
							    	   System.out.println(t);
							       }
			 				       break;
		    				case 4:taskList = tm.getTaskList(categoryName, Constants.LONGESTTIME);
			 				       for(TaskBean t:taskList){
							    	   System.out.println(t);
							       }
			 				       break;
		    				case 5:break;
		    				default:System.out.println("Enter valid option please!!");
		    				}
		    				
		    			}
		    			listOpt = 0;
		    		    break;
        		
        	case 5: 
				        	System.out.println("Enter keyword which you want to search in "+categoryName);
							String input = "";
					        input = sc4.nextLine();
					        TaskBean foundTb = tm.getTask(input, categoryName);
					        if( foundTb != null){
					        	System.out.println("Task "+input+" found in "+categoryName);
					        	System.out.println(foundTb);
					        }
					        else
					        	System.out.println("Task "+input+" not found in "+categoryName);
						    break;
        	case 6:
        					break;
	        default:		
	        				System.out.println("Enter valid input");
	        				break;		
	        	}
			}
	}	catch(Throwable t){
							System.out.println(t.getMessage());
					}				
		
}

	public static void main(String[] args)
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		try{
			int inp = 0;
			String catName = null;
			TaskModel tm = new TaskModel();
			String msg = "";
			Logger.getInstance().log("Main starting...");
			while(inp != 6){
				System.out.println("Enter 1 to create category");
				System.out.println("Enter 2 to load category");
				System.out.println("Enter 3 to delete category");
				System.out.println("Enter 4 to list category");
				System.out.println("Enter 5 to search category");
				System.out.println("Enter 6 to exit");
				while(!sc1.hasNextInt()){
					System.out.println("Enter valid int value between 1 to 6");
					sc1.next();
				}
				inp =  sc1.nextInt();
				switch(inp){
					case 1: 
							System.out.println("Enter category name");
							catName = sc2.nextLine();
							
							while(!(msg = TaskUtil.catNameValidate(catName)).equals(Constants.SUCCESS)){
								System.out.println(msg);
								System.out.println("Enter valid category name which should start with a letter and should be alphanumeric with no space & special character");
								catName = sc2.nextLine();
								 
							}
							if(!tm.doesCatExists(catName)){
								msg = tm.createCategory(catName);
								if(msg.equals(Constants.SUCCESS)){
										System.out.println("Category created successfully");
										displayTaskMenu(catName);
									}
								else
									System.out.println(msg);
							}
							else
								System.out.println("Category already exists");
								
							break;
					case 2:
						 	System.out.println("Enter category name which needs to be loaded");
					        catName = sc2.nextLine();
					        int fail = 0;
					        if(!(tm. doesCatExists(catName))){
					        	System.out.println("Sorry!!, "+catName+" doesn't exist");
					        	fail = 1;
					        	
					        }
					        if(fail == 1)
					        	break;
					        else{
						    System.out.println(catName+" loaded successfully");
						    StartApp.displayTaskMenu(catName);
					        }
					        break;
					case 3:
							System.out.println("Enter category name which needs to be deleted");
					        catName = sc2.nextLine();
					        int fail1 = 0;
					        if(!(tm. doesCatExists(catName))){
					        	System.out.println("Sorry!!, "+catName+" doesn't exist");
					        	fail1= 1;	
					        }
					        if(fail1 == 1)
					        break;
					        else{
					        	tm.deleteCategory(catName);
					        	System.out.println("Category deleted successffully");
						    }
					        break;
					case 4:
							System.out.println("List of categories");
					        List<String> catList = new ArrayList<String>();
					        catList = tm.getCategoryList();
					        int i = 1;
					        if(catList.size() > 0){
						        for(String s:catList){
						        	System.out.println(i+"."+s);
						        	i++;
						        }
					        }
					        else
					        	System.out.println("No category has been created yet");
							break;
					case 5:
							System.out.println("Enter category name which you want to search");
					        catName = sc2.nextLine();
					        if(tm.doesCatExists(catName))
					        	System.out.println("Category "+catName+" exists!!");
					        else
					        	System.out.println("Category "+catName+" doesn't exists!!");
						    break;
					case 6:
							System.exit(0);
							
					default: 
							System.out.println("Enter valid input");
							break;
				}
				
			}
			
		}catch(Throwable t){
			System.out.println("Exception "+t.getMessage());
		}
		finally
		{
			sc1.close();
			sc2.close();
		}

	}

}
