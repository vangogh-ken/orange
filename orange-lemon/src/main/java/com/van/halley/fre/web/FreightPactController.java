package com.van.halley.fre.web;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightPact;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightPactService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightPactController {
	@Autowired
	private FreightPactService freightPactService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-pact-list")
	public String unread(Model model, FreightPact freightPact, String partName, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " (PART_A LIKE '%" + partName + "%' OR PART_B LIKE '%" + partName + "%' OR PART_C LIKE '%" + partName + "%') "; 
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = freightPactService.query(pageView, freightPact);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-pact-list";
	}

	@RequestMapping(value = "fre-pact-input")
	public String input(Model model, String id) {
		FreightPact item = null;
		if (id != null) {
			item = freightPactService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-pact-input";
	}

	@RequestMapping(value = "fre-pact-save", method = RequestMethod.POST)
	public String add(FreightPact freightPact, RedirectAttributes redirectAttributes) {
		if(freightPact.getId() == null){
			freightPactService.add(freightPact);
		}else{
			freightPactService.modify(freightPact);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-pact-list.do";
	}
	
	@RequestMapping(value = "fre-pact-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightPactService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-pact-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-pact-to-import-pact", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1,2,3,4,5,6,7,8,9,10,11}, 1, map.get("fileData"));
		freightPactService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-pact-to-export-pact")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightPact> freightPacts = freightPactService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"合同编号","合同名称","合同类型","甲方", "乙方", "丙方","经办人", "签字日期","生效日期","截止日期","结算方式","合同内容"}, 
				freightPactService.toExport(freightPacts)), "合同列表.xls", response);
	}

}
