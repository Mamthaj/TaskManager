package com.uttara.taskManager;


import java.util.Comparator;

public class TaskListBasedOnCreatedDate implements Comparator<TaskBean> {

	@Override
	public int compare(TaskBean t1, TaskBean t2) {
		if(t1 == null || t2 == null)
			throw new IllegalArgumentException();
		else{
			return t1.getCr_dt().compareTo(t2.getCr_dt());
		}
	}

}
