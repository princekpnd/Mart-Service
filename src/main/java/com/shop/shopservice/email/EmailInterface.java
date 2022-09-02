package com.shop.shopservice.email;

import java.io.File;

public interface EmailInterface {

	public void sendPlainEmail(String emailId, String content);
	public boolean sendAttachmentEmail(String emailId, String content, File file);
	public void sendMailJetEmail(String subject, String body, String toEmail, String toName );
	
}
