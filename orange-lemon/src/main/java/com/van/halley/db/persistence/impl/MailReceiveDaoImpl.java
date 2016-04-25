package com.van.halley.db.persistence.impl;
import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.MailReceiveDao;
import com.van.halley.db.persistence.entity.MailReceive;
@Repository("mailReceiveDao")
public class MailReceiveDaoImpl extends BaseDaoImpl<MailReceive> implements MailReceiveDao {}
