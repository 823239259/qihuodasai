package com.tzdr.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * 压缩工具
 * 
 * @author Lin Feng
 * 
 */
public class Compressions {

	public static void zip(List<File> files, File target) {
		OutputStream out = null;
		try {
			out = new FileOutputStream(target);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		zip(files, out);
	}

	public static void zip(List<File> files, OutputStream out) {
		ZipOutputStream zipOut = null;
		try {
			zipOut = new ZipOutputStream(out);
			for (File file : files) {
				if (file.exists()) {
					zip(file, zipOut, false);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("ZIP压缩失败 -> " + e.getMessage());
		} finally {
			IOUtils.closeQuietly(zipOut);
		}
	}

	public static void zip(File source, File target, boolean deleteSource) {
		List<File> sources = Lists.newArrayList(source);
		zip(sources, target);
		if (deleteSource) {
			source.delete();
		}
	}

	public static File zip(File source) {
		File target = new File(source.getParentFile(),
				StringUtils.substringBeforeLast(source.getName(), ".") + ".zip");
		zip(source, target, true);
		return target;
	}

	public static void zip(File source, ZipOutputStream zipOut,
			boolean needCloseOutStream) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(source);
			zipOut.putNextEntry(new ZipEntry(source.getName()));
			byte[] buf = new byte[1024];
			int num;
			while ((num = in.read(buf)) != -1) {
				zipOut.write(buf, 0, num);
				zipOut.flush();
			}
		} catch (Exception e) {
			throw new RuntimeException("Zip [" + source.getName() + "] fail. <"
					+ e.getMessage() + ">");
		} finally {
			IOUtils.closeQuietly(in);
			if (needCloseOutStream) {
				IOUtils.closeQuietly(zipOut);
			}
		}

	}

}
