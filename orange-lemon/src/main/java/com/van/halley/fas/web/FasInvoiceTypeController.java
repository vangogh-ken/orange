package com.van.halley.fas.web;


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
import com.van.halley.db.persistence.entity.FasInvoiceType;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceTypeService;

@Controller
@RequestMapping(value = "/fas/")
public class FasInvoiceTypeController {
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fas-invoice-type-list")
	public String unread(Model model, FasInvoiceType fasInvoiceType, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(fasInvoiceType.getTypeName())){
			String filterText = " TYPE_NAME LIKE '%" + fasInvoiceType.getTypeName() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			
			fasInvoiceType.setTypeName(null);
		}
		pageView = fasInvoiceTypeService.query(pageView, fasInvoiceType);
		model.addAttribute("pageView", pageView);
		return "/content/fas/fas-invoice-type-list";
	}

	@RequestMapping(value = "fas-invoice-type-input")
	public String input(Model model, String id) {
		FasInvoiceType item = null;
		if (id != null) {
			item = fasInvoiceTypeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fas/fas-invoice-type-input";
	}

	@RequestMapping(value = "fas-invoice-type-save", method = RequestMethod.POST)
	public String add(FasInvoiceType fasInvoiceType, RedirectAttributes redirectAttributes) {
		if(fasInvoiceType.getId() == null){
			fasInvoiceTypeService.add(fasInvoiceType);
		}else{
			fasInvoiceTypeService.modify(fasInvoiceType);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fas-invoice-type-list.do";
	}
	
	@RequestMapping(value = "fas-invoice-type-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			fasInvoiceTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fas-invoice-type-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-invoice-type-check-typename")
	public boolean checkName(@RequestParam(required=true, value="typeName") String typeName,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FAS_INVOICE_TYPE WHERE TYPE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FAS_INVOICE_TYPE WHERE TYPE_NAME = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, typeName);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-invoice-type-to-import-invoice-type", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1}, 1, map.get("fileData"));
		fasInvoiceTypeService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-invoice-type-to-export-invoice-type")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<FasInvoiceType> fasInvoiceTypes = fasInvoiceTypeService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"发票票种","税率"}, 
				fasInvoiceTypeService.toExport(fasInvoiceTypes)), "发票列表.xls", response);
	}

}
