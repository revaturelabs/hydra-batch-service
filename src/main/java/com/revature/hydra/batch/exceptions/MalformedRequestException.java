package com.revature.hydra.batch.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Client sent a malformed request.")
public class MalformedRequestException extends Exception{

}
