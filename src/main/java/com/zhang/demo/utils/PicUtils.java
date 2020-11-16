package com.zhang.demo.utils;


import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @Author: zhang
 * @Date: 图片压缩工具类
 * @Description:
 * @Version: 1.0
 */
public class PicUtils {

    private static Logger logger = LoggerFactory.getLogger(PicUtils.class);


    /**
     * 指定体积压缩图片
     *
     * @param imageBytes  源图片字节数组
     * @param desFileSize 指定图片大小，单位kb
     * @return 压缩质量后的图片字节数组
     */
    public static byte[] comPicBySize(byte[] imageBytes, long desFileSize) {
        long start = System.currentTimeMillis();
        if (imageBytes == null || imageBytes.length <= 0 || imageBytes.length < desFileSize * 1024) {
            return imageBytes;
        }
        long srcSize = imageBytes.length;
        double accuracy = getAccuracy(srcSize / 1024);
        try {
            while (imageBytes.length > desFileSize * 1024) {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
                Thumbnails.of(inputStream)
                        .scale(accuracy)
                        .outputQuality(accuracy)
                        .toOutputStream(outputStream);
                imageBytes = outputStream.toByteArray();
            }
            long end = System.currentTimeMillis();
            logger.info("【图片压缩】 图片原大小={}kb | 压缩后大小={}kb | 耗时={}ms",
                    srcSize / 1024, imageBytes.length / 1024, end - start);
        } catch (Exception e) {
            logger.error("【图片压缩】msg=图片压缩失败!", e);
        }
        return imageBytes;
    }


    /**
     * 按照比例进行缩放
     *
     * @param imageBytes
     * @param scale      比例，为0时自动调节精度
     * @param size       大于多少k是开始压缩
     * @throws IOException
     */
    public static byte[] comPicByScale(byte[] imageBytes, double scale, int size) {
        long srcSize = imageBytes.length / 1024;
        if (srcSize < size) {
            return imageBytes;
        }
        long l = System.currentTimeMillis();
        if (scale == 0) {
            // 自动调节精度
            scale = getAccuracy(srcSize);
        }
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        try {
            Thumbnails.of(inputStream).scale(scale).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = outputStream.toByteArray();
        long l1 = System.currentTimeMillis();
        logger.info("【图片压缩】 图片原大小={}kb | 压缩后大小={}kb，耗时={}ms",
                srcSize, imageBytes.length / 1024, (l1 - l));
        return imageBytes;
    }


    /**
     * 指定高宽进行缩放
     *
     * @param imageBytes 原图
     * @param w          宽
     * @param h          高
     * @param ratio      等比缩放
     * @return
     */
    public static byte[] comPicByWidth(byte[] imageBytes, int w, int h, boolean ratio) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        try {
            Thumbnails.of(inputStream).size(w, h)
                    .keepAspectRatio(ratio)  // 默认true是按照比例缩放的
                    .toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = outputStream.toByteArray();
        return imageBytes;
    }


    /**
     * 旋转图片
     *
     * @param rotate 角度，正数：顺时针 负数：逆时针
     * @return
     */
    public static byte[] rotatePic(byte[] imageBytes, double rotate) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        try {
            Thumbnails.of(inputStream).rotate(rotate).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = outputStream.toByteArray();
        return imageBytes;
    }

    /**
     * 添加水印图片
     *
     * @param imageBytes 原图
     * @param waterBytes 水印图
     * @param position   区域
     * @return
     */
    public static byte[] watermarkPic(byte[] imageBytes, byte[] waterBytes, Positions position) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayInputStream waterStream = new ByteArrayInputStream(waterBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        try {
            // watermark(位置，水印图，透明度)
            Thumbnails.of(inputStream).watermark(position, ImageIO.read(waterStream), 0.5f)
                    .outputQuality(0.8f).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = outputStream.toByteArray();
        return imageBytes;
    }

    /**
     * 裁剪指定坐标
     *
     * @param imageBytes 原图
     * @param i          坐标
     * @param i1
     * @param i2
     * @param i3
     * @return
     */
    public static byte[] cropPic(byte[] imageBytes, int i, int i1, int i2, int i3) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        try {
            Thumbnails.of(inputStream).sourceRegion(i, i1, i2, i3)
                    .keepAspectRatio(false)
                    .toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = outputStream.toByteArray();
        return imageBytes;
    }

    /**
     * 裁剪某块区域坐标
     * (图片右下400*400的区域，Positions.BOTTOM_RIGHT,400,400)
     *
     * @param imageBytes
     * @param position
     * @param i
     * @param i1
     * @return
     */
    public static byte[] cropPic(byte[] imageBytes, Positions position, int i, int i1) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        try {
            Thumbnails.of(inputStream).sourceRegion(position, i, i1)
                    .keepAspectRatio(false)
                    .toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = outputStream.toByteArray();
        return imageBytes;
    }


    /**
     * 转化图像格式
     *
     * @param imageBytes 原图
     * @param format     png/jpg/gif…
     * @return
     */
    public static byte[] formatPic(byte[] imageBytes, String format) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(imageBytes.length);
        try {
            Thumbnails.of(inputStream).outputFormat(format).toOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageBytes = outputStream.toByteArray();
        return imageBytes;
    }

    /**
     * 输出到OutputStream(流对象)
     *
     * @throws IOException
     */
    public static OutputStream toOutStream(byte[] imageBytes, String filePath) throws IOException {
        OutputStream os = new FileOutputStream(filePath);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        Thumbnails.of(inputStream).size(1280, 1024).toOutputStream(os);
        return os;
    }

    /**
     * 输出到BufferedImage
     *
     * @throws IOException
     */
    private void toBufferedImage(byte[] imageBytes, String filePath) throws IOException {
        // asBufferedImage() 返回BufferedImage
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage thumbnail = Thumbnails.of(inputStream).size(1280, 1024).asBufferedImage();
        ImageIO.write(thumbnail, "jpg", new File(filePath));
    }


    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }

    public static void main(String[] args) {
        String path = "C:\\Users\\adminsitrator\\Desktop\\";
        String pathName = path + "xf1326082361871302656.jpg";
        try {
            byte[] bytes = FileConverUtils.readByteByPath(pathName);

            double scale = 0.9f;
            // 方法1
            byte[] bytes2 = comPicByScale(bytes, 0, 2048);
//            byte[] bytes2 = comPicByWidth(bytes, 800, 1920, true);
            FileConverUtils.saveByByte(path, "aaa.jpg", bytes2);

            // 方法2
//            Thumbnails.of(new File(pathName)).scale(scale).toFile(new File(path + "bbb.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}