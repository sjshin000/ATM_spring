package atm.service;

import atm.model.AtmSlot;
import atm.model.bank.Account;
import atm.model.bank.Banking;
import atm.repository.AccountRepository;
import atm.repository.BankingRepository;
import atm.view.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sjshin on 2016-04-07.
 */
@Service
public class DepositService implements Deposit{
	@Autowired
	BankingRepository bankingRepository;

	@Autowired
	AccountRepository accountRepository;

	@Transactional("transactionManager")
	public Map<String, Object> deposit(Account account, int depositAmount) {
		System.out.println(Screen.ANSI_PURPLE +"==========================deposit 메소드진입==========="+Screen.ANSI_RESET);
		Map<String, Object> result = new HashMap<String, Object>();
		AtmSlot atmSlot = new AtmSlot();
		Banking banking = new Banking();

		banking.setAccountNumber(account.getAccountNumber());
		banking.setDepositAmount(depositAmount);
		banking.setAccountBalance(account.getAccountBalance() + depositAmount);
		bankingRepository.insertDeposit(banking);

		account.setAccountBalance(account.getAccountBalance() + depositAmount);
		accountRepository.updateAccountBalance(account);

		atmSlot.setAtmBalance(atmSlot.getAtmBalance() + depositAmount);

		result.put("accountKey", account);
		result.put("bankingKey", banking);
		result.put("atmSlotKey", atmSlot);
		result.put("resultKey", true);
		return result;
	}
}
