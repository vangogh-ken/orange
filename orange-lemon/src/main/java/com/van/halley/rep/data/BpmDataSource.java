package com.van.halley.rep.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.halley.bpm.service.HistoricQueryService;

public class BpmDataSource {
	@Autowired
	private HistoricQueryService historicQueryService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Map<String, Object> e(Map<String, Object> params){
		int TJNF = (int)params.get("TJNF");
		int TJYF = (int)params.get("TJYF");
		Calendar cal = Calendar.getInstance();
		cal.set(TJNF, TJYF - 1, 1, 0, 0, 0);
		Date startTime = cal.getTime();
		cal.add(Calendar.MONTH, 1);
		Date endTime = cal.getTime();
		
		return null;
	}
	
	public Map<String, Object> monthly(Map<String, String> params){
		int TJNF = Integer.parseInt(params.get("TJNF"));
		int TJYF = Integer.parseInt(params.get("TJYF"));
		String sql = "select pd.name_ as PROC_NAME, count(pd.name_) as PROC_COUNT from act_hi_procinst pi,act_re_procdef pd where YEAR(START_TIME_)=? AND MONTH(START_TIME_)=? AND pi.proc_def_id_ =pd.id_ group by pd.name_";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, TJNF, TJYF);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("PROC", list);
		result.putAll(params);
		return result;
	}
}
