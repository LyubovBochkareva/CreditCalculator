package ru.test.digital_money.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.test.digital_money.dto.ServerResp;
import ru.test.digital_money.service.CalculateService;

import static ru.test.digital_money.util.validation.PathValue.CALCULATION_HTML;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

@RestController
public class CalculateRestController {

	private CalculateService calculateService;

	public CalculateRestController(CalculateService calculateService) {
		this.calculateService = calculateService;
	}

	/**
	 * Returns an object built using three parameters.
	 *
	 * @param loanAmount   the loan amount
	 * @param loanPeriod   the loan period
	 * @param interestRate the interest rate on the loan
	 * @return ServerResp object with a given loanAmount, loanPeriod, interestRate
	 */
	@GetMapping(CALCULATION_HTML)
	public ServerResp calculation(
			@RequestParam int loanAmount,
			@RequestParam int loanPeriod,
			@RequestParam double interestRate) {
		return calculateService.calculate(loanAmount, loanPeriod, interestRate);
	}
}
