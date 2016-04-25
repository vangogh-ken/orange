package com.van.halley.out.web;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.van.halley.core.page.PageView;
import com.van.halley.core.store.MultipartFileDataSource;
import com.van.halley.core.util.MessageHelper;
import com.van.halley.core.util.ServletUtils;
import com.van.halley.db.persistence.entity.DiskInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.DiskInfoService;

@Controller
@RequestMapping(value = "/disk/")
public class DiskInfoController {
	@Autowired
	private DiskInfoService diskInfoService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MessageHelper messageHelper;

	@RequestMapping(value = "disk-info-list")
	public String list(Model model, DiskInfo diskInfo, 
			@RequestParam(name="display", defaultValue="grid", required = false)String display, 
			@RequestParam(name="diskDir", defaultValue="", required = false)String diskDir, @ModelAttribute PageView<DiskInfo> pageView, HttpSession session) {
		if (pageView == null) {
			pageView = new PageView<DiskInfo>(1);
		}
		diskInfo.setFileDir(diskDir);
		diskInfo.setStatus(DiskInfo.Status.ACTIVE);
		String filterText = "CREATOR_ID ='" + ((User)session.getAttribute("userSession")).getId() + "'";
		pageView.setFilterText(filterText);
		
		pageView = diskInfoService.query(pageView, diskInfo);
		model.addAttribute("pageView", pageView);
		model.addAttribute("display", display);
		model.addAttribute("diskDir", diskDir);
		return "/content/out/disk-info-list";
	}
	
	@RequestMapping(value = "disk-info-list-previous")
	public String previous(@RequestParam(name="display", defaultValue="list", required = false)String display,
			@RequestParam(name="diskDir", defaultValue="", required = false)String diskDir) throws UnsupportedEncodingException {
		if (StringUtils.isBlank(diskDir)) {
            return "redirect:disk-info-list.do";
        }
        return "redirect:disk-info-list.do?display=" + display + "&diskDir=" + URLEncoder.encode(diskDir.substring(0, diskDir.lastIndexOf("/")), "UTF-8");
	}

	@RequestMapping(value = "disk-info-input")
	public String input(Model model, String id) {
		DiskInfo item = null;
		if (id != null) {
			item = diskInfoService.getById(id);
		}
		model.addAttribute("item", item);
		return "/content/out/disk-info-input";
	}

	@RequestMapping(value = "disk-info-save", method = RequestMethod.POST)
	public String add(DiskInfo diskInfo, RedirectAttributes redirectAttributes) {
		if(diskInfo.getId() == null){
			diskInfoService.add(diskInfo);
		}else{
			diskInfoService.modify(diskInfo);
		}
		messageHelper.addFlashMessage(redirectAttributes,"保存成功");
		return "redirect:disk-info-list.do";
	}
	
