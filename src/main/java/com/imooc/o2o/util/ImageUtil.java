package com.imooc.o2o.util;

import com.imooc.o2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

//用来封装thumbnailator的方法
public class ImageUtil {
    private static  String basePath=Thread.currentThread().getContextClassLoader().getResource("").getPath();//将水印设置成公有变量，获取水印classpath的绝对值路径
    private static final SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");//生成当前时间
    private static final Random r=new Random();//生成随机数
    private static Logger logger= LoggerFactory.getLogger(ImageUtil.class);//添加日志信息


    /**
     * 将generateThumbnail()方法中的CommonsMultipartFile转换成File
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile=new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);//需要抛出异常
        } catch (IOException e) {
            logger.error(e.toString());//出现异常就通过日志写出错误
            e.printStackTrace();
        }
        return  newFile;
    }
    /**
     * ImageUtil 的基础方法
     * 1.编写generateThumbnail方法，里面封装了Thumbnails方法来处理缩略图，并返回新生成图片的相对值路径
     * 先设置新生成的文件的保存路径，再创建缩略图
     * @param thumbnailInputStream
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(InputStream thumbnailInputStream,String fileName,String targetAddr){ //String targetAddr：设置接收文件后的保存地址
        String realFileName=getRandomFileName();//获取生成的随机文件名（需要实现getRandomFileName()方法）
        String extension=getFileExtension(fileName);//获取输入文件流的扩展名（需要实现getFileExtension(thumbnail)方法）
        makeDirPath(targetAddr);//创建目标路径上所涉及到的目录
        String relativeAddr=targetAddr+realFileName+extension; //获取到新的相对路径
        logger.debug("current relativeAddr is"+relativeAddr);//日志显示相对路径
        File dest=new File(PathUtil.getImgBasePath()+relativeAddr);  //完整的新生成文件的路径
        logger.debug("current complete addr is:"+PathUtil.getImgBasePath()+relativeAddr);//日志显示全路径
        try {   //创建缩略图
            Thumbnails.of(thumbnailInputStream).size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.5f)
                    .outputQuality(0.8f).toFile(dest);//toFile()来指定新生成的文件保存在哪个文件中
        }catch (IOException e){
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;//返回最终的图片相对路径（shop表中的shop_img就是储存的这个路径信息）相对路径保证了程序迁移后也可以正确读出图片地址而不用去改变shop_img的值
    }

    /**
     * 创建新生成目标路径上所涉及到的目录，即/c:/project/image/XXX.jpg
     * 那么/c:/project/image文件夹都会自动创建
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParent=PathUtil.getImgBasePath()+targetAddr;//获取targetAddr的绝对路径（全路径）
        File dirPath=new File(realFileParent);
        if(!dirPath.exists()){ //判断路径是否存在，如果不存在就递归地创建出来
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入文件流的扩展名(.jpg/.png/..)
     * @param fileName
     * @return
     */
    private static String getFileExtension(String  fileName) {
//        String originalFileName=cFile.getName();//获取输入文件流的文件名，后面会改成随机名输出(之前是CommonsMultipartFile,改成了File)
//        return originalFileName.substring(originalFileName.lastIndexOf("."));
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒+5位随机数
     * @return
     */
    public static String getRandomFileName() {

        int rannum=r.nextInt(89999)+10000; //获取随机的五位数
        String nowTimeStr=sDateFormat.format(new Date());//获取当前的时间
        return nowTimeStr+rannum;
    }

    public static void main(String[] args) throws IOException { //通过main()函数使用thumbnailator
        Thumbnails.of(new File("C:\\Users\\尧凯辉\\Pictures\\Saved Pictures\\保存的图片\\bz1.jpg"))
                .size(200,200).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath+"/watermark.jpg")),0.5f).outputQuality(0.8f)
                //ImageIO.read(new File(basePath+"/watermark.jpg":指定水印图片的路径
                .toFile("C:\\Users\\尧凯辉\\Pictures\\Saved Pictures\\保存的图片\\bz1new.jpg");
    }

    //删除旧的图片的工具类

    /**
     * storePath是文件路径还是目录路径
     * 如果storePath是文件路径则删除改文件
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath){
        File fileOrPath=new File(PathUtil.getImgBasePath()+storePath);//获取全路径
        if(fileOrPath.exists()){
            if (fileOrPath.isDirectory()){  //当文件或目录存在时，判断当fileOrPath是目录时
                //删除目录下的所有文件
                File files[]=fileOrPath.listFiles();
                for (int i=0;i<files.length;i++){
                    files[i].delete();
                }
            }
            fileOrPath.delete();//当是文件时删除文件及目录
        }
    }

    /**
     * 处理详情图，并返回新生成图片的相对值路径
     *
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.9f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩图片失败：" + e.toString());
        }
        // 返回图片相对路径地址
        return relativeAddr;
    }

    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     *
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        // 获取不重复的随机名
        String realFileName = getRandomFileName();
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is :" + relativeAddr);
        // 获取文件要保存到的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("basePath is :" + basePath);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
                    .outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            throw new RuntimeException("创建缩略图失败：" + e.toString());
        }
        // 返回图片相对路径地址
        return relativeAddr;
    }
}
