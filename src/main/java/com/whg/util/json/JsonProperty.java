package com.whg.util.json;

public abstract class JsonProperty {

	protected String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "JsonProperty [key=" + key + "]";
	}
	
}
