package com.ufs.madnilo.trabalho01;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	
	private String sender;
	private String date;
	private String time;
	private String content;
	
	
	public Message(String sender, String content) {
		super();
		SimpleDateFormat onlyDate = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat onlyHours = new SimpleDateFormat("HH:mm:ss");
		Date data = new Date();
		
		this.sender = sender;
		this.date = onlyDate.format(data);
		this.time = onlyHours.format(data);
		this.content = content;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	
}
