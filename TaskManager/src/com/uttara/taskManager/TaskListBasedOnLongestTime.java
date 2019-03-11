package com.uttara.taskManager;
import java.util.Comparator;


public class TaskListBasedOnLongestTime implements Comparator<TaskBean>{
		@Override
		public int compare(TaskBean t1, TaskBean t2) {
			if(t1 == null || t2 == null)
				throw new IllegalArgumentException();
			else{
				int a=LongestTime(t1);
				int b=LongestTime(t2);
				return a-b;
			}
		}
		public int LongestTime(TaskBean t) {
			long diff=t.getEnd_dt().getTime()-t.getCr_dt().getTime();
			return (int)(diff/(1000));
		}

		
}


