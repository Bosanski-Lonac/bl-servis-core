package exceptions;

import org.springframework.http.HttpStatus;

import enums.ErrorCode;

public class UnauthorisedException extends CustomException {
	public UnauthorisedException(String message) {
		super(message, ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.UNAUTHORIZED);
	}
}
