package exceptions;

import org.springframework.http.HttpStatus;

import enums.ErrorCode;

public class NotFoundException extends CustomException {
	public NotFoundException(String message) {
        super(message, ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}
