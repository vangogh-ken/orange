package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.DocInfo;

public interface DocInfoService {
	public PageView query(PageView pageView, DocInfo file);

	public void add(DocInfo file);

	public void delete(String id);

	public void modify(DocInfo docinfo);

	public DocInfo getById(String id);

	public List<DocInfo> getAll();
	
	public List<DocInfo> getUnEternalDoc();

	//public PageView getByUserId(PageView pageView, DocInfo docinfo);
//共享给某些人
	public void toOne(String userId, String selectedItem);
//共享给某部门
	public void toOrgEntity(String orgeEntityId, String selectedItem);
//共享给下属
	public void toUnderling(String userId, String selectedItem);
//共享给所有
	public void toAll(String selectedItem);

}
