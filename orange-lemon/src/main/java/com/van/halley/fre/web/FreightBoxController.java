package com.van.halley.fre.web;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.van.halley.db.persistence.entity.FreightBox;
import com.van.halley.db.persistence.entity.FreightBoxRequire;
import com.van.halley.fre.util.FreightFilterUtil;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.FreightBoxRequireService;
import com.van.service.FreightBoxService;

@Controller
@RequestMapping(value = "/fre/")
public class FreightBoxController {
	@Autowired
	private FreightBoxService freightBoxService;
	@Autowired
	private FreightBoxRequireService freightBoxRequireService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "fre-box-list")
	public String list(Model model, FreightBox freightBox, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightBoxService.query(pageView, freightBox);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-box-list";
	}
	
	@RequestMapping(value = "fre-box-list-release")
	public String listRelease(Model model, FreightBox freightBox, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = freightBoxService.query(pageView, freightBox);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-box-list-release";
	}
	
	@RequestMapping(value = "fre-box-list-manipulator")
	public String listManipulator(Model model, FreightBox freightBox, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(StringUtil.isNullOrEmpty(freightBox.getBoxSource())){
			freightBox.setBoxSource("外理箱");
		}
		pageView = freightBoxService.query(pageView, freightBox);

		model.addAttribute("pageView", pageView);
		return "/content/fre/fre-box-list-manipulator";
	}

	@RequestMapping(value = "fre-box-input")
	public String input(Model model, String id) {
		FreightBox item = null;
		if (id != null) {
			item = freightBoxService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-box-input";
	}
	
	@RequestMapping(value = "fre-box-input-release")
	public String inputRelease(Model model, String id) {
		FreightBox item = null;
		if (id != null) {
			item = freightBoxService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-box-input-release";
	}
	
	@RequestMapping(value = "fre-box-input-manipulator")
	public String inputManipulator(Model model, String id) {
		FreightBox item = null;
		if (id != null) {
			item = freightBoxService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/fre/fre-box-input-manipulator";
	}

	@RequestMapping(value = "fre-box-save", method = RequestMethod.POST)
	public String add(Model model,FreightBox freightBox, RedirectAttributes redirectAttributes) {
		if(freightBox.getId() == null){
			freightBoxService.add(freightBox);
		}else{
			freightBoxService.modify(freightBox);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		//return "redirect:fre-box-list.do";
		freightBox.setId(null);
		freightBox.setBoxNumber(null);
		model.addAttribute("item", freightBox);
		return "/content/fre/fre-box-input";
		//return "redirect:fre-input-list.do";
	}
	
	@RequestMapping(value = "fre-box-save-release", method = RequestMethod.POST)
	public String addRelease(Model model, FreightBox freightBox, RedirectAttributes redirectAttributes) {
		if(freightBox.getId() == null){
			freightBoxService.add(freightBox);
		}else{
			freightBoxService.modify(freightBox);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		//return "redirect:fre-box-list-release.do";
		freightBox.setId(null);
		freightBox.setBoxNumber(null);
		model.addAttribute("item", freightBox);
		return "/content/fre/fre-box-input-release";
		//return "redirect:fre-box-input-release.do";
	}
	
	@RequestMapping(value = "fre-box-save-manipulator", method = RequestMethod.POST)
	public String addManipulator(Model model,FreightBox freightBox, RedirectAttributes redirectAttributes) {
		if(freightBox.getId() == null){
			freightBoxService.add(freightBox);
		}else{
			freightBoxService.modify(freightBox);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		//return "redirect:fre-box-list-manipulator.do";
		freightBox.setId(null);
		freightBox.setBoxNumber(null);
		model.addAttribute("item", freightBox);
		return "/content/fre/fre-box-input-manipulator";
		//return "redirect:fre-box-input-manipulator.do";
	}
	
	@RequestMapping(value = "fre-box-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightBoxService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-box-list.do";
	}
	
	@RequestMapping(value = "fre-box-remove-release")
	public String removeRelease(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightBoxService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-box-list-release.do";
	}
	
	@RequestMapping(value = "fre-box-remove-manipulator")
	public String removeManipulator(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			freightBoxService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:fre-box-list-manipulator.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-by-requireid")
	public List<FreightBox> get(@RequestParam(value="freightBoxRequireId", required=true)String freightBoxRequireId) {
		FreightBoxRequire require = freightBoxRequireService.getById(freightBoxRequireId);
		FreightBox filter = new FreightBox();
		filter.setBoxSource(require.getBoxSource());
		filter.setBoxType(require.getBoxType());
		filter.setBoxBelong(require.getBoxBelong());
		//filter.setBoxCondition(require.getBoxCondition()); 取消箱况的过滤
		filter.setStatus("可用");
		return freightBoxService.queryForList(filter);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-check-boxnumber")
	public boolean checkName(@RequestParam(required=true, value="boxNumber") String boxNumber,  String id) {
		if(FreightFilterUtil.validateBoxNumber(boxNumber)){
			if(id != null){
				String sql = "SELECT COUNT(*) AS count FROM FRE_BOX WHERE BOX_NUMBER = ? AND ID <> ? AND STATUS = '可用'";
				int count = jdbcTemplate.queryForObject(sql, Integer.class, boxNumber, id);
				return count == 0;
			}else{
				String sql = "SELECT COUNT(*) AS count FROM FRE_BOX WHERE BOX_NUMBER = ? AND STATUS = '可用'";
				int count = jdbcTemplate.queryForObject(sql, Integer.class, boxNumber);
				return count == 0;
			}
		}else{
			return false;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-to-validate-boxnumber")
	public String validBoxNumber(@RequestParam(required=true, value="boxNumber") String boxNumber,  String id) {
		if(FreightFilterUtil.validateBoxNumber(boxNumber)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-to-import-box", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0, 1, 2, 3, 4}, 1, map.get("fileData"));
		freightBoxService.toImport(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-to-export-box")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<FreightBox> freightBoxs = freightBoxService.getAll();
		FileUtil.download(IExmportUtil.exportMultiColumn(
				new String[]{"集装箱来源", "箱号", "箱型", "箱属", "状态"}, freightBoxService.toExport(freightBoxs)), "集装箱.xls", response);
	}
	
	@ResponseBody
	@RequestMapping(value = "fre-box-to-export-mscedi-to-file")
	public String exportMscedi() {
		Calendar cal = Calendar.getInstance();
		Date current = cal.getTime();
		return  "CHUANGYUAN" + new SimpleDateFormat("yyyyMMdd").format(current) + (cal.get(Calendar.HOUR_OF_DAY) > 12 ? "PM" : "AM") +".TXT";
	}

}
