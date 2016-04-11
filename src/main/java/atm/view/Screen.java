package atm.view;

import atm.controller.AtmController;
import atm.model.bank.Banking;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by sjshin on 2016-03-24.
 * 화면 : 사용자에게 메시지를 출력한다
 */
public class Screen {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	@Autowired
	AtmController atmController;
	//3. 화면에 지정된 계좌 번호와 연계된 PIN을 입력하라는 메시지 출력

	//5. 사용자가 올바른 계좌 번호와  PIN을 입력한다면 주 메뉴가 화면에 출력되고, 틀린 계좌 번호나 PIN을 입력한다면, 화면은 적절한 메시지를 출력한 후 다시 1단계로 넘어간다.

	public void printMessage(String message) {
		System.out.println(ANSI_YELLOW + message + ANSI_RESET); //
	}

	public void printList(List<Banking> list) {
		System.out.println(ANSI_YELLOW + " | "
				+ "순번" + " | "
				+ "입금금액" + " | "
				+ "출금금액"  + " | "
				+ " 잔 액 " + " | "
				+ "거래날짜" + " | "
				+ ANSI_RESET
		);

		for(Banking banking : list) {
			System.out.println(ANSI_YELLOW + " | "
					+ banking.getNumber() + "  | "
					+ banking.getDepositAmount() + "    | "
					+ banking.getWithdrawalAmount() + "     | "
					+ banking.getAccountBalance() + "   | "
					+ banking.getDate() + "     | "
					+ ANSI_RESET
			);
		}
	}

	public void printWithdrawResult(Map<String, Object> result) {
		Banking banking = (Banking)result.get("bankingKey");

		System.out.println(ANSI_YELLOW + " 출금금액 :  "
				+ banking.getWithdrawalAmount() //출금금액
				+ "원  |  잔액 : "
				+ banking.getAccountBalance()
				+ "원"
				+ ANSI_RESET
		);
	}

	public void printDipositResult(Map<String, Object> result) {
		Banking banking = (Banking)result.get("bankingKey");

		System.out.println(ANSI_YELLOW + " 입금금액 :  "
				+ banking.getDepositAmount()
				+ "원  |  잔액 : "
				+ banking.getAccountBalance()
				+ "원"
				+ ANSI_RESET
		);
	}
}
