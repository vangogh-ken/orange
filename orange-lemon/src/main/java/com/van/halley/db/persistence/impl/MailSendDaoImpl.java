package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MailSendDao;
import com.van.halley.db.persistence.entity.MailSend;
@Repository("mailSendDao")
public class MailSendDaoImpl extends BaseDaoImpl<MailSend> implements MailSendDao {}
