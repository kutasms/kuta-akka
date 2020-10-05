package com.kuta.base.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.Base64.Decoder;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

/**
 * 图片工具
 */
public class PictureUtil {

	/**
	 * 基础宽度750, 多端基本使用此宽度
	 */
	private static final Double BASE_WIDTH = 750.00;

	/**
	 * 导入本地图片到缓冲区
	 * 
	 * @param imgName
	 *            本地图片
	 * @return 图片二进制对象
	 */
	public static BufferedImage loadImageLocal(String imgName) {
		try {
			return ImageIO.read(new File(imgName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 合并图片，本方法主要用于生成图形验证码中的滑块须滑动终点图片
	 * 
	 * @param upper
	 *            上层图片对象
	 * @param zoomWidth
	 *            缩放宽度
	 * @param upperActualWidth
	 *            上层真实宽度
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 图片二进制对象
	 */
	public static BufferedImage merge(BufferedImage upper, Float zoomWidth, Float upperActualWidth, int x, int y) {

		// Integer orgWidth = background.getWidth();
		// Integer orgHeight = background.getHeight();
		Float ratio = 610f / 343f;
		Float actualWidth = zoomWidth;
		Float actualHeight = zoomWidth / ratio;

		BufferedImage geneImage = new BufferedImage(actualWidth.intValue(), actualHeight.intValue(),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = geneImage.createGraphics();
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 1.0f));
		// g.drawImage(background.getScaledInstance(actualWidth.intValue(),
		// actualHeight.intValue(), Image.SCALE_SMOOTH), 0, 0, null);
		Integer upperWidth = upper.getWidth();
		Integer upperHeight = upper.getHeight();

		Double upperRatio = upperWidth.doubleValue() / upperHeight.doubleValue();
		Double upperActualHeight = upperActualWidth / upperRatio;

		g.drawImage(
				upper.getScaledInstance(upperActualWidth.intValue(), upperActualHeight.intValue(), Image.SCALE_SMOOTH),
				x, y, null);
		g.dispose();
		return geneImage;
	}

	/**
	 * 图片对象转换为base64字符串
	 * 
	 * @param image
	 *            图片对象
	 * @return base64字符串
	 */
	public static String toBase64(BufferedImage image) throws IOException {
		return toBase64(image, "png");
	}

	/**
	 * 图片对象转换为base64字符串
	 * 
	 * @param image
	 *            图片对象
	 * @param type
	 *            图片类型
	 * @return base64字符串
	 */
	public static String toBase64(BufferedImage image, String type) throws IOException {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();// io流
			ImageIO.write(image, type, out);// 写入流中
			byte[] bytes = out.toByteArray();// 转换成字节
			String base64 = Base64.getEncoder().encode(bytes).toString().trim();// 转换成base64串
			base64 = base64.replaceAll("\n", "").replaceAll("\r", "");// 删除 \r\n
			return base64;
		} finally {
			out.close();
		}
	}

	/**
	 * 图片对象转换为带url前缀的base64字符串
	 * 
	 * @param image
	 *            图片对象
	 * @return base64字符串
	 */
	public static String toBase64WithUrl(BufferedImage image) throws IOException {
		return toBase64WithUrl(image, "png");
	}

	/**
	 * 图片对象转换为带url前缀的base64字符串
	 * 
	 * @param image
	 *            图片对象
	 * @param type
	 *            图片类型
	 * @return base64字符串
	 */
	public static String toBase64WithUrl(BufferedImage image, String type) throws IOException {
		String prefix = "data:image/%s;base64,%s";
		return String.format(prefix, type, toBase64(image, type));
	}

	/**
	 * 将base64字符串保存为jpeg图片文件
	 * @param base64Str base64字符串
	 * @param savePath 图片保存路径
	 * @return true:保存成功,false:保存失败
	 * */
	public static boolean saveImage(String base64Str,String savePath) {
		//对字节数组字符串进行Base64解码并生成图片 
		if (base64Str == null) {
			return false;//图像数据为空
		}
		File file = new File(savePath);
		if(file.exists()) {
			file.delete();
		}
		Decoder decoder = Base64.getDecoder();

		try {
			//Base64解码 
			byte[] b = decoder.decode(base64Str);
			for(int i=0;i<b.length;++i) 
			{
				if(b[i]<0)
				{
					//调整异常数据
					b[i]+=256;
				}
			}
			//生成jpeg图片
			OutputStream out = new FileOutputStream(savePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
}
