package com.van.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.van.halley.core.page.PageView;
import com.van.halley.core.session.SessionContextHolder;
import com.van.halley.core.store.FileStoreHelper;
import com.van.halley.core.store.InputStreamDataSource;
import com.van.halley.core.store.StoreResult;
import com.van.halley.db.persistence.DiskAclDao;
import com.van.halley.db.persistence.DiskInfoDao;
import com.van.halley.db.persistence.DiskShareDao;
import com.van.halley.db.persistence.OrgEntityDao;
import com.van.halley.db.persistence.UserDao;
import com.van.halley.db.persistence.entity.DiskAcl;
import com.van.halley.db.persistence.entity.DiskInfo;
import com.van.halley.db.persistence.entity.DiskShare;
import com.van.halley.db.persistence.entity.User;
import com.van.halley.util.StringUtil;
import com.van.halley.util.file.ZipUtil;
import com.van.service.DiskInfoService;

@Transactional
@Service("diskInfoService")
public class DiskInfoServiceImpl implements DiskInfoService {
	private static Logger LOG = LoggerFactory.getLogger(DiskInfoServiceImpl.class);
	
	@Autowired
	private DiskInfoDao diskInfoDao;
	@Autowired
	private DiskShareDao diskShareDao;
	@Autowired
	private DiskAclDao diskAclDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OrgEntityDao orgEntityDao;
	@Autowired
	private FileStoreHelper fileStoreHelper;

	public List<DiskInfo> getAll() {
		return diskInfoDao.getAll();
	}

	public List<DiskInfo> queryForList(DiskInfo diskInfo) {
		return diskInfoDao.queryForList(diskInfo);
	}

	public int count(DiskInfo diskInfo) {
		return diskInfoDao.count(diskInfo);
	}

	public DiskInfo queryForOne(DiskInfo diskInfo) {
		return diskInfoDao.queryForOne(diskInfo);
	}

	public PageView<DiskInfo> query(PageView<DiskInfo> pageView, DiskInfo diskInfo) {
		List<DiskInfo> list = diskInfoDao.query(pageView, diskInfo);
		pageView.setResults(list);
		return pageView;
	}

	public void add(DiskInfo diskInfo) {
		diskInfoDao.add(diskInfo);
	}

	public void delete(String id) {
		diskInfoDao.delete(id);
	}

	public void modify(DiskInfo diskInfo) {
		diskInfoDao.modify(diskInfo);
	}

	public DiskInfo getById(String id) {
		return diskInfoDao.getById(id);
	}

