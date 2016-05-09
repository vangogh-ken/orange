package com.van.halley.mybatis.mapper.util;

public class DaoCreation {
	public static void doCreate(String entityName){
		createDao(entityName);
		createDaoImpl(entityName);
		createService(entityName);
		createServiceImpl(entityName);
		System.out.println(toLowerFileChar("Done"));
	}

	public static void createDao(String entityName) {
		StringBuilder content = new StringBuilder();
		content.append("package com.van.halley.db.persistence;\n");
		content.append("import com.van.halley.db.BaseDao;\n");
		content.append("import com.van.halley.db.persistence.entity." + entityName
				+ ";\n");
		content.append("public interface " + entityName
				+ "Dao extends BaseDao<" + entityName + "> {}");

		FileCreation.doCreate(entityName + "Dao.java", content.toString());
	}

	public static void createDaoImpl(String entityName) {
		StringBuilder content = new StringBuilder();
		content.append("package com.van.db.persistence.impl;\n");
		content.append("import org.springframework.stereotype.Repository;\n");
		content.append("import com.van.halley.db.BaseDaoImpl;\n");
		content.append("import com.van.halley.db.persistence." + entityName + "Dao;\n");
		content.append("import com.van.halley.db.persistence.entity." + entityName
				+ ";\n");
		content.append("@Repository(\"" + toLowerFileChar(entityName)
				+ "Dao\")\n");
		content.append("public class " + entityName
				+ "DaoImpl extends BaseDaoImpl<" + entityName + "> implements "
				+ entityName + "Dao {}\n");

		FileCreation.doCreate(entityName + "DaoImpl.java", content.toString());
	}

	public static void createService(String entityName) {
		StringBuilder content = new StringBuilder();
		content.append("package com.van.service;\n");
		content.append("import java.util.List;\n");
		content.append("import com.van.halley.db.persistence.entity." + entityName
				+ ";\n");
		content.append("import com.van.halley.core.page.PageView;\n");
		content.append("public interface " + entityName + "Service{\n");

		//content.append("public void addBatch(List<" + entityName + "> list);");

		content.append("public List<" + entityName + "> getAll();\n");

		content.append("public List<" + entityName + "> queryForList(" + entityName + " " + toLowerFileChar(entityName) + ");\n");
		
		content.append("public int count(" + entityName + " " + toLowerFileChar(entityName) + ");\n");
		
		content.append("public " + entityName + " queryForOne(" + entityName + " " + toLowerFileChar(entityName) + ");\n");

		content.append("public PageView<" + entityName + ">  query(PageView<" + entityName + ">  pageView," + entityName + " " + toLowerFileChar(entityName) + ");\n");

		content.append("public void add(" + entityName + " " + toLowerFileChar(entityName) + ");\n");

		content.append("public void delete(String id);\n");

		content.append("public void modify(" + entityName + " " + toLowerFileChar(entityName) + ");\n");

		content.append("public " + entityName + " getById(String id);\n");

		content.append("}\n");

		FileCreation.doCreate(entityName + "Service.java", content.toString());
	}

	public static void createServiceImpl(String entityName) {
		StringBuilder content = new StringBuilder();
		content.append("package com.van.service.impl;\n");
		content.append("import java.util.List;\n");
		content.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		content.append("import org.springframework.stereotype.Service;\n");
		content.append("import org.springframework.transaction.annotation.Transactional;\n");
		content.append("import com.van.halley.db.persistence." + entityName + "Dao;\n");
		content.append("import com.van.halley.db.persistence.entity." + entityName
				+ ";\n");
		content.append("import com.van.service." + entityName + "Service;\n");
		content.append("import com.van.halley.core.page.PageView;\n");
		content.append("@Transactional\n");
		content.append("@Service(\"" + toLowerFileChar(entityName)
				+ "Service\")\n");

		content.append("public class " + entityName + "ServiceImpl implements "
				+ entityName + "Service {\n");
		content.append("@Autowired\n");
		content.append("private " + entityName + "Dao "
				+ toLowerFileChar(entityName) + "Dao;\n");

		//content.append("public void addBatch(List<" + entityName
				//+ "> list) {\n");
		//content.append(toLowerFileChar(entityName) + "Dao.addBatch(list);\n");
		//content.append("}\n");

		//getAll
		content.append("public List<" + entityName + "> getAll() {\n");
		content.append("return " + toLowerFileChar(entityName) + "Dao.getAll();\n");
		content.append("}\n");
		
		//queryForList
		content.append("public List<" + entityName + "> queryForList(" + entityName + " " + toLowerFileChar(entityName) + ") { \n");
		content.append("return " + toLowerFileChar(entityName)
				+ "Dao.queryForList(" + toLowerFileChar(entityName) + "); \n");
		content.append("}\n");
		
		//count
		content.append("public int count(" + entityName + " " + toLowerFileChar(entityName) + ") { \n");
		content.append("return " + toLowerFileChar(entityName)
				+ "Dao.count(" + toLowerFileChar(entityName) + "); \n");
		content.append("}\n");
		
		//queryForOne	
		content.append("public " + entityName + " queryForOne(" + entityName + " " + toLowerFileChar(entityName) + ") { \n");
		content.append("return " + toLowerFileChar(entityName)
				+ "Dao.queryForOne(" + toLowerFileChar(entityName) + "); \n");
		content.append("}\n");
		
		//query
		content.append("public PageView<" + entityName + ">  query(PageView<" + entityName + ">  pageView, " + entityName
				+ " " + toLowerFileChar(entityName) + ") {\n");
		content.append("List<" + entityName + "> list = "
				+ toLowerFileChar(entityName) + "Dao.query(pageView, "
				+ toLowerFileChar(entityName) + ");\n");
		content.append("pageView.setResults(list);\n");
		content.append("return pageView;\n");
		content.append("}\n");

		//add
		content.append("public void add(" + entityName + " "
				+ toLowerFileChar(entityName) + ") {\n");
		content.append(toLowerFileChar(entityName) + "Dao.add("
				+ toLowerFileChar(entityName) + ");\n");
		content.append("}\n");

		//delete
		content.append("public void delete(String id) {\n");
		content.append(toLowerFileChar(entityName) + "Dao.delete(id);\n");
		content.append("}\n");

		//modify
		content.append("public void modify(" + entityName + " "
				+ toLowerFileChar(entityName) + ") {\n");
		content.append(toLowerFileChar(entityName) + "Dao.modify("
				+ toLowerFileChar(entityName) + ");\n");
		content.append("}\n");

		//getById
		content.append("public " + entityName + " getById(String id) {\n");
		content.append("return " + toLowerFileChar(entityName)
				+ "Dao.getById(id);\n");
		content.append("}\n");

		content.append("}\n");

		FileCreation.doCreate(entityName + "ServiceImpl.java", content.toString());
	}

	public static String toLowerFileChar(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

	
}
