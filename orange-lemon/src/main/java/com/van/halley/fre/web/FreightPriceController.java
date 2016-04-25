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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.FreightPrice;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FasInvoiceTypeService;
import com.van.service.FreightExpenseTypeService;
import com.van.service.FreightPactService;
import com.van.service.FreightPartService;
import com.van.service.FreightPriceService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightPriceController {
	@Autowired
	private FreightPriceService freightPriceService;
	@Autowired
	private FreightPartService freightPartService;
	@Autowired
	private FreightPactService freightPactService;
	@Autowired
	private FreightExpenseTypeService freightExpenseTypeService;
	@Autowired
	private FasInvoiceTypeService fasInvoiceTypeService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-price-list")
	public String list(Model model, FreightPrice freightPrice, String partName, String expenseTypeName, 
			String pactNumber, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" +partName+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName+ "%')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(expenseTypeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME LIKE '%" + expenseTypeName+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME LIKE '%" + expenseTypeName+ "%')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(pactNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PACT_ID IN(SELECT ID FROM FRE_PACT WHERE PACT_NUMBER LIKE '%" + pactNumber+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PACT_ID IN(SELECT ID FROM FRE_PACT WHERE PACT_NUMBER LIKE '%" +pactNumber+ "%')");
			}
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("MODIFY_TIME");
			pageView.setOrder("DESC");
		}
		
		pageView = freightPriceService.query(pageView, freightPrice);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-price-list";
	}
	
	/**
	 * 商务部所见
	 */
	@RequestMapping(value = "fre-price-list-commerce")
	public String commerce(Model model, FreightPrice freightPrice, String partName, String expenseTypeName, 
			String pactNumber, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		freightPrice.setStatus("O");//只可见原成本信息
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" +partName+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName+ "%')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(expenseTypeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME LIKE '%" + expenseTypeName+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME LIKE '%" + expenseTypeName+ "%')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(pactNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PACT_ID IN(SELECT ID FROM FRE_PACT WHERE PACT_NUMBER LIKE '%" + pactNumber+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PACT_ID IN(SELECT ID FROM FRE_PACT WHERE PACT_NUMBER LIKE '%" +pactNumber+ "%')");
			}
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("MODIFY_TIME");
			pageView.setOrder("DESC");
		}
		
		pageView = freightPriceService.query(pageView, freightPrice);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-price-list-commerce";
	}

	
	/**
	 * 客服所见
	 */
	@RequestMapping(value = "fre-price-list-service")
	public String service(Model model, FreightPrice freightPrice, String partName, String expenseTypeName, 
			String pactNumber, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		freightPrice.setStatus("O");//只可见原成本信息
		if(!StringUtil.isNullOrEmpty(partName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" +partName+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PART_ID IN(SELECT ID FROM FRE_PART WHERE PART_NAME LIKE '%" + partName+ "%')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(expenseTypeName)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_EXPENSE_TYPE_ID IN(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME LIKE '%" + expenseTypeName+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_EXPENSE_TYPE_ID IN(SELECT ID FROM FRE_EXPENSE_TYPE WHERE TYPE_NAME LIKE '%" + expenseTypeName+ "%')");
			}
		}
		
		if(!StringUtil.isNullOrEmpty(pactNumber)){
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(" FRE_PACT_ID IN(SELECT ID FROM FRE_PACT WHERE PACT_NUMBER LIKE '%" + pactNumber+ "%')");
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND FRE_PACT_ID IN(SELECT ID FROM FRE_PACT WHERE PACT_NUMBER LIKE '%" +pactNumber+ "%')");
			}
		}
		if(StringUtil.isNullOrEmpty(pageView.getOrderBy())){
			pageView.setOrderBy("MODIFY_TIME");
			pageView.setOrder("DESC");
		}
		
		pageView = freightPriceService.query(pageView, freightPrice);
		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-price-list-service";
	}


	@RequestMapping(value = "fre-price-input")
	public String input(Model model, String id) {
		FreightPrice item = null;
		if (id != null) {
			item = freightPriceService.getById(id);
		}
		model.addAttribute("freightParts", freightPartService.getAll());
		model.addAttribute("freightPacts", freightPactService.getAll());
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("freightExpenseTypes", freightExpenseTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/fre/fre-price-input";
	}
	
	@RequestMapping(value = "fre-price-input-batch")
	public String inputBatch(Model model, String id) {
		FreightPrice item = null;
		if (id != null) {
			item = freightPriceService.getById(id);
		}
		model.addAttribute("freightParts", freightPartService.getAll());
		model.addAttribute("freightPacts", freightPactService.getAll());
		model.addAttribute("fasInvoiceTypes", fasInvoiceTypeService.getAll());
		model.addAttribute("freightExpenseTypes", freightExpenseTypeService.getAll());
		model.addAttribute("item", item);
		return "/content/fre/fre-price-input-batch";
	}

	@RequestMapping(value = "fre-price-save", method = RequestMethod.POST)
	public String add(FreightPrice freightPrice, 
			String freightPartId, String freightPactId,String freightExpenseTypeId, 
			String fasInvoiceTypeId, RedirectAttributes redirectAttributes) {
		freightPrice.setFreightPact(freightPactService.getById(freightPactId));
		freightPrice.setFreightPart(freightPartService.getById(freightPartId));
		freightPrice.setFreightExpenseType(freightExpenseTypeService.getById(freightExpenseTypeId));
		freightPrice.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		if(freightPrice.getId() == null){
			freightPrice.setStatus("O");
			freightPriceService.add(freightPrice);
		}else{
			/*freightPrice.setId(StringUtil.getUUID());
			freightPriceService.add(freightPrice);*/
			freightPriceService.modify(freightPrice);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-price-input.do?freightPartId=" + freightPartId;//直接返回填报界面，继续填写。
	}
	
	@RequestMapping(value = "fre-price-save-batch", method = RequestMethod.POST)
	public String addBatch(FreightPrice freightPrice, 
			String freightPartId, String freightPactId,String freightExpenseTypeId, 
			String fasInvoiceTypeId, double moneyCount20, double moneyCount40, RedirectAttributes redirectAttributes) {
		freightPrice.setFreightPact(freightPactService.getById(freightPactId));
		freightPrice.setFreightPart(freightPartService.getById(freightPartId));
		freightPrice.setFreightExpenseType(freightExpenseTypeService.getById(freightExpenseTypeId));
		freightPrice.setFasInvoiceType(fasInvoiceTypeService.getById(fasInvoiceTypeId));
		freightPrice.setStatus("O");
		freightPriceService.doneAddBatch(freightPrice, moneyCount20, moneyCount40);
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:fre-price-input-batch.do?freightPartId=" + freightPartId;//直接返回填报界面，继续填写。
	}
	
	@RequestMapping(value = "fre-price-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightPriceService.remove(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-price-list.do";
	}
	
	@RequestMapping(value = "fre-price-remove-commerce")
	public String removeCommerce(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		freightPriceService.remove(selectedItem);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-price-list-commerce.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-price-all")
	public List<FreightPrice> all() {
		return freightPriceService.getAll();
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-price-for-add-expense")
	public FreightPrice getByPartId(@RequestParam(value="freightPartId",required=true)String freightPartId,
			@RequestParam(value="freightExpenseTypeId",required=true)String freightExpenseTypeId,
			String countUnit) {
		if(StringUtil.isNullOrEmpty(countUnit)){
			return freightPriceService.getOriginalPrice(freightPartId, freightExpenseTypeId, "票");
		}else{
			return freightPriceService.getOriginalPrice(freightPartId, freightExpenseTypeId, countUnit);
		}
		
		/*for(FreightPrice freightPrice : FreightPriceService.cacheFreightPrice.get(freightPartId)){
			if(StringUtil.isNullOrEmpty(countUnit)){
				if(freightExpenseTypeId.equals(freightPrice.getFreightExpenseType().getId())
						&& "票".equals(freightPrice.getCountUnit())){//如果没有传入计价方式，则取其按票费用
					return freightPrice;
				}
			}else{
				if(freightExpenseTypeId.equals(freightPrice.getFreightExpenseType().getId())
						&& countUnit.equals(freightPrice.getCountUnit())){
					return freightPrice;
				}
			}
		}
		return null;*/
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-price-to-import-price", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0,1,2,3,4,5,6}, 1, map.get("fileData"));
		freightPriceService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-price-to-export-price")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightPrice> freightPrices = FreightPriceService.cacheFreightPrice.get(null);
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"价格名称","单位名称","发票票种","计价单位","币种","价格","是否为固定金额"}, 
				freightPriceService.toExport(freightPrices)), "成本列表.xls", response);
	}

}
