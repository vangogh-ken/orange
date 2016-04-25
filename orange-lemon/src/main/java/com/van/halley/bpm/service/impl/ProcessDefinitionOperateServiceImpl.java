package com.van.halley.bpm.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.van.halley.bpm.service.ProcessDefinitionOperateService;
import com.van.halley.db.persistence.entity.BpmConfigAuth;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.BpmConfigNotice;
import com.van.halley.db.persistence.entity.BpmConfigOperation;
import com.van.halley.db.persistence.entity.BpmMailTemplate;
import com.van.halley.db.persistence.entity.BpmOperationType;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.BpmConfigAuthService;
import com.van.service.BpmConfigNodeService;
import com.van.service.BpmConfigNoticeService;
import com.van.service.BpmConfigOperationService;
import com.van.service.BpmMailTemplateService;
import com.van.service.BpmOperationTypeService;

@Service("processDefinitionOperateService")
public class ProcessDefinitionOperateServiceImpl implements
		ProcessDefinitionOperateService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private FormService formService;
	@Autowired
	private BpmConfigNodeService bpmConfigNodeService;
	@Autowired
	private BpmConfigNoticeService bpmConfigNoticeService;
	@Autowired
	private BpmMailTemplateService bpmMailTemplateService;
	@Autowired
	private BpmOperationTypeService bpmOperationTypeService;
	@Autowired
	private BpmConfigOperationService bpmConfigOperationService;
	@Autowired
	private BpmConfigAuthService bpmConfigAuthService;

	@Override
	public void convertToModel(String processDefinitionId) {
		try {
			ProcessDefinition processDefinition = repositoryService
					.createProcessDefinitionQuery()
					.processDefinitionId(processDefinitionId).singleResult();
			InputStream bpmnStream = repositoryService.getResourceAsStream(
					processDefinition.getDeploymentId(),
					processDefinition.getResourceName());
			XMLInputFactory xif = XMLInputFactory.newInstance();
			InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
			XMLStreamReader xtr;
			xtr = xif.createXMLStreamReader(in);
			BpmnModel bpmnModel = new BpmnXMLConverter()
					.convertToBpmnModel(xtr);
			BpmnJsonConverter converter = new BpmnJsonConverter();
			ObjectNode modelNode = converter.convertToJson(bpmnModel);
			Model modelData = repositoryService.newModel();
			modelData.setKey(processDefinition.getKey());
			modelData.setName(processDefinition.getResourceName());
			modelData.setCategory(processDefinition.getDeploymentId());

			ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME,
					processDefinition.getName());
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					processDefinition.getDescription());
			modelData.setMetaInfo(modelObjectNode.toString());

			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(), modelNode
					.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
