
package com.uttara.taskManager;

import java.util.Comparator;

public class TaskListBasedOnDueDate implements Comparator<TaskBean> {


	@Override
	public int compare(TaskBean t1, TaskBean t2) {
		if(t1 == null || t2 == null)
			throw new IllegalArgumentException();
		else{
			return t1.getEnd_dt().compareTo(t2.getEnd_dt());
		}
	}

}
