package com.tzdr.common.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class QrcodeUtil {
	private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    
    private static final int width= 500;
    private static final int height= 500;
    private static final String format = "gif"; // 二维码图片格式
    
	public static void createQrcode(String path,String rediectUrl,Map<String, String> mapParam){
		String  param= getRequsetMapParam(mapParam);
		String text = rediectUrl+"?"+param;//这里是URL ，扫描之后就跳转到这个界面
        try {
        	// 设置编码，防止中文乱码
        	Hashtable<EncodeHintType, Object> ht = new Hashtable<EncodeHintType, Object> ();
        	ht.put (EncodeHintType.CHARACTER_SET, "UTF-8");
        	// 设置二维码参数(编码内容，编码类型，图片宽度，图片高度,格式)
        	
        	BitMatrix bitMatrix = new MultiFormatWriter ().encode (text, BarcodeFormat.QR_CODE, width, height, ht);
        	// 生成二维码(定义二维码输出服务器路径)
        	File outputFile = new File (path);
        	if (!outputFile.exists ())
        	{
        		//创建文件夹
        		outputFile.mkdir ();
        	}
        	int b_width = bitMatrix.getWidth ();
        	int b_height = bitMatrix.getHeight ();
        	// 建立图像缓冲器
        	BufferedImage image = new BufferedImage (b_width, b_height, BufferedImage.TYPE_3BYTE_BGR);
        	for ( int x = 0; x < b_width; x++ )
        	{
        		for ( int y = 0; y < b_height; y++ )
        		{
        			image.setRGB (x, y, bitMatrix.get (x, y) ? BLACK : WHITE);
        		}
        	}
        	// 生成二维码
        	ImageIO.write (image, format, new File (path + ("/" + mapParam.get("generalizeId") + ".") + format)); //二维码的名称是generalizeId.sgif
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String getRequsetMapParam(Map<String, String> mapParam){
		StringBuffer buffer = new StringBuffer();
		if(mapParam != null){
			int size = mapParam.size();
			int count = 0 ; 
			for (Entry<String, String> param : mapParam.entrySet()) {
				count++;
				String key = param.getKey();
				Object value = param.getValue();
				buffer.append(key+"="+value);
				if(count != size){
					buffer.append("&");
				}
				
			}
		}
		return buffer.toString();
	}
}
