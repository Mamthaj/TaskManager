package com.uttara.taskManager;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;

public class TaskBean implements Comparable<TaskBean>{
	
	private String name;
	private String desc;
	private Date cr_dt;
	private Date end_dt;
	private int priority;
	private String status;
	private LinkedHashSet<String> tags = new LinkedHashSet<String>();
	
	public TaskBean(){
		
	}
	
	public TaskBean(String name, String desc, Date cr_dt, Date end_dt, int priority, String status, LinkedHashSet<String> tags) {
		super();
		this.name = name;
		this.desc = desc;
		this.cr_dt = cr_dt;
		this.end_dt = end_dt;
		this.priority = priority;
		this.status = status;
		this.tags = tags;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getCr_dt() {
		return cr_dt;
	}
	public void setCr_dt(Date cr_dt) {
		this.cr_dt = cr_dt;
	}
	public Date getEnd_dt() {
		return end_dt;
	}
	public void setEnd_dt(Date end_dt) {
		this.end_dt = end_dt;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LinkedHashSet<String> getTags() {
		return tags;
	}
	public void setTags(LinkedHashSet<String> tags) {
		if(tags != null && tags.size() > 0){
			for(String s:tags)
				this.tags.add(s);
		}
	}

	@Override
	public int hashCode() {
		return (name+desc+cr_dt+end_dt+status+tags.toString()+status+priority).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			throw new IllegalArgumentException();
		else{
		     if(obj  instanceof TaskBean)
		     {
		    	 TaskBean tb = (TaskBean)obj;
		    	 if(this.name.equals(tb.name) && this.desc.equals(tb.desc) && this.cr_dt.equals(tb.cr_dt) && this.end_dt.equals(tb.end_dt) && this.priority == tb.priority && this.status.equals(tb.status) && this.tags.equals(tb.tags))
		    		 return true;
		    	 else 
		    		 return false;
		     }
		     else
		    	 return false;
		}
	}

	@Override
	public int compareTo(TaskBean obj) {
		if(obj == null)
			throw new IllegalArgumentException();
		else if(!(obj instanceof TaskBean))
			throw new IllegalArgumentException();
		else{
			TaskBean tb = (TaskBean)obj;
			return this.name.compareTo(tb.name);
		}
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "Task [Name=" + name + ", Description=" + desc + ", Creation Date=" + sdf.format(cr_dt) + ", Due Date=" + sdf.format(end_dt) + ", Priority="
				+ priority + ", Status=" + status + ", Tags=" + tags.toString() + "]";
	}

	
	public String validate(){
		String msg = "";
		if(name == null || name.trim().equals(""))
			msg = "Task name should not be null or empty";
		if(desc == null || desc.trim().equals(""))
			msg = "Task description should not be null or empty";
		if(end_dt.compareTo(cr_dt) < 0)
			msg = "Task due date should be after task creation date";
		if(!(status.equals(Constants.NEWTASK) || status.equals(Constants.COMPLETED) || status.equals(Constants.PENDING)))
			msg = "Task status can be either New or Pending or Completed";
		if(msg == "")
			msg = Constants.SUCCESS;
		return msg;
	}
	
	
}

