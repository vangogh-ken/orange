package com.van.halley.util.file;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.van.halley.util.StringUtil;

/**
 * @author Think
 * excel处理相关工具
 * excel文件基本格式: 第一行各个字段标题, 从第二行开始时每条数据
 */
public class XlsUtil {
	private static Logger logger = LoggerFactory.getLogger(XlsUtil.class);
	/**
	 * 读取文件称xls等文件类型
	 */
	public static Workbook getWorkbookFull(File file) {
		Workbook wbook = null;
			// office 2007以上
		if (file.getName().endsWith(".xlsx")) {
			try {
				wbook = new XSSFWorkbook(new FileInputStream(file));
			} catch (IOException e) {
				logger.info("创建EXCEL文件对象出错:{}", e);
			}
			// office 2007以下
		} else if (file.getName().endsWith(".xls")) {
			POIFSFileSystem fs = null;
			try {
				fs = new POIFSFileSystem(new FileInputStream(file));
				wbook = new HSSFWorkbook(fs);
			} catch (IOException e) {
				logger.info("创建EXCEL文件对象出错:{}", e);
			}
		}

		return wbook;
	}
	
	/**
	 * 从每个sheet中获取数据 string 每一列的标题, list 一列的所有值
	 */
	public static Map<String, Object> getDataBySheet(Sheet sheet){
		List<String> attrs = new ArrayList<String>();
		List<List<Object>> values = new ArrayList<List<Object>>();
		int rowNum = sheet.getPhysicalNumberOfRows();
		if(rowNum == 0){
			logger.info("SHEET对象无数据:{}", sheet.getSheetName());
			return null;
		}
		Row rowTile = sheet.getRow(0);
		int cellNum = rowTile.getPhysicalNumberOfCells();
		for(int i=0; i< cellNum; i++){
			attrs.add(rowTile.getCell(i).getStringCellValue());
		}
		
		for(int i=1; i<rowNum; i++){
			Row row = sheet.getRow(i);
			List<Object> valueOfRow = new ArrayList<Object>();
			for(int j=0, len = attrs.size(); j<len; j++){
				valueOfRow.add(getValueByCell(row.getCell(j)));
			}
			
			values.add(valueOfRow);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("attrs", attrs);
		resultMap.put("values", values);
		return resultMap;
	}
	
	/**
	 * 获取cell的值,返回数据对象
	 */
	public static Object getValueByCell(Cell cell) {
		if (cell != null) {
			int type = cell.getCellType();
			switch (type) {
			case 0:
				double n = cell.getNumericCellValue();
				if (DateUtil.isCellDateFormatted(cell)) {
					return DateUtil.getJavaDate(n);
				} else {
					return n;
				}
			case 1:
				return cell.getStringCellValue();
			case 3:
				return "";
			case 4:
				return cell.getBooleanCellValue() + "";
			case 5:
				return "";
			default:
				return "";
			}
		} else {
			return null;
		}

	}
	
	public static void createXls(String fileName, String filePath){
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			workbook.write(out);
			InputStream in = new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 读取workbook到流
	 * @param workbook
	 * @return
	 */
	public static InputStream getInputStreamByXls(Workbook workbook){
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
	
	@SuppressWarnings("deprecation")
	public static Workbook createTemplate(){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("模板一");
		sheet.addMergedRegion(new CellRangeAddress(0, 3, 0, 7));
		sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 7));
		
		sheet.addMergedRegion(new CellRangeAddress(6, 7, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(6, 7, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(6, 7, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(6, 7, 6, 7));
		
		sheet.addMergedRegion(new CellRangeAddress(8, 9, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(8, 9, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(8, 9, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(8, 9, 6, 7));
		
		sheet.addMergedRegion(new CellRangeAddress(10, 11, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(10, 11, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(10, 11, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(10, 11, 6, 7));
		
		sheet.addMergedRegion(new CellRangeAddress(12, 13, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(12, 13, 2, 3));
		sheet.addMergedRegion(new CellRangeAddress(12, 13, 4, 5));
		sheet.addMergedRegion(new CellRangeAddress(12, 13, 6, 7));
		
		sheet.addMergedRegion(new CellRangeAddress(14, 15, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(14, 15, 2, 7));
		
		sheet.addMergedRegion(new CellRangeAddress(16, 19, 0, 1));
		sheet.addMergedRegion(new CellRangeAddress(16, 19, 2, 7));
		
		sheet.addMergedRegion(new CellRangeAddress(20, 23, 0, 7));
		
		//设置字体
		HSSFFont fontTitle = workbook.createFont();
		HSSFFont fontContent = workbook.createFont();
		fontTitle.setFontName("宋体");
		fontTitle.setFontHeight((short)380);
		fontTitle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		fontContent.setFontName("宋体");
		fontContent.setFontHeight((short)250);
		fontContent.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		
		
		Row row = null;
		Cell cell = null;
		HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
		HSSFCellStyle cellStyleContent = workbook.createCellStyle();
		
		HSSFCellStyle cellStyleOfComp = workbook.createCellStyle();
		cellStyleOfComp.setFont(fontContent);
		
		cellStyleTitle.setAlignment(CellStyle.ALIGN_CENTER);// 设置单元格字体居中（左右方向）
		cellStyleTitle.setFont(fontTitle);
		cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//设置字体显示居中(上下方向)
		
		cellStyleContent.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyleContent.setFont(fontContent);
		cellStyleContent.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		
		/*sheet.setPrintGridlines(true);
		sheet.setDisplayGridlines(true);
		sheet.setFitToPage(true);*/
		
		
		cellStyleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyleTitle.setLeftBorderColor((short)8); // 设置单元格的边框颜色． 
		cellStyleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		cellStyleTitle.setRightBorderColor((short)8); 
		cellStyleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		cellStyleTitle.setTopBorderColor((short)8);
		cellStyleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellStyleTitle.setBottomBorderColor((short)8);
		
		cellStyleContent.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 设置单无格的边框为粗体 
		cellStyleContent.setLeftBorderColor((short)8); // 设置单元格的边框颜色． 
		cellStyleContent.setBorderRight(HSSFCellStyle.BORDER_THIN); 
		cellStyleContent.setRightBorderColor((short)8); 
		cellStyleContent.setBorderTop(HSSFCellStyle.BORDER_THIN); 
		cellStyleContent.setTopBorderColor((short)8);
		cellStyleContent.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		cellStyleContent.setBottomBorderColor((short)8);
		
		// 设置单元格背景色 
		//cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index); 
		//cellStyle.setFillPattern(HSSFCellStyle.NO_FILL); 
		
		row = sheet.createRow(0);
		cell = row.createCell(0);
		cell.setCellValue("(创源）进场通知");
		
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellValue("TO:");
		
		row = sheet.createRow(6);
		cell = row.createCell(0);cell.setCellValue("客户编号:");
		cell = row.createCell(2);cell.setCellValue(" ");
		cell = row.createCell(4);cell.setCellValue("箱型箱重:");
		cell = row.createCell(6);cell.setCellValue(" ");
		
		row = sheet.createRow(8);
		cell = row.createCell(0);cell.setCellValue("货物品名:");
		cell = row.createCell(2);cell.setCellValue(" ");
		cell = row.createCell(4);cell.setCellValue("目的港:");
		cell = row.createCell(6);cell.setCellValue(" ");
		
		row = sheet.createRow(10);
		cell =row.createCell(0);cell.setCellValue("货物规格:");
		cell =row.createCell(2);cell.setCellValue(" ");
		cell =row.createCell(4);cell.setCellValue("货物重量:");
		cell =row.createCell(6);cell.setCellValue(" ");
		
		row = sheet.createRow(12);
		cell = row.createCell(0);cell.setCellValue("货物件数:");
		cell = row.createCell(2);cell.setCellValue(" ");
		cell = row.createCell(4);cell.setCellValue(" ");
		cell = row.createCell(6);cell.setCellValue(" ");
		
		row = sheet.createRow(14);
		cell = row.createCell(0);cell.setCellValue("进场编号:");
		cell = row.createCell(2);cell.setCellValue(" ");
		
		row = sheet.createRow(16);
		cell = row.createCell(0);cell.setCellValue("备注:");
		cell = row.createCell(2);cell.setCellValue(" ");
		
		row = sheet.createRow(20);
		cell = row.createCell(0);cell.setCellValue("请司机送货到堆场时，拿此进场通知复印件进场。");
		
		row = sheet.createRow(26);
		cell = row.createCell(5);cell.setCellValue("XXXXXX公司");
		cell.setCellStyle(cellStyleOfComp);
		
		for(int i=0;i<=7; i++){
			sheet.setColumnWidth(i, 2800);
		}
		
		for(int i=0, len = sheet.getNumMergedRegions(); i<len; i++){
			CellRangeAddress region = sheet.getMergedRegion(i);
			for (int j = region.getFirstRow(); j <= region.getLastRow(); j++) {
				HSSFRow hRow = HSSFCellUtil.getRow(j, sheet);
				hRow.setHeight((short)400);
				for (int k = region.getFirstColumn(); k <= region.getLastColumn(); k++) {
					HSSFCell hcell = HSSFCellUtil.getCell(hRow, (short) k);
					if(i == 0){
						hcell.setCellStyle(cellStyleTitle);
					}else{
						hcell.setCellStyle(cellStyleContent);
					}
					
					
				}
			}
		}
		
		HSSFPrintSetup printSetup = sheet.getPrintSetup();
		printSetup.setLandscape(false);//竖直打印
		//printSetup.setScale((short)70);//设置打印放缩70%
		printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);//设置打印页面
		printSetup.setLeftToRight(true);//打印顺序
		printSetup.setFitHeight((short)10);
		printSetup.setFitWidth((short)10);
		return workbook;
	}
	
	/**
	 * 设置单元格格式(主要是边框)
	 */
	public static void setRegionStyle(HSSFSheet sheet, CellRangeAddress region,
			HSSFCellStyle cellStyle) {
		for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
			HSSFRow row = HSSFCellUtil.getRow(i, sheet);
			for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
				HSSFCell cell = HSSFCellUtil.getCell(row, (short) j);
				cell.setCellStyle(cellStyle);
			}
		}
	}
	
	public static void addImageBackgrund(){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("F:\\系统资料\\创源操作委托模板\\提单确认背景扫描件\\PIL.jpg"));
			ImageIO.write(image, "jpg", out);
			FileInputStream fis = new FileInputStream("C:\\T\\test.xls");
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.getSheetAt(0);
			HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
			HSSFClientAnchor anchor = new HSSFClientAnchor();
			patriarch.createPicture(anchor, workbook.addPicture(out.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void header(){
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFHeader header = sheet.getHeader();
	}
	
	public static void main(String[] args) {
		/*File file = new File("C" + File.separator + "Users" + File.separator + "Think" + File.separator + "Desktop", "新建 Microsoft Excel 工作表.xlsx");
		Workbook workbook = getWorkbook(file);
		for(int i=0, len=workbook.getNumberOfSheets(); i<len; i++){
			getDataBySheet(workbook.getSheetAt(i));
		}*/
		
		try {
			FileUtils.copyInputStreamToFile(getInputStreamByXls(createTemplate()), new File("C:\\T\\test.xls"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取所有数据
	 * @param cell
	 * @return
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
	
	/**
	 * 获取sheet
	 */
	public static HSSFSheet getSheet(File file){
		return getWorkbook(file).getSheetAt(0);
	}
	
	public static String convertByDataSource(String s, Map<String, Object> dataSource){
		StringBuilder text = new StringBuilder(s);
		if(s.length() <= 6){
			return s;
		}else{
			int beginIndex = text.lastIndexOf("$$(");
			int endIndex = text.lastIndexOf(")$$");
			while(beginIndex >= 0 && endIndex > 0 && endIndex > beginIndex){
				String $value = text.substring(beginIndex + 3, endIndex);
				if($value.contains(",")){
					String[] vls = $value.split(",");
					Map<String, Object> map = (Map<String, Object>) dataSource.get(vls[0]);
					if(map == null){
						logger.error("数据源数据错误，{}", vls[0], new IllegalAccessError("数据源数据错误"));
						break;
					}else{
						Object originalValue = map.get(vls[1]);
						if(originalValue instanceof Date){
							text.replace(beginIndex, endIndex + 3, StringUtil.parseDateTime((Date)originalValue));
						}else{
							text.replace(beginIndex, endIndex + 3, (originalValue == null ? "" : originalValue.toString()));
						}
					}
					
				}else{
					logger.error("错误的数据格式: ", $value);
					break;
				}
				
				beginIndex = text.lastIndexOf("$$(");
				endIndex = text.lastIndexOf(")$$");
			}
		}
		return text.toString();
	}
	
	/**
	 * FRE_BOX_REQUIRE， FRE_ORDER_BOX， FRE_ACTION， FRE_ORDER；
	 * 数据源，模板文件
	 * 
	 * $$(FRE_ORDER,ORDER_NUMBER),无空格
	 * XXX $$(FRE_ORDER,ORDER_NUMBER) XXXX $$(FRE_ORDER,ORDER_NUMBER)
	 * 
	 * 模板中数据格式为，三种类型：
	 * $$(FRE_ORDER,ORDER_NUMBER)$$
	 * $L(FRE_ORDER,ORDER_NUMBER)L$
	 * $C(FRE_ORDER,ORDER_NUMBER)C$
	 * @param dataSource
	 * @param fileData
	 */
	public static InputStream createXlsAndFillData(Map<String, Object> dataSource, File templateFile){
		HSSFWorkbook workbook = getWorkbook(templateFile);
		if(workbook == null) return null;
		HSSFSheet sheet = workbook.getSheetAt(0);
		if(sheet == null) return null;
		
		for(int i=0, len = sheet.getPhysicalNumberOfRows(); i<len; i++){
			HSSFRow row = sheet.getRow(i);
			if(row == null){
				continue;
			}else{
				for(int j=0, lenRow = row.getPhysicalNumberOfCells(); j<lenRow; j++){
					if(row.getCell(j) == null){
						continue;
					}else{
						row.getCell(j).setCellValue(convertByDataSource(getValueToStringFromCell(row.getCell(j)).trim(), dataSource));
					}
				}
			}
		}
		return getInputStreamByXls(workbook);
	}
}
