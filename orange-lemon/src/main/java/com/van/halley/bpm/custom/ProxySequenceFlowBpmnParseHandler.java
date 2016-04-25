package com.van.halley.bpm.custom;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.SequenceFlowParseHandler;
import org.activiti.engine.impl.el.UelExpressionCondition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ScopeImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.parse.BpmnParseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Think
 * 解析器应该用于添加监听器, 增加condition表达式
 */
public class ProxySequenceFlowBpmnParseHandler implements BpmnParseHandler {
    private static Logger logger = LoggerFactory
            .getLogger(ProxySequenceFlowBpmnParseHandler.class);
    //使用delegate expression
    private String executionListenerId;
    //是否启用默认解析器
    private boolean useDefaultSequenceFlowParser;

    @Override
    public void parse(BpmnParse bpmnParse, BaseElement baseElement) {
        if (!(baseElement instanceof SequenceFlow)) {
            return;
        }

        if (useDefaultSequenceFlowParser) {
            new SequenceFlowParseHandler().parse(bpmnParse, baseElement);
        }

        SequenceFlow sequenceFlow = (SequenceFlow) baseElement;
        logger.info("sequenceFlow : {}", sequenceFlow);
        
        ScopeImpl scope = bpmnParse.getCurrentScope();
        ActivityImpl sourceActivity = scope.findActivity(sequenceFlow.getSourceRef());
        TransitionImpl transition = sourceActivity.findOutgoingTransition(sequenceFlow.getId());
        
        Object conditionText = transition.getProperty(SequenceFlowParseHandler.PROPERTYNAME_CONDITION_TEXT);
        if(conditionText == null){
        	Condition expressionCondition = new UelExpressionCondition(bpmnParse.getExpressionManager().createExpression("${throughUserTasks.contains('" + transition.getDestination().getId()+ "')}"));
            transition.setProperty(SequenceFlowParseHandler.PROPERTYNAME_CONDITION_TEXT, "${throughUserTasks.contains('" + transition.getDestination().getId()+ "')}");
            transition.setProperty(SequenceFlowParseHandler.PROPERTYNAME_CONDITION, expressionCondition);
        }

        this.configEvent(transition, bpmnParse, ExecutionListener.EVENTNAME_START);
        this.configEvent(transition, bpmnParse, ExecutionListener.EVENTNAME_END);
        this.configEvent(transition, bpmnParse, ExecutionListener.EVENTNAME_TAKE);
    }
    
    public void configEvent(TransitionImpl transition, BpmnParse bpmnParse,
            String eventName) {
        ActivitiListener activitiListener = new ActivitiListener();
        activitiListener.setEvent(eventName);
        activitiListener.setImplementationType(ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
        activitiListener.setImplementation("${" + executionListenerId + "}");
        
        //ActivitiListener 需要被转换成 ExecutionListener, 此处请参见默认解析器中的代码
        ExecutionListener executionListener = null;
        if (ImplementationType.IMPLEMENTATION_TYPE_CLASS.equalsIgnoreCase(activitiListener.getImplementationType())) {
            executionListener = bpmnParse.getListenerFactory().createClassDelegateExecutionListener(activitiListener);  
          } else if (ImplementationType.IMPLEMENTATION_TYPE_EXPRESSION.equalsIgnoreCase(activitiListener.getImplementationType())) {
            executionListener = bpmnParse.getListenerFactory().createExpressionExecutionListener(activitiListener);
          } else if (ImplementationType.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION.equalsIgnoreCase(activitiListener.getImplementationType())) {
            executionListener = bpmnParse.getListenerFactory().createDelegateExpressionExecutionListener(activitiListener);
          }
        transition.addExecutionListener(executionListener);
    }

    @Override
    public Collection<Class<? extends BaseElement>> getHandledTypes() {
        List types = Collections.singletonList(SequenceFlow.class);
        return types;
    }


    public void setUseDefaultSequenceFlowParser(boolean useDefaultSequenceFlowParser) {
        this.useDefaultSequenceFlowParser = useDefaultSequenceFlowParser;
    }

	public String getExecutionListenerId() {
		return executionListenerId;
	}

	public void setExecutionListenerId(String executionListenerId) {
		this.executionListenerId = executionListenerId;
	}

	
}
