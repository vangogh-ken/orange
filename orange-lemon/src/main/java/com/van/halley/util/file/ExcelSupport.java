package com.van.halley.util.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.van.service.UserService;

/**
 * 该类用于数据导入导出，有泛型的参数。可针对所有的Entity类。但是必须严格按照严谨的规范 属性名必须要与类的成员变量对应；
 * 第一栏对应各个Entity的成员变量 Entity的setter,getter，以及无参的构造方法为必备。
 * 目前对数据的处理类型有:int,double,string,date。其他类型的暂未添加
 * 
 * @author Vangogh
 * 
 * @param <T>
 */
@Service("excelSupport")
public class ExcelSupport<T> {

	/**
	 * 从文件直接获取一个对象之后交给reflectExcelToEntity处理
	 * 
	 * @param file
	 * @return
	 */
	public Workbook getWorkbook(File file) {
		Workbook wbook = null;
		// office 2007以上
		if (file.getName().endsWith(".xlsx")) {
			try {
				wbook = new XSSFWorkbook(new FileInputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// office 2007以下
		} else if (file.getName().endsWith(".xls")) {
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(new FileInputStream(file));
				wbook = new HSSFWorkbook(fs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return wbook;
	}

	/**
	 * 将任意类型写List入Excel。
	 * 
	 * @param clazz
	 * @param entities
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public InputStream reflectEntityToExcel(Class<T> clazz, List<T> entities)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		InputStream in = null;
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(clazz.getName());

		Field[] attrs = clazz.getDeclaredFields();
		Method[] methods = clazz.getDeclaredMethods();
		Map<String, Method> attrMethodMap = new HashMap<String, Method>();
		Map<Method, String> methodAttrTypeMap = new HashMap<Method, String>();

		for (Method m : methods) {
			String methodName = m.getName().toLowerCase();
			for (Field attr : attrs) {
				String attrName = attr.getName().toLowerCase();
				Type type = attr.getGenericType();
				String[] tStr = type.toString().split("\\.");
				String typeName = (tStr[tStr.length - 1]).toLowerCase();

				// if(methodName.startsWith("get") &&
				// methodName.endsWith(attrName)){
				if (methodName.equalsIgnoreCase("get" + attrName)) {
					attrMethodMap.put(attrName, m);
					methodAttrTypeMap.put(m, typeName);
				}
			}
		}

		Map<Integer, String> indexAttrMap = new HashMap<Integer, String>();

		Row firstRow = sheet.createRow(0);
		Object[] attrNames = attrMethodMap.keySet().toArray();
		for (int i = 0, len = attrNames.length; i < len; i++) {
			String attrName = attrNames[i].toString();
			Cell cell = firstRow.createCell(i);
			cell.setCellValue(attrName);
			indexAttrMap.put(i, attrName);
		}

		Row row = null;
		Cell cell = null;
		Object[] params = null;
		for (int i = 0, size = entities.size(); i < size; i++) {
			row = sheet.createRow(i + 1);
			T entity = entities.get(i);
			for (int j : indexAttrMap.keySet()) {
				cell = row.createCell(j);
				String attrName = indexAttrMap.get(j);
				Method m = attrMethodMap.get(attrName);
				String type = methodAttrTypeMap.get(m);
				Object value = m.invoke(entity, params);

				if (type.equalsIgnoreCase("int")) {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue(value == null ? 0 : Integer
							.parseInt(value.toString()));
				} else if (type.equalsIgnoreCase("double")) {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue(value == null ? "" : value.toString());
				} else if (type.equalsIgnoreCase("string")) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cell.setCellValue(value == null ? "" : value.toString());
				} else if (type.equalsIgnoreCase("date")) {
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cell.setCellValue(value == null ? null : (Date) value);
				}
			}
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return in;
	}

	/**
	 * 探测Excel数据,预设第一个row为字段信息,默认主键为自增ID，不在Excel中 通过反射自动寻找对应的字段名。 注意：
	 * 1.属性名必须要一致(大小写都可，程序默认都转换为小写) 2.默认属性类型为INT, STIRNG, DATE 其他类型的暂不做处理
	 * 3.最终返回指定类型的List
	 * 
	 * @param clazz
	 * @param wbook
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public List<T> reflectExcelToEntity(Class<T> clazz, File file)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Workbook wbook = getWorkbook(file);

		List<T> list = new ArrayList<T>();

		// 将属性和对应的set方法放入Map
		Field[] attrs = clazz.getDeclaredFields();
		Method[] methods = clazz.getDeclaredMethods();
		// 字段与方法
		Map<String, Method> attrMethodMap = new HashMap<String, Method>();
		// 方法与参数类型
		Map<Method, String> methodAttrTypeMap = new HashMap<Method, String>();

		for (Method m : methods) {
			String methodName = m.getName().toLowerCase();
			for (Field attr : attrs) {
				String attrName = attr.getName().toLowerCase();
				Type type = attr.getGenericType();
				String[] tStr = type.toString().split("\\.");
				String typeName = (tStr[tStr.length - 1]).toLowerCase();

				// if(methodName.startsWith("set") &&
				// methodName.endsWith(attrName)){
				if (methodName.equalsIgnoreCase("set" + attrName)) {
					attrMethodMap.put(attrName, m);
					methodAttrTypeMap.put(m, typeName);
				}
			}

		}
		// 匹配Excel
		int num = wbook.getNumberOfSheets();
		Sheet sheet = null;
		for (int i = 0; i < num; i++) {
			sheet = wbook.getSheetAt(i);
			int rowNum = sheet.getPhysicalNumberOfRows();
			// 使用第一行进行数据的比对。
			Row firstRow = sheet.getRow(0);
			int cellNum = firstRow.getPhysicalNumberOfCells();
			Map<Integer, String> indexAttrMap = new HashMap<Integer, String>();

			for (int j = 0; j < cellNum; j++) {
				Cell cell = firstRow.getCell(i);
				String cellValue = cell.getStringCellValue().toLowerCase();
				indexAttrMap.put(i, cellValue);
			}

			T t = null;
			String attrName = null;
			Method m = null;
			String type = null;
			Cell cell = null;

			for (int k = 1; k < rowNum; k++) {
				Row eachRow = sheet.getRow(k);
				t = clazz.newInstance();
				for (int l = 0, len = eachRow.getPhysicalNumberOfCells(); l < len; l++) {
					cell = eachRow.getCell(l);
					attrName = indexAttrMap.get(l);
					m = attrMethodMap.get(attrName);
					type = methodAttrTypeMap.get(m);

					if (type.equalsIgnoreCase("int")) {
						m.invoke(t, getInt(cell));
					} else if (type.equalsIgnoreCase("double")) {
						m.invoke(t, getDouble(cell));
					} else if (type.equalsIgnoreCase("string")) {
						m.invoke(t, getString(cell));
					} else if (type.equalsIgnoreCase("date")) {
						m.invoke(t, getDate(cell));
					}
				}
				list.add(t);
			}
		}

		return list;
	}

	/**
	 * 以下几个方法都是根据属性类型从Excel里面自动获取指定的类型
	 * 
	 * @param cell
	 * @return
	 */

	public int getInt(Cell cell) {
		return (int) cell.getNumericCellValue();
	}

	public double getDouble(Cell cell) {
		return cell.getNumericCellValue();
	}

	public String getString(Cell cell) {
		return cell.getStringCellValue();
	}

	public Date getDate(Cell cell) {
		Date date = null;
		double n = cell.getNumericCellValue();
		if (DateUtil.isCellDateFormatted(cell)) {
			date = DateUtil.getJavaDate(n);
		}

		return date;
	}

	/**
	 * public void getExcelPattern(){ SAXReader saxReader = new SAXReader();
	 * Document doc = null; try { doc = saxReader.read(""); } catch
	 * (DocumentException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 **/
	/*********** 以下内容暂未删除 ***************/

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		ExcelSupport e = new ExcelSupport();
		File f = new File("C:\\Users\\Vangogh\\Desktop\\2013年范磊.xls");
		//e.readExcel(f, OrderPay.class);
	}

	public List<?> readExcel(File file, Class clazz) {
		// office 2007以上
		if (file.getName().endsWith(".xlsx")) {
			XSSFWorkbook wb = null;
			try {
				wb = new XSSFWorkbook(new FileInputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}

			int num = wb.getNumberOfSheets();
			for (int i = 0; i < num; i++) {
				XSSFSheet sheet = wb.getSheetAt(i);
				System.out.println(sheet.getSheetName());
				int row_num = sheet.getPhysicalNumberOfRows();
				for (int j = 0; j < row_num; j++) {
					XSSFRow row = sheet.getRow(j);
					if (row != null) {
						int cell_num = row.getPhysicalNumberOfCells();
						for (int k = 0; k < cell_num; k++) {
							XSSFCell cell = row.getCell(k);
							String s = getValueToStringFromCell(cell);
						}
					}

				}
			}

			// office 2007以下
		} else if (file.getName().endsWith(".xls")) {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(new FileInputStream(file));
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int num = wb.getNumberOfSheets();
			System.out.println("该Excel有 " + num + " 个Sheet");
			for (int i = 0; i < num; i++) {
				HSSFSheet sheet = wb.getSheetAt(i);
				if (sheet != null) {
					int row_num = sheet.getPhysicalNumberOfRows();
					// 从第二行开始
					for (int j = 2; j < row_num; j++) {
						HSSFRow row = sheet.getRow(j);
						if (row != null) {
							int cell_num = row.getPhysicalNumberOfCells();
							for (int k = 0; k < cell_num; k++) {
								HSSFCell cell = row.getCell(k);
								String s = getValueToStringFromCell(cell);
								System.out.println(s);
							}
						}
					}
				}
			}

		}

		return null;
	}

	// 导出2007以下的版本
	/*public InputStream exportOrderPayToExcel(List<OrderPay> list,
			String weekTime) {
		InputStream in = null;
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet(weekTime);
		for (int i = 0, size = list.size(); i < size; i++) {
			//OrderPay orderPay = list.get(i);
			Row row = sheet.createRow(i);
			Cell cell_0 = row.createCell(0);
			cell_0.setCellValue(i + 1);
			Cell cell_1 = row.createCell(1);
			cell_1.setCellValue(orderPay.getUserRealname());
			Cell cell_2 = row.createCell(2);
			cell_2.setCellValue(orderPay.getOrderId());
			Cell cell_3 = row.createCell(3);
			cell_3.setCellValue(orderPay.getInvoiceCompany());
			Cell cell_4 = row.createCell(4);
			cell_4.setCellValue(orderPay.getInvoiceOutTime());
			Cell cell_5 = row.createCell(5);
			cell_5.setCellValue(orderPay.getInvoiceId());
			Cell cell_6 = row.createCell(6);
			cell_6.setCellValue(orderPay.getSubject());
			Cell cell_7 = row.createCell(7);
			cell_7.setCellValue(orderPay.getRevenueSumRMB());
			Cell cell_8 = row.createCell(8);
			cell_8.setCellValue(orderPay.getRevenueSumUSD());
			Cell cell_9 = row.createCell(9);
			cell_9.setCellValue(orderPay.getRemark());
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return in;
	}*/

	// 应收账款(此处就直接保存至数据库？避免对象太多内存溢出？)
	/*public List<OrderPay> readExcelToOrderPay(File file) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		List<OrderPay> orderPays = new ArrayList<OrderPay>();
		List<Sheet> sheets = getSheets(file);

		PageView pageView = new PageView();
		List<User> users = userService.getAll();
		Map<String, String> map = new HashMap<String, String>();
		for (User user : users) {
			map.put(user.getDisplayName(), user.getId());
		}

		for (Sheet sheet : sheets) {
			String weekTime = sheet.getSheetName();
			for (int i = 2, count = sheet.getPhysicalNumberOfRows(); i < count; i++) {
				// 从第3行开始取数据。
				Row row = sheet.getRow(i);
				OrderPay orderPay = new OrderPay();

				orderPay.setWeekTime(weekTime);
				String userRealname = getValueToStringFromCell(row.getCell(1));
				if (userRealname.equals("闵添乙")) {
					orderPay.setUserRealname("闵掭乙");
					userRealname = "闵掭乙";
				} else if (userRealname.equals("邹锦秀")) {
					orderPay.setUserRealname("邹锦绣");
					userRealname = "邹锦绣";
				}
				orderPay.setUserRealname(userRealname);
				orderPay.setOrderId(getValueToStringFromCell(row.getCell(2)));
				orderPay.setInvoiceCompany(getValueToStringFromCell(row
						.getCell(3)));
				orderPay.setInvoiceId(getValueToStringFromCell(row.getCell(5)));
				orderPay.setSubject(getValueToStringFromCell(row.getCell(6)));

				String rmb = getValueToStringFromCell(row.getCell(7));
				orderPay.setRevenueSumRMB(rmb.equals("") ? 0 : Double
						.parseDouble(rmb));

				String usd = getValueToStringFromCell(row.getCell(8));
				double sumUSD = 0;
				try {
					sumUSD = Double.parseDouble(usd);
				} catch (NumberFormatException e) {
					sumUSD = 0;
				}

				orderPay.setRevenueSumUSD(usd.equals("") ? 0 : sumUSD);

				// System.out.println(userRealname);
				if (userRealname != null) {
					orderPay.setUserId(map.get(userRealname));
				}

				String dateString = getValueToStringFromCell(row.getCell(4));
				// System.out.println("日期: " + dateString);
				try {
					orderPay.setInvoiceOutTime(dateString.equals("") ? null
							: format.parse(dateString));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
				System.out.println(orderPay);

				orderPays.add(orderPay);
			}
		}

		return orderPays;
	}*/

	// 将所有的都以字符串的方式存取。 日期是Numberic类型
	public String getValueToStringFromCell(Cell cell) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		if (cell != null) {
			int type = cell.getCellType();
			String s = "";
			switch (type) {
			case 0:
				double n = cell.getNumericCellValue();
				if (DateUtil.isCellDateFormatted(cell)) {
					Date date = DateUtil.getJavaDate(n);
					s = format.format(date);
				} else {
					s = n + "";
				}
				break;

			case 1:
				s = cell.getStringCellValue();
				break;
			case 3:
				s = "";
				break;
			case 4:
				s = cell.getBooleanCellValue() + "";
				break;
			case 5:
				s = "";
				break;

			default:
				s = "";
			}

			return s;
		} else {
			return "";
		}

	}

	public List<Sheet> getSheets(File file) {
		List<Sheet> sheets = new ArrayList<Sheet>();
		// office 2007以上
		if (file.getName().endsWith(".xlsx")) {
			XSSFWorkbook wb = null;
			try {
				wb = new XSSFWorkbook(new FileInputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}
			int num = wb.getNumberOfSheets();
			for (int i = 0; i < num; i++) {
				XSSFSheet sheet = wb.getSheetAt(i);
				sheets.add(sheet);
			}

			// office 2007以下
		} else if (file.getName().endsWith(".xls")) {
			HSSFWorkbook wb = null;
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(new FileInputStream(file));
				wb = new HSSFWorkbook(fs);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int num = wb.getNumberOfSheets();

			for (int i = 0; i < num; i++) {
				HSSFSheet sheet = wb.getSheetAt(i);
				sheets.add(sheet);
			}
		}

		return sheets;
	}
	/*****/
}
