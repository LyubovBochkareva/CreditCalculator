package ru.test.digital_money.dto;

import java.time.LocalDate;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

public class Payment {

	private LocalDate paymentDate;
	private int paymentNumber;
	private double amountInterest;
	private double amountPrincipal;
	private double paymentAmount;
	private double principalBalance;

	public Payment() {

	}

	public Payment(LocalDate paymentDate, int paymentNumber, float amountInterest, float amountPrincipal, float paymentAmount, float principalBalance) {
		this.paymentDate = paymentDate;
		this.paymentNumber = paymentNumber;
		this.amountInterest = amountInterest;
		this.amountPrincipal = amountPrincipal;
		this.paymentAmount = paymentAmount;
		this.principalBalance = principalBalance;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int getPaymentNumber() {
		return paymentNumber;
	}

	public void setPaymentNumber(int paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public double getAmountInterest() {
		return amountInterest;
	}

	public void setAmountInterest(double amountInterest) {
		this.amountInterest = amountInterest;
	}

	public double getAmountPrincipal() {
		return amountPrincipal;
	}

	public void setAmountPrincipal(double amountPrincipal) {
		this.amountPrincipal = amountPrincipal;
	}

	public double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public double getPrincipalBalance() {
		return principalBalance;
	}

	public void setPrincipalBalance(double principalBalance) {
		this.principalBalance = principalBalance;
	}
}

