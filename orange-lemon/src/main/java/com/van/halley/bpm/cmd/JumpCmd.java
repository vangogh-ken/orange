package com.van.halley.bpm.cmd;

import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.core.spring.ApplicationContextHelper;

/**
 * @author Think
 * 手动干预或修正流程
 */
public class JumpCmd implements Command<Object> {
    private String activityId;
    private String executionId;
    private String jumpOrigin;

    public JumpCmd(String executionId, String activityId) {
        this(executionId, activityId, "跳过");
    }

    public JumpCmd(String executionId, String activityId, String jumpOrigin) {
        this.activityId = activityId;
        this.executionId = executionId;
        this.jumpOrigin = jumpOrigin;
    }

    /* 
     * 1. 找到正在执行的活动节点,并删除. 2. 获取执行对象执行指定的活动节点
     */
    public Object execute(CommandContext commandContext) {
    	ExecutionEntity executionEntity = commandContext
                .getExecutionEntityManager().findExecutionById(executionId);
    	
        for (TaskEntity taskEntity : commandContext.getTaskEntityManager()
                .findTasksByExecutionId(executionId)) {
            taskEntity.setVariableLocal("跳转原因", jumpOrigin);
           
            commandContext.getTaskEntityManager().deleteTask(taskEntity,
                    jumpOrigin, false);
        }
        
        String sql = "UPDATE ACT_HI_ACTINST SET END_TIME_ = (SELECT SYSDATE()) WHERE PROC_INST_ID_=? AND END_TIME_ IS NULL";
        JdbcTemplate jdbcTemplate = ApplicationContextHelper.getBean(JdbcTemplate.class);
        jdbcTemplate.update(sql, executionEntity.getProcessInstanceId());
        
        ProcessDefinitionImpl processDefinition = executionEntity
                .getProcessDefinition();
        ActivityImpl activity = processDefinition.findActivity(activityId);

        executionEntity.executeActivity(activity);

        return null;
    }
}
