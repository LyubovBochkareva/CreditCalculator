package ru.test.digital_money.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

import static ru.test.digital_money.util.validation.ValidMessage.INCORRECT_INTEREST_RATE;
import static ru.test.digital_money.util.validation.ValidMessage.INCORRECT_LOAN_AMOUNT;
import static ru.test.digital_money.util.validation.ValidMessage.INCORRECT_LOAN_PERIOD;
import static ru.test.digital_money.util.validation.Variable.INTEREST_RATE;
import static ru.test.digital_money.util.validation.Variable.LOAN_AMOUNT;
import static ru.test.digital_money.util.validation.Variable.LOAN_PERIOD;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

@ControllerAdvice
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
		ArrayList<String> messageList = new ArrayList<>();
		if (ex.getName().equals(LOAN_AMOUNT)) {
			messageList.add(INCORRECT_LOAN_AMOUNT);
		}
		if (ex.getName().equals(LOAN_PERIOD)) {
			messageList.add(INCORRECT_LOAN_PERIOD);
		}
		if (ex.getName().equals(INTEREST_RATE)) {
			messageList.add(INCORRECT_INTEREST_RATE);
		}
		ApiError apiError = new ApiError(messageList, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Object>(apiError, apiError.getStatus());
	}
}
