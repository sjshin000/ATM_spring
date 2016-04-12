package atm.batch;

import java.util.Date;

/**
 * Created by sjshin on 2016-04-12.
 */
public class Statistics {
	Date date ;
	int dateDepositAmount;
	int dateWithdrawalAmount;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getDateDepositAmount() {
		return dateDepositAmount;
	}

	public void setDateDepositAmount(int dateDepositAmount) {
		this.dateDepositAmount = dateDepositAmount;
	}

	public int getDateWithdrawalAmount() {
		return dateWithdrawalAmount;
	}

	public void setDateWithdrawalAmount(int dateWithdrawalAmount) {
		this.dateWithdrawalAmount = dateWithdrawalAmount;
	}
}
