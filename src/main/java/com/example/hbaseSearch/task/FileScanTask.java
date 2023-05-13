package com.example.hbaseSearch.task;

import com.example.hbaseSearch.common.SearchFileEnum;
import com.example.hbaseSearch.service.PlatConfigService;
import com.example.hbaseSearch.tools.VideoCaptureUtils;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FrameGrabber;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class FileScanTask {
    @Resource
    private PlatConfigService platConfigService;

    public void scan() {
        try {
            String filePath = platConfigService.getFileListPath();
            String previewPath = filePath + "/video/";
            File previewFilePath = new File(previewPath);
            if (!previewFilePath.exists()) {
                previewFilePath.mkdirs();
            }
            for (File file : Objects.requireNonNull(new File(filePath).listFiles())) {
                this.createPreview(file, previewPath);
            }

        } catch (Exception e) {
            log.error("定时任务报错 ---  ",e);
        }

    }

    /**
     * 创建 视频缩略图
     *
     * @param file
     * @param previewPath
     * @throws IOException
     * @throws FrameGrabber.Exception
     */
    public void createPreview(File file, String previewPath) throws IOException, FrameGrabber.Exception {
        if (file.isDirectory()) {
            return;
        }
        String previewFile = previewPath + platConfigService.getPreviewPic(file.getName());
        if (SearchFileEnum.isVideo(file.getName()) && !new File(previewFile).exists()) {
            //缩略图不存在，创建；
            VideoCaptureUtils.fetchFrameToFile(file.getAbsolutePath(), previewFile, 2);
            if (new File(previewFile).exists()) {
                log.info("视频缩略图创建成功 {}", previewFile);
            }
        }
    }
}
