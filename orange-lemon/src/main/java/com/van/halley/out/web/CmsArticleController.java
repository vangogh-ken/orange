package com.van.halley.out.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.core.util.StringUtils;
import com.van.halley.db.persistence.entity.CmsArticle;
import com.van.halley.db.persistence.entity.CmsCatalog;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.service.CmsArticleService;
import com.van.service.CmsCatalogService;

@Controller
@RequestMapping(value = "/cms/")
public class CmsArticleController {
	@Autowired
	private CmsArticleService cmsArticleService;
	@Autowired
	private CmsCatalogService cmsCatalogService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "cms-article-list")
	public String unread(Model model, CmsArticle cmsArticle, String catalogName, @ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		
		if(!StringUtil.isNullOrEmpty(catalogName)){
			String filterText = " CATALOG_ID IN(SELECT ID FROM OUT_CMS_CATALOG WHERE NAME LIKE '%" + catalogName + "%')";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
		}
		
		if(!StringUtil.isNullOrEmpty(cmsArticle.getTitle())){
			String filterText = " TITLE LIKE '%" + cmsArticle.getTitle() + "%'";
			if(StringUtil.isNullOrEmpty(pageView.getFilterText())){
				pageView.setFilterText(filterText);
			}else{
				pageView.setFilterText(pageView.getFilterText() + " AND " + filterText);
			}
			cmsArticle.setTitle(null);
		}
		
		pageView = cmsArticleService.query(pageView, cmsArticle);
		model.addAttribute("pageView", pageView);
		return "/content/cms/cms-article-list";
	}

	@RequestMapping(value = "cms-article-input")
	public String input(Model model, String id) {
		CmsArticle item = null;
		if (id != null) {
			item = cmsArticleService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("catalogs", cmsCatalogService.getAll());
		return "/content/cms/cms-article-input";
	}

	@RequestMapping(value = "cms-article-save", method = RequestMethod.POST)
	public String add(CmsArticle cmsArticle, String cmsCatalogId, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		cmsArticle.setCmsCatalog(cmsCatalogService.getById(cmsCatalogId));
		cmsArticle.setPublisher((User) request.getSession().getAttribute("userSession"));
		if(StringUtils.isEmpty(cmsArticle.getStatus())){
			cmsArticle.setStatus("未发布");
		}
		if(cmsArticle.getId() == null){
			cmsArticleService.add(cmsArticle);
		}else{
			cmsArticleService.modify(cmsArticle);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:cms-article-list.do";
	}
	
	@RequestMapping(value = "cms-article-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			cmsArticleService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:cms-article-list.do";
	}
	
	@RequestMapping(value = "cms-article-publish")
	public String publish(Model model, String id, RedirectAttributes redirectAttributes) {
		CmsArticle cmsArticle = cmsArticleService.getById(id);
		cmsArticle.setStatus("已发布");
		cmsArticle.setPublishTime(new Date());
		cmsArticleService.modify(cmsArticle);
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:cms-article-list.do";
	}
	
	@RequestMapping(value = "cms-article-unpublish")
	public String unpublish(Model model, String id, RedirectAttributes redirectAttributes) {
		CmsArticle cmsArticle = cmsArticleService.getById(id);
		cmsArticle.setStatus("待发布");
		cmsArticle.setCloseTime(new Date());
		cmsArticleService.modify(cmsArticle);
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:cms-article-list.do";
	}
	
	@RequestMapping(value = "cms-article-image-upload")
	public String imageUpload(MultipartHttpServletRequest request, 
			RedirectAttributes redirectAttributes) throws IllegalStateException, IOException {
		//Map<String, String> map = fileSupport.upload("muiltFile", request);
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:cms-article-list.do";
	}
	
	@RequestMapping(value = "cms-article-list-view")
	public String viewList(Model model, String cmsCatalogId,
			RedirectAttributes redirectAttributes){
		if(cmsCatalogId != null){
			CmsCatalog cmsCatalog = cmsCatalogService.getById(cmsCatalogId);
			CmsArticle filter = new CmsArticle();
			filter.setCmsCatalog(cmsCatalog);
			
			List<CmsCatalog> cmsCatalogs = new ArrayList<CmsCatalog>();
			cmsCatalogs.add(cmsCatalog);
			
			model.addAttribute("cmsArticles", cmsArticleService.queryForList(filter));
			model.addAttribute("cmsCatalogs", cmsCatalogs);
		}else{
			model.addAttribute("cmsArticles", cmsArticleService.getAll());
			model.addAttribute("cmsCatalogs", cmsCatalogService.getAll());
		}
		
		return "/content/cms/cms-article-list-view";
	}
	
	@RequestMapping(value = "cms-article-view")
	public String viewSingle(Model model, String id,
			RedirectAttributes redirectAttributes){
		CmsArticle cmsArticle = cmsArticleService.getById(id);
		cmsArticle.setViewCount(cmsArticle.getViewCount() + 1);
		cmsArticleService.modify(cmsArticle);
		model.addAttribute("item", cmsArticle);
		return "/content/cms/cms-article-view";
	}
	
	@RequestMapping(value = "cms-article-allow-comment")
	public String allowComment(Model model, String id, RedirectAttributes redirectAttributes) {
		CmsArticle cmsArticle = cmsArticleService.getById(id);
		cmsArticle.setAllowComment("T");
		cmsArticleService.modify(cmsArticle);
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:cms-article-list.do";
	}
	
	@RequestMapping(value = "cms-article-unallow-comment")
	public String unallowComment(Model model, String id, RedirectAttributes redirectAttributes) {
		CmsArticle cmsArticle = cmsArticleService.getById(id);
		cmsArticle.setAllowComment("F");
		cmsArticleService.modify(cmsArticle);
		messageHelper.addFlashMessage(redirectAttributes,"操作成功");
		return "redirect:cms-article-list.do";
	}

}
