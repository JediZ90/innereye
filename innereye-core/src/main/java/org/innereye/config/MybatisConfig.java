package org.innereye.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"org.innereye.web.mbg.mapper"})
public class MybatisConfig {
}
