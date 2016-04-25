package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.ServerInfoDao;
import com.van.halley.db.persistence.entity.ServerInfo;
import com.van.service.ServerInfoService;

@Transactional
@Service("serverInfoService")
public class ServerInfoServiceImpl implements ServerInfoService {
	@Autowired
	private ServerInfoDao serverInfoDao;

	public void add(ServerInfo serverInfo) {
		serverInfoDao.add(serverInfo);
	}

	public void delete(String id) {
		serverInfoDao.delete(id);
	}

	public ServerInfo getById(String id) {
		return serverInfoDao.getById(id);
	}

	// 编译指令
	public PageView query(PageView pageView, ServerInfo serverInfo) {
		List<ServerInfo> list = serverInfoDao.query(pageView, serverInfo);
		pageView.setResults(list);
		return pageView;
	}

	public List<ServerInfo> getAll() {
		return serverInfoDao.getAll();
	}

	public void modify(ServerInfo serverInfo) {
		serverInfoDao.modify(serverInfo);
	}

}
