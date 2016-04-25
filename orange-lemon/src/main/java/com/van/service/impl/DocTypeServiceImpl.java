package com.van.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.DocTypeDao;
import com.van.halley.db.persistence.entity.DocType;
import com.van.service.DocTypeService;
@Transactional
@Service("docTypeService")
public class DocTypeServiceImpl implements DocTypeService {
@Autowired
private DocTypeDao docTypeDao;
public List<DocType> getAll() {
return docTypeDao.getAll();
}
public List<DocType> queryForList(DocType docType) { 
return docTypeDao.queryForList(docType); 
}
public PageView query(PageView pageView, DocType docType) {
List<DocType> list = docTypeDao.query(pageView, docType);
pageView.setResults(list);
return pageView;
}
public void add(DocType docType) {
docTypeDao.add(docType);
}
public void delete(String id) {
docTypeDao.delete(id);
}
public void modify(DocType docType) {
docTypeDao.modify(docType);
}
public DocType getById(String id) {
return docTypeDao.getById(id);
}
}
