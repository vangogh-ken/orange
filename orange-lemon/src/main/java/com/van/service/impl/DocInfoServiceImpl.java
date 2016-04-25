package com.van.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.DocInfoDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.DocInfo;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.service.DocInfoService;

@Transactional
@Service("docInfoService")
public class DocInfoServiceImpl implements DocInfoService {
	@Autowired
	private DocInfoDao docInfoDao;
	@Autowired
	private UserDao userDao;

	public void add(DocInfo file) {
		docInfoDao.add(file);
	}

	public void delete(String id) {
		docInfoDao.delete(id);
	}

	public List<DocInfo> getAll() {
		return docInfoDao.getAll();
	}

	public DocInfo getById(String id) {
		return docInfoDao.getById(id);
	}

	public void modify(DocInfo file) {
		docInfoDao.modify(file);
	}

	public PageView query(PageView pageView, DocInfo shareFile) {
		List<DocInfo> list = docInfoDao.query(pageView, shareFile);
		pageView.setResults(list);
		return pageView;
	}
/**
	public PageView getByUserId(PageView pageView, DocInfo shareFile) {
		List<DocInfo> list = docInfoDao.getByUserId(pageView, shareFile);
		pageView.setResults(list);
		return pageView;
	}
**/
	// 仅上传自己的
	/*public void onlyMyself(MultipartHttpServletRequest request) {
		String type = request.getParameter("type");
		User user = (User) request.getSession().getAttribute("userSession");
		DocInfo doc = new DocInfo();
		Map<String, String> map = null;
		try {
			map = fileLoadSupport.upload("muiltFile", request);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc.setType(type);
		doc.setData(map.get("fileData"));
		doc.setName(map.get("fileName"));
		doc.setOwner(user);
		docInfoDao.add(doc);
	}*/

	// 对某一个人
	/*public void onlyOne(MultipartHttpServletRequest request) {
		String type = request.getParameter("fileType");
		String displayName = request.getParameter("toUserRealname");
		User user = userDao.getByDisplayName(displayName);
		DocInfo doc = new DocInfo();
		Map<String, String> map = null;
		try {
			map = fileLoadSupport.upload("muiltFile", request);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc.setType(type);
		doc.setData(map.get("fileData"));
		doc.setName(map.get("fileName"));
		doc.setOwner(user);

		docInfoDao.add(doc);

	}*/
/**
	public void toAll(MultipartHttpServletRequest request) {
		String type = request.getParameter("type");
		DocInfo doc = new DocInfo();
		Map<String, String> map = null;
		try {
			map = fileLoadSupport.upload("muiltFile", request);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		doc.setType(type);
		doc.setData(map.get("fileData"));
		doc.setName(map.get("fileName"));
		List<User> users = userDao.getAll();
		for (User user : users) {
			doc.setOwner(user);
			docInfoDao.add(doc);
		}

	}
**/

	@Override
	public void toOne(String userId, String selectedItem) {
		String[] ids = selectedItem.split(";");
		String[] userIds = userId.split(",");
		
		for(String uid : userIds){
			for(String id : ids){
				DocInfo docinfo = docInfoDao.getById(id);
				DocInfo doc = new DocInfo();
				doc.setId(StringUtil.getUUID());
				doc.setCreateTime(new Date());
				doc.setDocName(docinfo.getDocName());
				doc.setDocType(docinfo.getDocType());
				doc.setOwner(userDao.getById(uid));
				doc.setDocData(FileUtil.copyFromDocInfo(docinfo.getDocData()));
				
				docInfoDao.add(doc);
			}
		}
	}

	@Override
	public void toOrgEntity(String orgeEntityId, String selectedItem){
		List<User> users = userDao.getByOrgEntityId(orgeEntityId);
		String[] ids = selectedItem.split(";");
		for(String id : ids){
			for(User user : users){
				DocInfo docinfo = docInfoDao.getById(id);
				
				DocInfo doc = new DocInfo();
				doc.setId(StringUtil.getUUID());
				doc.setCreateTime(new Date());
				doc.setOwner(user);
				doc.setDocName(docinfo.getDocName());
				doc.setDocType(docinfo.getDocType());
				doc.setDocData(FileUtil.copyFromDocInfo(docinfo.getDocData()));
				
				docInfoDao.add(doc);
			}
		}
	}

	@Override
	public void toUnderling(String userId, String selectedItem) {
		List<User> users = userDao.getUnderling(userId);
		String[] ids = selectedItem.split(";");
		for(String id : ids){
			for(User user : users){
				DocInfo docinfo = docInfoDao.getById(id);
				
				DocInfo doc = new DocInfo();
				doc.setId(StringUtil.getUUID());
				doc.setCreateTime(new Date());
				doc.setOwner(user);
				doc.setDocName(docinfo.getDocName());
				doc.setDocType(docinfo.getDocType());
				doc.setDocData(FileUtil.copyFromDocInfo(docinfo.getDocData()));
				
				docInfoDao.add(doc);
			}
		}
	}

	@Override
	public void toAll(String selectedItem) {
		List<User> users = userDao.getAll();
		String[] ids = selectedItem.split(";");
		for(String id : ids){
			for(User user : users){
				DocInfo docinfo = docInfoDao.getById(id);
				
				DocInfo doc = new DocInfo();
				doc.setId(StringUtil.getUUID());
				doc.setCreateTime(new Date());
				doc.setOwner(user);
				doc.setDocName(docinfo.getDocName());
				doc.setDocType(docinfo.getDocType());
				doc.setDocData(FileUtil.copyFromDocInfo(docinfo.getDocData()));
				
				docInfoDao.add(doc);
			}
		}
	}

	@Override
	public List<DocInfo> getUnEternalDoc() {
		// TODO Auto-generated method stub
		return null;
	}
}
