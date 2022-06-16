package com.flowchart.util;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: FileUploadUtil
 * @Description: 文件上传工具类
 * @author: ct
 * @date: 2022/3/7 9:47
 */
@Component
public class FileUtils {

    /**
     * 基础路径
     */
    private static String uploadBaseAddr;

    /**
     * 当日上传路径
     */
    private static String uploadNowAddr;

    /**
     * 上传地址日期分期
     */
    private static String uploadDateAddr;

    /**
     * 初始化目录，并按照日期自动生成目录
     * @param uploadAddr
     */
    @Value("${upload.uploadAddr}")
    private void setUploadAddr(String uploadAddr){

        //获取基础路径
        FileUtils.uploadBaseAddr = uploadAddr;

        //获取日期地址，用来向数据库存储相对路径
        FileUtils.uploadDateAddr = "/" + LocalDate.now().toString().replace("-","/") + "/";

        //基本上传地址+日期文件夹+分类
        FileUtils.uploadNowAddr = uploadAddr + FileUtils.uploadDateAddr;

    }

    /**
     * 创建新的文件，当用户创建新的图表时调用此方法，创建文件并将文件数据存入文件中
     * @param socketCode 文件名（UUID）
     * @param fileData 文件数据
     */
    public static String createEmptyFile(String socketCode,String fileData,String type) {
        //判断目录是否存在
        String addr = folderIsExists(type);
        //数据写入文件
        try(BufferedWriter bufferWritter = new BufferedWriter(new FileWriter(addr + socketCode + ".json"))) {
            bufferWritter.write(fileData);
            return type + uploadDateAddr + socketCode + ".json";
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * 从文件中获取数据
     * @param filePath 文件名
     * @return 字符串数据
     */
    public static String getFileData(String filePath,String type) {
        //判断目录是否存在
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(uploadBaseAddr + filePath))){
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e){
            System.out.println(e.toString());
        }
        return sb.toString();
    }

    /**
     * 覆盖更新文件数据，通过文件名与文件数据来进行覆盖更新
     * @param filePath 文件名
     * @param fileData 文件数据
     */
    public static void updateFileData(String filePath, String fileData,String type) {
        //判断目录是否存在
        try (PrintWriter out = new PrintWriter(uploadBaseAddr + filePath)) {
            out.println(fileData);
        } catch (Exception e){
            System.err.println(e.toString());
        }
    }

    /**
     * 文件从本地上传到服务器
     * @param fileCode 文件名
     * @param file 上传文件数据
     */
    public static String saveFileData(String fileCode, MultipartFile file,String type){
        String addr = folderIsExists(type);
        try {
            file.transferTo(Paths.get(addr + fileCode + ".json"));
            //返回文件地址
            return type + uploadDateAddr + fileCode + ".json";
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    /**
     * 向文件中设置当前socket连接总数
     * @param fileData
     */
    public static void setWebSocketNumberInFile(Integer fileData){
        String addr = folderIsExists("");
        try (PrintWriter out = new PrintWriter(addr + "countNumber.json")) {
            out.println(fileData);
        } catch (Exception e){
            System.err.println(e.toString());
        }
    }

    /**
     * 从文件中获取当前socket连接总数
     * @return 返回socket连接总数
     */
    public static String getWebSocketNumberInFile(){
        String addr = folderIsExists("");
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = Files.newBufferedReader(Paths.get(addr + "countNumber.json"))){
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e){
            System.out.println(e.toString());
        }
        return sb.toString();
    }

    /**
     * 上传图片方法
     */
    public static Map<String,String> uploadImage(String imgName, MultipartFile file){
        String addr = folderIsExists("img");
        try {
            if(file != null && !file.isEmpty()){
                String filename = file.getOriginalFilename();
                if(filename != null){
                    String suffixName = filename.substring(filename.lastIndexOf("."));
                    file.transferTo(Paths.get(addr + imgName + suffixName));
                    //返回半链接
                    // /flowchart/img\2022/03/31\618BBC43-34EA-46BA-A0E8-39E93B039E13.png
                    Map<String, String> resultMap = new HashMap<>();
                    resultMap.put("imgPath","/flowchart/img" + uploadDateAddr + imgName + suffixName);
                    resultMap.put("imgName",imgName + suffixName);
                    return resultMap;
                }
            }
        } catch (Exception e){
            System.out.println(e.toString());
        }

        return null;
    }

    public static void deleteFile(String filePath) {
        File file = new File(uploadBaseAddr + filePath);
        boolean delete = file.delete();
        if(delete){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }
    }

    /**
     * 当日目录非空判断，创建文件或者更新文件数据的时候，如果路径不存在会抛出异常
     * 此方法用作当目录不存在时创建目录，以免报错
     */
    private static String folderIsExists(String type){
        String addr = uploadBaseAddr + type + uploadDateAddr;
        if(type.isEmpty()){
            addr = uploadBaseAddr;
        }
        File folder = new File(addr);
        if(!folder.exists() && !folder.isDirectory()){
            //创建目录
            boolean mkdirs = folder.mkdirs();
        }
        return addr;
    }

    public static String createFileImage(String fileCode,String imgDate,String type) {
        String addr = folderIsExists(type);
        imgDate = imgDate.replaceAll("data:image/png;base64,", "");

        try {
            //base64图片解码
            byte[] byteimgDate = Base64.getDecoder().decode(imgDate);

            for (int i = 0; i < byteimgDate.length; ++i) {
                // 调整异常数据
                if (byteimgDate[i] < 0) {
                    byteimgDate[i] += 256;
                }
            }
            String imgName = addr + fileCode + ".png";

            File file = new File(imgName);
            file.createNewFile();
            if(!file.exists()){
                file.createNewFile();
            }
            OutputStream out = new FileOutputStream(file);
            out.write(byteimgDate);
            out.flush();
            out.close();

            //返回文件地址
            return "/flowchart/" + type + uploadDateAddr + fileCode + ".png";
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

    public static String insertImage(String fileCode, MultipartFile file, String type) {
        String addr = folderIsExists(type);
        //获取文件后缀，生成时间戳
        String filename = file.getOriginalFilename();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSSS");
        String dateTime = LocalDateTime.now().format(formatter);

        String suffix = "-" + dateTime + filename.substring(filename.lastIndexOf("."));
        try {
            file.transferTo(Paths.get(addr + fileCode + suffix));
            //返回文件地址
            return "/flowchart/" + type + uploadDateAddr + fileCode + suffix;
        } catch (Exception e){
            System.out.println(e.toString());
        }
        return null;
    }

}
