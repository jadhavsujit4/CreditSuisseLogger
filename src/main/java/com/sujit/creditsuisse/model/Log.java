package com.sujit.creditsuisse.model;

import org.springframework.beans.factory.annotation.Required;

public class Log {

	private String id;

	private String state;

	private long timestamp;

	private String type;

	private String host;

	public String getId() {
		return id;
	}

	@Required
	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	@Required
	public void setState(String state) {
		this.state = state;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Required
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
