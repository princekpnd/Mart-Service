package com.shop.shopservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author Avinash
 *
 */
public class UserRole {

	@Id
	@Column(name = "USER_ROLE_ID", nullable = false)
	private String UserRoleId;

	@Id
	@Column(name = "USER_ROLE_NAME", nullable = false)
	private String UserRoleName;
}
