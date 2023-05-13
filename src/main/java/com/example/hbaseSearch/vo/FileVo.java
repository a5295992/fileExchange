package com.example.hbaseSearch.vo;

import com.example.hbaseSearch.common.SearchFileEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileVo {
    private String fileName;
    private String path;
    private String type;
    private Long time;
    private String downloadPath;
    private Double fileSize;

    public static String realType(String name) {
        if(SearchFileEnum.isImg(name)){
            return "img";
        }else if(SearchFileEnum.isVideo(name)){
            return "video";
        }
        return "file";
    }

    public static String getPreviewPath( String name) {
        if(SearchFileEnum.isImg(name)){
            return "/api/file/downLoad?fileName="+name;
        }else if(SearchFileEnum.isVideo(name)){
            return "/api/file/downLoad?fileName="+name+"&type=video";
        }
        return "file.jpg";
    }
}
