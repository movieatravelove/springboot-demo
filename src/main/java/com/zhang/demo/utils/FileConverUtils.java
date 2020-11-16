package com.zhang.demo.utils;

import cn.hutool.core.codec.Base64Decoder;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 文件转换工具类
 * <p>
 * 读取文件内容为二进制数组  -->  readByteByPath
 * 通过二进制保存文件  -->  saveByByte
 * InputStream流转为二进制数组byte[]   -->  input2byte
 * BufferedImage转换为base64    -->   buffImg2Base
 * base64转换为BufferedImage    -->   base2BuffImg
 * BufferedImage转为InputStream   -->  buffImg2InStream
 * 将file转换为inputStream    -->    file2InStream
 * byte数组转为Base64字符串   -->   byte2Base
 * Base64字符串转为byte数组   -->  base2Byte
 * 将文件转换成Byte数组   -->   file2Byte
 * MultipartFile转File   -->   multi2File
 * InputStream写入到File中    -->   inStream2File
 * url转为base64   -->   url2Base
 * <p>
 * 通过base64来判断该文件类型是否属于img格式     isImageByBase64
 */
public class FileConverUtils {

    /**
     * 读取文件内容为二进制数组
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] readByteByPath(String filePath) throws IOException {
        InputStream in = new FileInputStream(filePath);
        byte[] data = input2byte(in);
        in.close();
        return data;
    }

    /**
     * InputStream流转为二进制数组byte[]
     *
     * @param in
     * @return
     * @throws IOException
     */
    public static byte[] input2byte(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    /**
     * 通过二进制保存文件
     *
     * @param filePath
     * @param fileName
     * @param content
     */
    public static void saveByByte(String filePath, String fileName, byte[] content) {
        try {
            File filedir = new File(filePath);
            if (!filedir.exists()) {
                filedir.mkdirs();
            }
            File file = new File(filedir, fileName);
            OutputStream os = new FileOutputStream(file);
            os.write(content, 0, content.length);
            os.flush();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * BufferedImage 编码转换为 base64
     *
     * @param bufferedImage
     * @return
     */
    public static String buffImg2Base(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        System.out.println("值为：" + "data:image/jpg;base64," + png_base64);
        return "data:image/jpg;base64," + png_base64;
    }

    /**
     * base64 编码转换为 BufferedImage
     *
     * @param base64
     * @return
     */
    public static BufferedImage base2BuffImg(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将BufferedImage转换为InputStream
     *
     * @param image
     * @return
     */
    public static InputStream buffImg2InStream(BufferedImage image) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
            InputStream input = new ByteArrayInputStream(os.toByteArray());
            return input;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将file转换为inputStream
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream file2InStream(File file) throws FileNotFoundException {
        return new FileInputStream(file);
    }

    /**
     * byte数组 转换为 Base64字符串
     */
    public String byte2Base(byte[] data) {
        return new BASE64Encoder().encode(data);
    }

    /**
     * Base64字符串 转换为 byte数组
     */
    public byte[] base2Byte(String base64) {
        try {
            return new BASE64Decoder().decodeBuffer(base64);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 将文件转换成Byte数组
     *
     * @param file
     * @return
     */
    public static byte[] file2Byte(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            byte[] data = bos.toByteArray();
            bos.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MultipartFile转File
     *
     * @param param
     * @return
     */
    public static File multi2File(MultipartFile param) {
        if (!param.isEmpty()) {
            File file = null;
            try {
                InputStream in = param.getInputStream();
                file = new File(param.getOriginalFilename());
                OutputStream out = new FileOutputStream(file);
                int bytesRead = 0;
                byte[] buffer = new byte[8192];
                while ((bytesRead = in.read(buffer, 0, 8192)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                in.close();
                out.close();
                return file;
            } catch (Exception e) {
                e.printStackTrace();
                return file;
            }
        }
        return null;
    }

    /**
     * 将InputStream写入到File中
     *
     * @param ins
     * @param file
     * @throws IOException
     */
    public void inStream2File(InputStream ins, File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
    }


    /**
     * 根据文件url获取文件并转换为base64编码
     *
     * @param srcUrl        文件地址
     * @param requestMethod 请求方式（"GET","POST"）
     * @return 文件base64编码
     */
    public static String url2Base64(String srcUrl, String requestMethod) {
        ByteArrayOutputStream outPut = new ByteArrayOutputStream();
        byte[] data = new byte[1024 * 8];
        try {
            // 创建URL
            URL url = new URL(srcUrl);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setConnectTimeout(10 * 1000);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //连接失败/链接失效/文件不存在
                return null;
            }
            InputStream inStream = conn.getInputStream();
            int len = -1;
            while (-1 != (len = inStream.read(data))) {
                outPut.write(data, 0, len);
            }
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outPut.toByteArray());
    }

    /**
     * 通过base64来判断该文件类型是否属于img格式
     *
     * @param base64Str
     * @return
     */
    public static boolean isImageByBase64(String base64Str) {
        boolean flag = false;
        try {
            BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(new Base64Decoder().decode(base64Str)));
            if (null == bufImg) {
                return flag;
            }
            flag = true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return flag;
    }


}
