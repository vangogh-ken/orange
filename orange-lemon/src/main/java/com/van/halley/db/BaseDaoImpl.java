package com.van.halley.db;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.van.halley.core.page.PageView;

/**
 * 集合持久层的公用的增，删，改，查类 <T> 表示传入实体类
 * @param <T>
 */
public class BaseDaoImpl<T> extends SqlSessionDaoSupport {
	private static Logger LOG = LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }
	/**
	 * 获取传过来的泛型类名字
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getClassName() {
		// 在父类中得到子类声明的父类的泛型信息
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		Class<T> clazz = (Class) pt.getActualTypeArguments()[0];
		// clazz.getSimpleName().toString().toLowerCase();
		// 这里是获取实体类的简单名称，再把类名转为小写
		return clazz.getSimpleName().toString().toLowerCase();
	}
	
	// 查询，分页
	public List<T> query(PageView<T> pageView, T t) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("pageView", pageView);
		map.put("t", t);
		return getSqlSession().selectList(this.getClassName() + ".query", map);
	}

	//查询，不分页
	public List<T> queryForList(T t) {
		return getSqlSession().selectList(this.getClassName() + ".queryForList", t);
	}
	
	public T queryForOne(T t){
		List<T> list = getSqlSession().selectList(this.getClassName() + ".queryForList", t);
		if(list != null && !list.isEmpty()){
			if(list.size() == 1){	//size等于1
				return list.get(0);
			}else{					//size大于1
				if(LOG.isDebugEnabled()){
					LOG.debug("记录数大于1, 返回第一条记录。 类型 {}", this.getClassName());
				}
				return list.get(0);
			}
		}else{
			if(LOG.isDebugEnabled()){
				LOG.debug("记录数为0, 返回空。 类型 {}", this.getClassName());
			}
			
			return null;
		}
	}

	// 返回所有数据，没有查询条件
	public List<T> getAll() {
		return getSqlSession().selectList(this.getClassName() + ".getAll");
	}

	public int add(T t) {
		return getSqlSession().insert(this.getClassName() + ".add", t);
	}
	
	//如果有与流程封装的ENTITY，则删除的时候直接删除相应流程
	public int delete(String id) {
		return getSqlSession().delete(this.getClassName() + ".deleteById", id);
	}

	@SuppressWarnings("unchecked")
	public T getById(String id) {
		return (T) getSqlSession().selectOne(this.getClassName() + ".getById", id);
	}

	public int modify(T t) {
		return getSqlSession().update(this.getClassName() + ".update", t);
	}
	
	public int count(T t){
		return Integer.parseInt(getSqlSession().selectOne(this.getClassName() + ".count", t).toString());
	}
	
	public int insertBatch(List<T> list){
		return getSqlSession().insert(this.getClassName() + ".insertBatch", list);
	}
	
	public int updateBatch(List<T> list){
		return getSqlSession().update(this.getClassName() + ".updateBatch", list);
	}
	
	public int deleteBatch(List<T> list){
		return getSqlSession().delete(this.getClassName() + ".deleteBatch", list);
	}

}
