package com.ztzh.ui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ztzh.ui.dao")
public class UIMaterialManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(UIMaterialManagementApplication.class, args);
	}
}
