package javacode.Wireless;

import javax.json.JsonObject;

public class Request {
	Requests requests;
	JsonObject data;

	Request(Requests requests, JsonObject data) {
		this.requests = requests;
		this.data = data;
	}

	public enum Requests {
		DATA,
		CONFIG,
		REQUEST_PING,
		REQUEST_CLOSE
	}

}