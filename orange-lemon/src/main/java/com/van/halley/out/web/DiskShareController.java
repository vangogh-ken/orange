package com.van.halley.out.web;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.DiskShare;
import com.van.halley.db.persistence.entity.User;
import com.van.service.DiskShareService;

@Controller
@RequestMapping(value = "/disk/")
public class DiskShareController {
	@Autowired
	private DiskShareService diskShareService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "disk-share-list")
	public String list(Model model, DiskShare diskShare, @ModelAttribute PageView<DiskShare> pageView, HttpSession session) {
		if (pageView == null) {
			pageView = new PageView<DiskShare>(1);
		}
		
		String filterText = "ID IN(SELECT OUT_DISK_SHARE_ID FROM OUT_DISK_ACL WHERE ACCESSARY_ID='" + ((User)session.getAttribute("userSession")).getId() + "') AND SHARE_TIME < SYSDATE() AND EXPIRE_TIME > SYSDATE()";
		pageView.setFilterText(filterText);
		
		pageView = diskShareService.query(pageView, diskShare);
		model.addAttribute("pageView", pageView);
		return "/content/out/disk-share-list";
	}

	@RequestMapping(value = "disk-share-input")
	public String input(Model model, String id) {
		DiskShare item = null;
		if (id != null) {
			item = diskShareService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/disk-share-input";
	}

	@RequestMapping(value = "disk-share-save", method = RequestMethod.POST)
	public String add(DiskShare diskShare, RedirectAttributes redirectAttributes) {
		if(diskShare.getId() == null){
			diskShareService.add(diskShare);
		}else{
			diskShareService.modify(diskShare);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:disk-share-list.do";
	}
	
	@RequestMapping(value = "disk-share-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			diskShareService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:disk-share-list.do";
	}
}
