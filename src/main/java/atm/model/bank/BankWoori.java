package atm.model.bank;

import java.util.ArrayList;
//import static atm.model.bank..;

/**
 * Created by sjshin on 2016-03-25.
 */
public class BankWoori extends Bank{
	//Account List
	Bank.BankName bankName = Bank.BankName.WOORI;
	private int bankCharge ; //은행수수료
	private ArrayList<Account> accountList = new ArrayList<Account>(); //account number 키/벨로로 바꿔야 하나?

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(ArrayList<Account> accountList) {
		this.accountList = accountList;
	}
}
