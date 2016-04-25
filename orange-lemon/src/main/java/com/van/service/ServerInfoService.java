package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.ServerInfo;

public interface ServerInfoService {
	public PageView query(PageView pageView, ServerInfo serverInfo);

	public List<ServerInfo> getAll();

	public void add(ServerInfo serverInfo);

	public void delete(String id);

	public ServerInfo getById(String id);

	public void modify(ServerInfo serverInfo);
}
