package com.van.halley.out.web;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ValueDictionary;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.IExmportUtil;
import com.van.service.ValueAttributeService;
import com.van.service.ValueDictionaryService;

@Controller
@RequestMapping(value = "/dic/")
public class ValueDictionaryController {
	@Autowired
	private ValueDictionaryService valueDictionaryService;
	@Autowired
	private ValueAttributeService valueAttributeService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "value-dictionary-list")
	public String unread(Model model, ValueDictionary valueDictionary, String valueAttributeId, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(valueAttributeId != null){
			valueDictionary.setValueAttribute(valueAttributeService.getById(valueAttributeId));
		}
		pageView = valueDictionaryService.query(pageView, valueDictionary);

		model.addAttribute("pageView", pageView);
		model.addAttribute("valueAttributes", valueAttributeService.getAll());
		return "/content/dic/value-dictionary-list";
	}

	@RequestMapping(value = "value-dictionary-input")
	public String input(Model model, String id) {
		ValueDictionary item = null;
		if (id != null) {
			item = valueDictionaryService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("valueAttributes", valueAttributeService.getAll());
		return "/content/dic/value-dictionary-input";
	}

	@RequestMapping(value = "value-dictionary-save", method = RequestMethod.POST)
	public String add(ValueDictionary valueDictionary, String valueAttributeId, RedirectAttributes redirectAttributes) {
		valueDictionary.setValueAttribute(valueAttributeService.getById(valueAttributeId));
		if(valueDictionary.getId() == null){
			valueDictionaryService.add(valueDictionary);
		}else{
			valueDictionaryService.modify(valueDictionary);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:value-dictionary-list.do";
	}
	
	@RequestMapping(value = "value-dictionary-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			valueDictionaryService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:value-dictionary-list.do";
	}
	
	
	@RequestMapping(value = "value-dictionary-byvattrid")
	@ResponseBody
	public List<ValueDictionary> get(String vAttrId, String vClsId, String vColumn, String vFilterId) {
		if(!StringUtil.isNullOrEmpty(vAttrId)){
			String[] sArray = vAttrId.split(",");//兼容将vClsId,vColumn,vFilterId以,分割进行
			if(sArray.length == 1){
				return ValueDictionaryService.cacheDictionary.get(vAttrId);
			}else if(sArray.length == 2){
				return valueDictionaryService.getByValueClassId(sArray[0], sArray[1], null);
			}else if(sArray.length == 3){
				return valueDictionaryService.getByValueClassId(sArray[0], sArray[1], sArray[2]);
			}else{
				return new ArrayList<ValueDictionary>();
			}
			
		}else if(!StringUtil.isNullOrEmpty(vClsId) && !StringUtil.isNullOrEmpty(vColumn)){
			return valueDictionaryService.getByValueClassId(vClsId, vColumn, vFilterId);
		}else{
			return new ArrayList<ValueDictionary>();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "value-dictionary-to-add-dictionary")
	public Map<String, Object> toAddActionFiled(@RequestParam(value="valueAttributeId",required=true)String valueAttributeId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("hasAddData", ValueDictionaryService.cacheDictionary.get(valueAttributeId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "value-dictionary-done-add-dictionary")
	public String doneAddActionFiled(@RequestBody ValueDictionary valueDictionary, 
			@RequestParam(value="valueAttributeId",required=true)String valueAttributeId,
			String valueDictionaryId){
		valueDictionary.setValueAttribute(valueAttributeService.getById(valueAttributeId));
		if(StringUtil.isNullOrEmpty(valueDictionaryId)){
			valueDictionaryService.add(valueDictionary);
		}else{
			valueDictionary.setId(valueDictionaryId);
			valueDictionaryService.modify(valueDictionary);
		}
		
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "value-dictionary-to-revise-dictionary")
	public Map<String, Object> toReviseActionFiled(@RequestParam(value="valueDictionaryId",required=true)String valueDictionaryId){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("valueDictionary", valueDictionaryService.getById(valueDictionaryId));
		return map;
	}
	
	@ResponseBody
	@RequestMapping(value = "value-dictionary-done-remove-dictionary")
	public String doneRemoveFiled(@RequestParam(value="valueDictionaryId",required=true)String[] valueDictionaryId){
		valueDictionaryService.batchDeleteDictionary(valueDictionaryId);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "value-dictionary-to-import-dictionary", method = RequestMethod.POST)
	public String add(MultipartHttpServletRequest request, String valueAttributeId)
			throws IOException, FileUploadException {
		Map<String, String> map = FileUtil.upload("file", request);
		List<List<String>> values = IExmportUtil.importMultiColumn(new int[]{0, 1}, 1, map.get("fileData"));
		valueDictionaryService.batchAddDictionary(values);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(value = "value-dictionary-to-export-dictionary")
	public void export(String valueAttributeId, HttpServletResponse response)throws IOException, FileUploadException {
		List<List<String>> values = new ArrayList<List<String>>();
		List<ValueDictionary> valueDictionarys = valueDictionaryService.getAll();
		for(ValueDictionary valueDictionary : valueDictionarys){
			if(valueDictionary.getValueAttribute() == null){
				continue;
			}
			List<String> value = new ArrayList<String>();
			value.add(valueDictionary.getValueContent());
			value.add(valueDictionary.getValueAttribute().getId());
			values.add(value);
		}
		FileUtil.download(IExmportUtil.exportMultiColumn(new String[]{"值", "属性ID"}, values), "值.xls", response);
	}
}
