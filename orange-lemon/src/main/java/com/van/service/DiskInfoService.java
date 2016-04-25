package com.van.service;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;

import com.van.halley.core.page.PageView;
import com.van.halley.db.persistence.entity.DiskInfo;
import com.van.halley.db.persistence.entity.User;

public interface DiskInfoService {
	public List<DiskInfo> getAll();

	public List<DiskInfo> queryForList(DiskInfo diskInfo);

	public int count(DiskInfo diskInfo);

	public DiskInfo queryForOne(DiskInfo diskInfo);

	public PageView<DiskInfo> query(PageView<DiskInfo> pageView, DiskInfo diskInfo);

	public void add(DiskInfo diskInfo);

	public void delete(String id);

	public void modify(DiskInfo diskInfo);

	public DiskInfo getById(String id);

	/**
	 * 分享
	 * @return
	 */
	public Map<String, Object> toShareDisk();
	
	/**
	 * 完成分享
	 * @param diskInfoIds 
	 * @param accessaryIds 用户ID或部门ID
	 * @param shareType 
	 * @param shareTime
	 * @param expireTime
	 * @return
	 */
	public boolean doneShareDisk(String[] diskInfoIds, String[] accessaryIds, String shareType, Date shareTime, Date expireTime);
	
	/**
	 * 取消分享
	 * @param diskInfoIds
	 * @return
	 */
	public boolean recallShareDiskByDiskInfoId(String[] diskInfoIds);
	
	/**
	 * 取消分享
	 * @param diskShareIds
	 * @return
	 */
	public boolean recallShareDiskByDiskShareId(String[] diskShareIds);

	/**
	 * 创建目录
	 * @param diskName
	 * @param diskDir
	 * @return
	 */
	public boolean doneCreateDir(String diskName, String diskDir);
	
	/**
	 * 创建文件，使用当前用户
	 * @param fileName
	 * @param fileSize
	 * @param fileDir
	 * @param dataSource
	 * @return
	 */
	public boolean doneCreateDisk(String fileName, long fileSize, String fileDir, DataSource dataSource);
	
	/**
	 * 创建文件，指定用户
	 * @param fileName
	 * @param fileSize
	 * @param fileDir
	 * @param dataSource
	 * @param creator
	 * @return
	 */
	public boolean doneCreateDisk(String fileName, long fileSize, String fileDir, DataSource dataSource, User creator);
	
	/**
	 * 加载文件
	 * @param diskInfoIds
	 * @return
	 */
	public InputStream doneLoadDisk(String[] diskInfoIds);

	/**
	 * 删除文件，修改状态为trash
	 * @param diskInfoIds
	 * @return
	 */
	public boolean doneDeleteDisk(String[] diskInfoIds);
	
	/**
	 * 清除文件，将文件直接清除
	 * @param diskInfoIds
	 * @return
	 */
	public boolean doneClearDisk(String[] diskInfoIds);
	
	/**
	 * 恢复文件，修改状态为active
	 * @param diskInfoIds
	 * @return
	 */
	public boolean recallDeleteDisk(String[] diskInfoIds);

	/**
	 * 复制到
	 * @param diskInfoId
	 * @param diskDirId
	 * @return
	 */
	public boolean doneCopyTo(String[] diskInfoIds, String diskDirId);

	/**
	 * 移动到
	 * @param diskInfoId
	 * @param diskDirId
	 * @return
	 */
	public boolean doneMoveTo(String[] diskInfoIds, String diskDirId);

	/**
	 * 重命名
	 * @param diskInfoId
	 * @param fileName
	 * @return
	 */
	public boolean doneRenameTo(String diskInfoId, String fileName);
	/**
	 * 保存
	 * @param diskShareIds
	 * @return
	 */
	public boolean doneKeepDisk(String[] diskInfoIds, String diskInfoId);
}
