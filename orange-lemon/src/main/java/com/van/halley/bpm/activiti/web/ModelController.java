package com.van.halley.bpm.activiti.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.apache.commons.io.IOUtils;
//import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.core.mapper.JsonMapper;
import com.van.halley.bpm.service.ModelOperateService;
import com.van.halley.bpm.service.RepositoryQueryService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.util.StringUtil;

@Controller
@RequestMapping(value = "/bpm/")
public class ModelController {
	private static Logger logger = LoggerFactory.getLogger(ModelController.class);
	@Autowired
	private ModelOperateService modelOperateService;
	@Autowired
	private RepositoryQueryService repositoryQueryService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private MessageHelper messageHelper;
	private JsonMapper jsonMapper = new JsonMapper();

	// 查询现有的模型
	@RequestMapping(value = "bpm-model-list")
	public String queryModel(Model model, HttpServletRequest request, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		model.addAttribute("pageView", repositoryQueryService.queryProcessModel(pageView, StringUtil.toSingleValueMap(request.getParameterMap())));
		return "/content/bpm/bpm-model-list";
	}

	/**
	 * 建立新流程
	 * 
	 * @return
	 */
	@RequestMapping(value = "bpm-model-input")
	public String addModelUI() {
		return "/content/bpm/bpm-model-input";
	}

	@RequestMapping(value = "bpm-model-save")
	public String addModel(String name, String key, String description) {
		modelOperateService.create(name, key, description);
		return "redirect: bpm-model-list.do";
	}

	@RequestMapping(value = "bpm-model-deploy")
	public String deploy(String id, HttpServletResponse response,
			RedirectAttributes redirectAttributes) throws IOException {
		modelOperateService.deploy(id);
		messageHelper.addFlashMessage(redirectAttributes, "部署流程成功");
		return "redirect: bpm-model-list.do";
	}

	@RequestMapping(value = "bpm-model-export")
	public void exportModel(String id, HttpServletResponse response)
			throws IOException {
		Map<String, Object> map = modelOperateService.export(id);
		String fileName = (String) map.get("fileName");
		InputStream inputStream = (InputStream) map.get("inputStream");
		//IOUtils.copy(inputStream, response.getOutputStream());
		IOUtils.copy(inputStream, response.getOutputStream());
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		response.flushBuffer();
	}

	@RequestMapping(value = "bpm-model-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			modelOperateService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除模型成功");
		return "redirect:bpm-model-list.do";
	}

}
