package com.van.halley.db.persistence.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.van.halley.db.BaseDaoImpl;
import com.van.halley.db.persistence.FreightDelegateDao;
import com.van.halley.db.persistence.entity.FreightDelegate;

@Repository("freightDelegateDao")
public class FreightDelegateDaoImpl extends BaseDaoImpl<FreightDelegate>
		implements FreightDelegateDao {

	@Override
	public FreightDelegate getByFreightActionId(String freightActionId) {
		//return getSqlSession().selectOne("freightdelegate.getByFreightActionId", freightActionId);
		List<FreightDelegate> freightDelegates = getSqlSession().selectList("freightdelegate.getByFreightActionId", freightActionId);
		if(freightDelegates != null && !freightDelegates.isEmpty()){
			if(freightDelegates.size() == 1){
				return freightDelegates.get(0);
			}else{
				for(int i=1, len=freightDelegates.size(); i<len; i++){
					delete(freightDelegates.get(i).getId());//删除多余的
				}
				
				return freightDelegates.get(0);
			}
		}else{
			return null;
		}
	}
}
