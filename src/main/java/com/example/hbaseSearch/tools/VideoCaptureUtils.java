package com.example.hbaseSearch.tools;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
public class VideoCaptureUtils {
    /*默认图片格式 jpg*/
    public static String DEFAULT_IMG_FORMAT = "jpg";
    /*图片格式 png*/
    public static String IMG_FORMAT_PNG = "png";
    /*图片格式 jpg*/
    public static String IMG_FORMAT_JPG = "jpg";
    /*第一帧*/
    public final static int FIRST = 0;
    /*最后一帧*/
    public final static int LAST = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException, FrameGrabber.Exception {
        VideoCaptureUtils.fetchFrameToFile("d://tmp/IMG_4198.MOV","d://tmp/test.png",5);
    }
    /**
     * 获取指定视频的帧并保存为图片JPG格式至指定文件
     *
     * @param videoFile  源视频文件路径
     * @param targetFile 截取帧的图片存放路径
     * @param frameNum   截取第几帧
     * @throws Exception
     */
    public static void fetchFrameToFile(String videoFile, String targetFile, int frameNum) throws FrameGrabber.Exception, IOException {
        //校验输入和输出
        checkInAnOut(videoFile, targetFile);
        try {
            File frameFile = new File(targetFile);
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
            ff.start();
            int length = ff.getLengthInFrames();
            /*第几帧判断设置*/
            if (frameNum < 0) {
                frameNum = 0;
            }
            if (frameNum > length) {
                frameNum = length - 5;
            }
            //指定第几帧
            ff.setFrameNumber(frameNum);
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if ((i >= 5) && (f.image != null)) {
                    break;
                }
                i++;
            }
            opencv_core.IplImage img = f.image;
            int width = img.width();
            int height = img.height();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            ff.flush();
            ff.stop();
            ImageIO.write(bi, DEFAULT_IMG_FORMAT, frameFile);
        } catch (Exception e) {
            throw new RuntimeException("转换视频图片异常");
        }

    }

    /**
     * 获取指定视频的帧并保存为图片自定义类型至指定文件
     *
     * @param videoFile  源视频文件路径
     * @param targetFile 截取帧的图片存放文件路径
     * @param outImgFormat 输出图片格式
     * @param frameNum   截取第几帧
     * @throws Exception
     */
    public static void fetchFrameToFile(String videoFile, String targetFile, String outImgFormat, int frameNum) throws FrameGrabber.Exception, IOException {
        //校验输入和输出
        checkInAnOut(videoFile, targetFile);
        try {
            File frameFile = new File(targetFile);
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
            ff.start();
            int length = ff.getLengthInFrames();
            /*第几帧判断设置*/
            if (frameNum < 0) {
                frameNum = 0;
            }
            if (frameNum > length) {
                frameNum = length - 5;
            }
            //指定第几帧
            ff.setFrameNumber(frameNum);
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if ((i >= 5) && (f.image != null)) {
                    break;
                }
                i++;
            }
            opencv_core.IplImage img = f.image;
            int width = img.width();
            int height = img.height();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            ff.flush();
            ff.stop();
            ImageIO.write(bi, outImgFormat, frameFile);
        } catch (Exception e) {
            throw new RuntimeException("转换视频图片异常");
        }
    }

    /**
     * 获取指定视频的帧图片，并转换为base64字符串
     *
     * @param videoFile 源视频文件路径
     * @param frameNum  截取第几帧
     * @throws Exception
     */
    public static String fetchFrameToBase64(String videoFile, int frameNum) throws FrameGrabber.Exception, IOException {
        //校验输入
        checkVideoFile(videoFile);
        try (ByteArrayOutputStream output = new ByteArrayOutputStream();) {
            FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videoFile);
            ff.start();
            int length = ff.getLengthInFrames();
            /*第几帧判断设置*/
            if (frameNum < 0) {
                frameNum = 0;
            }
            if (frameNum > length) {
                frameNum = length - 5;
            }
            //指定第几帧
            ff.setFrameNumber(frameNum);
            int i = 0;
            Frame f = null;
            while (i < length) {
                // 过滤前5帧，避免出现全黑的图片，依自己情况而定
                f = ff.grabFrame();
                if ((i >= 5) && (f.image != null)) {
                    break;
                }
                i++;
            }
            opencv_core.IplImage img = f.image;
            int width = img.width();
            int height = img.height();
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            bi.getGraphics().drawImage(f.image.getBufferedImage().getScaledInstance(width, height, Image.SCALE_SMOOTH),
                    0, 0, null);
            ImageIO.write(bi, DEFAULT_IMG_FORMAT, output);
            // 这里需要获取图片的base64数据串，所以将图片写到流里面
            ff.flush();
            ff.stop();
            return new BASE64Encoder().encode(output.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("转换视频图片异常");
        }
    }


    /**
     * 校验输入输出
     *
     * @param videoFile
     * @param targetFile
     */
    public static void checkInAnOut(String videoFile, String targetFile) {
        checkVideoFile(videoFile);
        checkTargetFileDir(targetFile);
    }

    /**
     * 验证文件目录是否存在，不存在就创建
     *
     * @param targetFile 文件路径
     * @return
     */
    public static void checkTargetFileDir(String targetFile) {
        String dirPath = targetFile.substring(0, targetFile.lastIndexOf(File.separator) + 1);
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 检验文件是否存在
     *
     * @param videoFile
     */
    public static void checkVideoFile(String videoFile) {
        File file = new File(videoFile);
        if (!file.exists()) {
            throw new RuntimeException("文件不存在");
        }
    }
}
