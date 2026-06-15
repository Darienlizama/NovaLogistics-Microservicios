package com.ms_comercial.ms_comercial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@EnableDiscoveryClient
@SpringBootApplication
public class MsComercialApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsComercialApplication.class, args);
	}

}
