package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MailReceive;

public interface MailReceiveService {
	public List<MailReceive> getAll();

	public List<MailReceive> queryForList(MailReceive mailReceive);

	public PageView query(PageView pageView, MailReceive mailReceive);

	public void add(MailReceive mailReceive);

	public void delete(String id);

	public void modify(MailReceive mailReceive);

	public MailReceive getById(String id);
}
