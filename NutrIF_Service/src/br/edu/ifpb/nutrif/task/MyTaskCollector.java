package br.edu.ifpb.nutrif.task;
import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskTable;

/**
 * The custom TaskCollector used to retrieve the task list. This sample
 * implementation returns always the same task that the scheduler executes once
 * a minute.
 */
public class MyTaskCollector implements TaskCollector {

	public TaskTable getTasks() {
		/*
		 * Padrão:
		 * Minutes sub-pattern - 
		 * Hours sub-pattern - 
		 * Days of month sub-pattern - 
		 * Months sub-pattern - 
		 * Days of week sub-pattern
		 */
		// 59 23 * * 1,2,3,4,5 ou 3 * * * *
		SchedulingPattern pattern = new SchedulingPattern("59 23 * * 1,2,3,4,5");
		Task myTask = new ExtratoRefeicaoTask();
		TaskTable ret = new TaskTable();
		ret.add(pattern, myTask);
		return ret;
	}
}