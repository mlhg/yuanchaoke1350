package com.kk.docker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yuanchaoke
 * @version 1.0.0
 * @ClassName Swagger2Configuration.java
 * @Description TODO
 * @createTime 2021年04月16日 01:23:00
 */

@Configuration
@EnableSwagger2
public class Swagger2Configuration {



    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
//要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("com.kk.docker.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @Title: 构建API基本信息
     * @methodName: buildApiInfo
     * @param
     * @return springfox.documentation.service.ApiInfo
     * @Description
     * @author: yuanchaoke
     * @date: 20210416
     */
    private ApiInfo buildApiInfo() {

        return new ApiInfoBuilder()
                .title("公用系统<>API文档")
                .description("这里除了查看接口功能外，还提供了调试测试功能")
                .contact("袁朝科")
                .version("1.0")
                .build();

    }

}
