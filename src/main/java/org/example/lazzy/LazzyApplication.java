package org.example.lazzy;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

// slf4j is provided by lombok and is used to display some information for developers
// EnableAspectJAutoProxy is enabled by default
@Slf4j
@SpringBootApplication
@ServletComponentScan // enable filter
@EnableTransactionManagement
@EnableCaching // enable data cache

public class LazzyApplication {
    public static void main(String[] args) {
        SpringApplication.run(LazzyApplication.class, args);
        log.info("project started successfully...");
    }
}
