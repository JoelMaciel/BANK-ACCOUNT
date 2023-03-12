package com.bank.contas.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

	INVALID_DATA("/invalid-data", "Invalid Data"),
	SYSTEM_ERROR("/system-error", "System Error"),
	INVALID_PARAMETER("/invalid-parameter", "Invalid Parameter"),
	MESSAGE_INCOMPREENSIBLE("/message-incomprehensible", "Incomprehensible Message"),
	RESOURCE_NOT_FUND("/resource-not-found", "Resource Not Found"),
	ENTITY_IN_USE("/entity-in-use", "Entity In Use"),
	BUSINESS_ERROR("/error-business", "Business Rule Violation");
	
	private String title;
	private String uri;
	
	private ProblemType(String path, String title) {
		this.uri = "https://back_account.com.br" + path;
		this.title = title;
	}

}
