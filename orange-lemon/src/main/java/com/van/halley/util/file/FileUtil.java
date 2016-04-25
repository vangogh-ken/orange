package com.van.halley.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.van.core.spring.ApplicationContextHelper;
import com.van.core.spring.ApplicationPropertiesFactoryBean;
import com.van.halley.db.persistence.entity.DocInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.swf.Office2PdfUtil;
import com.van.halley.util.swf.Pdf2SwfUtil;
import com.van.service.DocTypeService;
import com.van.service.UserService;

/**
 * @author Vangogh 文件下载
 */
public class FileUtil {
	private static ApplicationPropertiesFactoryBean applicationProperties;
	private static UserService userService;
	private static DocTypeService docTypeService;
	
	private static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	/**
	 * 附件内容实际存放路径
	 */
	public static String attachmentPath;
	/**
	 * 项目实际路径
	 */
	public static String projectPath;
	
	static {
		userService = ApplicationContextHelper.getBean("userService");
		docTypeService = ApplicationContextHelper.getBean("docTypeService");
		applicationProperties = ApplicationContextHelper.getBean(ApplicationPropertiesFactoryBean.class);
		attachmentPath = applicationProperties.getProperties().getProperty("attachment.filePath");
		
		//在线预览所用到的服务路径
		Office2PdfUtil.OFFICE_PATH = applicationProperties.getProperties().getProperty("view.openoffice.path");
		Pdf2SwfUtil.PDF2SWF_PATH = applicationProperties.getProperties().getProperty("view.swftools.path");
	}

