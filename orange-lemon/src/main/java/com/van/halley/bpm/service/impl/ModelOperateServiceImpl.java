package com.van.halley.bpm.service.impl;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
//import org.codehaus.jackson.JsonNode;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.van.halley.bpm.service.ModelOperateService;
import com.van.halley.bpm.service.ProcessDefinitionOperateService;

@Service("modelOperateService")
public class ModelOperateServiceImpl implements ModelOperateService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ProcessDefinitionOperateService processDefinitionOperateService;

	@Override
	public String create(String name, String key, String description) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace",
					"http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			Model modelData = repositoryService.newModel();

			ObjectNode modelObjectNode = objectMapper.createObjectNode();
			modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
			description = StringUtils.defaultString(description);
			modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,
					description);
			modelData.setMetaInfo(modelObjectNode.toString());
			modelData.setName(name);
			modelData.setKey(StringUtils.defaultString(key));

			// 保存模型
			repositoryService.saveModel(modelData);
			repositoryService.addModelEditorSource(modelData.getId(),
					editorNode.toString().getBytes("UTF-8"));
			return modelData.getId();
		} catch (UnsupportedEncodingException e) {
			// 创建失败
			e.printStackTrace();
			return "";
		}
	}

	@Override
	public boolean deploy(String id) {
		try {
			Model modelData = repositoryService.getModel(id);
			ObjectNode modelNode = (ObjectNode) new ObjectMapper()
					.readTree(repositoryService.getModelEditorSource(modelData
							.getId()));
			byte[] bpmnBytes = null;

			BpmnModel model = new BpmnJsonConverter()
					.convertToBpmnModel(modelNode);
			bpmnBytes = new BpmnXMLConverter().convertToXML(model);

			String processName = modelData.getName() + ".bpmn20.xml";
			Deployment deployment = repositoryService.createDeployment()
					.name(modelData.getName())
					.addString(processName, new String(bpmnBytes)).deploy();
			if (deployment != null) {
				processDefinitionOperateService.SyncNodeConfig(deployment.getId());
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Object> export(String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Model modelData = repositoryService.getModel(id);
			BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
			JsonNode editorNode = new ObjectMapper().readTree(repositoryService
					.getModelEditorSource(modelData.getId()));
			BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
			BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
			byte[] bpmnBytes = xmlConverter.convertToXML(bpmnModel);

			ByteArrayInputStream inputStream = new ByteArrayInputStream(
					bpmnBytes);
			// IOUtils.copy(in, response.getOutputStream());
			String fileName = bpmnModel.getMainProcess().getId()
					+ ".bpmn20.xml";

			map.put("inputStream", inputStream);
			map.put("fileName", fileName);
			// response.setHeader("Content-Disposition", "attachment; filename="
			// + filename);
			// response.flushBuffer();
		} catch (Exception e) {
			// logger.error("导出model的xml文件失败：modelId={}", modelId, e);
			return map;
		}

		return map;
	}

	@Override
	public boolean delete(String id) {
		repositoryService.deleteModel(id);
		return true;
	}

}
