package com.van.halley.job.freight;

import java.io.File;
import java.util.List;

import javax.activation.FileDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.van.halley.core.store.FileStoreHelper;
import com.van.halley.core.store.StoreResult;
import com.van.halley.db.persistence.entity.DiskInfo;
import com.van.halley.db.persistence.entity.FreightOrder;
import com.van.halley.util.file.FileUtil;
import com.van.service.DiskInfoService;
import com.van.service.FreightOrderService;
import com.van.service.UserService;

public class FreightOrderCommissionTask {
	@Autowired
	private FreightOrderService feightOrderService;
	@Autowired
	private DiskInfoService diskInfoService;
	@Autowired
	private FileStoreHelper fileStoreHelper;
	@Autowired
	private UserService userService;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void execute(){
		List<FreightOrder> list = feightOrderService.getAll();
		String sql;
		for(FreightOrder item : list){
			sql = "SELECT COUNT(1) FROM OUT_DISK_INFO WHERE FILE_NAME LIKE '" + item.getOrderNumber() + "%'";
			int count = jdbcTemplate.queryForObject(sql, Integer.class);
			if(count == 0){
				try {
					StoreResult storeResult = fileStoreHelper.addStore(null, new FileDataSource(new File(FileUtil.attachmentPath, item.getCommission())));
					DiskInfo diskInfo = new DiskInfo(item.getOrderNumber() + "." + FileStoreHelper.getSuffix(item.getCommission()), storeResult.getKey(), new File(FileUtil.attachmentPath, item.getCommission()).getTotalSpace(), "/order");
					diskInfo.setCreator(userService.getById(item.getCreatorUserId()));
					diskInfoService.add(diskInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
