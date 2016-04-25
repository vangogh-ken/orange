package com.zen.junit.test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.van.halley.db.persistence.entity.FreightInvoice;
import com.van.service.impl.FreightDelegateServiceImpl;
import com.van.service.impl.FreightInvoiceServiceImpl;
import com.van.service.impl.ResourceServiceImpl;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class ResourceServiceImplTest {

	// @Test
	public void test() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/spring-*.xml");
		ResourceServiceImpl bean = (ResourceServiceImpl) ctx
				.getBean("resourceService");
		bean.getAll();
		bean.getById("1");
		//bean.getResourceByUserId("1");
	}

	//@Test
	public void testJxls() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/spring-*.xml");
		FreightDelegateServiceImpl freightDelegateService = (FreightDelegateServiceImpl) ctx
				.getBean("freightDelegateService");
		System.out.println(freightDelegateService);
		Map<String, Object> map = freightDelegateService
				.getDataSource(
						"38dfe194-7d12-11e4-bfd7-b870f47f73d5");
		String templateDir = "C:/Users/ken/Desktop/装箱单-JXLS.xls";
		String targetDir = "C:/Users/ken/Desktop/test.xls";
		// 关联模板
		XLSTransformer transformer = new XLSTransformer();
		try {
			// transformer.transformXLS(templateDir, map, targetDir);
			// transformer.transformXLS(templateFileName, beans, destFileName);
			InputStream is = new BufferedInputStream(new FileInputStream(
					templateDir));
			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					targetDir));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is,
					map);
			HSSFSheet sheet = workbook.getSheetAt(0);
			// sheet.addMergedRegion(new Region(2,(short)0,2+i,(short)0));
			// sheet.addMergedRegion(new Region(2,(short)2,2+i,(short)2));
			boolean f = true;
			if (f) {
				// 装箱单需要合并单元格
				sheet.addMergedRegion(new CellRangeAddress(3, ((List) map
						.get("BOX")).size() + 2, 0, 0));
				sheet.addMergedRegion(new CellRangeAddress(3, ((List) map
						.get("BOX")).size() + 2, 4, 4));
				sheet.addMergedRegion(new CellRangeAddress(3, ((List) map
						.get("BOX")).size() + 2, 5, 5));
				sheet.addMergedRegion(new CellRangeAddress(3, ((List) map
						.get("BOX")).size() + 2, 6, 6));
			}

			workbook.write(out);
			System.out.println("----end---");
			is.close();
			out.close();
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void t(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"spring/spring-*.xml");
		FreightInvoiceServiceImpl bean = (FreightInvoiceServiceImpl) ctx.getBean("freightInvoiceService");
		List<FreightInvoice> list = bean.getAll();
		System.out.println(list.get(0).getFasInvoiceType() == null);
		System.out.println("done");
	}
	/**
	 * 重新设置函数 private void resetCellFormula(HSSFWorkbook wb) {
	 * HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(wb); int sheetNum =
	 * wb.getNumberOfSheets(); for (int i = 0; i < sheetNum; i++) { HSSFSheet
	 * sheet = wb.getSheetAt(i); int rows = sheet.getLastRowNum() + 1; for (int
	 * j = 0; j < rows; j++) { HSSFRow row = sheet.getRow(j); if (row == null)
	 * continue; int cols = row.getLastCellNum(); for (int k = 0; k < cols; k++)
	 * { HSSFCell cell = row.getCell(k); // if (cell != null) //
	 * System.out.println("cell["+j+","+k+"]=:"+cell.getCellType()); if (cell ==
	 * null) continue; if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
	 * cell.setCellFormula(cell.getCellFormula()); //
	 * System.out.println("----公式："+cell.getCellFormula()); cell =
	 * e.evaluateInCell(cell); //
	 * System.out.println("-----------"+cell.getNumericCellValue()); } } } } }
	 * 
	 **/

}
