package com.van.halley.util.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批量导入数据工具
 * @author Think
 *
 */
public class IExmportUtil {
	private static Logger logger = LoggerFactory.getLogger(IExmportUtil.class);
	/**
	 * 获取Excel中的某列的数据
	 * @param columnIndex 取第几列的数据
	 * @param rowIndex 从第几行取值
	 * @param fileData 文件数据
	 * @return
	 */
	public static List<String> importSingleColumn(int columnIndex, int rowIndex, String fileData){
		List<String> list = new ArrayList<String>();
		HSSFSheet sheet = getSheet(new File(FileUtil.attachmentPath, fileData));
		for(int i=rowIndex, len = sheet.getPhysicalNumberOfRows(); i<len; i++){
			HSSFRow row = sheet.getRow(i);
			list.add(getValueToStringFromCell(row.getCell(columnIndex)));
		}
		return list;
	}
	
	/**
	 * 多个字段的导入
	 * @param columnIndexs 列号
	 * @param rowIndex 起始行
	 * @param fileData 文件
	 * @return
	 */
	public static List<List<String>> importMultiColumn(int[] columnIndexs, int rowIndex, String fileData){
		List<List<String>> values = new ArrayList<List<String>>();
		HSSFSheet sheet = getSheet(new File(FileUtil.attachmentPath, fileData));
		for(int i=rowIndex, len = sheet.getPhysicalNumberOfRows(); i<len; i++){
			List<String> singleValue = new ArrayList<String>();
			HSSFRow row = sheet.getRow(i);
			for(int j=0, l=columnIndexs.length; j<l; j++){
				singleValue.add(getValueToStringFromCell(row.getCell(columnIndexs[j])).trim());
			}
			values.add(singleValue);
		}
		return values;
	}
	
	public static InputStream exportSingleColumn(String columnName, List<String> values){
		HSSFSheet sheet = getSheet();
		for(int i=0, len=values.size() + 1; i<len; i++){
			if(i == 0){
				sheet.createRow(i).createCell(0).setCellValue(columnName);
			}else{
				sheet.createRow(i).createCell(0).setCellValue(values.get(i - 1));
			}			
		}
		
		return getInputStreamOfWorkbook(sheet.getWorkbook());
	}
	
	/**
	 * Instated of method xlsMultiColumn
	 * 批量导出 ，已被替代
	 * @param columnNames 第一行的列名
	 * @param values 数据值
	 * @return
	 */
	@Deprecated
	public static InputStream exportMultiColumn(String[] columnNames, List<List<String>> values){
		HSSFSheet sheet = getSheet();
		for(int i=0, len=values.size() + 1; i<len; i++){
			if(i == 0){
				HSSFRow row = sheet.createRow(i);
				for(int j=0,l=columnNames.length; j<l; j++){
					row.createCell(j).setCellValue(columnNames[j]);
				}
			}else{
				HSSFRow row = sheet.createRow(i);
				for(int j=0,l=columnNames.length; j<l; j++){
					row.createCell(j).setCellValue(values.get(i - 1).get(j));
				}
			}			
		}
		
		return getInputStreamOfWorkbook(sheet.getWorkbook());
	}
	
	/**
	 * 导出数据
	 * @param columnNames
	 * @param values
	 * @return
	 */
	public static InputStream xlsMultiColumn(String[] columnNames, List<List<Object>> values){
		
		HSSFSheet sheet = getSheet();
		
		//~ title style
		HSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFFont font = sheet.getWorkbook().createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeight((short)16);
		style.setFont(font);
		
		for(int i=0, len=values.size() + 1; i<len; i++){
			if(i == 0){
				HSSFRow row = sheet.createRow(i);
				for(int j=0,l=columnNames.length; j<l; j++){
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(style);
					cell.setCellValue(columnNames[j]);
				}
			}else{
				HSSFRow row = sheet.createRow(i);
				for(int j=0,l=columnNames.length; j<l; j++){
					Object value = values.get(i - 1).get(j);
					if(value == null){
						row.createCell(j).setCellValue("");
					}else if(value instanceof Short){
						row.createCell(j).setCellValue((Short)value);
					}else if(value instanceof Integer){
						row.createCell(j).setCellValue((Integer)value);
					}else if(value instanceof Long){
						row.createCell(j).setCellValue((Long)value);
					}else if(value instanceof Double){
						row.createCell(j).setCellValue((Double)value);
					}else if(value instanceof Float){
						row.createCell(j).setCellValue((Float)value);
					}else if(value instanceof Character){
						row.createCell(j).setCellValue((Character)value);
					}else if(value instanceof String){
						row.createCell(j).setCellValue((String)value);
					}else if(value instanceof Boolean){
						row.createCell(j).setCellValue((Boolean)value);
					}else if(value instanceof Date){
						row.createCell(j).setCellValue((Date)value);
					}else{
						row.createCell(j).setCellValue(value.toString());
					}
				}
			}			
		}
		
		return getInputStreamOfWorkbook(sheet.getWorkbook());
	}
	/**
	 * 获取workbook
	 */
	public static HSSFWorkbook getWorkbook(File file) {
		HSSFWorkbook wbook = null;
		if (file.getName().endsWith(".xls")) {
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
	
	public static HSSFWorkbook newWorkbook(){
		HSSFWorkbook wbook = new HSSFWorkbook();
		return wbook;
	}
	
	/**
	 * 获取sheet
	 */
	public static HSSFSheet getSheet(File file){
		return getWorkbook(file).getSheetAt(0);
	}
	
	public static HSSFSheet getSheet(){
		return newWorkbook().createSheet();
	}
	
	/**
	 * 获取cell数据，都直接转换为字符串
	 */
	public static String getValueToStringFromCell(Cell cell) {
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
	
	public static InputStream getInputStreamOfWorkbook(Workbook workbook){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = null;
		try {
			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return in;
	}
}
