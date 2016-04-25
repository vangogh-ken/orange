package com.van.halley.fre.web;


import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightDelegateTemplate;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.FreightDelegateTemplateService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightDelegateTemplateController {
	@Autowired
	private FreightDelegateTemplateService freightDelegateTemplateService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-delegate-template-list")
	public String list(Model model, FreightDelegateTemplate freightDelegateTemplate, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(freightDelegateTemplate.getTemplateName())){
			String filterText = " TEMPLATE_NAME LIKE '%" + freightDelegateTemplate.getTemplateName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			freightDelegateTemplate.setTemplateName(null);
		}
		
		pageView = freightDelegateTemplateService.query(pageView, freightDelegateTemplate);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-delegate-template-list";
	}

	@RequestMapping(value = "fre-delegate-template-input")
	public String input(Model model, String id) {
		FreightDelegateTemplate item = null;
		if (id != null) {
			item = freightDelegateTemplateService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-delegate-template-input";
	}

	@RequestMapping(value = "fre-delegate-template-save", method = RequestMethod.POST)
	public String add(FreightDelegateTemplate freightDelegateTemplate, MultipartHttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, String> map = FileUtil.upload("muiltFile", request);
		freightDelegateTemplate.setTemplateFile(map.get("fileData"));
		freightDelegateTemplateService.addOrModify(freightDelegateTemplate);
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-delegate-template-list.do";
	}
	
	@RequestMapping(value = "fre-delegate-template-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightDelegateTemplateService.deleteTemplate(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-delegate-template-list.do";
	}
	
	@RequestMapping(value = "fre-delegate-template-download")
	public void download(String freightDelegateTemplateId, HttpServletResponse response) throws IOException {
		freightDelegateTemplateService.downloadTemplate(freightDelegateTemplateId, response);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "fre-delegate-template-check-templatename")
	public boolean checkColumnName(@RequestParam(required=true, value="templateName") String templateName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_DELEGATE_TEMPLATE WHERE TEMPLATE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, templateName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_DELEGATE_TEMPLATE WHERE TEMPLATE_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, templateName);
			return count == 0;
		}
	}

}
