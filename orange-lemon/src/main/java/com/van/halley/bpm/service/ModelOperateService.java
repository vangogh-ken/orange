package com.van.halley.bpm.service;

import java.util.Map;

public interface ModelOperateService {
	public String create(String name, String key, String description);

	public boolean deploy(String id);

	public Map<String, Object> export(String id);

	public boolean delete(String id);
}
