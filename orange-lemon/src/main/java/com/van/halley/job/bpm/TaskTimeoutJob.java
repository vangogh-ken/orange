package com.van.halley.job.bpm;

import java.util.List;

import org.activiti.engine.ManagementService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.van.halley.bpm.cmd.SendNoticeCmd;

public class TaskTimeoutJob {
    private static Logger LOG = LoggerFactory.getLogger(TaskTimeoutJob.class);
    @Autowired
    private ManagementService managementService;
    @Autowired
    private TaskService taskService;

    @Scheduled(cron="0 0 9 * * ?")
    public void execute() throws Exception {
        List<Task> tasks = taskService.createTaskQuery().list();

        for (Task task : tasks) {
            if (task.getDueDate() != null) {
                SendNoticeCmd sendNoticeCmd = new SendNoticeCmd(task.getId());
                managementService.executeCommand(sendNoticeCmd);
            }
        }
    }

}
