package com.van.halley.util.file;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZipUtil {
	private static Logger LOG = LoggerFactory.getLogger(ZipUtil.class);

	@SuppressWarnings("resource")
	public static List<Map<String, String>> unZip(File zip, String root) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			// 處理zip檔
			// FileInputStream fins = new FileInputStream(zip);
			// ZipInputStream zins = new ZipInputStream(fins);
			ZipFile zipFile = new ZipFile(zip, "GBK");
			Enumeration<ZipArchiveEntry> e = zipFile.getEntries();
			// ZipEntry ze = null;
			byte ch[] = new byte[1024];
			while (e.hasMoreElements()) {
				ZipArchiveEntry zipEntry = e.nextElement();
				String temp = zipEntry.getName();
				LOG.info("unziping " + zipEntry.getName());
				File zfile = new File(root, temp);
				File fpath = new File(zfile.getParentFile().getPath());

				if (zipEntry.isDirectory()) {
					if (!zfile.exists())
						zfile.mkdirs();
				} else {
					if (!fpath.exists())
						fpath.mkdirs();
					FileOutputStream fouts = new FileOutputStream(zfile);
					InputStream in = zipFile.getInputStream(zipEntry);
					int i;
					while ((i = in.read(ch)) != -1)
						fouts.write(ch, 0, i);
					// zins.closeEntry();
					fouts.close();
					in.close();
				}
			}
			// fins.close();
			// zins.close();

		} catch (Exception e) {
			System.err.println("Exception from ZipUtil -> unZip() : " + e.getMessage());
			e.printStackTrace(System.err);
		}
		return list;
	}
	
	/**
	 * 将多个文件进行压缩
	 * @param zipName
	 * @param inputFiles
	 */
	public static void filesToZip(String zipName, String... inputFiles) {
		ZipArchiveOutputStream zaos = null;
		BufferedOutputStream bos = null;
		try {
			zaos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(zipName)));
			zaos.setEncoding("GBK");
			bos = new BufferedOutputStream(new ByteArrayOutputStream());
			for (int i = 0; i < inputFiles.length; i++) {
				File file = new File(inputFiles[i]);
				if (file.isDirectory()) {
					packToolFiles(zaos, file.getAbsolutePath(), file.getName() + File.separator);
				} else {
					zaos.putArchiveEntry(new ZipArchiveEntry(file.getName()));
					IOUtils.copy(new FileInputStream(file.getAbsolutePath()), zaos);
					zaos.closeArchiveEntry();
				}
			}
			bos.flush();
		} catch (FileNotFoundException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", inputFiles, zipName, e);
		} catch (IOException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", inputFiles, zipName, e);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (zaos != null) {
					zaos.close();
				}
			} catch (IOException e) {
				LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", inputFiles, zipName, e);
			}
		}
	}
	
	/**
	 * 压缩目录
	 * @param inputDir
	 * @param zipName
	 */
	public static void folderToZip(String inputDir, String zipName) {
		ZipArchiveOutputStream zaos = null;
		try {
			zaos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(zipName)));
			zaos.setEncoding("GBK");

			packToolFiles(zaos, inputDir, "");
		} catch (FileNotFoundException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", zipName, inputDir, e);
		} catch (IOException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", zipName, inputDir, e);
		} finally {
			try {
				if (zaos != null) {
					zaos.close();
				}
			} catch (IOException e) {
				LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", zipName, inputDir, e);
			}
		}
	}

	/**
	 * 压缩目录
	 * @param inputDir 被压缩目录
	 * @param zipName 压缩文件
	 * @param zipDir 压缩文件中的目录
	 */
	public static void folderToZip(String inputDir, String zipName, String zipDir) {
		if (StringUtils.isNotEmpty(zipDir)) {
			zipDir = zipDir + File.separator;
		}else{
			zipDir = "";
		}
		ZipArchiveOutputStream zaos = null;
		try {
			zaos = new ZipArchiveOutputStream(new BufferedOutputStream(new FileOutputStream(zipName)));
			zaos.setEncoding("GBK");

			packToolFiles(zaos, inputDir, zipDir);
		} catch (FileNotFoundException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", zipName, inputDir, e);
		} catch (IOException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", zipName, inputDir, e);
		} finally {
			try {
				if (zaos != null) {
					zaos.close();
				}
			} catch (IOException e) {
				LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", zipName, inputDir, e);
			}
		}
	}

	public static void packToolFiles(ZipArchiveOutputStream zaos, String inputDir, String zipDir)
			throws FileNotFoundException, IOException {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new ByteArrayOutputStream());
			File dir = new File(inputDir);
			// 返回此绝对路径下的文件
			File[] files = dir.listFiles();
			if (files == null || files.length < 1) {
				return;
			}
			for (int i = 0; i < files.length; i++) {
				// 判断此文件是否是一个文件夹
				if (files[i].isDirectory()) {
					packToolFiles(zaos, files[i].getAbsolutePath(), zipDir + files[i].getName() + File.separator);
				} else {
					zaos.putArchiveEntry(new ZipArchiveEntry(zipDir + files[i].getName()));
					IOUtils.copy(new FileInputStream(files[i].getAbsolutePath()), zaos);
					zaos.closeArchiveEntry();
				}
			}
			bos.flush();
		} catch (FileNotFoundException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", inputDir, inputDir, e);
		} catch (IOException e) {
			LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", inputDir, inputDir, e);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				LOG.error("压缩文件 {} 至指定压缩文件 {} 发生错误 {}", inputDir, inputDir, e);
			}
		}
	}

	/**
	 * 把一个zip文件解压到一个指定的目录中
	 * 
	 * @param zipName
	 *            zip文件路径
	 * @param outputDir
	 *            目标目录
	 */
	public static void zipToFolder(String zipName, String outputDir) {
		File zipfile = new File(zipName);
		ZipFile zf = null;
		if (zipfile.exists()) {
			try {
				outputDir = outputDir + File.separator;
				FileUtils.forceMkdir(new File(outputDir));
				zf = new ZipFile(zipfile, "GBK");
				Enumeration<ZipArchiveEntry> zipArchiveEntrys = zf.getEntries();
				while (zipArchiveEntrys.hasMoreElements()) {
					ZipArchiveEntry zipArchiveEntry = (ZipArchiveEntry) zipArchiveEntrys.nextElement();
					if (zipArchiveEntry.isDirectory()) {
						FileUtils.forceMkdir(new File(outputDir + zipArchiveEntry.getName() + File.separator));
					} else {
						IOUtils.copy(zf.getInputStream(zipArchiveEntry),
								FileUtils.openOutputStream(new File(outputDir + zipArchiveEntry.getName())));
					}
				}
			} catch (IOException e) {
				LOG.error("解压缩文件 {} 至指定目录 {} 发生错误 {}", zipName, outputDir, e);
			} finally {
				try {
					if (zf != null) {
						zf.close();
					}
				} catch (IOException e) {
					LOG.error("解压缩文件 {} 至指定目录 {} 发生错误 {}", zipName, outputDir, e);
				}
			}
		}
	}

	public static void main(String[] args) {
		// zipToFolder("C:\\T\\2014326163556.zip", "C:\\T\\t");
		//folderToZip("C:\\T\\t", "C:\\T\\tt2.zip");
		filesToZip("C:\\T\\tt3.zip", new String[]{"C:\\T\\t\\FW150R V11.0升级软件20140304\\升级帮助-WEB浏览.mht", "C:\\T\\t\\FW150R V11.0升级软件20140304\\fw150rv11.bin"});
	}
}