	@RequestMapping(value = "disk-info-remove")
	public String remove(Model model, String[] selectedItem, RedirectAttributes redirectAttributes) {
		for (String id : selectedItem) {
			diskInfoService.delete(id);
		}
		messageHelper.addFlashMessage(redirectAttributes,"删除成功");
		return "redirect:disk-info-list.do";
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-to-view-disk")
	public Map<String, Object> toViewDisk(String diskInfoId) {
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isBrowse = false;
		DiskInfo diskInfo = diskInfoService.getById(diskInfoId);
		String[] ss = new String[]{"doc", "docx", "xls", "xlsx", "ppt", "pptx", "pdf", "jpg", "png", "gif","txt"};
		List<String> list = Arrays.asList(ss);
		if(list.contains(diskInfo.getFileSuffix())){
			isBrowse = true;
		}
		result.put("isBrowse", isBrowse);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-to-browse-disk")
	public String toBrowseDisk(String diskInfoId) throws IOException {
		InputStream in = null;
		OutputStream out = null;
		DiskInfo diskInfo = diskInfoService.getById(diskInfoId);
		String fileName = StringUtil.getUUID() + "." + diskInfo.getFileSuffix();
		try{
			in = diskInfoService.doneLoadDisk(new String[]{diskInfoId});
			out = new FileOutputStream(new File(FileUtil.attachmentPath, fileName));
			IOUtils.copy(in, out);
			out.flush();
		}finally{
			if(in != null){
				in.close();
			}
			if(out != null){
				out.close();
			}
		}
		return fileName;
	}
	
	@RequestMapping(value = "disk-info-done-view-disk")
	public String doneViewDisk(Model model, String diskInfoId) {
		model.addAttribute("diskInfo", diskInfoService.getById(diskInfoId));
		return "/content/out/disk-info-view";
	}
	
	@RequestMapping("disk-info-done-upload-disk")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,
            @RequestParam("diskDir") String diskDir) throws Exception {
		diskInfoService.doneCreateDisk(file.getOriginalFilename(), file.getSize(), diskDir, new MultipartFileDataSource(file));
		return "{\"success\":true}";
    }
	
	@RequestMapping("disk-info-done-download-disk")
    public void download(@RequestParam("diskInfoId") String[] diskInfoId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DiskInfo diskInfo = diskInfoService.getById(diskInfoId[0]);
		if(diskInfoId.length == 1){
			ServletUtils.setFileDownloadHeader(request, response, "dir".equals(diskInfo.getFileSuffix()) ? (diskInfo.getFileName() + ".zip") : diskInfo.getFileName());
		}else{
			ServletUtils.setFileDownloadHeader(request, response, (StringUtils.isNotBlank(diskInfo.getFileDir()) ? diskInfo.getFileDir() : "压缩文件") + ".zip");
		}
		
		InputStream in = null;
		OutputStream out = null;
		try{
			in = diskInfoService.doneLoadDisk(diskInfoId);
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			out.flush();
		}finally{
			if(in != null){
				in.close();
			}
		}
    }
	
	@ResponseBody
	@RequestMapping(value = "disk-info-done-create-dir")
	public String doneCreateDir(String diskName, String diskDir) {
		if(diskInfoService.doneCreateDir(diskName, diskDir)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-done-delete-disk")
	public String doneDeleteDisk(String[] diskInfoId) {
		if(diskInfoService.doneDeleteDisk(diskInfoId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-done-copy-to")
	public String doneCopyTo(String[] diskInfoId, String diskDirId) {
		if(diskInfoService.doneCopyTo(diskInfoId, diskDirId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-done-move-to")
	public String doneMoveTo(String[] diskInfoId, String diskDirId) {
		if(diskInfoService.doneMoveTo(diskInfoId, diskDirId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-to-rename-to")
	public Map<String, Object> toRenameTo(String diskInfoId) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("diskInfo", diskInfoService.getById(diskInfoId));
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-done-rename-to")
	public String doneRenameTo(String diskInfoId, String fileName) {
		if(diskInfoService.doneRenameTo(diskInfoId, fileName)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-to-share-disk")
	public Map<String, Object> toShareDisk() {
		return diskInfoService.toShareDisk();
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-done-share-disk")
	public String doneShareDisk(String[] selectedItem, String[] accessaryId, String shareType, Date shareTime, Date expireTime) {
		if(diskInfoService.doneShareDisk(selectedItem, accessaryId, shareType, shareTime, expireTime)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-recall-share-disk-by-disk-share-id")
	public String recallShareDiskByDiskShareId(String[] diskShareId) {
		if(diskInfoService.recallShareDiskByDiskShareId(diskShareId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-recall-share-disk-by-disk-info-id")
	public String recallShareDiskByDiskInfoId(String[] diskInfoId) {
		if(diskInfoService.recallShareDiskByDiskInfoId(diskInfoId)){
			return "success";
		}else{
			return "error";
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "disk-info-check-file-name")
	public boolean checkName(@RequestParam(required=true, value="fileName") String fileName,  String id) {
		if(id != null){
			String sql = "SELECT COUNT(*) AS count FROM OUT_DISK_INFO WHERE FILE_NAME = ? AND ID <> ?";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, fileName, id);
			return count == 0;
		}else{
			String sql = "SELECT COUNT(*) AS count FROM OUT_DISK_INFO WHERE FILE_NAME = ? ";
			int count = jdbcTemplate.queryForObject(sql, Integer.class, fileName);
			return count == 0;
		}
	}

}
