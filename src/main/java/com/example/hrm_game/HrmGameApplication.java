package com.example.hrm_game;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class HrmGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrmGameApplication.class, args);
	}

}
