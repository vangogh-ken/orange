package com.van.service;

import java.util.List;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.MotorcadeInsurance;

public interface MotorcadeInsuranceService {
	public List<MotorcadeInsurance> getAll();

	public List<MotorcadeInsurance> queryForList(MotorcadeInsurance motorcadeInsurance);

	public MotorcadeInsurance queryForOne(MotorcadeInsurance motorcadeInsurance);

	public PageView<MotorcadeInsurance> query(PageView<MotorcadeInsurance> pageView, MotorcadeInsurance motorcadeInsurance);

	public void add(MotorcadeInsurance motorcadeInsurance);

	public void delete(String id);

	public void modify(MotorcadeInsurance motorcadeInsurance);

	public MotorcadeInsurance getById(String id);

	public List<List<String>> doneBatchExport(String[] motorcadeInsuranceIds);
}