	/**
	 * 下载文件
	 */
	public static boolean download(String fileData, String fileName, HttpServletResponse response){
		boolean flag = true;
		FileInputStream in = null;
		ServletOutputStream servletOS = null;
		try{
			if (!StringUtil.isNullOrEmpty(fileData) && !StringUtil.isNullOrEmpty(fileName)) {
				in = FileUtils.openInputStream(new File(attachmentPath, fileData));
				int len = in.available();
				response.reset();
				response.setHeader("Connection", "close");
				response.setContentType("application/x-msdownload");
				response.addHeader("Content-Type;charset=UTF-8","application/octet-stream");
				response.addHeader("Content-Length", "" + len);
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));

				byte[] cache = new byte[1024 * 1024];
				servletOS = response.getOutputStream();
				while (in.read(cache) > -1) {
					servletOS.write(cache);
				}
				servletOS.flush();
			}
		}catch(IOException e){
			logger.error("文件下载出错， 文件 {}， 错误 {}", fileName, e);
			flag = false;
		}finally{
			try{
				if(in != null){
					in.close();
				}
				if(servletOS != null){
					servletOS.close();
				}
			}catch(IOException e){
				logger.error("文件下载出错， 文件 {}， 错误 {}", fileName, e);
			}
		}

		return flag;
	}

	public static boolean download(InputStream in, String fileName, HttpServletResponse response){
		boolean flag = true;
		ServletOutputStream servletOS = null;
		try{
			if (!StringUtil.isNullOrEmpty(fileName) && in != null) {
				int len = in.available();
				response.reset();
				response.setHeader("Connection", "close");
				response.setContentType("application/x-msdownload");
				response.addHeader("Content-Type;charset=UTF-8","application/octet-stream");
				response.addHeader("Content-Length", "" + len);
				response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8"));
				
				byte[] cache = new byte[1024 * 1024];
				servletOS = response.getOutputStream();
				while (in.read(cache) > -1) {
					servletOS.write(cache);
				}
				servletOS.flush();
			} else {
				flag = false;
			}
		}catch(IOException e){
			logger.error("文件下载出错， 文件 {}， 错误 {}", fileName, e);
			flag = false;
		}finally{
			try{
				if(in != null){
					in.close();
				}
				if(servletOS != null){
					servletOS.close();
				}
			}catch(IOException e){
				logger.error("文件下载出错， 文件 {}， 错误 {}", fileName, e);
			}
		}
		return flag;
	}

	/**
	 * 上传文件
	 * @param targetFileParam
	 * @param request
	 */
	public static Map<String, String> upload(String targetFileParam,
			MultipartHttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		MultipartFile multipartFile = request.getFile(targetFileParam);

		if (multipartFile == null || multipartFile.isEmpty()) {
			return null;
		} else {

			logger.info("文件长度: {}", multipartFile.getSize());
			logger.info("文件类型: {}", multipartFile.getContentType());
			logger.info("文件名称: {}", multipartFile.getName());
			logger.info("文件原名: {}", multipartFile.getOriginalFilename());
			
			String orgFileName = multipartFile.getOriginalFilename();
			String uuidName = StringUtil.getUUID();
			String suffix = orgFileName.contains("\\.") ? ""
					: "." + orgFileName.split("\\.")[orgFileName.split("\\.").length - 1];
			File dir = new File(attachmentPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}

			try {
				multipartFile.transferTo(new File(attachmentPath, uuidName + suffix));
			} catch (IllegalStateException e) {
				logger.error("Upload file error", e);
			} catch (IOException e) {
				logger.error("Upload file error", e);
			}

			map.put("fileData", uuidName + suffix);
			map.put("fileName", orgFileName);

			return map;
		}

	}
	
	/**
	 * 保存附件,返回文件名和数据文件
	 */
	public static Map<String, String> saveAttachemnt(String fileName, InputStream is){
		Map<String, String> map = new HashMap<String, String>();
		String uuid = StringUtil.getUUID();
		String suffix = fileName.contains("\\.") ? "" : "." + fileName.split("\\.")[fileName.split("\\.").length - 1];
		File dir = new File(attachmentPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			FileUtils.copyInputStreamToFile(is, new File(attachmentPath, uuid + suffix));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		map.put("fileData", uuid + suffix);
		map.put("fileName", fileName);
		return map;
	}
	
	/**
	 * 通过文件数据获取这个文件的对象
	 */
	public static File getAttachment(String fileData){
		return FileUtils.getFile(attachmentPath, fileData);
	}

	/**
	 * 将子文件存放之后，返回存放之后的fileData，以及要保存的文件名
	 * 
	 * @param request
	 * @param zipFileData
	 *            zip文件
	 
	public List<Map<String, String>> extraZiptoSingleFileBySuffixName(
			HttpServletRequest request, String zipFileData) throws IOException {
		List<Map<String, String>> fileInfoes = new ArrayList<Map<String, String>>();

		if (!zipFileData.contains(".zip")) {
			return null;
		}
		File file = new File(realPath, zipFileData);
		ZipFile zipFile = new ZipFile(file);
		// 解压缩
		Enumeration<ZipEntry> zipEntries = (Enumeration<ZipEntry>) zipFile
				.getEntries();
		while (zipEntries.hasMoreElements()) {
			ZipEntry zipEntry = zipEntries.nextElement();
			String fileName = zipEntry.getName();
			if (!fileName.contains(".")) {
				continue;
			}
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
					fileName.length());
			String uuid = StringUtil.getUUID();
			String suffix = fileName.substring(fileName.lastIndexOf("."),
					fileName.length());
			// 取出存放
			FileUtils.copyInputStreamToFile(zipFile.getInputStream(zipEntry),
					new File(realPath, uuid + suffix));
			String realName = (fileName.split("\\.")[0]).contains("_") ? (fileName
					.split("\\.")[0]).split("_")[1] : "";
			if (!realName.equals("")) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("fileName", fileName);
				map.put("fileData", uuid + suffix);

				fileInfoes.add(map);
			}
		}

		return fileInfoes;
	}
	*/
	//***姓名_文件名
	public static List<DocInfo> zipToFileByPrefixName(
			String formInputName, MultipartHttpServletRequest request){
		List<DocInfo> docInfos = new ArrayList<DocInfo>();
		String typeId = request.getParameter("type");
		MultipartFile multipartFile = request.getFile(formInputName);
		if(multipartFile.isEmpty()){
			return null;
		}else{
			logger.info("文件长度: {}", multipartFile.getSize());
			logger.info("文件类型: {}", multipartFile.getContentType());
			logger.info("文件名称: {}", multipartFile.getName());
			logger.info("文件原名: {}", multipartFile.getOriginalFilename());
			String zipFileName = multipartFile.getOriginalFilename();
			if(!multipartFile.getOriginalFilename().contains(".zip")){
				logger.info("文件不为ZIP文件");
				return null;
			}else{
				String zipFileData = StringUtil.getUUID() + ".zip";
				File dir = new File(attachmentPath);
				if(!dir.exists()){
					dir.mkdirs();
				}
				try {
					multipartFile.transferTo(new File(attachmentPath, zipFileData));
				
					DocInfo docInfo = new DocInfo();
					docInfo.setOwner((User)request.getSession().getAttribute("userSession"));
					docInfo.setDocType(docTypeService.getById(typeId));
					docInfo.setDocName(zipFileName);
					docInfo.setDocData(zipFileData);
					docInfos.add(docInfo);
					List<Map<String, String>> files = extraZip(zipFileData);
					if(files == null){
						return null;
					}
					for(int i=0, size = files.size(); i<size; i++){
						Map<String, String> map = files.get(i);
						String name = map.get("fileName");
						if(name == null || name.equals("") || !name.contains("_")){
							return null;
							//continue;
						}else{
							String dispalyName = name.split("_")[0];
							User user = userService.getByDisplayName(dispalyName);
							if(user == null){
								//continue;
								return null;
							}else{
								DocInfo doc = new DocInfo();
								doc.setOwner(user);
								doc.setDocType(docTypeService.getById(typeId));
								doc.setDocName(name);
								doc.setDocData(map.get("fileData"));
								docInfos.add(doc);
							}
						}
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return docInfos;
		}
	}
	
	//***解压缩文件
	public static List<Map<String, String>> extraZip(String data){
		List<Map<String, String>> files = new ArrayList<Map<String,String>>();
		if(!data.contains(".zip")){
			return files;
		}
		
		File file = new File(attachmentPath, data);
		
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(file, "GBK");
			Enumeration<ZipEntry> zipEntries = zipFile.getEntries();
			while (zipEntries.hasMoreElements()) {
				ZipEntry zipEntry = zipEntries.nextElement();
				String fileName = zipEntry.getName();
System.out.println(fileName);
				if (!fileName.contains(".")) {
					continue;
				}
				
				fileName = fileName.substring(fileName.lastIndexOf("/") + 1,
						fileName.length());
				String uuid = StringUtil.getUUID();
				String suffix = fileName.substring(fileName.lastIndexOf("."),
						fileName.length());
				FileUtils.copyInputStreamToFile(zipFile.getInputStream(zipEntry),
						new File(attachmentPath, uuid + suffix));
				String realName = (fileName.split("\\.")[0]).contains("_") ? (fileName
						.split("\\.")[0]).split("_")[1] : "";
						
				if (!realName.equals("")) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("fileName", fileName);
					map.put("fileData", uuid + suffix);

					files.add(map);
				}else{
					return null;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(zipFile != null ){
				try{
					zipFile.close();
				}catch(IOException e1){
					e1.printStackTrace();
				}
			}
			
		}
		
		return files;
	}
	
	
	public static String copyFromDocInfo(String data){
		String newData = StringUtil.getUUID() + "." + data.split("\\.")[1];
		try {
			FileUtils.copyFile(new File(attachmentPath, data),  new File(FileUtil.attachmentPath, newData));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newData;
	}
	
	/**
	 * 后台复制文件
	 * @param data
	 * @return
	 */
	public static String copy(String data){
		String newData = StringUtil.getUUID() + "." + data.split("\\.")[1];
		try {
			FileUtils.copyFile(new File(attachmentPath, data),  new File(FileUtil.attachmentPath, newData));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return newData;
	}


}
