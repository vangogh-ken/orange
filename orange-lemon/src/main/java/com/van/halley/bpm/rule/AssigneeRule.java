package com.van.halley.bpm.rule;

import java.util.List;

/**
 * 分配任务时,在配置中使用了常用词等获取对应的任务委托人
 * 注意：获取到的任务委托人都以流程发起人为基点,比如“直接上级领导”指流程发起人的直接上级领导
 * 
 * @author Think
 *
 */
public interface AssigneeRule {
    // FIXME: 流程发起人 常用词, 都返回一个list
    List<String> process(String value, String initiator);
}
