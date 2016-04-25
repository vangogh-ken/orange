package com.van.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.FreightDelegateTemplateDao;
import com.van.halley.db.persistence.ReportIsDao;
import com.van.halley.db.persistence.entity.FreightDelegateTemplate;
import com.van.halley.db.persistence.entity.ReportIs;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.FreightDelegateTemplateService;

@Transactional
@Service("freightDelegateTemplateService")
public class FreightDelegateTemplateServiceImpl implements
		FreightDelegateTemplateService {
	@Autowired
	private FreightDelegateTemplateDao freightDelegateTemplateDao;
	@Autowired
	private ReportIsDao reportIsDao;

	public List<FreightDelegateTemplate> getAll() {
		return freightDelegateTemplateDao.getAll();
	}

	public List<FreightDelegateTemplate> queryForList(
			FreightDelegateTemplate freightDelegateTemplate) {
		return freightDelegateTemplateDao.queryForList(freightDelegateTemplate);
	}

	public PageView query(PageView pageView,
			FreightDelegateTemplate freightDelegateTemplate) {
		List<FreightDelegateTemplate> list = freightDelegateTemplateDao.query(
				pageView, freightDelegateTemplate);
		pageView.setResults(list);
		return pageView;
	}

	public void add(FreightDelegateTemplate freightDelegateTemplate) {
		freightDelegateTemplateDao.add(freightDelegateTemplate);
	}

	public void delete(String id) {
		freightDelegateTemplateDao.delete(id);
	}

	public void modify(FreightDelegateTemplate freightDelegateTemplate) {
		freightDelegateTemplateDao.modify(freightDelegateTemplate);
	}

	public FreightDelegateTemplate getById(String id) {
		return freightDelegateTemplateDao.getById(id);
	}

	@Override
	public void addOrModify(FreightDelegateTemplate freightDelegateTemplate) {
		try {
			String freightDelegateTemplateId = freightDelegateTemplate.getId();
			if(freightDelegateTemplateId == null){
				freightDelegateTemplateId = StringUtil.getUUID();
				freightDelegateTemplate.setId(freightDelegateTemplateId);
				freightDelegateTemplateDao.add(freightDelegateTemplate);
			}else{
				freightDelegateTemplateDao.modify(freightDelegateTemplate);
			}
			reportIsDao.deleteByReportTemplateId(freightDelegateTemplateId);
			ReportIs reportIs = new ReportIs();
			reportIs.setTemplateId(freightDelegateTemplateId);
			reportIs.setTemplateStream(new FileInputStream(new File(FileUtil.attachmentPath, freightDelegateTemplate.getTemplateFile())));
			reportIsDao.add(reportIs);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("找不到模板文件。", e);
		} catch(Exception e){
			throw new RuntimeException("保存模板出错。", e);
		}
		
	}

	@Override
	public void deleteTemplate(String[] freightDelegateTemplateIds) {
		for(String freightDelegateTemplateId : freightDelegateTemplateIds){
			reportIsDao.deleteByReportTemplateId(freightDelegateTemplateId);
			freightDelegateTemplateDao.delete(freightDelegateTemplateId);
		}
	}

	@Override
	public void downloadTemplate(String freightDelegateTemplateId,
			HttpServletResponse response) throws IOException {
		FreightDelegateTemplate freightDelegateTemplate = freightDelegateTemplateDao.getById(freightDelegateTemplateId);
		String templateFileName = freightDelegateTemplate.getTemplateFile();
		File templateFile = new File(FileUtil.attachmentPath, freightDelegateTemplate.getTemplateFile());
		if(templateFile.exists()){
			String fileName = "XXX.xls";//默认
			if(templateFileName.lastIndexOf(".") > 0){
				fileName = freightDelegateTemplate.getTemplateName() + "." + templateFileName.substring(templateFileName.lastIndexOf(".") + 1, templateFileName.length());
			}
			FileUtil.download(templateFileName, fileName, response);
		}else{
			ReportIs reportIs = reportIsDao.getByReportTemplateId(freightDelegateTemplate.getId());
			byte[] blob = (byte[]) reportIs.getTemplateStream();
			InputStream is = new ByteArrayInputStream(blob);
			if(is == null || is.available() == 0){
				return;
			}else{
				FileUtils.copyInputStreamToFile(is, templateFile);
			}
			if(is != null){
				is.close();
			}
			//下载
			String fileName = "XXX.xls";//默认
			if(templateFileName.lastIndexOf(".") > 0){
				fileName = freightDelegateTemplate.getTemplateName() + "." + templateFileName.substring(templateFileName.lastIndexOf(".") + 1, templateFileName.length());
			}
			FileUtil.download(templateFileName, fileName, response);
		}
	}
}