	@Override
	public Map<String, Object> toShareDisk() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("users", userDao.getAll());
		map.put("orgEntities", orgEntityDao.getAll());
		return map;
	}

	@Override
	public boolean doneShareDisk(String[] diskInfoIds, String[] accessaryIds, String shareType, Date shareTime, Date expireTime) {
		for(String diskInfoId : diskInfoIds){
			DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
			DiskShare diskShare = new DiskShare(diskInfo, shareType, shareTime, expireTime);
			diskShare.setId(StringUtil.getUUID());
			diskShareDao.add(diskShare);
			List<DiskAcl> diskAcls = new ArrayList<DiskAcl>();
			
			String creatorUserId = diskInfo.getCreator().getId();
			boolean containsSelf = false;
			if(DiskShare.Type.PUBLIC.equals(shareType)){
				for(User user : userDao.getAll()){
					if(creatorUserId.equals(user.getId())){
						containsSelf = true;
					}
					diskAcls.add(new DiskAcl(user.getId(), diskShare.getId()));
				}
			}else if(DiskShare.Type.GROUP.equals(shareType)){
				List<User> users = new ArrayList<User>();
				
				for(String accessaryId : accessaryIds){
					users.addAll(userDao.getByOrgEntityId(accessaryId));
				}
				
				for(User user : users){
					if(creatorUserId.equals(user.getId())){
						containsSelf = true;
					}
					
					diskAcls.add(new DiskAcl(user.getId(), diskShare.getId()));
				}
			}else {
				for(String accessaryId : accessaryIds){
					if(creatorUserId.equals(accessaryId)){
						containsSelf = true;
					}
					
					diskAcls.add(new DiskAcl(accessaryId, diskShare.getId()));
				}
			}
			//~ 将自己添加到
			if(!containsSelf){
				diskAcls.add(new DiskAcl(diskInfo.getCreator().getId(), diskShare.getId()));
			}
			
			diskAclDao.insertBatch(diskAcls);
		}
		
		return true;
	}

	@Override
	public boolean recallShareDiskByDiskInfoId(String[] diskInfoIds) {
		if(diskInfoIds != null && diskInfoIds.length > 0){
			for(String diskInfoId : diskInfoIds){
				DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
				DiskShare filter = new DiskShare();
				filter.setDiskInfo(diskInfo);
				List<DiskShare> diskShares = diskShareDao.queryForList(filter);
				if(diskShares != null && !diskShares.isEmpty()){
					List<DiskAcl> diskAcls = new ArrayList<DiskAcl>();
					for(DiskShare diskShare : diskShares){
						DiskAcl diskAcl = new DiskAcl();
						diskAcl.setDiskShareId(diskShare.getId());
						diskAcls.addAll(diskAclDao.queryForList(diskAcl));
					}
					diskShareDao.deleteBatch(diskShares);
					diskAclDao.deleteBatch(diskAcls);
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean recallShareDiskByDiskShareId(String[] diskShareIds) {
		if(diskShareIds != null && diskShareIds.length > 0){
			List<DiskShare> diskShares = new ArrayList<DiskShare>();
			List<DiskAcl> diskAcls = new ArrayList<DiskAcl>();
			for(String diskShareId : diskShareIds){
				DiskShare diskShare = diskShareDao.getById(diskShareId);
				diskShares.add(diskShare);
				
				DiskAcl diskAcl = new DiskAcl();
				diskAcl.setDiskShareId(diskShare.getId());
				diskAcls.addAll(diskAclDao.queryForList(diskAcl));
			}
			
			diskShareDao.deleteBatch(diskShares);
			diskAclDao.deleteBatch(diskAcls);
		}
		return true;
	}

	@Override
	public boolean doneCreateDir(String diskName, String diskDir) {
		DiskInfo diskInfo = new DiskInfo();
		diskInfo.setFileName(diskName);
		diskInfo.setFileSuffix("dir");
		diskInfo.setFileDir(diskDir);
		diskInfo.setFileSize(0);
		diskInfo.setFileVersion(DiskInfo.defaultFileVersion);
		diskInfo.setCreator(SessionContextHolder.getContext().getCurrentUser());
		diskInfo.setStatus(DiskInfo.Status.ACTIVE);
		diskInfoDao.add(diskInfo);
		return true;
	}
	
	@Override
	public boolean doneCreateDisk(String fileName, long fileSize, String fileDir, DataSource dataSource) {
		try {
			StoreResult storeResult = fileStoreHelper.addStore(
					String.format(DiskInfo.defaultDirSuffix, 
							SessionContextHolder.getContext().getCurrentUser().getId()) + fileDir, 
					dataSource);
			DiskInfo diskInfo = new DiskInfo(fileName, storeResult.getKey(), fileSize, fileDir);
			diskInfo.setCreator(SessionContextHolder.getContext().getCurrentUser());
			diskInfoDao.add(diskInfo);
		} catch (Exception e) {
			LOG.info("clear disk catch error : {}", e);
		}
		return true;
	}
	
	@Override
	public boolean doneCreateDisk(String fileName, long fileSize, String fileDir, DataSource dataSource, User creator) {
		try {
			StoreResult storeResult = fileStoreHelper.addStore(
					String.format(DiskInfo.defaultDirSuffix, creator.getId()) + fileDir, dataSource);
			DiskInfo diskInfo = new DiskInfo(fileName, storeResult.getKey(), fileSize, fileDir);
			diskInfo.setCreator(creator);
			diskInfoDao.add(diskInfo);
		} catch (Exception e) {
			LOG.info("create disk catch error : {}", e);
		}
		return false;
	}

	@Override
	public InputStream doneLoadDisk(String[] diskInfoIds) {
		try {
			if(diskInfoIds.length == 1){
				DiskInfo diskInfo = diskInfoDao.getById(diskInfoIds[0]);
				if("dir".equals(diskInfo.getFileSuffix())){
					String zipFile = System.getProperty("java.io.tmpdir") + StringUtil.getUUID() + ".zip";
					ZipUtil.folderToZip(fileStoreHelper.getDir(String.format(DiskInfo.defaultDirSuffix, diskInfo.getCreator().getId()) + diskInfo.getFileDir(), diskInfo.getFileRef()), zipFile);
					return new FileInputStream(zipFile);
				}else{
					StoreResult storeResult = fileStoreHelper.getStore(String.format(DiskInfo.defaultDirSuffix, diskInfo.getCreator().getId()) + diskInfo.getFileDir(), diskInfo.getFileRef());
					return storeResult.getDataSource().getInputStream();
				}
			}else{
				List<String> fileNames = new ArrayList<String>();
				for(String diskInfoId : diskInfoIds){
					DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
					if("dir".equals(diskInfo.getFileSuffix())){
						String zipFile = System.getProperty("java.io.tmpdir") + StringUtil.getUUID() + ".zip";
						fileNames.add(zipFile);
						ZipUtil.folderToZip(fileStoreHelper.getDir(String.format(DiskInfo.defaultDirSuffix, diskInfo.getCreator().getId()), diskInfo.getFileRef()) + diskInfo.getFileDir(), zipFile);
					}else{
						StoreResult storeResult = fileStoreHelper.getStore(String.format(DiskInfo.defaultDirSuffix, diskInfo.getCreator().getId()) + diskInfo.getFileDir(), diskInfo.getFileRef());
						fileNames.add(((FileDataSource)storeResult.getDataSource()).getFile().getAbsolutePath());
					}
				}
				
				String zipFile = System.getProperty("java.io.tmpdir") + StringUtil.getUUID() + ".zip";
				ZipUtil.filesToZip(zipFile, fileNames.toArray(new String[fileNames.size()]));
				return new FileInputStream(zipFile);
			}
		} catch (Exception e) {
			LOG.info("load disk catch error : {}", e);
		}
		return null;
	}

	@Override
	public boolean doneDeleteDisk(String[] diskInfoIds) {
		if(diskInfoIds != null && diskInfoIds.length > 0){
			for(String diskInfoId : diskInfoIds){
				DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
				if("dir".equals(diskInfo.getFileSuffix())){
					DiskInfo filter = new DiskInfo();
					filter.setFileDir(diskInfo.getFileDir() + "/" + diskInfo.getFileName());
					filter.setCreator(diskInfo.getCreator());
					filter.setStatus(DiskInfo.Status.ACTIVE);
					List<DiskInfo> list = diskInfoDao.queryForList(filter);
					if(list != null && !list.isEmpty()){
						List<String> ids = new ArrayList<String>();
						for(DiskInfo item : list){
							ids.add(item.getId());
						}
						doneDeleteDisk(ids.toArray(new String[ids.size()]));
					}
					diskInfo.setStatus(DiskInfo.Status.TRASH);
					diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
					diskInfoDao.modify(diskInfo);
				}else{
					diskInfo.setStatus(DiskInfo.Status.TRASH);
					diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
					diskInfoDao.modify(diskInfo);
				}
			}
			return true;
		}else{
			return false;
		}	
	}
	
	@Override
	public boolean doneClearDisk(String[] diskInfoIds) {
		if(diskInfoIds != null && diskInfoIds.length > 0){
			for(String diskInfoId : diskInfoIds){
				clear(diskInfoDao.getById(diskInfoId));			
			}
			
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 删除记录和文件内容
	 * @param diskInfo
	 */
	private void clear(DiskInfo diskInfo){
		if("dir".equals(diskInfo.getFileSuffix())){
			DiskInfo filter = new DiskInfo();
			filter.setFileDir(diskInfo.getFileDir() + "/" + diskInfo.getFileName());
			filter.setCreator(diskInfo.getCreator());
			filter.setStatus(DiskInfo.Status.TRASH);
			
			List<DiskInfo> list = diskInfoDao.queryForList(filter);
			if(list != null && !list.isEmpty()){
				for(DiskInfo item : list){
					clear(item);
				}
			}
			
			diskInfoDao.delete(diskInfo.getId());
		}else{
			try {
				fileStoreHelper.removeStore(diskInfo.getFileDir(), diskInfo.getFileRef());
				diskInfoDao.delete(diskInfo.getId());
			} catch (Exception e) {
				LOG.info("clear disk catch error : {}", e);
			}
		}
	}

	@Override
	public boolean recallDeleteDisk(String[] diskInfoIds) {
		if(diskInfoIds != null && diskInfoIds.length > 0){
			for(String diskInfoId : diskInfoIds){
				DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
				if("dir".equals(diskInfo.getFileSuffix())){
					DiskInfo filter = new DiskInfo();
					filter.setFileDir(diskInfo.getFileDir() + "/" + diskInfo.getFileName());
					filter.setCreator(diskInfo.getCreator());
					filter.setStatus(DiskInfo.Status.TRASH);
					List<DiskInfo> list = diskInfoDao.queryForList(filter);
					if(list != null && !list.isEmpty()){
						List<String> ids = new ArrayList<String>();
						for(DiskInfo item : list){
							ids.add(item.getId());
						}
						doneDeleteDisk(ids.toArray(new String[ids.size()]));
					}
					diskInfo.setStatus(DiskInfo.Status.ACTIVE);
					diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
					diskInfoDao.modify(diskInfo);
				}else{
					diskInfo.setStatus(DiskInfo.Status.ACTIVE);
					diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
					diskInfoDao.modify(diskInfo);
				}
			}
			return true;
		}else{
			return false;
		}	
	}

	@Override
	public boolean doneCopyTo(String[] diskInfoIds, String diskDirId) {
		if(diskInfoIds != null && diskInfoIds.length > 0){
			for(String diskInfoId : diskInfoIds){
				DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
				DiskInfo diskDir = diskInfoDao.getById(diskDirId);
				
				diskInfo.setId(StringUtil.getUUID());
				diskInfo.setFileDir(diskDir.getFileName());
				
				diskInfo.setCreateTime(new Date());
				diskInfo.setModifyTime(new Date());
				diskInfo.setCreator(SessionContextHolder.getContext().getCurrentUser());
				diskInfo.setModifier(null);
				
				diskInfoDao.add(diskInfo);
			}
			
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean doneMoveTo(String[] diskInfoIds, String diskDirId) {
		if(diskInfoIds != null && diskInfoIds.length > 0){
			try{
				DiskInfo diskDir = diskInfoDao.getById(diskDirId);
				for(String diskInfoId : diskInfoIds){
					DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
					if("dir".equals(diskInfo.getFileSuffix())){
						
						DiskInfo filter = new DiskInfo();
						filter.setFileDir(diskInfo.getFileDir() + "/" + diskInfo.getFileName());
						filter.setCreator(diskInfo.getCreator());
						
						diskInfo.setFileDir(diskDir.getFileDir() + "/" + diskDir.getFileName());
						diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
						
						List<DiskInfo> list = diskInfoDao.queryForList(filter);
						if(list != null && !list.isEmpty()){
							for(DiskInfo item : list){
								renameDirTo(item, diskInfo.getFileDir() + "/" + diskInfo.getFileName());
							}
						}
						
						diskInfoDao.modify(diskInfo);
					}else{
						String model = diskInfo.getModel();
						String key = diskInfo.getKey();
						diskInfo.setFileDir(diskDir.getFileDir() + "/" + diskDir.getFileName());
						String nModel = diskInfo.getModel();
						StoreResult storeResult = fileStoreHelper.budgeStore(model, key, nModel);
						
						diskInfo.setFileRef(storeResult.getKey());
						diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
						diskInfoDao.modify(diskInfo);
					}
				}
			}catch(Exception e){
				
			}
			
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean doneRenameTo(String diskInfoId, String fileName) {
		DiskInfo diskInfo = diskInfoDao.getById(diskInfoId);
		if("dir".equals(diskInfo.getFileSuffix())){
			DiskInfo filter = new DiskInfo();
			filter.setFileDir(diskInfo.getFileDir() + "/" + diskInfo.getFileName());
			filter.setCreator(diskInfo.getCreator());
			
			diskInfo.setFileName(fileName);
			diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
			
			List<DiskInfo> list = diskInfoDao.queryForList(filter);
			if(list != null && !list.isEmpty()){
				for(DiskInfo item : list){
					renameDirTo(item, diskInfo.getFileDir() + "/" + diskInfo.getFileName());
				}
			}
			
			diskInfoDao.modify(diskInfo);
		}else{
			diskInfo.setFileName(fileName);
			diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
			diskInfoDao.modify(diskInfo);
		}
		return true;
	}
	
	private void renameDirTo(DiskInfo diskInfo, String fileDir){
		if("dir".equals(diskInfo.getFileSuffix())){
			DiskInfo filter = new DiskInfo();
			filter.setFileDir(diskInfo.getFileDir() + "/" + diskInfo.getFileName());
			filter.setCreator(diskInfo.getCreator());
			
			List<DiskInfo> list = diskInfoDao.queryForList(filter);
			
			diskInfo.setFileDir(fileDir);
			diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
			if(list != null && !list.isEmpty()){
				for(DiskInfo item : list){
					renameDirTo(item, diskInfo.getFileDir() + "/" + diskInfo.getFileName());
				}
			}
			
			diskInfoDao.modify(diskInfo);
		}else{
			String model = diskInfo.getModel();
			String key = diskInfo.getKey();
			diskInfo.setFileDir(fileDir);
			String nModel = diskInfo.getModel();
			
			StoreResult storeResult;
			try {
				storeResult = fileStoreHelper.budgeStore(model, key, nModel);
				diskInfo.setFileRef(storeResult.getKey());
				diskInfo.setModifier(SessionContextHolder.getContext().getCurrentUser());
				diskInfoDao.modify(diskInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public boolean doneKeepDisk(String[] diskInfoIds, String diskInfoId) {
		boolean flag = true;
		
		DiskInfo diskDir = diskInfoDao.getById(diskInfoId);
		if(!"dir".equals(diskDir.getFileSuffix())){
			flag = false;
			return flag;
		}
		
		for(String id : diskInfoIds){
			DiskInfo diskInfo = diskInfoDao.getById(id);
			doneCreateDisk(diskInfo.getFileName(), diskInfo.getFileSize(), diskDir.getFileName(), new InputStreamDataSource(doneLoadDisk(new String[]{id})));
		}
		return flag;
	}
}
