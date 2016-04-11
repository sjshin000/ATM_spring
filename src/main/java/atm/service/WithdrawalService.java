package atm.service;

import atm.model.AtmSlot;
import atm.model.bank.Account;
import atm.model.bank.Banking;
import atm.repository.AccountRepository;
import atm.repository.BankingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static atm.view.MessageCode.CODE_013;
import static atm.view.MessageCode.CODE_014;

/**
 * Created by sjshin on 2016-04-11.
 */
@Service
public class WithdrawalService implements Withdrawal {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	BankingRepository bankingRepository;

	public Map<String, Object> withdraw(Account account, int withdrawalAmount) {
		Map<String, Object> result = new HashMap<String, Object>();
		AtmSlot atmSlot = new AtmSlot();

		//고객잔고와 출금요청금액 비교
		if(withdrawalAmount > account.getAccountBalance()) {
			result.put("resultKey", false);
			result.put("accountKey", account);
			result.put("messageKey", CODE_013.getMessage());
			return result;
		}

		if(withdrawalAmount > atmSlot.getAtmBalance()) {
			result.put("resultKey", false);
			result.put("accountKey", account);
			result.put("messageKey", CODE_014.getMessage());
			return result;
		}

		Banking bankingResult = bankingSet(account, withdrawalAmount);

		Account accountResult = accountSet(account, withdrawalAmount);


		atmSlot.setAtmBalance(atmSlot.getAtmBalance() - withdrawalAmount);
		result.put("accountKey", accountResult);
		result.put("bankingKey", bankingResult);
		result.put("atmSlotKey", atmSlot);
		result.put("resultKey", true);
		return result;
	}

	private Account accountSet(Account account, int withdrawalAmount) {
		account.setAccountBalance(account.getAccountBalance() - withdrawalAmount);
		accountRepository.updateAccountBalance(account);
		return account;
	}

	private Banking bankingSet(Account account, int withdrawalAmount) {
		Banking banking = new Banking();
		banking.setAccountNumber(account.getAccountNumber());
		banking.setWithdrawalAmount(withdrawalAmount);
		banking.setAccountBalance(account.getAccountBalance() - withdrawalAmount);
		bankingRepository.insertWithdraw(banking);
		return banking;
	}
}
