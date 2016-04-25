package com.van.halley.out.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.van.halley.db.persistence.entity.DocInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.file.FileUtil;
import com.van.service.DocInfoService;
import com.van.service.DocTypeService;
import com.van.service.OrgEntityService;
import com.van.service.UserService;

@Controller
@RequestMapping(value = "/out/")
public class DocInfoController {
	private static Logger logger = LoggerFactory.getLogger(DocInfoController.class);
	@Autowired
	private DocInfoService docInfoService;
	@Autowired
	private DocTypeService docTypeService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrgEntityService orgEntityService;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "doc-info-list")
	public String query(Model model, DocInfo docInfo, String typeId,
			@ModelAttribute PageView pageView, HttpServletRequest request) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		if(docInfo == null){
			docInfo = new DocInfo();
		}
		if(typeId != null){
			docInfo.setDocType(docTypeService.getById(typeId));
		}
		docInfo.setOwner((User)request.getSession().getAttribute("userSession"));
		
		pageView = docInfoService.query(pageView, docInfo);
		
		model.addAttribute("docTypes", docTypeService.getAll());
		model.addAttribute("users", userService.getAll());
		model.addAttribute("orgEntities", orgEntityService.getAll());
		model.addAttribute("pageView", pageView);
		return "/content/out/doc-info-list";
	}

	@RequestMapping(value = "doc-info-input")
	public String input(Model model, String id) {
		DocInfo item = null;
		if (id != null) {
			item = docInfoService.getById(id);
		}
		model.addAttribute("item", item);
		model.addAttribute("docTypes", docTypeService.getAll());
		return "/content/out/doc-info-input";
	}


	@RequestMapping(value = "doc-info-save", method = RequestMethod.POST)
	public String add(DocInfo docInfo, String typeId, MultipartHttpServletRequest request){
		Map<String, String> map = FileUtil.upload("muiltFile", request);
		docInfo.setDocType(docTypeService.getById(typeId));
		if (map != null) {
			docInfo.setOwner((User)request.getSession().getAttribute("userSession"));
			docInfo.setDocName(map.get("fileName"));
			docInfo.setDocData(map.get("fileData"));
			docInfo.setCreateTime(new Date());
			docInfoService.add(docInfo);
		}else{
			docInfoService.modify(docInfo);
		}

		return "redirect:doc-info-list.do";
	}

	// 批量上传文件目前只能采取使用压缩文件的方式进行。目前只支持zip格式。文件必须以"真名_"结尾,并且只能有一个_
	@RequestMapping(value = "doc-info-zip-deliver", method = RequestMethod.POST)
	public String zipDeliver(MultipartHttpServletRequest request,
			RedirectAttributes redirectAttributes){
		List<DocInfo> list = FileUtil.zipToFileByPrefixName("muiltFile", request);
		if(list == null){
			messageHelper.addFlashMessage(redirectAttributes, "上传ZIP文件不正确或有子文件找不到对应用户");
			return "redirect:doc-info-list.do";
		}else{
			for(int i=0, size = list.size(); i<size; i++){
				docInfoService.add(list.get(i));
			}
			messageHelper.addFlashMessage(redirectAttributes, "分发成功");
			return "redirect:doc-info-list.do";
		}
	}

	@RequestMapping(value = "doc-info-remove")
	public String remove(Model model, String[] selectedItem,
			RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			docInfoService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:doc-info-list.do";
	}

	@RequestMapping(value = "doc-info-delete")
	public String delete(Model model, String id,
			RedirectAttributes redirectAttributes) {
		docInfoService.delete(id);
		messageHelper.addFlashMessage(redirectAttributes, "删除成功");
		return "redirect:doc-info-list.do";
	}

	@RequestMapping(value = "doc-info-deliver")
	public String deliver(String sendType, String receiverId, String selectedItem,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if (sendType.equals("to-one")) {
			docInfoService.toOne(receiverId, selectedItem);
		}else if (sendType.equals("to-orgentity")) {
			docInfoService.toOrgEntity(receiverId, selectedItem);
		}else if (sendType.equals("to-underling")) {
			docInfoService.toUnderling(
					((User)request.getSession().getAttribute("userSession")).getId(), selectedItem);
		}else if (sendType.equals("to-all")) {
			docInfoService.toAll(selectedItem);
		}

		messageHelper.addFlashMessage(redirectAttributes, "共享成功");
		return "redirect:doc-info-list.do";
	}

	// 附件下载
	@RequestMapping(value = "doc-info-down")
	public void down(HttpServletRequest request, HttpServletResponse response,
			String id) throws IOException, SQLException {
		DocInfo doc = docInfoService.getById(id);
		String uuidFileName = doc.getDocData();
		String realFileName = doc.getDocName();
		if (FileUtil.download(uuidFileName, realFileName, response)) {
			logger.error("下载成功", realFileName);
		} else {
			logger.error("下载失败", realFileName);
		}
	}
	
	///////////////////////////////// 共享     /////////////////////////////////////////////
	
	@RequestMapping(value = "doc-info-participate-list")
	public String queryParticipate(Model model, DocInfo docinfo,
			@ModelAttribute PageView pageView) {
		if (pageView == null) {
			pageView = new PageView(1);
		}
		docinfo.setParticipate("T");
		pageView = docInfoService.query(pageView, docinfo);
		
		model.addAttribute("pageView", pageView);
		return "/content/out/doc-info-participate-list";
	}
	
	@RequestMapping(value = "doc-info-participate")
	public String participate(Model model, String id, RedirectAttributes redirectAttributes) {
		DocInfo docInfo = docInfoService.getById(id);
		docInfo.setParticipate("T");
		docInfoService.modify(docInfo);
		messageHelper.addFlashMessage(redirectAttributes, "共享成功");
		return "redirect:doc-info-list.do";
	}
	
	@RequestMapping(value = "doc-info-disparticipate")
	public String disparticipate(Model model, String id, RedirectAttributes redirectAttributes) {
		DocInfo docInfo = docInfoService.getById(id);
		docInfo.setParticipate("F");
		docInfoService.modify(docInfo);
		messageHelper.addFlashMessage(redirectAttributes, "取消共享成功");
		return "redirect:doc-info-list.do";
	}

}
