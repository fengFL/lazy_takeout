package org.example.lazzy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

// slf4j is provided by lombok and is used to display some information for developers
// EnableAspectJAutoProxy is enabled by default
@Slf4j
@SpringBootApplication
@ServletComponentScan // enable filter
@EnableTransactionManagement
public class LazzyApplication {
    public static void main(String[] args) {
        SpringApplication.run(LazzyApplication.class, args);
        log.info("project started successfully...");
    }
}
