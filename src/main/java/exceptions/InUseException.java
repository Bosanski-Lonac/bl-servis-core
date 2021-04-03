package exceptions;

import org.springframework.http.HttpStatus;

import enums.ErrorCode;

public class InUseException extends CustomException {
	public InUseException(String message) {
        super(message, ErrorCode.RESOURCE_IN_USE, HttpStatus.LOCKED);
    }
}
