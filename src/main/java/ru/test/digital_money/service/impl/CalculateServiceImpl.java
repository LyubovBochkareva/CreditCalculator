package ru.test.digital_money.service.impl;

import org.apache.commons.math3.util.Precision;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.test.digital_money.dto.Page;
import ru.test.digital_money.dto.Payment;
import ru.test.digital_money.dto.ServerResp;
import ru.test.digital_money.handler.exception.ApiError;
import ru.test.digital_money.service.CalculateService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ru.test.digital_money.util.validation.Number.*;
import static ru.test.digital_money.util.validation.ValidMessage.*;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

@Service
public class CalculateServiceImpl implements CalculateService {

	/**
	 * Returns an object built using three parameters.
	 *
	 * @param loanAmount   the loan amount
	 * @param loanPeriod   the loan period
	 * @param interestRate the interest rate on the loan
	 * @return ServerResp object with a given loanAmount, loanPeriod, interestRate
	 */
	public ServerResp calculate(int loanAmount, int loanPeriod, double interestRate) {
		ApiError status = checkValidParam(loanAmount, loanPeriod, interestRate);
		if (status.getStatus() == HttpStatus.OK) {
			return calculation(loanAmount, loanPeriod, interestRate, status);
		} else {
			return new ServerResp(null, status);
		}
	}

	/**
	 * Сalculation of the payment schedule when status OK.
	 *
	 * @param loanAmount   the loan amount
	 * @param loanPeriod   the loan period
	 * @param interestRate the interest rate on the loan
	 * @param status       ApiError status
	 * @return ServerResp object with a a payment schedule
	 */
	private ServerResp calculation(int loanAmount, int loanPeriod, double interestRate, ApiError status) {
		Page<Payment> calculatePage = new Page<>(loanAmount, loanPeriod, interestRate);
		List<Payment> paymentList = new ArrayList<>();
		double monthRate = interestRate / TWELVE / HUNDRED;
		double flLoanAmount = loanAmount;
		double monthAnnuityPayment = calcMonthAnnuityPayment(monthRate, loanPeriod, flLoanAmount);
		LocalDate datePayment = LocalDate.now();
		double sumPercent = 0;
		for (int i = 1; i <= loanPeriod; i++) {
			Payment payment = new Payment();
			payment.setPaymentNumber(i);
			payment.setPaymentDate(datePayment.plusMonths(i));
			double amountInterest = calcAmountInterest(flLoanAmount, monthRate);
			monthAnnuityPayment = checkLastPayment(i, loanPeriod, flLoanAmount, loanAmount, monthAnnuityPayment);
			monthAnnuityPayment = checkLastPayment(monthAnnuityPayment, flLoanAmount, amountInterest);
			double amountPrincipal = calcAmountPrincipal(monthAnnuityPayment, amountInterest);
			double principalBalance = calcPrincipalBalance(flLoanAmount, amountPrincipal);
			payment.setAmountInterest(amountInterest);
			payment.setAmountPrincipal(amountPrincipal);
			payment.setPaymentAmount(monthAnnuityPayment);
			payment.setPrincipalBalance(principalBalance);
			paymentList.add(payment);
			flLoanAmount -= amountPrincipal;
			sumPercent += amountInterest;
		}
		calculatePage.setContent(paymentList);
		calculatePage.setSumPercent(Precision.round(sumPercent, TWO));
		calculatePage.setTotalAmountPayments(calcTotalAmountPayments(sumPercent, loanAmount));
		return new ServerResp(calculatePage, status);
	}

	/**
	 * Monthly payment calculation.
	 *
	 * @param monthRate    the month rate
	 * @param loanPeriod   the loan period
	 * @param flLoanAmount the principal balance
	 * @return monthAnnuityPayment
	 */
	private double calcMonthAnnuityPayment(double monthRate, int loanPeriod, double flLoanAmount) {
		return Precision.round(((monthRate * Math.pow(1 + monthRate, loanPeriod) / (Math.pow(1 + monthRate, loanPeriod) - 1)) * flLoanAmount), 2);
	}

