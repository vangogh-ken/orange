package com.van.halley.fas.web;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.rep.ReportUtil;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FasPay;
import com.van.halley.db.persistence.entity.ReportTemplate;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.FasPayService;
import com.van.service.FreightPartService;
import com.van.service.ReportTemplateService;
import com.van.service.UserService;


@Controller
@RequestMapping(value = "/fas/")
public class FasPayController {
	@Autowired
	private FasPayService fasPayService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReportTemplateService reportTemplateService;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fas-pay-list")
	public String unread(Model model, FasPay fasPay, String partName, String payTimeStart, String payTimeEnd, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("CREATE_TIME");
			pageView.setOrder("DESC");
		}
		
		if(!StringUtil.isNullOrEmpty(partName)){
			String filterString = " FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterString);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterString);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(payTimeStart) && !StringUtil.isNullOrEmpty(payTimeEnd)){
			String filterText = " (STATUS='已付款' AND PAY_TIME BETWEEN '" + payTimeStart + "' AND '" + payTimeEnd + "') ";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		pageView = fasPayService.query(pageView, fasPay);

		model.addAttribute("pageView", pageView);
		return "/content/fas/fas-pay-list";
	}

	@RequestMapping(value = "fas-pay-input")
	public String input(Model model, String id) {
		FasPay item = null;
		if (id != null) {
			item = fasPayService.getById(id);
		}
		model.addAttribute("freightParts", freightPartService.getAll());
		model.addAttribute("item", item);
		return "/content/fas/fas-pay-input";
	}

	@RequestMapping(value = "fas-pay-save", method = RequestMethod.POST)
	public String add(FasPay fasPay, String freightPartId, String userId,
			RedirectAttributes redirectAttributes) {
		fasPay.setBeneficiary(freightPartService.getById(freightPartId));
		fasPay.setProposer(userService.getById(userId));
		fasPay.setOrgEntity(fasPay.getProposer().getOrgEntity());
		fasPay.setStatus("未提交");
		if(StringUtil.isNullOrEmpty(fasPay.getPayNumber())){
			fasPay.setPayNumber(fasPayService.getNextPayNumber());
		}
		
		if(fasPay.getId() == null){
			fasPayService.add(fasPay);
		}else{
			fasPayService.modify(fasPay);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fas-pay-list.do";
	}
	
	@RequestMapping(value = "fas-pay-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			fasPayService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fas-pay-list.do";
	}
	
	@RequestMapping(value = "fas-pay-download")
	public void load(String fasPayId, HttpServletResponse response) throws IOException {
		//先初始化一下
		fasPayService.initFasPay(fasPayId);
		//获取付款申请数据模板
		ReportTemplate reportTemplate = reportTemplateService.getById("6c3e8cec-8241-4f1b-a3d4-fa760d3d7e99");
		Map<String, Object> dataSource = new HashMap<String, Object>();
		dataSource.put("fasPay", fasPayService.getById(fasPayId));
		FileUtil.download(ReportUtil.createSimpleReportToStream(dataSource, new File(FileUtil.attachmentPath, reportTemplate.getTemplateFile())), 
				fasPayService.getById(fasPayId).getPayNumber() + "-付款申请." + reportTemplate.getTemplateFile().split("\\.")[1], response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-pay-browse")
	public String browse(String fasPayId) throws IOException{
		//先初始化一下
		fasPayService.initFasPay(fasPayId);
		//获取付款申请数据模板
		ReportTemplate reportTemplate = reportTemplateService.getById("6c3e8cec-8241-4f1b-a3d4-fa760d3d7e99");
		Map<String, Object> dataSource = new HashMap<String, Object>();
		dataSource.put("fasPay", fasPayService.getById(fasPayId));
		//FileUtil.download(ReportUtil.createSimpleReportToStream(dataSource, new File(FileUtil.realPath, reportTemplate.getTemplateFile())), 
				//reportTemplate.getTemplateFile(), response);
		String fileName = StringUtil.getUUID() + "." + reportTemplate.getTemplateFile().split("\\.")[1];
		FileUtils.copyInputStreamToFile(ReportUtil.createSimpleReportToStream(dataSource, new File(FileUtil.attachmentPath, reportTemplate.getTemplateFile())), new File(FileUtil.attachmentPath, fileName));
		return fileName;
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-pay-done-confirm-pay")
	public String confirmPay(@RequestParam(value="fasPayId", required=true)String[] fasPayId) {
		fasPayService.confirmPay(fasPayId);
		return "success";
	}
	
	/**
	 * 付款销账，并生成相应的付款申请书。
	 */
	@ResponseBody
	@RequestMapping(value = "fas-pay-to-add-reconcile")
	public Map<String, Object> toAddReconcile(@RequestParam(value="fasPayId", required=true) String fasPayId) {
		return fasPayService.toAddReconcile(fasPayId);
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-pay-done-add-reconcile")
	public String doneAddReconcile(@RequestParam(value="freightInvoiceId", required=true) String freightInvoiceId,
			@RequestParam(value="fasPayId", required=true) String fasPayId,
			@RequestParam(value="moneyCount", required=true) double moneyCount) {
		if(fasPayService.doneAddReconcile(freightInvoiceId, fasPayId, moneyCount)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fas-pay-done-delete-reconcile")
	public String doneDeleteReconcile(@RequestParam(value="fasReconcileId", required=true) String[] fasReconcileId) {
		fasPayService.doneDeleteReconcile(fasReconcileId);
		return "success";
	}

}
