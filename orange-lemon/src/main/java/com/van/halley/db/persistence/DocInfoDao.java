package com.van.halley.db.persistence;

import java.util.List;

import com.van.halley.db.BaseDao;
import com.van.halley.db.persistence.entity.DocInfo;

/**
 * @author Vangogh
 * 
 */
public interface DocInfoDao extends BaseDao<DocInfo> {
	//public List<DocInfo> getByUserId(PageView pageView, DocInfo docInfo);
	// 获取临时文件
	public List<DocInfo> getUnEternalDoc();

}
