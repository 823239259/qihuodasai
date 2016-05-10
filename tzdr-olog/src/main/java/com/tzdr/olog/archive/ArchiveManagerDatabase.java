package com.tzdr.olog.archive;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Compressions;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.Reflections;
import com.tzdr.common.utils.mapper.CsvMapper;
import com.tzdr.olog.OlogManager;
import com.tzdr.olog.db.domain.Olog;



/**
 * Olog数据归档数据库实现
 * 
 * <p>
 * 功能：
 * <li>归档到指定目录，默认在WEB-INF/logs/archive/Olog_yyyyMMdd.log
 * <li>支持归档后自动压缩归档文件
 * <li>支持归档后清除已归档的数据
 * <li>支持设置归档的数据范围，使用设定N天前的数据可以归档
 * <li>支持数据每次抓取的大小（服务器内存占用更下，可控）
 * <li>支持指定归档文件的前缀和文件的编码
 * 
 * @author Lin Feng
 * 
 */
public class ArchiveManagerDatabase implements ArchiveManager {

	/** 系统日志 */
	private static final Logger logger = LoggerFactory
			.getLogger(ArchiveManagerDatabase.class);

	/** 存储根目录 */
	private String storageRoot = "/WEB-INF/logs/archive";
	/** 归档后是否清除数据 */
	private boolean archiveCleanup = true;
	/** 归档后是否压缩数据 */
	private boolean archiveCompress = true;
	/** 归档多少天前的数据 */
	private int archivebeforeDays = 90;
	/** 归档文件前缀 ${archiveFileNamePrefix}_yyyyMMdd.log */
	private String archiveFileNamePrefix = "Olog";
	/** 归档文件编码 */
	private String archiveEncoding = "UTF-8";
	/** 从数据库每次抓取到内存的数据量 */
	private int batchSize = 100;

	/** Olog日志管理 */
	OlogManager ologManager;

