package atm.service;

import atm.controller.AtmController;
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
import java.util.List;
import java.util.Map;
import static atm.view.MessageCode.*;
/**
 * Created by sjshin on 2016-03-24.
 * 은행 거래 실행하기
 * ATM은 계좌 정보 DB에 각 은행 계좌에 대한 계좌 번호, 비밀번호, 계좌의 잔액이 저장된다.
 * 사용자가 인증되었다.
 * ATM을 사용자는 다음과 같은 이벤트 순서를 경험할 것이다.
 */
@Service
public class AtmService {
	@Autowired
	AtmController atmController;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	BankingRepository bankingRepository;

	public boolean validData(Account account) {
		boolean result = false;

		String inputString = Integer.toString(account.getAccountNumber());
		if(inputString.length() == 5) {
			result = true;
		}
		return result;
	}

	//계좌번호와 비밀번호 일치여부 체크
	public Map<String, Object> validAccount(Account account) {
		Map<String, Object> result = new HashMap<String, Object>();
		Account bankAccount = accountRepository.selectAccount(account.getAccountNumber());
		System.out.println("3-3 account === " + bankAccount.getAccountBalance());

		if(account.getAccountNumber() == bankAccount.getAccountNumber() && account.getAccoutPwd() == bankAccount.getAccoutPwd()) {
			System.out.println(Screen.ANSI_PURPLE+"account : " + account.getAccountNumber());
			System.out.println(Screen.ANSI_PURPLE+"pwd : " + account.getAccoutPwd());

			result.put("resultKey", true);
			result.put("accountKey", bankAccount);
		} else {
			result.put("resultKey", false);
			result.put("accountKey", account);
		}

		return result;
	}


	//거래내역 조회
	public List<Banking> bankingList(Account account) {
		List<Banking> bankingList = bankingRepository.selectBankingList(account.getAccountNumber());
		return bankingList;
	}

	//잔액조회
	public void balanceInquiry(Account account) {
//		bankApi.inquireDeposit(account);

	}

//	//예금
//	@Transactional
//	public Map<String, Object> deposit(Account account, int depositAmount) {
//		Map<String, Object> result = new HashMap<String, Object>();
//		AtmSlot atmSlot = new AtmSlot();
//		Banking banking = new Banking();
//
//		banking.setAccountNumber(account.getAccountNumber());
//		banking.setDepositAmount(depositAmount);
//		banking.setAccountBalance(account.getAccountBalance() + depositAmount);
//		bankingRepository.insertDeposit(banking);
//
//		account.setAccountBalance(account.getAccountBalance() + depositAmount);
//		accountRepository.updateAccountBalance(account);
//
//		atmSlot.setAtmBalance(atmSlot.getAtmBalance() + depositAmount);
//
//		result.put("accountKey", account);
//		result.put("bankingKey", banking);
//		result.put("atmSlotKey", atmSlot);
//		result.put("resultKey", true);
//
//		return result;
//	}

	//출금
	@Transactional
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

//		try {
			Banking bankingResult = bankingSet(account, withdrawalAmount);

			Account accountResult = accountSet(account, withdrawalAmount);


			atmSlot.setAtmBalance(atmSlot.getAtmBalance() - withdrawalAmount);
			result.put("accountKey", accountResult);
			result.put("bankingKey", bankingResult);
			result.put("atmSlotKey", atmSlot);
			result.put("resultKey", true);
//			return result;
//		} catch (Exception e) {
//			result.put("resultKey", false);
//			result.put("accountKey", account);
//			result.put("messageKey", CODE_002.getMessage() );
//		}

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
