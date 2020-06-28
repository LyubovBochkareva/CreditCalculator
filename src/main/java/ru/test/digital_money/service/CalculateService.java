package ru.test.digital_money.service;

import ru.test.digital_money.dto.ServerResp;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

public interface CalculateService {

	/**
	 * Returns an object built using three parameters.
	 *
	 * @param loanAmount   the loan amount
	 * @param loanPeriod   the loan period
	 * @param interestRate the interest rate on the loan
	 * @return ServerResp object with a given loanAmount, loanPeriod, interestRate
	 */
	ServerResp calculate(int loanAmount, int loanPeriod, double interestRate);
}
