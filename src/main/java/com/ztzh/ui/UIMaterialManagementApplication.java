package com.ztzh.ui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.ztzh.ui.dao")
@EnableTransactionManagement
@EnableCaching
public class UIMaterialManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(UIMaterialManagementApplication.class, args);
	}
}
