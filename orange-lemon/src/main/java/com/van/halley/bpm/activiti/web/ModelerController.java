package com.van.halley.bpm.activiti.web;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.van.core.mapper.JsonMapper;

//import com.van.bpm.cmd.SyncProcessCmd;

/**
 * modeler.
 * 
 * @author Lingo
 */
@Controller
@RequestMapping("modeler")
public class ModelerController {
	private static Logger logger = LoggerFactory.getLogger(ModelerController.class);
    private ProcessEngine processEngine;
    @Autowired
	private RepositoryService repositoryService;
	private JsonMapper jsonMapper = new JsonMapper();

    @RequestMapping("modeler-list")
    public String list(org.springframework.ui.Model model) {
        List<Model> models = processEngine.getRepositoryService()
                .createModelQuery().list();
        model.addAttribute("models", models);

        return "modeler/modeler-list";
    }

    @RequestMapping("modeler-open")
    public String open(@RequestParam(value = "id", required = false) String id)
            throws Exception {
        RepositoryService repositoryService = processEngine
                .getRepositoryService();
        Model model = repositoryService.getModel(id);

        if (model == null) {
            model = repositoryService.newModel();
            repositoryService.saveModel(model);
            id = model.getId();
        }

        return "redirect:/widgets/modeler/editor.html?id=" + id;
    }

    @RequestMapping("modeler-remove")
    public String remove(@RequestParam("id") String id) {
        processEngine.getRepositoryService().deleteModel(id);

        return "redirect:/modeler/modeler-list.do";
    }

    @RequestMapping("modeler-deploy")
    public String deploy(@RequestParam("id") String id) throws Exception {
        RepositoryService repositoryService = processEngine
                .getRepositoryService();
        Model modelData = repositoryService.getModel(id);
      //修改 Version 5.16
        //ObjectNode modelNode = (ObjectNode) new ObjectMapper()
               // .readTree(repositoryService.getModelEditorSource(modelData
                       // .getId()));
        JsonNode modelNode = new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
        byte[] bpmnBytes = null;

        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);

        List<ProcessDefinition> processDefinitions = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId()).list();

        for (ProcessDefinition processDefinition : processDefinitions) {
            //processEngine.getManagementService().executeCommand(
                    //new SyncProcessCmd(processDefinition.getId()));
        }

        return "redirect:/modeler/modeler-list.do";
    }
    
    
    @RequestMapping("model/{modelId}/json")
    @ResponseBody
    public String openModel(@PathVariable("modelId") String modelId)throws Exception {
        org.activiti.engine.repository.Model model = repositoryService.getModel(modelId);

        if (model == null) {
            logger.info("model({}) is null", modelId);
            model = repositoryService.newModel();
            repositoryService.saveModel(model);
        }

        Map root = new HashMap();
        root.put("modelId", model.getId());
        root.put("name", "name");
        root.put("revision", 1);
        root.put("description", "description");

        byte[] bytes = repositoryService.getModelEditorSource(model.getId());

        if (bytes != null) {
            String modelEditorSource = new String(bytes, "utf-8");
            logger.info("modelEditorSource : {}", modelEditorSource);

            Map modelNode = jsonMapper.fromJson(modelEditorSource, Map.class);
            root.put("model", modelNode);
        } else {
            Map modelNode = new HashMap();
            modelNode.put("id", "canvas");
            modelNode.put("resourceId", "canvas");

            Map stencilSetNode = new HashMap();
            stencilSetNode.put("namespace",
                    "http://b3mn.org/stencilset/bpmn2.0#");
            modelNode.put("stencilset", stencilSetNode);

            model.setMetaInfo(jsonMapper.toJson(root));
            model.setName("name");
            model.setKey("key");

            root.put("model", modelNode);
        }

        logger.info("model : {}", root);

        return jsonMapper.toJson(root);
    }

    @RequestMapping("editor/stencilset")
    @ResponseBody
    public String stencilset() throws Exception {
        InputStream stencilsetStream = this.getClass().getClassLoader().getResourceAsStream("stencilset.json");
        try {
            return IOUtils.toString(stencilsetStream, "utf-8");
            //return IOUtils.toString(stencilsetStream);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading stencil set", e);
        }
    }

    @RequestMapping("model/{modelId}/save")
    @ResponseBody
    public String modelSave(@PathVariable("modelId") String modelId,
            @RequestParam("description") String description,
            @RequestParam("json_xml") String jsonXml,
            @RequestParam("name") String name,
            @RequestParam("svg_xml") String svgXml) throws Exception {
    	org.activiti.engine.repository.Model model = repositoryService.getModel(modelId);
        model.setName(name);
        // model.setMetaInfo(root.toString());
        logger.info("jsonXml : {}", jsonXml);
        repositoryService.saveModel(model);
        repositoryService.addModelEditorSource(model.getId(), jsonXml.getBytes("utf-8"));

        return "{}";
    }

    // ~ ==================================================
    @Resource
    public void setProcessEngine(ProcessEngine processEngine) {
        this.processEngine = processEngine;
    }
}