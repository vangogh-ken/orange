package com.van.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.OutMsgInfoDao;
import com.van.halley.db.persistence.entity.OutMsgInfo;
import com.van.service.OutMsgInfoService;

@Transactional
@Service("outMsgInfoService")
public class OutMsgInfoServiceImpl implements OutMsgInfoService {
	@Autowired
	private OutMsgInfoDao outMsgInfoDao;

	public List<OutMsgInfo> getAll() {
		return outMsgInfoDao.getAll();
	}

	public List<OutMsgInfo> queryForList(OutMsgInfo outMsgInfo) {
		return outMsgInfoDao.queryForList(outMsgInfo);
	}

	public OutMsgInfo queryForOne(OutMsgInfo outMsgInfo) {
		return outMsgInfoDao.queryForOne(outMsgInfo);
	}

	public PageView query(PageView pageView, OutMsgInfo outMsgInfo) {
		List<OutMsgInfo> list = outMsgInfoDao.query(pageView, outMsgInfo);
		pageView.setResults(list);
		return pageView;
	}

	public void add(OutMsgInfo outMsgInfo) {
		outMsgInfoDao.add(outMsgInfo);
	}

	public void delete(String id) {
		outMsgInfoDao.delete(id);
	}

	public void modify(OutMsgInfo outMsgInfo) {
		outMsgInfoDao.modify(outMsgInfo);
	}

	public OutMsgInfo getById(String id) {
		return outMsgInfoDao.getById(id);
	}

	@Override
	public boolean doneBatchReply(String content, String[] outMsgInfoIds) {
		boolean flag = true;
		for(String outMsgInfoId : outMsgInfoIds){
			OutMsgInfo outMsgInfo = outMsgInfoDao.getById(outMsgInfoId);
			if("未读".equals(outMsgInfo.getStatus()) || "已读".equals(outMsgInfo.getStatus())){
				outMsgInfo.setStatus("已回复");
				outMsgInfoDao.modify(outMsgInfo);
				
				OutMsgInfo reply = new OutMsgInfo();
				reply.setMsgType(outMsgInfo.getMsgType());
				reply.setReceiver(outMsgInfo.getSender());
				reply.setSender(outMsgInfo.getReceiver());
				reply.setContent(content + "\r\n 回复: " + outMsgInfo.getContent());
				reply.setTitle("RE: " + outMsgInfo.getTitle());
				reply.setStatus("未读");
				reply.setHandled("F");
				outMsgInfoDao.add(reply);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}

	@Override
	public boolean doneSettleRead(String[] outMsgInfoIds) {
		boolean flag = true;
		for(String outMsgInfoId : outMsgInfoIds){
			OutMsgInfo outMsgInfo = outMsgInfoDao.getById(outMsgInfoId);
			if("未读".equals(outMsgInfo.getStatus())){
				outMsgInfo.setStatus("已读");
				outMsgInfoDao.modify(outMsgInfo);
			}else{
				flag = false;
				break;
			}
		}
		
		if(!flag){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return flag;
	}
}
