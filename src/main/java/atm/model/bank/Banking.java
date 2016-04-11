package atm.model.bank;

import java.util.Date;

/**
 * Created by sjshin on 2016-04-04.
 */
public class Banking {
	private int number; //거래넘버
	private long accountNumber; //계좌번호
	private int depositAmount;//입금금액
	private int withdrawalAmount;//출금금액
	private int accountBalance; //계좌잔액
	private Date date; //거래일

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(int paymentAmount) {
		this.depositAmount = paymentAmount;
	}

	public int getWithdrawalAmount() {
		return withdrawalAmount;
	}

	public void setWithdrawalAmount(int withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}

	public int getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
