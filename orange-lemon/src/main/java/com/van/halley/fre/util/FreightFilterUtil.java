package com.van.halley.fre.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FreightFilterUtil {
	private static Logger logger = LoggerFactory.getLogger(FreightFilterUtil.class);
	/**
	 * 集装箱号校验
	 * 说明: 
	 * 集装箱编号和校验码集装箱编号是集装箱全球唯一识别标识　　
	 * 根据ISO6346：1995 《集装箱--代码、识别和标记》标准，集装箱校验码算法如下：　　
	 * 集装箱编号共11位，前四位是字母，最后一位为校验码，举例如◎◎◎◎×××××××。　　
	 * 字母取数值规则为：A＝10，B至K依次取12至21，L至U依次取23至32，V至Z依次取34至38。　　
	 * 箱号第一位的值乘以2的0次幂，第二位乘以2的1次幂，...，第十位乘以2的9次幂，然后求和。　　
	 * 其和除以11的余数即为校验码的值集装箱校验码就是集装箱号的最后一位（第7位数字），实际箱号中会用“口”框起来。　　
	 * 早期的集装箱是没有校验位的，甚至箱主之间定义的也不同，有5、6位等等，后来为了规范和避免资料录入时的错误，联盟就出台了箱号规范、以及第7位校验码。　　
	 * 如果在录入数据时箱号输错了，系统会自动提示你箱号不规范，是否确认？（一般的集装箱行业操作软件都可以）从而减少不必要的错误
	 * @param text
	 * @return
	 */
	public static boolean validateBoxNumber(String text){
		text = text.replaceAll(" ", "");
		if(text.length() != 11){
			return false;
		}
		char[] chArray = {'A','B', 'C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		int [] inArray = {10,12,13,14,15,16,17,18,19,20,21,23,24,25,26,27,28,29,30,31,32,34,35,36,37,38};
		char[] values = text.toCharArray();
		int result = 0;
		for(int i=0, len=values.length - 1; i<len; i++){
			char ch = values[i];
			boolean isChar = false;
			for(int j=0, lenCh = chArray.length; j<lenCh; j++){//先比对数字
				if(ch == chArray[j]){
					isChar = true;
					result += inArray[j] * Math.pow(2, i);
					//System.out.println(inArray[j] * Math.pow(2, i));
					break;
				}
			}
			if(!isChar){//如果不是字母
				result += Integer.parseInt(ch + "") * Math.pow(2, i);
			}
		}
		try{
			//System.out.println(result);
			int lastNumber = Integer.parseInt(values[values.length -1] + "");
			//余数为10是应看做为0
			if(result % 11 != lastNumber){
				if(result % 11 == 10 && lastNumber == 0){
					return true;
				}else{
					return false;
				}
			}else{
				return true;
			}
		}catch(NumberFormatException e){
			logger.error("集装箱校验错误: ", e);
			return false;
		}
	}
	
	/**
	 * 通过动作信息过滤数据
	 * @param fieldColumn
	 * @param actionValue
	 * @return
	 */
	/*public static String valueSQL(String column, String value){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT M.FRE_ORDER_ID AS FRE_ORDER_ID FROM FRE_ACTION_VALUE AS V ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_ACTION_FIELD AS F ");
		sql.append(" ON V.FRE_ACTION_FIELD_ID=F.ID ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_ACTION AS A ");
		sql.append(" ON V.FRE_ACTION_ID=A.ID ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_MAINTAIN AS M ");
		sql.append(" ON A.FRE_MAINTAIN_ID=M.ID ");
		sql.append(" WHERE F.FIELD_COLUMN='" + column + "' ");
		sql.append(" AND M.FRE_ORDER_ID IS NOT NULL ");
		sql.append(" AND V.STRING_VALUE LIKE '%" + value + "%' ");
		
		if("TDH".equals(column)){//如果是提单号，则添加箱需的搜索
			sql.append(" UNION ALL (SELECT FRE_ORDER_ID FROM FRE_BOX_REQUIRE WHERE BL_NO LIKE '%" + value + "%')");
		}
		
		return sql.toString();
	}*/
	
	/**
	 * @param 从箱需中检索提单号
	 * @return
	 */
	public static String requireSQL(String TDH){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT FRE_ORDER_ID FROM FRE_BOX_REQUIRE WHERE BL_NO LIKE '%" + TDH + "%'");
		return sql.toString();
	}
	
	/**
	 * 根据集装箱号获取
	 * @param boxNumber
	 * @return
	 */
	/*public static String numberSQL(String boxNumber){
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT R.FRE_ORDER_ID AS FRE_ORDER_ID FROM FRE_ORDER_BOX AS O ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_BOX_REQUIRE AS R ");
		sql.append(" ON O.FRE_BOX_REQUIRE_ID=R.ID ");
		sql.append(" LEFT JOIN  ");
		sql.append(" FRE_BOX AS B ");
		sql.append(" ON O.FRE_BOX_ID=B.ID ");
		sql.append(" WHERE B.BOX_NUMBER LIKE '%" + boxNumber + "%' ");
		
		return sql.toString();
	}*/
	
	
	/**
	 * 通过集装箱号获取订单ID进行过滤
	 * @param filterColumn
	 * @param boxNumber
	 * @return
	 */
	public static String sqlFilterNumber(String filterColumn, String boxNumber){
		StringBuilder sql = new StringBuilder();
		sql.append(" EXISTS (SELECT 1 FROM (");
		sql.append(" SELECT R.FRE_ORDER_ID AS FRE_ORDER_ID_ FROM FRE_ORDER_BOX AS O ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_BOX_REQUIRE AS R ");
		sql.append(" ON O.FRE_BOX_REQUIRE_ID=R.ID ");
		sql.append(" LEFT JOIN  ");
		sql.append(" FRE_BOX AS B ");
		sql.append(" ON O.FRE_BOX_ID=B.ID ");
		sql.append(" WHERE B.BOX_NUMBER LIKE '%" + boxNumber + "%' ");
		sql.append(" ) AS T WHERE " + filterColumn + "=T.FRE_ORDER_ID_)");
		return sql.toString();
	}
	
	/**
	 * 根据箱属查询
	 * @param filterColumn
	 * @param boxNumber
	 * @return
	 */
	public static String sqlFilterBelong(String filterColumn, String boxBelong){
		StringBuilder sql = new StringBuilder();
		sql.append(" EXISTS (SELECT 1 FROM (");
		sql.append(" SELECT R.FRE_ORDER_ID AS FRE_ORDER_ID_ FROM FRE_ORDER_BOX AS O ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_BOX_REQUIRE AS R ");
		sql.append(" ON O.FRE_BOX_REQUIRE_ID=R.ID ");
		//sql.append(" LEFT JOIN  ");
		//sql.append(" FRE_BOX AS B ");
		//sql.append(" ON O.FRE_BOX_ID=B.ID ");
		sql.append(" WHERE R.BOX_BELONG LIKE '%" + boxBelong + "%' ");
		sql.append(" ) AS T WHERE " + filterColumn + "=T.FRE_ORDER_ID_)");
		return sql.toString();
	}
	
	/**
	 * 通过动作信息获取订单ID进行过滤
	 * @param filterColumn
	 * @param columnName
	 * @param columnValue
	 * @return
	 */
	public static String sqlFilterColumn(String filterColumn, String columnName, String columnValue){
		//~ 针对提单号查询进行优化，只取箱需里面的提单号。
		if("TDH".equals(columnName)){
			return "EXISTS (SELECT 1 FROM (SELECT FRE_ORDER_ID AS FRE_ORDER_ID_ FROM FRE_BOX_REQUIRE WHERE BL_NO LIKE '%" + columnValue + "%') AS T WHERE " + filterColumn + "=T.FRE_ORDER_ID_)";
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" EXISTS (SELECT 1 FROM (");
		sql.append(" SELECT M.FRE_ORDER_ID AS FRE_ORDER_ID_ FROM FRE_ACTION_VALUE AS V ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_ACTION_FIELD AS F ");
		sql.append(" ON V.FRE_ACTION_FIELD_ID=F.ID ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_ACTION AS A ");
		sql.append(" ON V.FRE_ACTION_ID=A.ID ");
		sql.append(" LEFT JOIN ");
		sql.append(" FRE_MAINTAIN AS M ");
		sql.append(" ON A.FRE_MAINTAIN_ID=M.ID ");
		//sql.append(" WHERE F.FIELD_COLUMN='" + columnName + "' ");
		sql.append(" WHERE V.STATUS='T' ");
		sql.append(" AND F.FIELD_COLUMN='" + columnName + "' ");
		//sql.append(" AND M.FRE_ORDER_ID IS NOT NULL ");
		sql.append(" AND V.STRING_VALUE LIKE '%" + columnValue + "%' ");
		
		if("TDH".equals(columnName)){//如果是提单号，则添加箱需的搜索
			sql.append(" UNION ALL (SELECT FRE_ORDER_ID AS FRE_ORDER_ID_ FROM FRE_BOX_REQUIRE WHERE BL_NO LIKE '%" + columnValue + "%')");
		}
		
		sql.append(" ) AS T WHERE " + filterColumn + "=T.FRE_ORDER_ID_)");
		return sql.toString();
	}
	
	public static void main(String[] args) {
		//PCIU1531531 PCIU3846841 PCIU2530756
		System.out.println(validateBoxNumber("PCIU8782621"));
		System.out.println(validateBoxNumber("CAIU8589542"));
		//System.out.println(valueSQL("TDH", "D"));
	}
}
