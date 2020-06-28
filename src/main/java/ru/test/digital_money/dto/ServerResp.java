package ru.test.digital_money.dto;

import ru.test.digital_money.handler.exception.ApiError;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

public class ServerResp {

	private Page page;
	private ApiError status;

	public ServerResp(Page page, ApiError status) {
		this.page = page;
		this.status = status;
	}

	public ServerResp() {
	}

	public ServerResp(ApiError status) {
		this.status = status;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public ApiError getStatus() {
		return status;
	}

	public void setStatus(ApiError status) {
		this.status = status;
	}
}
