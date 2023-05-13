package com.example.hbaseSearch.web.controller;


import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import com.example.hbaseSearch.common.SearchFileEnum;
import com.example.hbaseSearch.service.PlatConfigService;
import com.example.hbaseSearch.task.FileScanTask;
import com.example.hbaseSearch.task.SysInit;
import com.example.hbaseSearch.tools.VideoCaptureUtils;
import com.example.hbaseSearch.vo.FileVo;
import com.example.hbaseSearch.vo.ResultBean;
import com.example.hbaseSearch.web.vo.req.SearchFileReq;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

@RestController
@Slf4j
public class FileController {
    @Resource
    private PlatConfigService platConfigService;
    @Resource
    private FileScanTask fileScanTask;

    @RequestMapping("/uploadFile")
    public void uploadFile(MultipartHttpServletRequest req) throws IOException, ServletException, FrameGrabber.Exception {
        //log.info("------转发 index.html");
        List<MultipartFile> multipartFiles = req.getMultiFileMap().get("file");
        String rootPath = platConfigService.getFileListPath();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = new Date().getTime()+"-"+multipartFile.getOriginalFilename();

            OutputStream outputStream = new FileOutputStream(rootPath + fileName);
            InputStream inputStream = multipartFile.getInputStream();
            IoUtil.copy(multipartFile.getInputStream(), outputStream);
            IoUtil.close(outputStream);
            IoUtil.close(inputStream);
            String previewPath = rootPath+"/video/";
            File previewFilePath = new File(previewPath);
            if(!previewFilePath.exists()){
                previewFilePath.mkdirs();
            }
            assert fileName != null;
            fileScanTask.createPreview(new File(rootPath + fileName),previewPath);
        }
    }

    //获取文件列表；
    @RequestMapping("/file/getFileList")
    public ResultBean<List<FileVo>> getFileList(@RequestBody SearchFileReq searchFileReq) throws Exception {
        List<FileVo> fileVos = new ArrayList<>();
        String filePath = platConfigService.getFileListPath();
        if (!new File(filePath).exists()) {
            return ResultBean.success(fileVos);
        }
        //文件夹删选;
        for (File file : Objects.requireNonNull(new File(filePath).listFiles())) {
            if(file.isDirectory()){
                continue;
            }
            if(null != searchFileReq.getType()){
                if (searchFileReq.getType().equalsIgnoreCase(SearchFileEnum.PIC.name()) && !SearchFileEnum.isImg(file.getName())
                ) {
                    continue;
                } else if (searchFileReq.getType().equalsIgnoreCase(SearchFileEnum.VIDEO.name()) && !SearchFileEnum.isVideo(file.getName())) {
                    continue;
                } else if (searchFileReq.getType().equalsIgnoreCase(SearchFileEnum.FILE.name()) && !SearchFileEnum.isNotVideoAndImg(file.getName())) {
                    continue;
                }
            }
            String type = FileVo.realType(file.getName());
            String downloadPath = FileVo.getPreviewPath(file.getName());
            fileVos.add(FileVo.builder()
                    .fileName(file.getName())
                    .downloadPath(downloadPath)
                    .path(file.getAbsolutePath())
                    .time(file.lastModified())
                    .type(type)
                    .fileSize(file.length()/1000*1.0)
                    .build());
        }
        return ResultBean.success(fileVos.stream().sorted(Comparator.comparing(FileVo::getTime)));
    }

    //获取文件列表；
    @RequestMapping("/file/downLoad")
    public void downLoad(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            String fileName = httpServletRequest.getParameter("fileName");
            String type = httpServletRequest.getParameter("type");
            outputStream = httpServletResponse.getOutputStream();
            String path = platConfigService.getFileListPath() + fileName;
            if("video".equals(type)){
                path = platConfigService.getFileListPath()+"/video/"+this.platConfigService.getPreviewPic(fileName);
            }
            inputStream = new FileInputStream(path);
            //设置headers
            httpServletResponse.setHeader("Content-Type", "application/octet-stream");
            //设置下载文件的名称，解决中文文件名乱码问题
            httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            IoUtil.copyByNIO(inputStream, outputStream,1024,null);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }finally {
            IoUtil.close(inputStream);
            IoUtil.close(outputStream);
        }

    }
}
