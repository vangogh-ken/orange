package com.van.halley.out.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.mail.MailService;
import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.MailReceive;
import com.van.halley.db.persistence.entity.MailSend;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.db.persistence.entity.UserBase;
import com.van.service.MailReceiveService;
import com.van.service.MailSendService;
import com.van.service.UserBaseService;

@Controller
@RequestMapping(value = "/out/")
public class MailSendController {
	@Autowired
	private MailSendService mailSendService;
	@Autowired
	private MailReceiveService mailReceiveService;
	//@Autowired
	//private MailService mailService;
	@Autowired
	private UserBaseService userBaseService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "mail-send-list")
	public String unread(Model model, MailSend mailSend,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		mailSend.setUserId(((User)request.getSession().getAttribute("userSession")).getId());
		pageView = mailSendService.query(pageView, mailSend);

		model.addAttribute("pageView", pageView);
		return "/content/out/mail-send-list";
	}

	@RequestMapping(value = "mail-send-input")
	public String input(Model model, String id) {
		MailSend item = null;
		if (id != null) {
			item = mailSendService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/mail-send-input";
	}
	
	/**
	 * 回复邮件
	 */
	@RequestMapping(value = "mail-reply-input")
	public String reply(Model model, @RequestParam(required=true, value="id") String id) {
		MailReceive	replyMail = mailReceiveService.getById(id);
		model.addAttribute("replyMail", replyMail);
		return "/content/out/mail-reply-input";
	}
	
	@RequestMapping(value = "mail-send-input-by-userid")
	public String sendByUserId(Model model, @RequestParam(required=true, value="userId") String userId) {
		String addressTo =  userBaseService.getByUserId(userId).getMailAddress();
		MailSend item = new MailSend();
		item.setAddressTo(addressTo);
		model.addAttribute("item", item);
		return "/content/out/mail-send-input";
	}

	@RequestMapping(value = "mail-send-save", method = RequestMethod.POST)
	public String add(MailSend mailSend, RedirectAttributes redirectAttributes) {
		if(mailSend.getId() == null){
			mailSendService.add(mailSend);
		}else{
			mailSendService.modify(mailSend);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:mail-send-list.do";
	}
	
	@RequestMapping(value = "mail-send-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			mailSendService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:mail-send-list.do";
	}
	
	@RequestMapping(value = "mail-send-to")
	public String send(Model model, MailSend mail, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		UserBase userBase = userBaseService.getByUserId(
				((User)request.getSession().getAttribute("userSession")).getId());
		if(userBase.getMailAddress() == null || userBase.getMailPassword() == null){
			messageHelper.addFlashMessage(redirectAttributes,"获取不到个人邮件信息，发送失败");
		}else{
			
			/*boolean flag = mailService.send(userBase.getMailAddress(), 
					userBase.getMailPassword(), mail.getAddressTo(), mail.getSubject(), 
					mail.getContent() , userBase.getUserId());
			if(flag == true){
				messageHelper.addFlashMessage(redirectAttributes,"发送成功");
			}else{
				messageHelper.addFlashMessage(redirectAttributes,"发送失败");
			}*/
			
		}
		return "redirect:mail-send-list.do";
	}
	
	@RequestMapping(value = "mail-send-toss")
	public String sendMail(Model model, String userIds, String subject, String content, RedirectAttributes redirectAttributes) {
		
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:mail-send-list.do";
	}

}
