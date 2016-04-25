package com.van.halley.bpm.activiti.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.form.StartFormDataImpl;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.bpm.service.ProcessDefinitionOperateService;
import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.util.StringUtil;

@Controller
@RequestMapping(value = "/bpm/")
public class ProcessDefinitionController {
	@Autowired
	private ProcessDefinitionOperateService processDefinitionOperateService;
	//@Autowired
	//private ProcessDefinitionQueryService processDefinitionQueryService;
	@Autowired
	private RepositoryQueryService repositoryQueryService;
	@Autowired
	private MessageHelper messageHelper;

	// 查询所有的现有的流程
	@RequestMapping(value = "bpm-process-definition-list")
	public String list(Model model, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		//processDefinitionQueryService.all(pageView);
		//model.addAttribute("pageView", pageView);
		
		model.addAttribute("pageView", repositoryQueryService.queryProcessDefinition(pageView, StringUtil.toSingleValueMap(request.getParameterMap())));
		return "/content/bpm/bpm-process-definition-list";
	}

	// 转换为model
	@RequestMapping(value = "bpm-process-definition-convert")
	public String convertToModel(String id,
			RedirectAttributes redirectAttributes) {
		processDefinitionOperateService.convertToModel(id);
		messageHelper.addFlashMessage(redirectAttributes, "转换流程模型成功");
		return "redirect: /bpm-model-list.do";
	}

	// 多个删除
	@RequestMapping(value = "bpm-process-definition-remove")
	public String remove(String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			processDefinitionOperateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除流程定义成功");
		return "redirect: bpm-process-definition-list.do";
	}

	// 删除
	@RequestMapping(value = "bpm-process-definition-delete")
	public String delete(String deploymentIdAndProcessDefinitionId,
			RedirectAttributes redirectAttributes) {
		processDefinitionOperateService.delete(deploymentIdAndProcessDefinitionId);
		messageHelper.addFlashMessage(redirectAttributes, "删除流程定义成功");
		return "redirect: bpm-process-definition-list.do";
	}

	// 根据流程定义Id获取对应资源的文件
	@RequestMapping(value = "bpm-process-definition-resource")
	public void readResource(String id, String resourceType,
			HttpServletResponse response) throws IOException {
		Map<String, Object> map = processDefinitionOperateService
				.exportXMLOrPNGById(id, resourceType);
		InputStream in = (InputStream) map.get("inputStream");
		
		response.reset();
		response.setHeader("Connection", "close");
		response.setContentType("application/x-msdownload");
		response.addHeader("Content-Type;charset=UTF-8","application/octet-stream");
		response.addHeader("Content-Length", "" + in.available());
		response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode((String)map.get("fileName"), "UTF-8"));
		
		IOUtils.copy(in, response.getOutputStream());
	}

	@RequestMapping(value = "bpm-process-definition-deploy-byfile")
	public String deploy(Model model, MultipartHttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		boolean result = processDefinitionOperateService.deployByFile(request);
		if (result) {
			messageHelper.addFlashMessage(redirectAttributes, "部署流程成功");
		} else {
			messageHelper.addFlashMessage(redirectAttributes, "部署流程失败");
		}

		return "redirect:bpm-process-definition-list.do";
	}
	
	@RequestMapping(value = "bpm-process-definition-to-active")
	public String updateToActive(String id, RedirectAttributes redirectAttributes){
		processDefinitionOperateService.updateToActive(id);
		messageHelper.addFlashMessage(redirectAttributes, "激活流程成功");
		return "redirect:bpm-process-definition-list.do";
	}
	
	@RequestMapping(value = "bpm-process-definition-to-suspend")
	public String updateToSuspend(String id, RedirectAttributes redirectAttributes){
		processDefinitionOperateService.updateToSuspend(id);
		messageHelper.addFlashMessage(redirectAttributes, "挂起流程成功");
		return "redirect:bpm-process-definition-list.do";
	}

	/********************
	 * 对动态表单和外置表单都采取统一的渲染方式 对于自定义页面，直接调用就行了。
	 * 
	 * ********************/
	/**
	 * 通过流程定义ID获取内置的formpProperties
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	@RequestMapping(value = "start-form-dynamic")
	public Map<String, Object> getDynamicStartFormByProcessDefinitionId(
			String processDefinitionId) {
		Map<String, Object> result = new HashMap<String, Object>();
		StartFormDataImpl startFormData = processDefinitionOperateService
				.startInnerForm(processDefinitionId);
		result.put("form", startFormData);
		return result;
	}

	/**
	 * 通过流程定义ID获取外置表单，主要是以formKey的形式，外部的form文件
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	@RequestMapping(value = "start-form-formkey")
	public Map<String, Object> getExternalStartFormByProcessDefinitionId(
			String processDefinitionId) {
		Map<String, Object> result = new HashMap<String, Object>();
		Object object = processDefinitionOperateService
				.startExternalForm(processDefinitionId);
		result.put("form", object);
		return result;
	}
}