//deploymentId, ProcessDefinitionId都传过来。分别删除对应的信息。
	@Override
	public void delete(String deploymentIdAndProcessDefinitionId) {
		if(deploymentIdAndProcessDefinitionId != null && deploymentIdAndProcessDefinitionId.contains(";")){
			String[] ids = deploymentIdAndProcessDefinitionId.split(";");
			repositoryService.deleteDeployment(ids[0], true);
			bpmConfigNodeService.deleteByPdId(ids[1]);
		}
	}

	@Override
	public boolean deployByFile(MultipartHttpServletRequest request) {
		try {
			Map<String, String> map = FileUtil.upload("muiltFile",
					request);
			String fileName = map.get("fileName");
			String fileData = map.get("fileData");
			FileInputStream fis = new FileInputStream(FileUtil.attachmentPath + "/" + fileData);
			if (fileName.endsWith(".xml")) {
				DeploymentBuilder deploymentBuilder = repositoryService
						.createDeployment();
				deploymentBuilder.addInputStream(fileName, fis);
				Deployment deployment = deploymentBuilder.deploy();
				
				SyncNodeConfig(deployment.getId());
			} else if (fileName.endsWith(".zip")) {
				ZipInputStream zis = new ZipInputStream(fis);
				DeploymentBuilder deploymentBuilder = repositoryService
						.createDeployment();
				deploymentBuilder.addZipInputStream(zis);
				Deployment deployment = deploymentBuilder.deploy();
				
				SyncNodeConfig(deployment.getId());
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Object> exportXMLOrPNGById(String id, String resourceType) {
		Map<String, Object> map = new HashMap<String, Object>();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(id)
				.singleResult();
		String fileName = "";
		if (resourceType.equals("PNG")) {
			fileName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equals("XML")) {
			fileName = processDefinition.getResourceName();
		}
		InputStream inputStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), fileName);
		map.put("inputStream", inputStream);
		map.put("fileName", fileName);

		return map;
	}

	@Override
	public Map<String, Object> getResource(String processKey, String resourceType) {
		Map<String, Object> map = new HashMap<String, Object>();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionKey(processKey)
				.latestVersion().singleResult();
		String fileName = "";
		if (resourceType.equalsIgnoreCase("PNG")) {
			fileName = processDefinition.getDiagramResourceName();
		} else if (resourceType.equalsIgnoreCase("XML")) {
			fileName = processDefinition.getResourceName();
		}
		InputStream inputStream = repositoryService.getResourceAsStream(
				processDefinition.getDeploymentId(), fileName);
		map.put("inputStream", inputStream);
		map.put("fileName", fileName);

		return map;
	}

	/**流程部署的时候将node信息更新**/
	@Override
	public void SyncNodeConfig(String deploymentId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
		ProcessDefinitionEntity pd = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinition.getId());
		
		//获取旧版本的配置信息
		List<BpmConfigNode> secondVersionBpmNodes = new ArrayList<BpmConfigNode>();
		if(processDefinition.getVersion() > 1){
			secondVersionBpmNodes = getSecondVersionBpmNodes(processDefinition.getKey(), processDefinition.getVersion());
		}
		
		Map<String, TaskDefinition> map = pd.getTaskDefinitions();
		
		String processDefinitionId = pd.getId();
		String processDefinitionName = pd.getName();
		String processDefinitionKey = pd.getKey();
		
		Collection<TaskDefinition> taskDefinitions = map.values();
		for(TaskDefinition taskDefinition  : taskDefinitions){
			String taskDefinitionKey = taskDefinition.getKey();
			String taskDefinitionName = taskDefinition.getNameExpression().getExpressionText();
			BpmConfigNode bpmConfigNode = new BpmConfigNode();
			bpmConfigNode.setId(StringUtil.getUUID());//先给定一个ID，为设置权限
			bpmConfigNode.setPdId(processDefinitionId);
			bpmConfigNode.setPdName(processDefinitionName);
			bpmConfigNode.setPdKey(processDefinitionKey);
			bpmConfigNode.setTdKey(taskDefinitionKey);
			bpmConfigNode.setTdName(taskDefinitionName);
			
			if(!secondVersionBpmNodes.isEmpty()){
				boolean isContains = false;
				for(BpmConfigNode node : secondVersionBpmNodes){
					if(node.getTdKey().equals(taskDefinitionKey)){
						bpmConfigNode.setSourceStatus(node.getSourceStatus());
						bpmConfigNode.setTargetStatus(node.getTargetStatus());
						bpmConfigNode.setTdListners(node.getTdListners());
						bpmConfigNode.setOnCreate(node.getOnCreate());
						bpmConfigNode.setOnComplete(node.getOnComplete());
						//设置状态
						bpmConfigNode.setBasisStatus(node.getBasisStatus());
						//设置权限
						BpmConfigAuth filterAuth = new BpmConfigAuth();
						filterAuth.setBpmNodeId(node.getId());
						List<BpmConfigAuth> bpmConfigAuthes = bpmConfigAuthService.queryForList(filterAuth);
						if(bpmConfigAuthes != null && !bpmConfigAuthes.isEmpty()){
							for(BpmConfigAuth bpmConfigAuth : bpmConfigAuthes){
								bpmConfigAuth.setId(StringUtil.getUUID());
								bpmConfigAuth.setBpmNodeId(bpmConfigNode.getId());
								bpmConfigAuth.setCreateTime(new Date());
								bpmConfigAuth.setModifyTime(new Date());
								bpmConfigAuthService.add(bpmConfigAuth);
							}
						}
						
						isContains = true;
						break;
					}
				}
				
				if(!isContains){
					bpmConfigNode.setSourceStatus(taskDefinitionName);
					bpmConfigNode.setTargetStatus("完成");
				}
			}else{
				/**初始化的时候设置**/
				//暂且将任务名设置为状态。
				bpmConfigNode.setSourceStatus(taskDefinitionName);
				bpmConfigNode.setTargetStatus("完成");
			}
			/**保存数据**/
			bpmConfigNodeService.add(bpmConfigNode);
			
			/**此处添加提醒邮件信息**/
			//BpmConfigNode newBpmConfigNode = bpmConfigNodeService.getByPdIdAndTdKey(processDefinitionId, taskDefinitionKey);
			BpmConfigNotice bpmConfigNotice = new BpmConfigNotice();
			bpmConfigNotice.setBpmConfigNode(bpmConfigNode);
			//bpmConfigNotice.setBpmConfigNode(newBpmConfigNode);
			
			List<BpmMailTemplate> templates = bpmMailTemplateService.getAll();
			if(templates != null && !templates.isEmpty()){
				for(BpmMailTemplate template: templates){
					if(template.getTemplateName().equals("任务到达(任务委托人)")){
						bpmConfigNotice.setType("TYPE_ARRIVAL");
						bpmConfigNotice.setReceiver("任务委托人");
						bpmConfigNotice.setBpmMailTemplate(template);
					
					}else if(template.getTemplateName().equals("任务到达(流程发起人)")){
						bpmConfigNotice.setType("TYPE_ARRIVAL");
						bpmConfigNotice.setReceiver("流程发起人");
						bpmConfigNotice.setBpmMailTemplate(template);
					
					}else if(template.getTemplateName().equals("任务完成")){
						bpmConfigNotice.setType("TYPE_COMPLETE");
						bpmConfigNotice.setReceiver("流程发起人");
						bpmConfigNotice.setBpmMailTemplate(template);
					
					}else if(template.getTemplateName().equals("任务超时提醒")){
						bpmConfigNotice.setType("TYPE_TIMEOUT");
						bpmConfigNotice.setReceiver("任务委托人");
						bpmConfigNotice.setBpmMailTemplate(template);
					}
					
					bpmConfigNoticeService.add(bpmConfigNotice);
				}
			}
		}
		//初始化每个节点的可用操作信息
		BpmConfigNode filter = new BpmConfigNode();
		filter.setPdId(processDefinitionId);
		List<BpmConfigNode> nodes = bpmConfigNodeService.queryForList(filter);
		for(BpmConfigNode node : nodes){
			List<BpmOperationType> opertaionTypes = bpmOperationTypeService.getAll();
			for(BpmOperationType type : opertaionTypes){
				BpmConfigOperation bpmConfigOperation = new BpmConfigOperation();
				bpmConfigOperation.setBpmConfigNode(node);
				bpmConfigOperation.setBpmOperationType(type);
				List<BpmConfigOperation> list = bpmConfigOperationService.queryForList(bpmConfigOperation);
				if(list == null || list.isEmpty()){
					if(bpmConfigOperation.getBpmOperationType().getName().equals("处理")){
						bpmConfigOperation.setEnable("T");
					}else{
						bpmConfigOperation.setEnable("F");
					}
					
					bpmConfigOperationService.add(bpmConfigOperation);
				}
			}
		}
	}
	@Override
	public StartFormDataImpl startInnerForm(String processDefinitionId) {
		StartFormDataImpl startFormData = (StartFormDataImpl) formService
				.getStartFormData(processDefinitionId);
		startFormData.setProcessDefinition(null);
		return startFormData;
	}
	@Override
	public Object startExternalForm(String processDefinitionId) {
		Object renderedTaskForm = formService
				.getRenderedStartForm(processDefinitionId);
		return renderedTaskForm;
	}
	@Override
	public void updateToActive(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().
				processDefinitionId(processDefinitionId).singleResult();
		if(processDefinition != null){
			repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
		}
		
	}
	@Override
	public void updateToSuspend(String processDefinitionId) {
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().
				processDefinitionId(processDefinitionId).singleResult();
		if(processDefinition != null){
			repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
		}
		
	}
	
	@Override
	public List<BpmConfigNode> getSecondVersionBpmNodes(String processDefinitionKey, int lastVersion) {
		if(lastVersion <= 1){
			throw new IllegalAccessError("This process definition just only 1 version");
		}
		
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processDefinitionKey).list();
		for(ProcessDefinition pd : processDefinitions){
			if(pd.getVersion() + 1 == lastVersion){
				String processDefinitionId = pd.getId();
				BpmConfigNode filter = new BpmConfigNode();
				filter.setPdId(processDefinitionId);
				return bpmConfigNodeService.queryForList(filter);
			}
		}
		
		return null;
	}

}
