package com.tzdr.common.utils;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;

import jodd.mail.MailException;
import jodd.mail.SmtpServer;

public class SmtpServerUtils extends SmtpServer {

	public SmtpServerUtils(String host, int port, String username,
			String password) {
		super(host, port, username, password);
	}

	public SendMailSessionUtil createSession() {
		Session mailSession = Session.getInstance(sessionProperties, authenticator);
		Transport mailTransport;
		try {
			mailTransport = getTransport(mailSession);
		} catch (NoSuchProviderException nspex) {
			throw new MailException(nspex);
		}
		return new SendMailSessionUtil(mailSession, mailTransport);
	}
}
