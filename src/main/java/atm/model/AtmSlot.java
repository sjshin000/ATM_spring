package atm.model;

import java.util.Date;

/**
 * Created by sjshin on 2016-04-01.
 */
public class AtmSlot { //Atm 기기의 잔액, 입금(고객,기계충전),출금(고객,기계회수), 거래일, atmRecordDetail
	private int number; //거래넘버
	private static int atmBalance = 10000;  //최초는
	private static int won_10000 = 50;
	private static int won_50000 = 10;

	private int depositAmount;
	private int withdrawAmount;
	private Date date;

	public static int getWon_10000() {
		return won_10000;
	}

	public static void setWon_10000(int won_10000) {
		AtmSlot.won_10000 = won_10000;
	}

	public static int getWon_50000() {
		return won_50000;
	}

	public static void setWon_50000(int won_50000) {
		AtmSlot.won_50000 = won_50000;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getAtmBalance() {
		return atmBalance;
	}

	public void setAtmBalance(int atmBalance) {
		this.atmBalance = atmBalance;
	}

	public int getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(int depositAmount) {
		this.depositAmount = depositAmount;
	}

	public int getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(int withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
