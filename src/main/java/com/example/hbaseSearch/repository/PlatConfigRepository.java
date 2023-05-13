package com.example.hbaseSearch.repository;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.hbaseSearch.repository.dao.PlatConfigMapper;
import com.example.hbaseSearch.repository.entity.PlatConfig;
import org.springframework.stereotype.Component;

@Component
public class PlatConfigRepository extends ServiceImpl<PlatConfigMapper, PlatConfig> {


}
