package com.shop.shopservice.jwtsecurity;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shop.shopservice.service.IUserService;

/**
 * @author Avinash
 *
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	IUserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("javainuse".equals(username)) {
			return new User("javainuse", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
	
	
	public UserDetails authenticateUserwithPassword(String username,String pasword) throws UsernameNotFoundException {
		com.shop.shopservice.entity.User user = userService.loginUser(username, pasword);
		if (null != user) {
			return new User(user.getEmailId(), user.getPwd(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
