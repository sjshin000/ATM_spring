package atm.model.bank;

/**
 * Created by sjshin on 2016-03-24.
 * 계좌정보만
 *
 * 	//은행 거래 실행하기
 * 	계좌 번호	비밀번호 잔액
 * CashDispenser	 날마다 20달러 500개로 시작
 * 	현금 지급하기,
 인출할 현금이 충분한지 여부를 알려주기
 *
 *계좌 잔액 정보 가져오기,
 *계좌에 입금하기,
 *계좌로부터 출금하기
 */
public class Account {
	////5. 사용자가 올바른 계좌 번호와  PIN을 입력한다면 주 메뉴가 화면에 출력되고, 틀린 계좌 번호나 PIN을 입력한다면, 화면은 적절한 메시지를 출력한 후 다시 1단계로 넘어간다.
	//ATM은 계좌 정보 DB에 각 은행 계좌에 대한 계좌 번호, 비밀번호, 계좌의 잔액
	private String userNumber; //고객넘버
	private String userName; //고객이름
	private String bankName; //은행명
	private int accountNumber; //계좌번호
	private int accoutPwd; //계좌비밀번호
	private int accountBalance; //계좌잔액

	public String getBank() {
		return bankName;
	}

	public void setBank(int bankCode) {
		Bank.BankName bankName = null;

		switch (bankCode) {
			case  1 : bankName = Bank.BankName.WOORI;
			case  2 : bankName = Bank.BankName.HANA;
		}
		this.bankName = bankName.name();
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNumber() {
		return userNumber;
	}

	public void setUserNumber(String userNumber) {
		this.userNumber = userNumber;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public int getAccoutPwd() {
		return accoutPwd;
	}

	public void setAccoutPwd(int accoutPwd) {
		this.accoutPwd = accoutPwd;
	}

	public int getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}


}
