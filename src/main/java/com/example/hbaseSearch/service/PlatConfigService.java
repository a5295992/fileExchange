package com.example.hbaseSearch.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.hbaseSearch.repository.PlatConfigRepository;
import com.example.hbaseSearch.repository.entity.PlatConfig;
import com.example.hbaseSearch.vo.PlatConfigVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlatConfigService {
    public static final String D_TMP = "d://tmp/";
    public static final String FILE_LIST_PATH_KEY = "fileListPathKey";
    @Resource
    private PlatConfigRepository platConfigRepository;

    /**
     * 根据key获取配置信息
     *
     * @param configKey
     * @return
     */
    public PlatConfigVo getConfigByKey(String configKey) {
        return BeanUtil.copyProperties(
                Optional.ofNullable(this.platConfigRepository.getOne(
                        createKeysSearchCondition(new HashSet<>(Collections.singletonList(configKey)))
                )).orElse(new PlatConfig()), PlatConfigVo.class
        );
    }

    public List<PlatConfigVo> getConfigs() {
        return this.platConfigRepository.list(createAllCondition()).
                stream().map(
                p -> BeanUtil.copyProperties(p, PlatConfigVo.class)
        ).collect(Collectors.toList());
    }

    /**
     * 保存配置
     *
     * @param platConfigVoList
     */
    public void setConfig(List<PlatConfigVo> platConfigVoList) throws Exception {
        platConfigRepository.saveOrUpdateBatch(this.prepareDataForListSave(platConfigVoList));
    }

    private List<PlatConfig> prepareDataForListSave(List<PlatConfigVo> platConfigVoList) throws Exception {
        Set<String> keys = platConfigVoList.stream().map(PlatConfigVo::getKey).collect(Collectors.toSet());
        Map<String, Long> keyIdsMap = this.platConfigRepository.list(
                createKeysSearchCondition(keys)
        ).stream().collect(Collectors.toMap(PlatConfig::getKey, PlatConfig::getId));
        List<PlatConfig> saveData = new java.util.ArrayList<>(Collections.emptyList());
        for (PlatConfigVo platConfigVo : platConfigVoList) {
            platConfigVo.setId(keyIdsMap.get(platConfigVo.getKey()));
            if (FILE_LIST_PATH_KEY.equals(platConfigVo.getKey())) {
                if (!new File(platConfigVo.getValue()).exists()) {
                    throw new Exception("文件夹不存在,请检查");
                }
            }
            saveData.add(BeanUtil.copyProperties(platConfigVo, PlatConfig.class));
        }
        return saveData;
    }

    /**
     * 获取文件列表目录;
     *
     * @return 文件目录路径
     */
    public String getFileListPath() {
        String fileListPathKey = FILE_LIST_PATH_KEY;
        PlatConfigVo platConfigVo = this.getConfigByKey(fileListPathKey);
        if (null != platConfigVo && StringUtils.isNotEmpty(platConfigVo.getValue())) {
            return platConfigVo.getValue();
        }
        return D_TMP;
    }

    //获取视频缩略图地址
    public String getPreviewPic(String fileName) {
        return fileName.split("\\.")[0] + ".png";
    }

    public static QueryWrapper<PlatConfig> createKeysSearchCondition(Set<String> keys) {
        QueryWrapper<PlatConfig> lambdaQueryWrapper =
                new QueryWrapper<>();
        lambdaQueryWrapper.in("key", keys);
        lambdaQueryWrapper.eq("stat", 1);
        return lambdaQueryWrapper;
    }

    public static QueryWrapper<PlatConfig> createAllCondition() {
        QueryWrapper<PlatConfig> lambdaQueryWrapper =
                new QueryWrapper<>();
        lambdaQueryWrapper.eq("stat", 1);
        return lambdaQueryWrapper;
    }
}
