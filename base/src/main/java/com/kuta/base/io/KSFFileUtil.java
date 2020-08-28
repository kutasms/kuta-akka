package com.kuta.base.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 文件工具类
 * */
public class KSFFileUtil {

	/**
	 * 以UTF-8字符集读取文件内容
	 * @param fileName 文件名称
	 * @return 读取的字符串
	 * */
	public static String readFileAll(String fileName) {
		return readFileAll(fileName, "UTF-8");
	}

	/**
	 * 读取文件内容
	 *
	 * @param fileName 文件名称
	 * @param encoding 字符集编码
	 * @return 读取的字符串
	 */
	public static String readFileAll(String fileName, String encoding) {
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
	 * 
	 * @param filePath
	 * @param content
	 * @param flag
	 *            true:如果文件存在且存在内容，则内容换行追加；false:如果文件存在且存在内容，则内容替换
	 * @return 操作类型
	 */
	public static String fileLinesWrite(String filePath, String content, boolean flag) {
		String filedo = "write";
		FileWriter fw = null;
		try {
			File file = new File(filePath);
			// 如果文件夹不存在，则创建文件夹
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (!file.exists()) {// 如果文件不存在，则创建文件,写入第一行内容
				file.createNewFile();
				fw = new FileWriter(file);
				filedo = "create";
			} else {// 如果文件存在,则追加或替换内容
				fw = new FileWriter(file, flag);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pw = new PrintWriter(fw);
		pw.println(content);
		pw.flush();
		try {
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filedo;
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
	 * @param fileName 文件地址
	 * @return 二进制数组
	 */
	public static byte[] readFileByBytes(String fileName) throws IOException {
		FileInputStream in = new FileInputStream(fileName);
		byte[] bytes = null;
		try {

			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);

			byte[] temp = new byte[1024];

			int size = 0;

			while ((size = in.read(temp)) != -1) {
				out.write(temp, 0, size);
			}

			in.close();

			bytes = out.toByteArray();

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}
		}
		return bytes;
	}

	/**
	 * 读取文件
	 * @param file 文件对象
	 * @return 读取的字符串
	 * */
	public static String readFile(File file) {
		BufferedReader in = null;
		FileReader reader = null;
		try {
			reader = new FileReader(file);
			in = new BufferedReader(reader);
			StringBuilder sBuilder = new StringBuilder();
			String cbuf = null;
			while ((cbuf = in.readLine()) != null) {
				sBuilder.append(cbuf);
				sBuilder.append("\n");
			}
			return sBuilder.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				reader.close();
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
