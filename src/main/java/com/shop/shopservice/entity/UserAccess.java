package com.shop.shopservice.entity;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @author Avinash
 *
 */
public class UserAccess {

	@Id
	@Column(name = "USER_ACCESS_ID", nullable = false)
	private String UserRoleId;

	@Id
	@Column(name = "USER_ID", nullable = false)
	private String userId;

	@Id
	@Column(name = "USER_PWD", nullable = false)
	private String userPwd;
}
