package com.van.halley.fre.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.db.persistence.entity.FreightOrder;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

/**
 * 用于委托模板数据处理
 * 包括EXCEL, WORD. EXCEL以xls, WORD以docx
 * @author Think
 *
 */
public class FreightDelegateUtil {
	private static Logger logger = LoggerFactory.getLogger(FreightDelegateUtil.class);
	/**
	 * 获取到委托模板流
	 * @param dataSource 数据源
	 * @param templateFile 委托模板文件绝对路径
	 * @param hasRegionParam 是否有单元格参数
	 * @param regionParam 单元格参数
	 * @return
	 */
	public static InputStream xlsTemplate(Map<String, Object> dataSource, File templateFile, 
			boolean hasRegionParam, String regionParam){
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(templateFile));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, dataSource);
			//resetCellFormula(workbook);
			HSSFSheet sheet = workbook.getSheetAt(0);
			List<int[]> regionParamList = new ArrayList<int[]>();
			boolean canRegion = true;//是否可以合并单元格
			if (hasRegionParam) {
				String[] paramGroups = regionParam.split(";");
				for(String paramGroup : paramGroups){
					String[] params = paramGroup.split(",");
					if(params.length != 4){
						logger.error("合并单元格参数不正确：{}", regionParam);
						return null;
					}else{
						int[] regigoParamNumber = new int[4];
						for(int i=0, len=params.length; i<len; i++){
							String param = params[i];
							if(param.contains("RS") || param.contains("BS")){//RS 箱需大小, BS 箱封大小 ，参数格式：BS+2
								int count = 0;
								String[] numbers =  param.split("\\+");
								for(String number : numbers){
									if(number.equals("RS")){
										int size = ((List)dataSource.get("REQUIRE")).size();
										if(size == 0){
											canRegion = false;
											break;//如果合并单元格时，没有相应的列数，则不应当进行合并，直接跳出
										}
										count += size;
									}else if(number.equals("BS")){
										int size = ((List)dataSource.get("BOX")).size();
										if(size == 0){
											canRegion = false;
											break;
										}
										count += size;
									}else{
										count += Integer.parseInt(number);
									}
								}
								regigoParamNumber[i] = count;
							}else{
								regigoParamNumber[i] = Integer.parseInt(param);
							}
						}
						regionParamList.add(regigoParamNumber);
					}
				}
				//合并单元格
				if(canRegion){
					for(int[] regionParamSingle : regionParamList){
						sheet.addMergedRegion(new CellRangeAddress(regionParamSingle[0], regionParamSingle[1], 
								regionParamSingle[2], regionParamSingle[3]));
					}
				}
			}
			
			return getInputStreamOfWorkbook(workbook);
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
	 * 获取流
	 */
	public static InputStream getInputStreamOfWorkbook(Workbook workbook){
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
	 * 获取流
	 */
	public static InputStream getInputStreamOfDocument(XWPFDocument doc){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		InputStream in = null;
		try {
			doc.write(out);
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
	
	//重置函数
	/**
	private static void resetCellFormula(HSSFWorkbook wb) {
		HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(wb);
		int sheetNum = wb.getNumberOfSheets();
		for (int i = 0; i < sheetNum; i++) {
			HSSFSheet sheet = wb.getSheetAt(i);
			int rows = sheet.getLastRowNum() + 1;
			for (int j = 0; j < rows; j++) {
				HSSFRow row = sheet.getRow(j);
				if (row == null)
					continue;
				int cols = row.getLastCellNum();
				for (int k = 0; k < cols; k++) {
					HSSFCell cell = row.getCell(k);
					if (cell == null){
						continue;
					}else{
						if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
							System.out.println("cell[" + j + "," + k + "]=:" + cell.getCellType());
							cell.setCellFormula(cell.getCellFormula()); //
							System.out.println("----公式：" + cell.getCellFormula());
							cell = e.evaluateInCell(cell); //
							System.out.println("-----------"+ cell.getNumericCellValue());
						}
					}
				}
			}
		}
	}
	**/
	
	/**
	 * 获取docx模板数据
	 * @param dataSource
	 * @param templateFile
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static InputStream docxTemplate(Map<String, Object> dataSource, File templateFile){
		Map<String, Object> action = (Map<String, Object>) dataSource.get("ACTION");
		for(String key : action.keySet()){
			Object object = action.get(key);
			if(object instanceof Date){
				object = new SimpleDateFormat("yyyy-MM-dd").format(object);
			}
			dataSource.put(key, object);
		}
		FreightOrder freightOrder = (FreightOrder)dataSource.get("ORDER");
		dataSource.put("orderNumber", freightOrder.getOrderNumber());
		dataSource.put("manipulator", freightOrder.getManipulator());
		dataSource.put("cargoName", freightOrder.getCargoName());
		dataSource.put("cargoCapacity", freightOrder.getCargoCapacity());
		dataSource.put("cargoWeight", freightOrder.getCargoWeight());
		dataSource.put("cargoAmount", freightOrder.getCargoAmount());
		
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(templateFile);
			XWPFDocument doc = new XWPFDocument(in);
			replaceParamsInDocument(doc, dataSource);
			replaceParamsInTable(doc, dataSource);
			return getInputStreamOfDocument(doc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			try{
				if(in != null){
					in.close();
				}
				if(out != null){
					out.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 对参数进行替换
	 * @param doc
	 * @param params
	 */
	public static void replaceParamsInDocument(XWPFDocument doc, Map<String, Object> params){
		Iterator<XWPFParagraph> it = doc.getParagraphsIterator();
		XWPFParagraph paragraph;
		while(it.hasNext()){
			paragraph = it.next();
			replaceParamsInParagraph(paragraph, params);
		}
	} 
	
	/**
	 * 在段落中进行替换
	 * @param paragraph
	 * @param params
	 */
	public static void replaceParamsInParagraph(XWPFParagraph paragraph, Map<String, Object> params){
		List<XWPFRun> runs;
		Matcher matcher;
		if(getMatcher(paragraph.getParagraphText()).find()){
			runs = paragraph.getRuns();
			for(int i=0, len=runs.size(); i<len; i++){
				String text = runs.get(i).toString();
				matcher = getMatcher(text);
				if(matcher.find()){//如果有能匹配的才进行处理
					while((matcher = getMatcher(text)).find()){
						Object object = params.get(matcher.group(1)) == null ? "" : params.get(matcher.group(1));
						text = matcher.replaceFirst((String)object);
					}
					/*
					 * 直接调用setText方法设置文本时候，在底层会重新创建一个XWFRun，会把文本附加在当前文本之后。
					 * 所以此处不能直接设置，而是首先删除当前Run，然后再插入一个新的Run，再进行设值。
					 */
					UnderlinePatterns underlinePattern = runs.get(i).getUnderline();
					
					String color = runs.get(i).getColor();
					String font = runs.get(i).getFontFamily();
					boolean blod = runs.get(i).isBold();
					boolean italic = runs.get(i).isItalic();
				
					paragraph.removeRun(i);
					XWPFRun newRun = paragraph.insertNewRun(i);
					newRun.setText(text);
					newRun.setUnderline(underlinePattern);
					
					newRun.setColor(color);
					newRun.setFontFamily(font);
					newRun.setBold(blod);
					newRun.setItalic(italic);
				}
				
			}
		}
	} 
	
	public static void replaceParamsInTable(XWPFDocument doc, Map<String, Object> params){
		Iterator<XWPFTable> it = doc.getTablesIterator();
		XWPFTable table;
		List<XWPFTableRow> rows;
		List<XWPFTableCell> cells;
		List<XWPFParagraph> paragraphs;
		while(it.hasNext()){
			table = it.next();
			rows = table.getRows();
			for(XWPFTableRow row : rows){
				cells = row.getTableCells();
				for(XWPFTableCell cell : cells){
					paragraphs = cell.getParagraphs();
					for(XWPFParagraph paragraph : paragraphs){
						replaceParamsInParagraph(paragraph, params);
					}
				}
			}
		}
	}
	
	/**
	 * 正则表达式匹配字符串 ${}
	 * @param text
	 * @return
	 */
	public static Matcher getMatcher(String text){
		Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(text);
		return matcher;
	}
}
