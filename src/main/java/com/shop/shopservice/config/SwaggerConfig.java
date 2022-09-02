package com.shop.shopservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	   public Docket productApi() {
	      return new Docket(DocumentationType.SWAGGER_2).select()
	         .apis(RequestHandlerSelectors.basePackage("com.shop.shopservice"))
	         .build()
	         .apiInfo(apiInfo());
	   }
	
	 private ApiInfo apiInfo() {
	        return new ApiInfoBuilder().title("Combined Shop Service Rest API")
	                                   .description("Build.")
	                                   .contact(new Contact(null, null, null))
	                                   .license("Milaan Search")
	                                   .licenseUrl("https://www.milaansearch.com")
	                                   .version("1.0.0")
	                                   .build();
	    }
}
