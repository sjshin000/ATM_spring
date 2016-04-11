package atm.model.bank;

import java.util.ArrayList;

/**
 * Created by sjshin on 2016-03-24.
 */
public class Bank {
	//Account List
	public enum BankName{
		WOORI,
		HANA
	}
	private int bankCharge;
	private  ArrayList<Account> accountList = new ArrayList<Account>(); //account number 키/벨로로 바꿔야 하나?

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}
}
