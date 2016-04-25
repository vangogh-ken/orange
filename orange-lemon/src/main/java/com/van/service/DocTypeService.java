package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.DocType;

public interface DocTypeService {
	public List<DocType> getAll();

	public List<DocType> queryForList(DocType docType);

	public PageView query(PageView pageView, DocType docType);

	public void add(DocType docType);

	public void delete(String id);

	public void modify(DocType docType);

	public DocType getById(String id);
}
