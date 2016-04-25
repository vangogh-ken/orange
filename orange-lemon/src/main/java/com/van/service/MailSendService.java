package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MailSend;

public interface MailSendService {
	public List<MailSend> getAll();

	public List<MailSend> queryForList(MailSend mailSend);

	public PageView query(PageView pageView, MailSend mailSend);

	public void add(MailSend mailSend);

	public void delete(String id);

	public void modify(MailSend mailSend);

	public MailSend getById(String id);
	
	public void send(String id);
}
