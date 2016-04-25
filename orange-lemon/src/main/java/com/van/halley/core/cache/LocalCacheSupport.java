package com.van.halley.core.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.van.halley.db.persistence.BasisAttributeDao;
import com.van.halley.db.persistence.BasisStatusAttributeDao;
import com.van.halley.db.persistence.BasisSubstanceDao;
import com.van.halley.db.persistence.FasExchangeRateDao;
import com.van.halley.db.persistence.FasInvoiceTypeDao;
import com.van.halley.db.persistence.FreightExpenseTypeDao;
import com.van.halley.db.persistence.ValueAttributeDao;
import com.van.halley.db.persistence.ValueDictionaryDao;
import com.van.halley.db.persistence.entity.BasisAttribute;
import com.van.halley.db.persistence.entity.BasisStatus;
import com.van.halley.db.persistence.entity.BasisStatusAttribute;
import com.van.halley.db.persistence.entity.BasisSubstance;
import com.van.halley.db.persistence.entity.FasExchangeRate;
import com.van.halley.db.persistence.entity.FasInvoiceType;
import com.van.halley.db.persistence.entity.FreightExpenseType;
import com.van.halley.db.persistence.entity.ValueAttribute;
import com.van.halley.db.persistence.entity.ValueDictionary;

/**
 * @author anxin
 * 此类用于初始化保存并提供给其他bean使用
 */
@Component
public class LocalCacheSupport {
	@Autowired
	private FasInvoiceTypeDao fasInvoiceTypeDao;
	@Autowired
	private FasExchangeRateDao fasExchangeRateDao;
	@Autowired
	private FreightExpenseTypeDao freightExpenseTypeDao;
	@Autowired
	private ValueDictionaryDao valueDictionaryDao;
	@Autowired
	private ValueAttributeDao valueAttributeDao;
	@Autowired
	private BasisSubstanceDao basisSubstanceDao;
	@Autowired
	private BasisAttributeDao basisAttributeDao;
	@Autowired
	private BasisStatusAttributeDao basisStatusAttributeDao;
	private LocalCacheStrategy localCacheStrategy = new LocalCacheStrategy();
	
	@PostConstruct
	public void init(){
		
	}
	
	public CacheStrategy getCacheStrategy(){
		if(this.localCacheStrategy == null){
			this.localCacheStrategy = new LocalCacheStrategy();
		}
		return this.localCacheStrategy;
	}
	
	/**
	 * 状态与字段关系
	 */
	public void basisStatusCache(){
		Cache cache = localCacheStrategy.getCache(BasisStatus.class.getName());
		//获取所有属性
		Map<String, String> attributeMap = new HashMap<String, String>();
		List<BasisAttribute> basisAttributes = basisAttributeDao.getAll();
		for(BasisAttribute basisAttribute : basisAttributes){
			attributeMap.put(basisAttribute.getId(), basisAttribute.getAttributeColumn());
		}
		//缓存状态字段关联
		List<BasisStatusAttribute> basisStatusAttributes = basisStatusAttributeDao.getAll();
		for(BasisStatusAttribute basisStatusAttribute : basisStatusAttributes){
			cache.put(basisStatusAttribute.getBasisStatusId() + attributeMap.get(basisStatusAttribute.getBasisAttributeId()), 
					basisStatusAttribute.getReadonly());
		}
	}
	
	/**
	 * 基础数据，主要是流程数据
	 */
	public void basisSubstanceCache(){
		Cache cache = localCacheStrategy.getCache(BasisSubstance.class.getName());
		List<BasisSubstance> basisSubstances = basisSubstanceDao.getAll();
		cache.put(null, basisSubstances);
	}
	
	/**
	 * 票种
	 */
	public void fasInvoiceTypeCache(){
		Cache cache = localCacheStrategy.getCache(FasInvoiceType.class.getName());
		cache.put(null, fasInvoiceTypeDao.getAll());
	}
	
	/**
	 * 汇率
	 */
	public void fasExchangeRateCache(){
		Cache cache = localCacheStrategy.getCache(FasExchangeRate.class.getName());
		cache.put(null, fasExchangeRateDao.getAll());
	}
	
	/**
	 * 费用类型
	 */
	public void freightExpenseTypeCache(){
		Cache cache = localCacheStrategy.getCache(FreightExpenseType.class.getName());
		cache.put(null, freightExpenseTypeDao.getAll());
	}
	
	/**
	 * 电子词典
	 */
	public void valueDictionaryCache(){
		Cache cache = localCacheStrategy.getCache(ValueDictionary.class.getName());
		
		List<ValueAttribute> valueAttributes = valueAttributeDao.getAll();
		ValueDictionary filter = new ValueDictionary();
		for(ValueAttribute valueAttribute : valueAttributes){
			filter.setValueAttribute(valueAttribute); 
			cache.put(valueAttribute.getId(), valueDictionaryDao.queryForList(filter));
		}
	}
}
