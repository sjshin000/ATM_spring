package atm.service;

import atm.controller.AtmController;
import atm.model.bank.Account;
import atm.model.bank.Banking;
import atm.repository.AccountRepository;
import atm.repository.BankingRepository;
import atm.view.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

}
