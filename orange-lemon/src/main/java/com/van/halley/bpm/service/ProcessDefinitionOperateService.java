package com.van.halley.bpm.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.form.StartFormDataImpl;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.van.halley.db.persistence.entity.BpmConfigNode;

public interface ProcessDefinitionOperateService {
	/**
	 * 转换成流程模型
	 */
	public void convertToModel(String processDefinitionId);

	/**
	 * 删除, 参数包含流程定义ID, 流程部署ID
	 */
	public void delete(String deploymentIdAndProcessDefinitionId);

	/**
	 * 以上传文件的方式进行部署
	 */
	public boolean deployByFile(MultipartHttpServletRequest request);

	/**
	 * 通过流程定义ID获取资源数据
	 */
	public Map<String, Object> exportXMLOrPNGById(String id, String resourceType);
	/**
	 * 通过流程定义KEY获取资源数据
	 */
	public Map<String, Object> getResource(String processKey, String resourceType);
	
	/**
	 * 将新部署的流程信息存入到库中,如果之前有旧版本的信息,则将旧版本的配置信息添加
	 */
	public void SyncNodeConfig(String deploymentId);
	
	/**
	 * 获取第二新版本的流程节点配置信息
	 */
	public List<BpmConfigNode> getSecondVersionBpmNodes(String processDefinitionKey, int lastVersion);
	
	public StartFormDataImpl startInnerForm(String processDefinitionId);
	
	public Object startExternalForm(String processDefinitionId);
	
	public void updateToActive(String processDefinitionId);
	public void updateToSuspend(String processDefinitionId);
}
