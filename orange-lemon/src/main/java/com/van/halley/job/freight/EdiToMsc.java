package com.van.halley.job.freight;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.van.halley.util.file.FileUtil;


/**
 * 船公司MSC的EDI文件报送
 * @author Think
 *
 */
@Component
public class EdiToMsc {
	private static Map<String, String> eventW2Map = new HashMap<String, String>();
	private static Map<String, String> eventQ5Map = new HashMap<String, String>();
	private static Map<String, String> boxTypeMap = new HashMap<String, String>();
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//@Scheduled(cron="0 0 10,15 * * ?")//上报时间为上午11点之前，下午4点之后
	@Scheduled(cron="59 * * * * ?")//每分钟执行
	public void execute(){
		init();//初始化
		StringBuilder text = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		Date current = cal.getTime();
		int eventCount = 0;//总事件数
		//int eventAmount = 0;//总事件数
		//int eventRowCount = 0;//事件行数
		int eventRowAmount = 0;//总事件行数
		//文件头
		//text.append("BG*CHYN** CHYN *MSCU*" + format(current, "yyyyMMdd") + "*" + format(current, "HHmm") +"*0000" + (cal.get(Calendar.HOUR_OF_DAY) > 12 ? '2' : '1'));
		text.append("BG*CHYN**CHYN*MSCU*" + format(current, "yyyyMMdd") + "*" + format(current, "HHmm") +"*0000" + (cal.get(Calendar.HOUR_OF_DAY) > 12 ? '2' : '1'));
		text.append("\r\n");
		text.append("GS*SO*****00001*T*");
		text.append("\r\n");
		
		//String sqlSelect = "SELECT * FROM FRE_BOX WHERE BOX_BELONG='MSC' AND BOX_SOURCE='自管箱'";//对何种状态的箱子处理？
		String sqlSelect = "SELECT * FROM FRE_BOX WHERE BOX_BELONG='MSC' AND BOX_SOURCE='自管箱' AND STATUS = '可用' AND EVENT_STATUS IS NOT NULL";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sqlSelect);
		if(list != null && !list.isEmpty()){
			for(int i=1, size=list.size(); i<=size; i++){
				String countString = "";
				if(i < 10){
					countString = ("000" + i);
				}else if(i < 100){
					countString = ("00" + i);
				}else if(i < 1000){
					countString = ("0" + i);
				}else if(i < 1000){
					countString = String.valueOf(i);
				}
				text.append("ST*322*001" + countString);
				text.append("\r\n");
				String boxNumber = (String)list.get(i - 1).get("BOX_NUMBER");
				text.append("N7*" + boxNumber.substring(0, 4) + "*" + boxNumber.substring(4, 10)  +"*0*G*******CN*MSC*NOR****K*" + boxNumber.substring(10) + "*DR***" + boxTypeMap.get((String)list.get(i - 1).get("BOX_TYPE")));
				text.append("\r\n");
				text.append("W2*" + boxNumber.substring(0, 4) + "*" + boxNumber.substring(4, 10)  + "**CN*" + eventW2Map.get((String)list.get(i - 1).get("EVENT_STATUS")) +"********" + boxNumber.substring(10));
				text.append("\r\n");
				text.append("Q5*" + eventQ5Map.get((String)list.get(i - 1).get("EVENT_STATUS")) + "*" + format(current, "yyMMdd") + "*" + format(current, "HHmm") + "*LT");
				text.append("\r\n");
				text.append("R4*R*K**");
				text.append("\r\n");
				text.append("R4*L*K**");
				text.append("\r\n");
				text.append("R4*D*K**");
				text.append("\r\n");
				text.append("R4*N*K**");
				text.append("\r\n");
				text.append("N9*BN*" + (String)list.get(i - 1).get("DESCN"));//需取提单号
				text.append("\r\n");
				text.append("SE*10*001" + countString);
				text.append("\r\n");
				eventCount++;//事件总数
				eventRowAmount += 10;//目前每个事件的行数固定
			}
		}
	
		//文件尾
		text.append("GE*" + eventCount + "*00001");
		text.append("\r\n");
		text.append("EG*00001*1*" + eventCount + "*" + eventRowAmount);
		//System.out.println(text.toString());
		
		String fileName = "CHUANGYUAN" + format(current, "yyyyMMdd") + (cal.get(Calendar.HOUR_OF_DAY) > 12 ? "PM" : "AM") +".TXT";
		try {
			FileUtils.writeStringToFile(new File(FileUtil.attachmentPath, fileName), text.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按照一定的模式格式化时期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public String format(Date date, String pattern){
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public void init(){
		eventW2Map.put(null, "X");
		eventW2Map.put("进口重箱卸船进场", "L");
		eventW2Map.put("进口重箱提重出场", "AH");
		eventW2Map.put("进口拆空回箱", "E");
		eventW2Map.put("出口空箱出场取消", "E");
		eventW2Map.put("退关返场", "E");
		eventW2Map.put("坏箱", "E");
		eventW2Map.put("坏箱修复", "E");
		eventW2Map.put("空箱出场装箱", "E");
		eventW2Map.put("出口重箱装箱回场", "");
		eventW2Map.put("空箱装船出场", "E");
		eventW2Map.put("空箱调空进场", "E");
		
		eventQ5Map.put(null, "X");
		eventQ5Map.put("进口重箱卸船进场", "UO");
		eventQ5Map.put("进口重箱提重出场", "D");
		eventQ5Map.put("进口拆空回箱", "A");
		eventQ5Map.put("出口空箱出场取消", "A");
		eventQ5Map.put("退关返场", "A");
		eventQ5Map.put("坏箱", "DM");
		eventQ5Map.put("坏箱修复", "G");
		eventQ5Map.put("空箱出场装箱", "D");
		eventQ5Map.put("出口重箱装箱回场", "EBY");
		eventQ5Map.put("空箱装船出场", "AO");
		eventQ5Map.put("空箱调空进场", "UO");
		
		boxTypeMap.put("20'DV", "2210");
		boxTypeMap.put("20'GP", "2210");
		boxTypeMap.put("40'DV", "4310");
		boxTypeMap.put("40'GP", "4310");
		boxTypeMap.put("40'HC", "4510");
		boxTypeMap.put("20'OT", "2251");
		boxTypeMap.put("40'OT", "4351");
	}
	
	public static void main(String[] args) {
	}
}
