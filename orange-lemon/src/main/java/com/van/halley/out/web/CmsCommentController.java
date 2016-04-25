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
import com.van.halley.db.persistence.entity.CmsArticle;
import com.van.halley.db.persistence.entity.CmsComment;
import com.van.halley.db.persistence.entity.User;
import com.van.service.CmsArticleService;
import com.van.service.CmsCommentService;

@Controller
@RequestMapping(value = "/cms/")
public class CmsCommentController {
	@Autowired
	private CmsCommentService cmsCommentService;
	@Autowired
	private CmsArticleService cmsArticleService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "cms-comment-list")
	public String unread(Model model, CmsComment cmsComment,
			@ModelAttribute PageView pageView, HttpServletRequest request,
			HttpServletResponse response) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		pageView = cmsCommentService.query(pageView, cmsComment);

		model.addAttribute("pageView", pageView);
		return "/content/cms/cms-comment-list";
	}

	@RequestMapping(value = "cms-comment-input")
	public String input(Model model, String id) {
		CmsComment item = null;
		if (id != null) {
			item = cmsCommentService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/cms/cms-comment-input";
	}

	@RequestMapping(value = "cms-comment-save", method = RequestMethod.POST)
	public String add(CmsComment cmsComment, String cmsCatalogId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(cmsComment.getId() == null){
			cmsCommentService.add(cmsComment);
		}else{
			cmsCommentService.modify(cmsComment);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:cms-comment-list.do";
	}
	
	@RequestMapping(value = "cms-comment-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			cmsCommentService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:cms-comment-list.do";
	}
	
	@RequestMapping(value = "cms-comment-post-save", method = RequestMethod.POST)
	public String saveAsync(CmsComment cmsComment, String cmsArticleId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		CmsArticle cmsArticle = cmsArticleService.getById(cmsArticleId);
		if("T".equals(cmsArticle.getAllowComment())){
			cmsComment.setPoster((User)request.getSession().getAttribute("userSession"));
			if(cmsArticleId != null){
				cmsComment.setCmsArticle(cmsArticleService.getById(cmsArticleId));
			}
			
			if(cmsComment.getId() == null){
				cmsCommentService.add(cmsComment);
			}else{
				cmsCommentService.modify(cmsComment);
			}
			messageHelper.addFlashMessage(redirectAttributes,"评论成功");
		}else{
			messageHelper.addFlashMessage(redirectAttributes,"本文章不允许评论");
		}
		
		return "redirect:cms-article-view.do?id=" + cmsArticleId;
	}

}
