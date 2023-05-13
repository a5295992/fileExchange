package com.example.hbaseSearch.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_plat_config")
public class PlatConfig {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String key;
    private String value;
    private Integer stat;
}
