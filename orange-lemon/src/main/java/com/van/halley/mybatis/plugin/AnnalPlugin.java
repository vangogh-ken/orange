package com.van.halley.mybatis.plugin;

import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import com.van.halley.core.page.PageView;
import com.van.halley.mybatis.jdbc.dialet.Dialect;
import com.van.halley.util.StringUtil;

/**
 * 用于记录所有操作的SQL语句：update, insert, delete
 * @author Think
 *
 */
@SuppressWarnings("unchecked")
@Intercepts({ @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class })})
public class AnnalPlugin implements Interceptor {
	private static Dialect dialectObject = null; // 数据库方言
	public Object intercept(Invocation ivk) throws Throwable {
		if (ivk.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
			/**
			 * 方法1：通过id来区分是否需要分页．.*query.*(在application.xml配置好的)
			 * 方法2：传入的参数是否有page参数，如果有，则分页.
			 */
			// if (mappedStatement.getId().matches(pageSqlId)){ // 拦截需要分页的SQL
			BoundSql boundSql = delegate.getBoundSql();
			
			System.out.println(boundSql.getSql());
			/**
			Object parameterObject = boundSql.getParameterObject();// 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
			if (parameterObject == null) {//参数为空,说明调用的的是无参方法,直接继续
				return ivk.proceed();
			} else {
				PageView pageView = null;
				if (parameterObject instanceof PageView) { // 参数就是Pages实体
					pageView = (PageView) parameterObject;
				} else if (parameterObject instanceof Map) {
					for (Entry entry : (Set<Entry>) ((Map) parameterObject).entrySet()) {
						if (entry.getValue() instanceof PageView) {
							pageView = (PageView) entry.getValue();
							break;
						}
					}
				} else { // 参数为某个实体，该实体拥有Pages属性
					pageView = ReflectHelper.getValueByFieldType(parameterObject, PageView.class);
					if (pageView == null) {
						return ivk.proceed();
					}
				}

				String sql = boundSql.getSql();
				PreparedStatement countStmt = null;
				ResultSet rs = null;
				try {
					Connection connection = (Connection) ivk.getArgs()[0];
					String countSql = "select count(1) from (" + sql + ") tmp_count"; //记录统计

					countStmt = connection.prepareStatement(countSql);
					ReflectHelper.setValueByFieldName(boundSql, "sql", countSql);
					ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
					parameterHandler.setParameters(countStmt);
					rs = countStmt.executeQuery();
					int count = 0;
					if (rs.next()) {
						count = ((Number) rs.getObject(1)).intValue();
					}
					pageView.setTotalCount(count);
				} finally {
					try {
						rs.close();
					} catch (Exception e) {
					}
					try {
						countStmt.close();
					} catch (Exception e) {
					}
				}

				String pageSql = generatePagesSql(sql, pageView);
				ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql); // 将分页sql语句反射回BoundSql.
			}
			// }
			 * 
			 */
		}
		return ivk.proceed();
	}

	/**
	 * 根据数据库方言，生成特定的分页sql
	 * @param sql
	 * @param page
	 * @return
	 */
	private String generatePagesSql(String sql, PageView page) {
		if (page != null && dialectObject != null) {
			// pageNow默认是从1，而已数据库是从0开始计算的．所以(page.getPageNow()-1)
			int pageNow = page.getPageNo();
			return dialectObject.getLimitString(sql, (pageNow <= 0 ? 0
					: pageNow - 1) * page.getPageSize(), page.getPageSize());
		}
		return sql;
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
/**
	public void setProperties(Properties p) {
		String dialect = ""; // 数据库方言
		dialect = p.getProperty("dialect");
		if (StringUtil.isNullOrEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		} else {
			try {
				dialectObject = (Dialect) Class.forName(dialect)
						.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(dialect + ", init fail!\n" + e);
			}
		}
		pageSqlId = p.getProperty("pageSqlId");// 根据id来区分是否需要分页
		if (StringUtil.isNullOrEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
	**/

	@Override
	public void setProperties(Properties props) {
		 String dialect = props.getProperty("dialect");
		if (!StringUtil.isNullOrEmpty(dialect)) {
			try {
				dialectObject = (Dialect) Class.forName(dialect)
						.getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				throw new RuntimeException(dialect + ", init fail!\n" + e);
			}
		} else {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				e.printStackTrace();
			}
		}
	}
}
