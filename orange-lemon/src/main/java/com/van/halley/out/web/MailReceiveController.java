package com.van.halley.out.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.MailReceive;
import com.van.halley.db.persistence.entity.User;
import com.van.service.MailReceiveService;

@Controller
@RequestMapping(value = "/out/")
public class MailReceiveController {
	@Autowired
	private MailReceiveService mailReceiveService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "mail-receive-list")
	public String unread(Model model, MailReceive mailReceive,
			@ModelAttribute PageView pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(pageView.getOrderBy() == null){
			pageView.setOrderBy("RECEIVE_TIME");
			pageView.setOrder("DESC");
		}
		mailReceive.setUserId(((User)request.getSession().getAttribute("userSession")).getId());
		pageView = mailReceiveService.query(pageView, mailReceive);

		model.addAttribute("pageView", pageView);
		return "/content/out/mail-receive-list";
	}

	@RequestMapping(value = "mail-receive-input")
	public String input(Model model, String id) {
		MailReceive item = null;
		if (id != null) {
			item = mailReceiveService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/mail-receive-input";
	}
	
	@RequestMapping(value = "mail-receive-view")
	public String view(Model model, String id) {
		MailReceive item = mailReceiveService.getById(id);
		if(item.getStatus().equals("未读")){
			item.setStatus("已读");
			mailReceiveService.modify(item);
		}
		
		model.addAttribute("item", item);
		return "/content/out/mail-receive-view";
	}

	@RequestMapping(value = "mail-receive-save", method = RequestMethod.POST)
	public String add(MailReceive mailReceive, RedirectAttributes redirectAttributes) {
		if(mailReceive.getId() == null){
			mailReceiveService.add(mailReceive);
		}else{
			mailReceiveService.modify(mailReceive);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:mail-receive-list.do";
	}
	
	@RequestMapping(value = "mail-receive-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			mailReceiveService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:mail-receive-list.do";
	}

}
