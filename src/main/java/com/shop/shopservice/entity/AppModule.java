package com.shop.shopservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author Avinash
 *
 */
public class AppModule {

	@Id
	@Column(name = "APP_ID", nullable = false)
	private String AppModuleId;

	@Id
	@Column(name = "APP_NAME", nullable = false)
	private String AppName;
}