	/**
	 * Сalculating the interest amount in the monthly payment
	 *
	 * @param flLoanAmount the principal balance
	 * @param monthRate    the month rate
	 * @return amountInterest
	 */
	private double calcAmountInterest(double flLoanAmount, double monthRate) {
		return Precision.round(flLoanAmount * monthRate, TWO);
	}

	/**
	 * Сalculation of the principal amount in the monthly payment
	 *
	 * @param monthAnnuityPayment the monthly payment
	 * @param amountInterest      the amount of interest
	 * @return amountPrincipal
	 */
	private double calcAmountPrincipal(double monthAnnuityPayment, double amountInterest) {
		return Precision.round(monthAnnuityPayment - amountInterest, TWO);
	}

	/**
	 * Сalculation the remaining amount of the principal debt.
	 *
	 * @param flLoanAmount    the principal balance
	 * @param amountPrincipal the principal amount in the monthly payment
	 * @return principalBalance
	 */
	private double calcPrincipalBalance(double flLoanAmount, double amountPrincipal) {
		return Precision.round(flLoanAmount - amountPrincipal, TWO);
	}

	/**
	 * Сalculating the total payout amount.
	 *
	 * @param sumPercent total amount of interest
	 * @param loanAmount the loan amount
	 * @return totalAmountPayments
	 */
	private double calcTotalAmountPayments(double sumPercent, int loanAmount) {
		return Precision.round(sumPercent + loanAmount, TWO);
	}

	/**
	 * Аdjusting the last payment.
	 *
	 * @param paymentNumber       the payment number
	 * @param loanPeriod          the loan period
	 * @param flLoanAmount        the principal balance
	 * @param loanAmount          the loan amount
	 * @param monthAnnuityPayment the monthly payment
	 * @return monthAnnuityPayment
	 */
	private double checkLastPayment(int paymentNumber, int loanPeriod, double flLoanAmount, int loanAmount, double monthAnnuityPayment) {
		if (paymentNumber == loanPeriod && flLoanAmount == loanAmount) {
			return monthAnnuityPayment + flLoanAmount;
		} else {
			return monthAnnuityPayment;
		}
	}

	/**
	 * Аdjusting the last payment.
	 *
	 * @param monthAnnuityPayment the monthly payment
	 * @param flLoanAmount        the principal balance
	 * @param amountInterest      amount of interest
	 * @return monthAnnuityPayment
	 */
	private double checkLastPayment(double monthAnnuityPayment, double flLoanAmount, double amountInterest) {
		if (monthAnnuityPayment > flLoanAmount) {
			return Precision.round(amountInterest + flLoanAmount, TWO);
		} else {
			return monthAnnuityPayment;
		}
	}

	/**
	 * Сhecking the entered parameters for correctness.
	 *
	 * @param loanAmount   the loan amount
	 * @param loanPeriod   the loan period
	 * @param interestRate the interest rate on the loan
	 * @return ApiError object with a messageList and status
	 */
	private ApiError checkValidParam(int loanAmount, int loanPeriod, double interestRate) {
		ArrayList<String> messageList = new ArrayList<>();
		ApiError apiError = new ApiError(messageList, HttpStatus.OK);
		if (loanAmount < ONE) {
			messageList.add(INCORRECT_LOAN_AMOUNT);
			apiError.setStatus(HttpStatus.BAD_REQUEST);
		}
		if (loanPeriod > THREE_HUNDRED_SIXTY || loanPeriod < ONE) {
			messageList.add(INCORRECT_LOAN_PERIOD);
			apiError.setStatus(HttpStatus.BAD_REQUEST);
		}
		if (interestRate < ZERO || interestRate > THREE_HUNDRED_AND_SIXTY_FIVE) {
			messageList.add(INCORRECT_INTEREST_RATE);
			apiError.setStatus(HttpStatus.BAD_REQUEST);
		}
		return apiError;
	}
}
