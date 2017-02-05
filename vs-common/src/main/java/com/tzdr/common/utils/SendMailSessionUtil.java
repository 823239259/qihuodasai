package com.tzdr.common.utils;

import java.io.UnsupportedEncodingException;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.Message;


import javax.mail.internet.InternetAddress;

import jodd.mail.Email;
import jodd.mail.MailException;
import jodd.mail.SendMailSession;

public class SendMailSessionUtil extends SendMailSession {

	public SendMailSessionUtil(Session session, Transport transport) {
		super(session, transport);
	}

	/**
	 * Prepares message and sends it.
	 */
	public void sendMail(Email mail) {
		Message msg;
		try {
			msg = createMessage(mail, mailSession);
		} catch (MessagingException mex) {
			throw new MailException("Unable to prepare email message: " + mail, mex);
		}
		try {
			mailTransport.sendMessage(msg, msg.getAllRecipients());
		} catch (MessagingException mex) {
			throw new MailException("Unable to send email message: " + mail, mex);
		}
	}
	
	/**
	 * Prepares message and sends it.
	 */
	public void sendMail(Email mail,String nickname) {
		Message msg;
		try {
			msg = createMessage(mail, mailSession);
			msg.setFrom(new InternetAddress(mail.getFrom(),nickname));
		} catch (MessagingException mex) {
			throw new MailException("Unable to prepare email message: " + mail, mex);
		} catch(UnsupportedEncodingException mex){
			throw new MailException("Unable to prepare email message: " + mail, mex);
		}
		try {
			mailTransport.sendMessage(msg, msg.getAllRecipients());
		} catch (MessagingException mex) {
			throw new MailException("Unable to send email message: " + mail, mex);
		}
	}
}
