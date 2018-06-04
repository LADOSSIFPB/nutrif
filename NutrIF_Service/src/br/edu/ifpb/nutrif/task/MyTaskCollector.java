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
		SchedulingPattern pattern = new SchedulingPattern("* * * * *");
		Task task = new MyTask();
		TaskTable ret = new TaskTable();
		ret.add(pattern, task);
		return ret;
	}
}