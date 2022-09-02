package com.shop.shopservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Avinash
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class SpringEurekaClientshopServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEurekaClientshopServiceApplication.class, args);
	}

}