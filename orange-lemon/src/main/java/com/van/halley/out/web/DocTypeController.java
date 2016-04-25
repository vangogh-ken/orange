package com.van.halley.out.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.DocType;
import com.van.service.DocTypeService;

@Controller
@RequestMapping(value = "/out/")
public class DocTypeController {
	@Autowired
	private DocTypeService docTypeService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "doc-type-list")
	public String query(Model model, DocType docType, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = docTypeService.query(pageView, docType);
		model.addAttribute("pageView", pageView);
		return "/content/out/doc-type-list";
	}

	@RequestMapping(value = "doc-type-input")
	public String input(Model model, String id) {
		DocType item = null;
		if (id != null) {
			item = docTypeService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/doc-type-input";
	}


	@RequestMapping(value = "doc-type-save", method = RequestMethod.POST)
	public String add(DocType docType){
		if(docType.getId() == null){
			docTypeService.add(docType);
		}else{
			docTypeService.modify(docType);
		}
		return "redirect:doc-type-list.do";
	}


	@RequestMapping(value = "doc-type-remove")
	public String remove(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			docTypeService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:doc-type-list.do";
	}

}
