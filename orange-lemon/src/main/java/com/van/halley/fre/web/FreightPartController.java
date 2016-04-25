package com.van.halley.fre.web;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
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
import com.van.halley.db.persistence.entity.FreightPart;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightPartService;
import com.van.service.OrgEntityService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightPartController {
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private OrgEntityService orgEntityService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-part-list")
	public String unread(Model model, FreightPart freightPart, String DWMC, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(DWMC)){
			String filterString = " PART_NAME LIKE '%" + DWMC + "%' ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		pageView = freightPartService.query(pageView, freightPart);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-part-list";
	}

	@RequestMapping(value = "fre-part-input")
	public String input(Model model, String id) {
		FreightPart item = null;
		if (id != null) {
			item = freightPartService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("orgEntities", orgEntityService.getAll());
		return "/content/fre/fre-part-input";
	}

	@RequestMapping(value = "fre-part-save", method = RequestMethod.POST)
	public String add(FreightPart freightPart, String orgEntityId, RedirectAttributes redirectAttributes) {
		freightPart.setOrgEntity(orgEntityService.getById(orgEntityId));
		if(freightPart.getId() == null){
			freightPartService.add(freightPart);
		}else{
			freightPartService.modify(freightPart);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-part-list.do";
	}
	
	@RequestMapping(value = "fre-part-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightPartService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-part-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-part-all")
	public List<FreightPart> all() {
		return FreightPartService.cacheFreightPart.get(null);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-part-by-expensetypeid")
	public List<FreightPart> byExpenseName(@RequestParam(value="freightExpenseTypeId", required=true)String freightExpenseTypeId) {
		return FreightPartService.cacheFreightPart.get(freightExpenseTypeId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-part-for-add-expense")
	public List<FreightPart> to(@RequestParam(value="freightExpenseTypeId", required=true)String freightExpenseTypeId,
			String countUnit) {
		if(StringUtil.isNullOrEmpty(countUnit)){
			return FreightPartService.cacheFreightPart.get(freightExpenseTypeId);
		}else{
			return FreightPartService.cacheFreightPart.get(freightExpenseTypeId + countUnit);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-part-check-partname")
	public boolean checkColumnName(@RequestParam(required=true, value="partName") String partName, String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FRE_PART WHERE PART_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, partName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FRE_PART WHERE PART_NAME = ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, partName);
			return count == 0;
		}
	}

	@ResponseBody
	@RequestMapping(value = "fre-part-to-import-part", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1,2,3,4}, 1, map.get("fileData"));
		freightPartService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-part-to-export-part")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightPart> freightParts = FreightPartService.cacheFreightPart.get(null);
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"单位名称","人民币银行名称","人民币银行账号","美元银行名称","美元银行账号"}, 
				freightPartService.toExport(freightParts)), "单位列表.xls", response);
	}

}
