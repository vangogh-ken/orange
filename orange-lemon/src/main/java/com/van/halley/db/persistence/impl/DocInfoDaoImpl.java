package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.DocInfoDao;
import com.van.halley.db.persistence.entity.DocInfo;

@Repository("docInfoDao")
public class DocInfoDaoImpl extends BaseDaoImpl<DocInfo> implements DocInfoDao {
	
	/*@Override
	 * public List<DocInfo> getByUserId(PageView pageView, DocInfo docInfo) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("pageView", pageView);
		map.put("docInfo", docInfo);
		return getSqlSession().selectList("docinfo.getByUserId", map);
	}*/
	@Override
	public List<DocInfo> getUnEternalDoc() {
		return getSqlSession().selectList("docinfo.getUnEternalDoc");
	}

}
