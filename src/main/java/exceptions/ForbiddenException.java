package exceptions;

import org.springframework.http.HttpStatus;

import enums.ErrorCode;

public class ForbiddenException extends CustomException {
	public ForbiddenException(String message) {
		super(message, ErrorCode.RESOURCE_INACCESSIBLE, HttpStatus.FORBIDDEN);
	}
}
