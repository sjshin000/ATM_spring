package atm.controller;

import atm.model.AtmSlot;
import atm.model.bank.Account;
import atm.model.bank.Banking;
import atm.service.AtmService;
import atm.service.Deposit;
import atm.service.Withdrawal;
import atm.view.Keypad;
import atm.view.Screen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static atm.view.MessageCode.*;

/**
 * Created by sjshin on 2016-03-24.
 */
@Controller
public class AtmController {
	@Autowired
	AtmService atmService;

	@Autowired
	Deposit depositService;

	@Autowired
	Withdrawal withdrawalService;


	//ATM 실행기 흐름 제어 (다른 일반 메소드에서는 흐름을 제어하지 않도록 작업)
	public Map<String, Object> process(int actionCode, Account account){
		//1. 화면에 환영 메시지가 출력되고 사용자에게 계좌 번호 입력을 요구하는 메시지가 출력
		Map<String, Object> result = new HashMap<String, Object>();

		switch (actionCode) {
			case 1 : // 환영 메시지 출력
				print(CODE_101.getMessage());

			case 2 : // 계좌번호 입력 메시지 출력 -> 계좌번호 입력 -> 계좌번호 자리수 검증 | 성공 : case3 으로 이동, 실패 case1로 이동
				result = validData();
				break;

			case 3 : // 비밀번호 입력/검증
				result = validAccount(account);

				System.out.println("account 3 : " + result.get("accountKey"));
				break;

			case 4 : //ATM이 사용자를 인증한 후에 메인 화면은 잔액 조회(옵션1), 출금(옵션2), 입금(옵션3), 시스템 종료(옵션4)
				result = selectMenu(account);
				break;

			case 5 : //거래내역조회 balanceInquiry / 잔액조회 inquire deposit
				result = bankingList(account);
				//process((Integer)result5.get("actionKey"), (Account)result5.get("accountKey"));
				break;

			case 6 : //출금 Withdraw
				//System.out.println("account : 6 " + account.getAccountBalance());
				result = withdraw(account);
				break;

			case 7 : //입금 Deposit
				result = deposit(account);
				break;

			case 8 : //
				print(CODE_020.getMessage());
				System.exit(999);
				break;
			case 9 : //슬롯잔액확인
				System.out.println("============9번 메뉴");
				result = atmBalance(account);
				break;
		}
		return result;
	}

	private Map<String,Object> deposit(Account account) {
		Map<String, Object> result = new HashMap<String, Object>();
		print(CODE_106.getMessage());
		int depositAmount = input();

		Map<String, Object> depositResult = depositService.deposit(account, depositAmount);
		if(true == (Boolean) depositResult.get("resultKey")) {
			print(CODE_016.getMessage());
			result = depositResult;
			printDepositResult(result);
			result.put("actionKey", 4);
			result.put("accountKey", account);
		}
		return result;
	}

	//출금 Withdraw
	private Map<String,Object> withdraw(Account account) {
		Map<String, Object> result = new HashMap<String, Object>();
		print(CODE_105.getMessage());
		int withdrawalAmount = input();

		try {
			Map<String, Object> withdrawResult = withdrawalService.withdraw(account, withdrawalAmount);
			if(false == (Boolean) withdrawResult.get("resultKey")) {
				//print(CODE_013.getMessage());
				result = withdrawResult;
				print((String) result.get("messageKey"));

				result.put("actionKey", 4);
				result.put("accountKey", account);
			} else {
				result = withdrawResult;
				//print((String) result.get("messageKey"));
				print(CODE_015.getMessage());
				printWithdrawResult(result);

				result.put("actionKey", 4);
				result.put("accountKey", account);
			}
		} catch (Exception e) {
			print(CODE_002.getMessage());
			result.put("actionKey", 4);
			result.put("accountKey", account);
		}
		return result;
	}

	private Map<String,Object> bankingList(Account account) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Banking> bankingList = atmService.bankingList(account); //거래내역조회

		System.out.println("account 뱅킹리스트 " + account.getAccountBalance());
		printList(bankingList);

		result.put("actionKey", 4);
		result.put("accountKey", account);
		return result;
	}

	

	private Map<String, Object>  selectMenu(Account account) {
		System.out.println("==account 4 : " + account.getAccountBalance());
		Map<String, Object> result = new HashMap<String, Object>();
		int menuCode = 0;
		print(CODE_104.getMessage()); //메인 화면: 잔액 조회(옵션1), 출금(옵션2), 입금(옵션3), 시스템 종료(옵션4)
		int selectMenuCode = input();

		switch (selectMenuCode) {
			case 1 : menuCode = 5;
				break;
			case 2 : menuCode = 6;
				break;
			case 3 : menuCode = 7;
				break;
			case 4 : menuCode = 8;
				break;
			case 9 : menuCode = 9;
				break;
		}

		result.put("actionKey", menuCode);
		result.put("accountKey", account);
		return result;
	}

	public Map<String, Object> validData() {  //Bank에 계좌/비밀번호 체크 하기전에 먼저 계좌번호 자리수 검증
		Map<String, Object> result = new HashMap<String, Object>();

		print(CODE_102.getMessage()); //은행코드 입력
		int inputBankCode = input();

		print(CODE_103.getMessage()); //계좌번호 입력
		int inputAccountNumber = input();

		Account account = new Account();
		account.setAccountNumber(inputBankCode);
		account.setAccountNumber(inputAccountNumber);

		if (true == atmService.validData(account)) {
			print(CODE_003.getMessage());
			Object actionCode =  3;
			result.put("actionKey", actionCode);
			result.put("accountKey", account);

		} else {
			print(CODE_001.getMessage());  // 비밀번호 다시 입력
			//validData();
			Object actionCode =  2;
			result.put("actionKey", actionCode);
			result.put("accountKey", account);
		}

		System.out.println(Screen.ANSI_PURPLE+"===result 사이즈 " + result.size());
		return result;
	}



	public Map<String, Object> validAccount(Account account) { //Bank에 계좌/비밀번호 검증
		System.out.println(Screen.ANSI_PURPLE+"validAccount account 1 : " + account.getAccountNumber());
		print(CODE_107.getMessage());
		int inputPwd = input();

		account.setAccoutPwd(inputPwd);
		Map<String, Object> result = atmService.validAccount(account);
		if(true == (Boolean) result.get("resultKey")) {
			result.put("actionKey", 4);
		} else {
			print(CODE_012.getMessage());
			result.put("actionKey", 2);
		}

		Account account1 = (Account) result.get("accountKey");
		System.out.println("==account 3 : " + account1.getAccountBalance());
		return result;
	}



	public void print(String message) {
		Screen screen = new Screen();
		screen.printMessage(message);
	}

	public void printList(List<Banking> list) {
		Screen screen = new Screen();
		screen.printList(list);
	}

	private void printWithdrawResult(Map<String, Object> result) {
		Screen screen = new Screen();
		screen.printWithdrawResult(result);
	}

	private void printDepositResult(Map<String, Object> result) {
		Screen screen = new Screen();
		screen.printDipositResult(result);
	}
	public int input() {
		Keypad keypad = new Keypad();
		return keypad.inputData();
	}

	public Map<String, Object> atmBalance(Account account) {
		Map<String, Object> result = new HashMap<String, Object>();

		AtmSlot atmSlot = new AtmSlot();
		System.out.println(Screen.ANSI_PURPLE + "atm기계의잔액은 : " + atmSlot.getAtmBalance() + Screen.ANSI_RESET);

		result.put("actionKey", 4);
		result.put("accountKey", account);
		return result;
	}
}
