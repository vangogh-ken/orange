package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.core.spring.ApplicationContextHelper;
import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.MailSendDao;
import com.van.halley.db.persistence.entity.MailSend;
import com.van.service.MailSendService;

@Transactional
@Service("mailSendService")
public class MailSendServiceImpl implements MailSendService {
	@Autowired
	private MailSendDao mailSendDao;

	public List<MailSend> getAll() {
		return mailSendDao.getAll();
	}

	public List<MailSend> queryForList(MailSend mailSend) {
		return mailSendDao.queryForList(mailSend);
	}

	public PageView query(PageView pageView, MailSend mailSend) {
		List<MailSend> list = mailSendDao.query(pageView, mailSend);
		pageView.setResults(list);
		return pageView;
	}

	public void add(MailSend mailSend) {
		mailSendDao.add(mailSend);
	}

	public void delete(String id) {
		mailSendDao.delete(id);
	}

	public void modify(MailSend mailSend) {
		mailSendDao.modify(mailSend);
	}

	public MailSend getById(String id) {
		return mailSendDao.getById(id);
	}

	@Override
	public void send(String id) {
		MailSend mail = mailSendDao.getById(id);
		/*MailFacade mailFacade = ApplicationContextHelper.getBean(MailFacade.class);
		mailFacade.sendMail(mail.getAddressFrom(), mail.getAddressTo(),
				mail.getSubject(), mail.getContent());*/
		mail.setStatus("已发送");
		mailSendDao.modify(mail);
	}
}
