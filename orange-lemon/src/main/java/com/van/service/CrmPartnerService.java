package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.CrmPartner;

public interface CrmPartnerService {
	public List<CrmPartner> getAll();

	public List<CrmPartner> queryForList(CrmPartner crmPartner);

	public PageView query(PageView pageView, CrmPartner crmPartner);

	public void add(CrmPartner crmPartner);

	public void delete(String id);

	public void modify(CrmPartner crmPartner);

	public CrmPartner getById(String id);
	
	public void toImport(List<List<String>> values);
	
	public List<List<String>> toExport(List<CrmPartner> crmPartners);
}
