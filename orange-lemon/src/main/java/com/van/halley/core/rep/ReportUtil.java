package com.van.halley.core.rep;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.db.persistence.entity.ReportSet;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.FileUtil;
import com.van.halley.util.file.XlsUtil;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class ReportUtil {
	private static Logger logger = LoggerFactory.getLogger(ReportUtil.class);
	
	/**
	 * 生成通用报表，数据填充完毕，合并单元格
	 * @param dataSource
	 * @param templateFile
	 * @param reportSets
	 * @return
	 */
	public static InputStream createNormalReport(Map<String, Object> dataSource, File templateFile, List<ReportSet> reportSets){
		return createSimpleReportToStream(dataSource, templateFile);
	}
	
	public static InputStream createNormalReport(Map<String, Object> dataSource, InputStream is){
		return createSimpleReportToStream(dataSource, is);
	}
	
	/**
	 * 生成简单报表，仅填充数据
	 * @param dataSource
	 * @param templateFile
	 * @return
	 */
	public static InputStream createSimpleReportToStream(Map<String, Object> dataSource, File templateFile){
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFile));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, dataSource);
			resetCellFormula(workbook);//重置公式
			return getInputStream(workbook);
		} catch (ParsePropertyException e) {
			logger.error(e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public static InputStream createSimpleReportToStream(Map<String, Object> dataSource, InputStream is){
		try {
			XLSTransformer transformer = new XLSTransformer();
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, dataSource);
			resetCellFormula(workbook);//重置公式
			//resetReginStyle(workbook, true, false, true);//设置样式和合并单元格
			return getInputStream(workbook);
		} catch (ParsePropertyException e) {
			logger.error(e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error(e.getMessage());
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 生成一个简单报表并返回具体的文件路径
	 * @param dataSource
	 * @param templateFile
	 * @return
	 */
	public static String createSimpleReportToFile(Map<String, Object> dataSource, File templateFile){
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFile));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, dataSource);
			resetCellFormula(workbook);//重置公式
			//resetReginStyle(workbook, true, false, true);//设置样式和合并单元格
			return toRandomFile(workbook);
		} catch (ParsePropertyException e) {
			logger.error(e.getMessage());
		} catch (InvalidFormatException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取生成一个xls，并返回其文件路径
	 * @param workbook
	 * @return
	 */
	public static String toRandomFile(Workbook workbook){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = null;
		String fileName = StringUtil.getUUID() + ".xls";
		try {
			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
			FileUtils.copyInputStreamToFile(in, new File(FileUtil.attachmentPath, fileName));
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			try{
				if(out != null){
					out.close();
				}
			}catch(IOException e){
				logger.error(e.getMessage());
			}
		}
		return fileName;
	}
	
	/**
	 * 获取生成报表流数据
	 * @param workbook
	 * @return
	 */
	public static InputStream getInputStream(Workbook workbook){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = null;
		try {
			workbook.write(out);
			in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}finally{
			try{
				if(out != null){
					out.close();
				}
			}catch(IOException e){
				logger.error(e.getMessage());
			}
		}
		return in;
	}
	
	/**
	 * 重置公式
	 * @param workbook
	 */
	public static void resetCellFormula(HSSFWorkbook workbook){
		HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(workbook);
		for(int i=0, len=workbook.getNumberOfSheets(); i<len; i++){
			HSSFSheet sheet = workbook.getSheetAt(i);
			for(int j=0, si=sheet.getPhysicalNumberOfRows(); j<si; j++){
				HSSFRow row = sheet.getRow(j);
				if(row == null){
					continue;
				}else{
					for(int k=0, s=row.getPhysicalNumberOfCells(); k<s; k++){
						HSSFCell cell = row.getCell(k);
						if(cell == null){
							continue;
						}else{
							if(cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
								logger.info("重置公式 : {}", cell.getCellFormula());
								cell.setCellFormula(cell.getCellFormula());
								cell = evaluator.evaluateInCell(cell);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * 合并单元格并设置格式和数字
	 * @param workbook
	 * @param isSetCenter 是否设置居中
	 * @param isEffectAll 是否设置全局
	 * @param isSetNumberic 是否设置数字格式
	 */
	public static void resetReginStyle(HSSFWorkbook workbook, boolean isSetCenter, boolean isEffectAll, boolean isSetNumberic){
		//~ only support single column to merge
		HSSFCellStyle style = workbook.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		HSSFSheet sheet = workbook.getSheetAt(0);
		int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
		int rowCount = sheet.getPhysicalNumberOfRows();
		Map<Integer, List<int[]>> map = new HashMap<Integer, List<int[]>>();
		for(int i=0; i<columnCount; i++){
			
			List<int[]> items = new ArrayList<int[]>();
			int[] item = new int[2];
			String value = null;
			for(int j=0; j<rowCount; j++){
				if(sheet.getRow(j) == null || sheet.getRow(j).getCell(i) == null){
					continue;
				}
				if(isSetCenter && isEffectAll){
					sheet.getRow(j).getCell(i).setCellStyle(style);//~ set style
				}
				
				if(value == null){
					String temp = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
					if(NumberUtils.isNumber(temp) || StringUtils.isBlank(temp)){//except numbers and blank
						if(isSetNumberic && NumberUtils.isNumber(temp)){
							sheet.getRow(j).getCell(i).setCellValue(NumberUtils.toDouble(temp));
							sheet.getRow(j).getCell(i).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
						continue;
					}
					value = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
					item[0] = j;
				}else{
					String temp = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
					if(NumberUtils.isNumber(temp) || StringUtils.isBlank(temp)){//except numbers and blank
						if(isSetNumberic && NumberUtils.isNumber(temp)){
							sheet.getRow(j).getCell(i).setCellValue(NumberUtils.toDouble(temp));
							sheet.getRow(j).getCell(i).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
						
						if(item[1] != 0){//~ catch
							items.add(item);
						}
						value = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
						item = new int[2];
						item[0] = j;
						item[1] = 0;
					}
					
					if(value.equals(XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i)))){
						item[1] = j;
					}else{
						if(item[1] != 0){//~ catch
							items.add(item);
						}
						value = XlsUtil.getValueToStringFromCell(sheet.getRow(j).getCell(i));
						item = new int[2];
						item[0] = j;
						item[1] = 0;
					}
				}
			}
			
			if(item[1] != 0){//~ catch
				items.add(item);
			}
			
			map.put(i, items);
		}
		
		//~ merge cell
		for(Entry<Integer, List<int[]>> entry : map.entrySet()){
			if(entry.getValue() != null && !entry.getValue().isEmpty()){
				for(int[] item : entry.getValue()){
					sheet.addMergedRegion(new CellRangeAddress(item[0], item[1], 
							entry.getKey(), entry.getKey()));
					
					if(isSetCenter && !isEffectAll){
						for(int k=item[0]; k<item[1] + 1; k++){
							String temp = XlsUtil.getValueToStringFromCell(sheet.getRow(k).getCell(entry.getKey()));
							Cell cell = sheet.getRow(k).getCell(entry.getKey());
							cell.setCellValue(temp);
							cell.setCellStyle(style);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 去除自动添加的常量数据
	 * @param params
	 * @return
	 */
	public static Map<String, String> excludeConstant(Map<String, String> params){
		
		List<String> excludes = new ArrayList<String>();
		excludes.add("CURRENT_USER_ID");
		excludes.add("CURRENT_USER_NAME");
		excludes.add("CURRENT_DISPLAY_NAME");
		excludes.add("CURRENT_ORG_ENTITY_NAME");
		excludes.add("CURRENT_ORG_ENTITY_ID");
		
		Map<String, String> result = new HashMap<String, String>();
		for(Iterator<Entry<String, String>> it = params.entrySet().iterator(); it.hasNext(); ){
			Entry<String, String> entry = it.next();
			if(!excludes.contains(entry.getKey())){
				result.put(entry.getKey(), entry.getValue());
			}
		}
		
		return result;
	}
	
}
