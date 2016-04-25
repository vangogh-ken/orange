package com.van.halley.rep.data;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.van.halley.core.rep.BasisSqlHelper;
import com.van.halley.db.persistence.BasisAttributeDao;

@Component
public class BasisDataSourceSupport {
	@Autowired
	private BasisAttributeDao basisAttributeDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 只获取列数据
	 * @param basisSubstanceTypeId
	 * @param params
	 * @param filterText
	 * @return
	 */
	public Map<String, Object> getBasisList(String basisSubstanceTypeId, Map<String, String> params, String filterText){
		//此处可以将basisAttribute进行过滤，只需要显示的字段也是可以的，且可以避免流程版本更新导致数据列不对应的问题。
		String sql = BasisSqlHelper.getSqlOfBasisSubstanceWithEqual(basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceTypeId), params);
		if(!StringUtils.isBlank(filterText)){
			if(params == null || params.isEmpty()){
				if(StringUtils.isNotBlank(filterText)){
					sql = sql + " WHERE " + filterText;
				}
			}else{
				if(StringUtils.isNotBlank(filterText)){
					sql = sql + " AND " + filterText;
				}
			}
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("BASIS", jdbcTemplate.queryForList(sql));
		return map;
	}
	
	public Map<String, Object> getBasisList(String basisSubstanceTypeId, String filterText){
		//此处可以将basisAttribute进行过滤，只需要显示的字段也是可以的，且可以避免流程版本更新导致数据列不对应的问题。
		String sql = BasisSqlHelper.getSqlOfBasisSubstanceWithEqual(basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceTypeId), null);
		if(StringUtils.isNotBlank(filterText)){
			sql = sql + " WHERE " + filterText;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("BASIS", jdbcTemplate.queryForList(sql));
		return map;
	}
	
	/**
	 * 可获取分组数据
	 * @param basisSubstanceTypeId
	 * @param params
	 * @param filterText
	 * @param selectedColumns eg: SUM(1) AS SL, SJ
	 * @param groupBy
	 * @return
	 */
	public Map<String, Object> getBasisGroup(String basisSubstanceTypeId, Map<String, String> params, String filterText, String selectedColumns, String groupBy){
		//此处可以将basisAttribute进行过滤，只需要显示的字段也是可以的，且可以避免流程版本更新导致数据列不对应的问题。
		String sql = BasisSqlHelper.getSqlOfBasisSubstanceWithEqual(basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceTypeId), params);
		if(params == null || params.isEmpty()){
			if(StringUtils.isNotBlank(filterText)){
				sql = sql + " WHERE " + filterText;
			}
		}else{
			if(StringUtils.isNotBlank(filterText)){
				sql = sql + " AND " + filterText;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("GROUP", jdbcTemplate.queryForList("SELECT " + selectedColumns + " FROM (" + sql + ") AS T GROUP BY " + groupBy));
		return map;
	}
	
	public Map<String, Object> getBasisGroup(String basisSubstanceTypeId, String filterText, String selectedColumns, String groupBy){
		//此处可以将basisAttribute进行过滤，只需要显示的字段也是可以的，且可以避免流程版本更新导致数据列不对应的问题。
		String sql = BasisSqlHelper.getSqlOfBasisSubstanceWithEqual(basisAttributeDao.getByBasisSubstanceTypeId(basisSubstanceTypeId), null);
		
		if(StringUtils.isNotBlank(filterText)){
			sql = sql + " WHERE " + filterText;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("GROUP", jdbcTemplate.queryForList("SELECT " + selectedColumns + " FROM (" + sql + ") AS T GROUP BY " + groupBy));
		return map;
	}
}
