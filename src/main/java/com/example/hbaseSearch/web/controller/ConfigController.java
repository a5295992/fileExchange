package com.example.hbaseSearch.web.controller;

import com.example.hbaseSearch.service.PlatConfigService;
import com.example.hbaseSearch.vo.PlatConfigVo;
import com.example.hbaseSearch.vo.ResultBean;
import com.example.hbaseSearch.web.vo.req.ConfigSaveReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class ConfigController {
    @Resource
    private PlatConfigService platConfigService;

    @PostMapping("/saveConfig")
    public void saveConfig(@RequestBody  ConfigSaveReq configSaveReq) throws Exception {
        platConfigService.setConfig(configSaveReq.getData());
    }
    @PostMapping("/getConfig")
    public ResultBean<PlatConfigVo> getConfig() {
        return ResultBean.success(platConfigService.getConfigs());
    }
}
