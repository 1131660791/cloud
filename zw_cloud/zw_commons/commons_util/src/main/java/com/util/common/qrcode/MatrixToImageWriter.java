package com.util.common.qrcode;
import java.io.File;
import java.io.IOException; 
import java.util.Hashtable;
import java.io.OutputStream; 
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage; 
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix; 
import com.google.zxing.MultiFormatWriter;
    /**
     * 	二维码生成类
     * @Copyright 
     * @author hzw
     * @Date:2015年9月10日
     * 调用  类名.方法名(传入参数);
     */
 public final class MatrixToImageWriter { 
   
   private static final int BLACK = 0xFF000000;   //二维码的条码色
   private static final int WHITE = 0xFFFFFFFF;   //二维码背景颜色

   // 添加构造方法   方便调用
   public MatrixToImageWriter() {} 
   
   // 制作图片方法
   public static BufferedImage toBufferedImage(BitMatrix matrix) { 
     int width = matrix.getWidth();
     int height = matrix.getHeight();
     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
     for (int x = 0; x < width; x++) {
       for (int y = 0; y < height; y++) {
         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE); 
       }
     }
     return image; 
   }
   
    
   // 
   public static void writeToFile(BitMatrix matrix, String format, File file) 
       throws IOException { 
     BufferedImage image = toBufferedImage(matrix);
     if (!ImageIO.write(image, format, file)) {
       throw new IOException("Could not write an image of format " + format + " to " + file);
     }
   }
   
   // 
   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
       throws IOException {
     BufferedImage image = toBufferedImage(matrix);
     if (!ImageIO.write(image, format, stream)) {
       throw new IOException("Could not write an image of format " + format); 
     }
   }
   
   /**
    * 获取二维码 图片 流 对象
    * @param text
    * @param width
    * @param height
    * @return
    * @throws Exception
    */
   public static BufferedImage getQrCode(String text, int width,int height) throws Exception{
	   //二维码的图片格式 
       Hashtable hints = new Hashtable();
       //内容所使用编码
       hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
       BitMatrix bitMatrix = new MultiFormatWriter().encode(text,BarcodeFormat.QR_CODE, width, height, hints); //(text 就是内容)
       //生成二维码
       BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
       for (int x = 0; x < width; x++) {
         for (int y = 0; y < height; y++) {
           image.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE); 
         }
       }
	   return image;
   }

   // 生成二维码  方法 传入 需要 扫描出的值;   成为静态方法 方便用类名进行调用
   public static void GenerateQrcode(String type,int value,String fpdm,int fphm,String sjje,String kprqsj,String xym) throws Exception{
	    // value  是需要的值 先占时 固定 以后在 根据需要 修改。
	      value=10;
	    // 传入的内容  赋值 给text  //type: 类型  value  fpdm:发票代码  fphm: 发票号码  sjje: 价税金额  kprqsj : 开票日期和时间   String : String 校验码
   	 	String text = type+value+fpdm+fphm+sjje+kprqsj+xym;
        int width = 300;
        int height = 300;
        //二维码的图片格式 
        String format = "gif";
        Hashtable hints = new Hashtable();
        //内容所使用编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text,BarcodeFormat.QR_CODE, width, height, hints); //(text 就是内容)
        
        //生成二维码
        File outputFile = new File("HYEICSPlatform/../file"+ File.separator+"HYFPEWM.gif");     //	("d:"+File.separator+"new.gif")("生成的路径:"+生成的图片+"图片名称")
        if(!outputFile.exists()){
        	outputFile.createNewFile();
        }
        MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile); 
   } 
   
   

 } 
 
 
 
 
 
 