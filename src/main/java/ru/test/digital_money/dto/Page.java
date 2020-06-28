package ru.test.digital_money.dto;

import java.util.List;

/**
 * @author Lyubov Bochkareva
 * @since 25.06.20.
 */

public class Page<T> {

	private List<T> content;
	private int loanAmount;
	private int loanPeriod;
	private double interestRate;
	private double sumPercent;
	private double totalAmountPayments;

	public Page(int loanAmount, int loanPeriod, double interestRate) {
		this.loanAmount = loanAmount;
		this.loanPeriod = loanPeriod;
		this.interestRate = interestRate;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(int loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public int getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(int loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public double getSumPercent() {
		return sumPercent;
	}

	public void setSumPercent(double sumPercent) {
		this.sumPercent = sumPercent;
	}

	public double getTotalAmountPayments() {
		return totalAmountPayments;
	}

	public void setTotalAmountPayments(double totalAmountPayments) {
		this.totalAmountPayments = totalAmountPayments;
	}
}
