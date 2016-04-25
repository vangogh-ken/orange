package com.van.halley.bpm.config.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.BpmConfigNode;
import com.van.halley.db.persistence.entity.BpmConfigOperation;
import com.van.halley.db.persistence.entity.BpmOperationType;
import com.van.service.BpmConfigNodeService;
import com.van.service.BpmConfigOperationService;
import com.van.service.BpmOperationTypeService;

@Controller
@RequestMapping(value = "/bpm/")
public class BpmConfigNodeController {
	@Autowired
	private BpmConfigNodeService bpmConfigNodeService;
	@Autowired
	private BpmConfigOperationService bpmConfigOperationService;
	@Autowired
	private BpmOperationTypeService bpmOperationTypeService;
	@Autowired
	private RepositoryQueryService repositoryQueryServiceImpl;
	@Autowired
	private MessageHelper messageHelper;
	

	@RequestMapping(value = "bpm-config-node-list")
	public String queryModel(Model model, BpmConfigNode bpmConfigNode, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		bpmConfigNodeService.query(pageView, bpmConfigNode);
		List<ProcessDefinition> processDefinitions = repositoryQueryServiceImpl.getProcessDefinitions();
		model.addAttribute("pageView", pageView);
		model.addAttribute("pds", processDefinitions);
		return "/content/bpm/bpm-config-node-list";
	}

	@RequestMapping(value = "bpm-config-node-input")
	public String input(Model model, String id) {
		BpmConfigNode item = null;
		if(id != null){
			item = bpmConfigNodeService.getById(id);
		}
		
		List<String> keys = repositoryQueryServiceImpl.getProcessKeys();
		model.addAttribute("keys", keys);
		model.addAttribute("item", item);
		return "/content/bpm/bpm-config-node-input";
	}

	@RequestMapping(value = "bpm-config-node-save")
	public String addModel(BpmConfigNode bpmConfigNode) {
		if(bpmConfigNode.getId() != null){
			bpmConfigNodeService.modify(bpmConfigNode);
		}else{
			bpmConfigNodeService.add(bpmConfigNode);
		}
		
		return "redirect:bpm-config-node-list.do?processDefinitionId=" + bpmConfigNode.getPdId();
	}

	@RequestMapping(value = "bpm-config-node-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			bpmConfigNodeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:bpm-config-node-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-node-to-config-operation")
	public List<BpmConfigOperation> nodeOperation(String bpmConfigNodeId) {
		BpmConfigNode node = bpmConfigNodeService.getById(bpmConfigNodeId);
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
		
		List<BpmConfigOperation> results = new ArrayList<BpmConfigOperation>();
		BpmConfigOperation filter = new BpmConfigOperation();
		filter.setBpmConfigNode(node);
		results = bpmConfigOperationService.queryForList(filter);
		return results;
	}
	
	@RequestMapping(value = "bpm-config-node-operation-save")
	public String nodeOperationSave(HttpServletRequest request, RedirectAttributes redirectAttributes){
		Map<String, String[]> paramMap = request.getParameterMap();
		for (String param : paramMap.keySet()) {
			if (param.endsWith("_id")) {
				String id = paramMap.get(param)[0];
				String enable = paramMap.get(param.split("_")[0] + "_enable")[0];

				BpmConfigOperation bpmConfigOperation = new BpmConfigOperation();
				bpmConfigOperation.setId(id);
				bpmConfigOperation.setEnable(enable);

				bpmConfigOperationService.modify(bpmConfigOperation);
			}
		}
		messageHelper.addFlashMessage(redirectAttributes, "配置成功");
		return "redirect:bpm-config-node-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-node-to-config-status")
	public Map<String, Object> toConfigStatus(@RequestParam(value="bpmConfigNodeId", required=true) String bpmConfigNodeId) {
		return bpmConfigNodeService.toConfigStatus(bpmConfigNodeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-node-done-config-status")
	public String doneConfigStatus(@RequestParam(value="bpmConfigNodeId", required=true) String bpmConfigNodeId,
			@RequestParam(value="basisStatusId", required=true) String basisStatusId) {
		bpmConfigNodeService.doneConfigStatus(bpmConfigNodeId, basisStatusId);
		return "success"; 
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-node-to-config-auth")
	public Map<String, Object> toConfigAuth(@RequestParam(value="bpmConfigNodeId", required=true) String bpmConfigNodeId) {
		return bpmConfigNodeService.toConfigAuth(bpmConfigNodeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-node-done-config-auth")
	public String doneConfigAuth(@RequestParam(value="bpmConfigNodeId", required=true) String bpmConfigNodeId,
			@RequestParam(value="authId", required=true) String authId,
			@RequestParam(value="authType", required=true) String authType) {
		bpmConfigNodeService.doneConfigAuth(bpmConfigNodeId, authId, authType);
		return "success"; 
	}
	
	@ResponseBody
	@RequestMapping(value = "bpm-config-node-done-delete-auth")
	public String doneDeleteAuth(@RequestParam(value="bpmConfigNodeId", required=true) String bpmConfigNodeId,
			@RequestParam(value="authId", required=true) String authId) {
		bpmConfigNodeService.doneDeleteAuth(bpmConfigNodeId, authId);
		return "success"; 
	}

}
