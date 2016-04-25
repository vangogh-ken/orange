package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.OutMsgInfo;

public interface OutMsgInfoService {
	public List<OutMsgInfo> getAll();

	public List<OutMsgInfo> queryForList(OutMsgInfo outMsgInfo);

	public OutMsgInfo queryForOne(OutMsgInfo outMsgInfo);

	public PageView query(PageView pageView, OutMsgInfo outMsgInfo);

	public void add(OutMsgInfo outMsgInfo);

	public void delete(String id);

	public void modify(OutMsgInfo outMsgInfo);

	public OutMsgInfo getById(String id);

	/**
	 * 批量回复信息
	 * 
	 * @param content
	 * @param outMsgInfoId
	 * @return
	 */
	public boolean doneBatchReply(String content, String[] outMsgInfoIds);

	/**
	 * 已读标记
	 * @param outMsgInfoIds
	 * @return
	 */
	public boolean doneSettleRead(String[] outMsgInfoIds);
}
