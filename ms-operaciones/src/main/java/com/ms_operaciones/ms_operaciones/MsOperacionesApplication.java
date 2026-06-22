package com.ms_operaciones.ms_operaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MsOperacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsOperacionesApplication.class, args);
	}

}
