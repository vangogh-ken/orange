package com.van.halley.out.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.db.persistence.entity.ForumPost;
import com.van.halley.db.persistence.entity.ForumTopic;
import com.van.halley.db.persistence.entity.User;
import com.van.service.ForumPostService;
import com.van.service.ForumTopicService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/out/")
public class ForumController {
	@Autowired
	private UserService userService;
	@Autowired
	private ForumTopicService forumTopicService;
	@Autowired
	private ForumPostService forumPostService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "forum-topic-list")
	public String unread(Model model, ForumTopic forumTopic,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = forumTopicService.query(pageView, forumTopic);

		model.addAttribute("pageView", pageView);
		return "/content/out/forum-topic-list";
	}

	@RequestMapping(value = "forum-topic-input")
	public String input(Model model, String id) {
		ForumTopic item = null;
		if (id != null) {
			item = forumTopicService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/forum-topic-input";
	}
	/**
	@RequestMapping(value = "forum-topic-delete")
	public String delete(Model model, String id, RedirectAttributes redirectAttributes) {
		forumTopicService.delete(id);
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:forum-topic-list.do";
	}
	**/
	@RequestMapping(value = "forum-topic-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			forumTopicService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:forum-topic-list.do";
	}

	@RequestMapping(value = "forum-topic-save", method = RequestMethod.POST)
	public String add(ForumTopic forumTopic, HttpServletRequest request, 
			RedirectAttributes redirectAttributes) {
		User user = (User) request.getSession().getAttribute("userSession");
		forumTopic.setUser(user);
		forumTopicService.add(forumTopic);
		messageHelper.addFlashMessage(redirectAttributes,"发帖成功");
		return "redirect:forum-topic-list.do";
	}
	
	@RequestMapping(value = "forum-post-input")
	public String post(Model model, String forumTopicId) {
		ForumTopic forumTopic  = forumTopicService.getById(forumTopicId);
		forumTopic.setHitCount(forumTopic.getHitCount() + 1);
		forumTopicService.modify(forumTopic);
		model.addAttribute("forumTopic", forumTopic);
		
		PageView pageView = new PageView(1);
		pageView.setOrderBy("CREATE_TIME");
		pageView.setOrder("DESC");
		model.addAttribute("forumTopics", forumTopicService.query(pageView, new ForumTopic()).getResults());
		return "/content/out/forum-post-input";
	}
	
	@RequestMapping(value = "forum-post-save")
	public String postSave(Model model, String forumTopicId, 
			ForumPost forumPost, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		ForumTopic forumTopic  = forumTopicService.getById(forumTopicId);
		forumTopic.setPostCount(forumTopic.getPostCount() + 1);
		forumTopicService.modify(forumTopic);
		User user = (User) request.getSession().getAttribute("userSession");
		forumPost.setForumTopicId(forumTopicId);
		forumPost.setUser(user);
		forumPostService.add(forumPost);
		messageHelper.addFlashMessage(redirectAttributes,"回复成功");
		return "redirect:forum-post-input.do?forumTopicId=" + forumTopicId;
	}

}
