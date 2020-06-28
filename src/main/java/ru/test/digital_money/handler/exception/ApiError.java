package ru.test.digital_money.handler.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

public class ApiError {

	private List<String> messageList;
	private HttpStatus status;

	public ApiError(List<String> messageList, HttpStatus status) {
		this.messageList = messageList;
		this.status = status;
	}

	public ApiError(HttpStatus status) {
		this.status = status;
	}

	public List<String> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
}
