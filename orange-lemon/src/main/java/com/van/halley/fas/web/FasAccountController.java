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
import com.van.halley.db.persistence.entity.FasAccount;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasAccountService;
import com.van.service.FreightPartService;

@Controller
@RequestMapping(value = "/fas/")
public class FasAccountController {
	@Autowired
	private FasAccountService fasAccountService;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fas-account-list")
	public String list(Model model, FasAccount fasAccount, String partName, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterText = " FRE_PART_ID IN (SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		pageView = fasAccountService.query(pageView, fasAccount);
		model.addAttribute("pageView", pageView);
		return "/content/fas/fas-account-list";
	}

	@RequestMapping(value = "fas-account-input")
	public String input(Model model, String id) {
		FasAccount item = null;
		if (id != null) {
			item = fasAccountService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fas/fas-account-input";
	}

	@RequestMapping(value = "fas-account-save", method = RequestMethod.POST)
	public String addFas(FasAccount fasAccount, String freightPartId, RedirectAttributes redirectAttributes) {
		fasAccount.setFreightPart(freightPartService.getById(freightPartId));
		if(fasAccount.getId() == null){
			fasAccountService.add(fasAccount);
		}else{
			fasAccountService.modify(fasAccount);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fas-account-list.do";
	}
	
	@RequestMapping(value = "fas-account-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			fasAccountService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fas-account-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-account-check-accountnumber")
	public boolean checkName(@RequestParam(required=true, value="accountNumber") String accountNumber,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FAS_ACCOUNT WHERE ACCOUNT_NUMBER = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, accountNumber, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FAS_ACCOUNT WHERE ACCOUNT_NUMBER = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, accountNumber);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-account-check-currency")
	public boolean currency(@RequestParam(required=true, value="freightPartId") String freightPartId,
			@RequestParam(required=true, value="currency") String currency,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM FAS_ACCOUNT WHERE FRE_PART_ID = ? AND CURRENCY=? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, freightPartId, currency, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM FAS_ACCOUNT WHERE FRE_PART_ID = ? AND CURRENCY=? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, freightPartId, currency);
			return count == 0;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-account-get-by-part")
	public List<FasAccount> getByPayPartId(@RequestParam(required=true, value="payPartId") String payPartId) {
		return fasAccountService.getPartAccount(payPartId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-account-to-import-account", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1,2,3}, 1, map.get("fileData"));
		fasAccountService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-account-to-export-account")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<FasAccount> fasAccounts = fasAccountService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"单位名称","币种", "银行", "账号"}, 
				fasAccountService.toExport(fasAccounts)), "账户列表.xls", response);
	}

}
