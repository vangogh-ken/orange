package com.van.halley.core.mail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMultipart;

public class MailDTO {
	/**
	 * 
	 */
	private String from;
	/** List of "to" email addresses. */
    private List<String> toList = new ArrayList<String>();

    /** List of "cc" email addresses. */
    private List<String> ccList = new ArrayList<String>();

    /** List of "bcc" email addresses. */
    private List<String> bccList = new ArrayList<String>();
    
    private String subject;
    
    /** The content. */
    private Object content;
    
    /** An attachment. */
    private MimeMultipart emailBody;

    /** The content type. */
    private String contentType;
    
    private Date sendTime;
    
    private Date receiveTime;
    
}
