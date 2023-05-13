package com.example.hbaseSearch.web.vo.req;

import com.example.hbaseSearch.vo.PlatConfigVo;
import lombok.Data;

import java.util.List;

@Data
public class ConfigSaveReq {
    private List<PlatConfigVo> data;
}