	@Override
	public void archive() {

		long millisecond = archivebeforeDays * 24 * 60 * 60 * 1000L;
		Date executeTime = new Date();
		Date beforeDate = DateUtils
				.truncate(Dates.addDate(executeTime, -millisecond),
						Calendar.DAY_OF_MONTH);

		PrintWriter out = null;
		try {
			File dest = getArchiveFile();
			if (dest.exists()) {
				dest.delete();
			}
			out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(dest, true), archiveEncoding)));

			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("LT_operateTime", beforeDate);

			PageInfo<Olog> pageInfo = new PageInfo<Olog>(batchSize, 1);
			pageInfo = ologManager.query(conditions, pageInfo);
			archivePage(pageInfo.getPageResults(), out);

			long totalPages = pageInfo.getTotalPage();
			for (int i = 2; i <= totalPages; i++) {
				pageInfo = new PageInfo<Olog>(batchSize, i);
				pageInfo = ologManager.query(conditions, pageInfo);
				archivePage(pageInfo.getPageResults(), out);
			}
			// 先关闭归档文件，然后在归档压缩
			long totalCount = pageInfo.getTotalCount();
			pageInfo.setPageResults(null);
			pageInfo = null;

			IOUtils.closeQuietly(out);
			String executeTimeForLogger = Dates.format(executeTime);
			String beforeDateForLogger = Dates.format(beforeDate, "yyyy-MM-dd");
			logger.info("Archive file success. execute-time: ["
					+ executeTimeForLogger + "], Data range before:["
					+ beforeDateForLogger + "], total:[" + totalCount
					+ "], file:[" + dest.getName() + "]");

			// 压缩
			if (archiveCompress) {
				try {
					File target = Compressions.zip(dest);
					logger.info("Archive Compression success.execute-time: ["
							+ executeTimeForLogger + "], Data range before:["
							+ beforeDateForLogger + "], total:[" + totalCount
							+ "],file: [" + target.getName() + "]");
				} catch (Exception e) {
					logger.info("Archive Compression failure. execute-time: ["
							+ executeTimeForLogger + "], Data range before:["
							+ beforeDateForLogger + "], total:[" + totalCount
							+ "], errorMessage: [ " + e.getMessage() + "]");
					// don't do anything.
				}

			}

			// 清理
			if (archiveCleanup) {
				ologManager.cleanup(beforeDate);
				logger.info("Archive cleanup success. execute-time: ["
						+ executeTimeForLogger + "], Data range before:["
						+ beforeDateForLogger + "], total:[" + totalCount + "]");
			}

		} catch (Exception e) {
			logger.info("Archive[ before " + Dates.format(beforeDate)
					+ "] failue. -->" + e.getMessage());
			throw new RuntimeException("Archive[ before "
					+ Dates.format(beforeDate) + "] failue. -->"
					+ e.getMessage());
		} finally {
			IOUtils.closeQuietly(out);
		}

	}

	protected String marshal(Olog olog) {
		return CsvMapper.marshal(
				olog,
				Reflections.getSimpleFieldNames(Olog.class).toArray(
						new String[] {}));
	}

	/**
	 * 按文件名前缀和天的日期戳命名
	 * 
	 * @return
	 */
	protected String getArchiveFileName() {
		return getArchiveFileNamePrefix() + "_"
				+ Dates.format(new Date(), "yyyyMMdd") + ".log";
	}

	/**
	 * 归档1页数据，每页数据大小根据参数batchSize确定。
	 * 
	 * @param ologs
	 * @param out
	 */
	private void archivePage(List<Olog> ologs, PrintWriter out) {
		for (Olog olog : ologs) {
			out.println(marshal(olog));
		}
		out.flush();
	}

	/**
	 * 获取归档文件的文件名
	 * 
	 * @return
	 */
	private File getArchiveFile() {
		return new File(getRoot(), getArchiveFileName());
	}

	/**
	 * 获取归档文件的存放目录，如果没有，则创建 
	 * @return
	 */
	private File getRoot() {
		String root = storageRoot;
		if (StringUtils.isBlank(root)) {
			throw new RuntimeException("Archive storage root is not setting.");
		}
		if (StringUtils.contains(root, ":")) {
			String rootPrefix = StringUtils.substringBefore(root, ":");
			String rootPrefixReplaced = System.getProperty(rootPrefix);
			if (StringUtils.isBlank(rootPrefixReplaced)) {
				throw new RuntimeException("exsit prefix:[" + rootPrefix
						+ "], but there is no system environment variable.");
			}
			root = StringUtils.trimToEmpty(rootPrefixReplaced)
					+ StringUtils.substringAfter(root, ":");
		}
		File rootFile = new File(root);
		if (!rootFile.exists()) {
			rootFile.mkdirs();
		}
		return rootFile;

	}

	public String getStorageRoot() {
		return storageRoot;
	}

	public void setStorageRoot(String storageRoot) {
		this.storageRoot = storageRoot;
	}

	public boolean isArchiveCleanup() {
		return archiveCleanup;
	}

	public void setArchiveCleanup(boolean archiveCleanup) {
		this.archiveCleanup = archiveCleanup;
	}

	public int getArchivebeforeDays() {
		return archivebeforeDays;
	}

	public void setArchivebeforeDays(int archivebeforeDays) {
		this.archivebeforeDays = archivebeforeDays;
	}

	public String getArchiveFileNamePrefix() {
		return archiveFileNamePrefix;
	}

	public void setArchiveFileNamePrefix(String archiveFileNamePrefix) {
		this.archiveFileNamePrefix = archiveFileNamePrefix;
	}

	public boolean isArchiveCompress() {
		return archiveCompress;
	}

	public void setArchiveCompress(boolean archiveCompress) {
		this.archiveCompress = archiveCompress;
	}

	public int getBatchSize() {
		return batchSize;
	}

	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	public String getArchiveEncoding() {
		return archiveEncoding;
	}

	public void setArchiveEncoding(String archiveEncoding) {
		this.archiveEncoding = archiveEncoding;
	}

	public void setOlogManager(OlogManager ologManager) {
		this.ologManager = ologManager;
	}

}
