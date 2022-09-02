package com.shop.shopservice.ijcr;

import javax.jcr.LoginException;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;

/**
 * @author Avinash
 *
 */
public interface IJcrAuthentication {

	public boolean loginRepository(String userId, String pwd) throws LoginException, RepositoryException;

	public boolean createRepository(SimpleCredentials simpleCredential, String repositoryName);

}
