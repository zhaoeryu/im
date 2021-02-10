package cn.study.im.mvc.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.MD5;
import cn.study.common.utils.FileUtil;
import cn.study.im.model.LayuiResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc :
 * @Create : zhaoey ~ 2020/06/13
 */
@RequestMapping("/upload")
@RestController
public class UploadController {

    @Value("${file.local-url}")
    private String localUrl;
    @Value("${file.path}")
    private String path;
    @Value("${file.maxSize}")
    private long maxSize;

    /**
     * 上传图片
     */
    @PostMapping("/image")
    public LayuiResult uploadImg(@RequestParam("file") MultipartFile multipartFile){
        StringBuilder url = new StringBuilder();
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        Date date = new Date();
        File file = FileUtil.upload(multipartFile, path + type +  File.separator,date);
        if(ObjectUtil.isNull(file)){
            throw new RuntimeException("上传失败");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
        String nowStr = "-" + format.format(date);
        String fileName = MD5.create().digestHex(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()),"UTF-8") + nowStr + "." + suffix;
        url = url.append(localUrl + "/file/" + type + "/" + fileName);
        Map<String,Object> map = new HashMap<>();
        map.put("src",url);
        return LayuiResult.ok().data(map);
    }

    /**
     * 上传文件
     */
    @PostMapping("/file")
    public LayuiResult uploadFile(@RequestParam("file") MultipartFile multipartFile){
        StringBuilder url = new StringBuilder();
        FileUtil.checkSize(maxSize, multipartFile.getSize());
        String suffix = FileUtil.getExtensionName(multipartFile.getOriginalFilename());
        String type = FileUtil.getFileType(suffix);
        Date date = new Date();
        File file = FileUtil.upload(multipartFile, path + type +  File.separator,date);
        if(ObjectUtil.isNull(file)){
            throw new RuntimeException("上传失败");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssS");
        String nowStr = "-" + format.format(date);
        String fileName = MD5.create().digestHex(FileUtil.getFileNameNoEx(multipartFile.getOriginalFilename()),"UTF-8") + nowStr + "." + suffix;
        url = url.append(localUrl + "/file/" + type + "/" + fileName);

        Map<String,Object> map = new HashMap<>();
        map.put("src",url);
        map.put("name",fileName);
        return LayuiResult.ok().data(map);
    }

}
