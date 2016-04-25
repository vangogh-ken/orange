package com.van.halley.auth.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ConstantAuth;
import com.van.service.ConstantAuthService;

@Controller
@RequestMapping(value = "/auth/")
public class ConstantAuthController {
	
	@Autowired
	private ConstantAuthService constantAuthService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "constant-auth-list")
	public String queryModel(Model model, ConstantAuth constantAuth, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		constantAuthService.query(pageView, constantAuth);
		model.addAttribute("pageView", pageView);
		return "/content/auth/constant-auth-list";
	}

	@RequestMapping(value = "constant-auth-input")
	public String input(Model model, String id) {
		ConstantAuth item = null;
		if(id != null){
			item = constantAuthService.getById(id);
		}
		
		model.addAttribute("item", item);
		return "/content/auth/constant-auth-input";
	}

	@RequestMapping(value = "constant-auth-save")
	public String addModel(ConstantAuth constantAuth) {
		if(constantAuth.getId() != null){
			constantAuthService.modify(constantAuth);
		}else{
			constantAuthService.add(constantAuth);
		}
		
		return "redirect: constant-auth-list.do";
	}

	@RequestMapping(value = "constant-auth-remove")
	public String delete(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			constantAuthService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:constant-auth-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "constant-auth-get-all")
	public List<ConstantAuth> get(String positionId) {
		return constantAuthService.getAll();
	}

}
