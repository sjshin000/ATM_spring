package atm.service;

import atm.controller.AtmController;
import atm.model.AtmSlot;
import atm.model.bank.Account;
import atm.model.bank.Banking;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by sjshin on 2016-04-04.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/expert.xml" })
public class AtmServiceTest {
	@Autowired
	AtmService atmService;
	@Autowired
	AtmController atmController;


//	@Before
//	public void initialize() {
//		Account account = new Account();
//		Banking banking = new Banking();
//		AtmSlot atmSlot = new AtmSlot();
//	}

	@Test
	public void test_01_출금테스트_고객잔고가작을때() {
		Account account = new Account();
		Banking banking = new Banking();
		AtmSlot atmSlot = new AtmSlot();

		int withdrawalAmount = 300;
		account.setAccountBalance(200);
		atmSlot.setAtmBalance(400);
		account.setAccountNumber(12345);

		Map<String, Object> result = atmService.withdraw(account, withdrawalAmount);
		assertThat(false, equalTo((Boolean)result.get("resultKey")));

		Account accountResult = (Account)result.get("accountKey");
		assertThat(200, equalTo(accountResult.getAccountBalance()));

		assertThat("고객님 계좌의 잔액이 부족합니다.", equalTo((String)result.get("messageKey")));
	}


	@Test
	public void test_02_출금테스트_atm잔고가작을때() {
		Account account = new Account();
		Banking banking = new Banking();
		AtmSlot atmSlot = new AtmSlot();

		int withdrawalAmount = 300;
		account.setAccountBalance(400);
		atmSlot.setAtmBalance(200);
		account.setAccountNumber(12345);

		Map<String, Object> result = atmService.withdraw(account, withdrawalAmount);
		assertThat(false, equalTo((Boolean)result.get("resultKey")));

		Account accountResult = (Account)result.get("accountKey");
		assertThat(400, equalTo(accountResult.getAccountBalance()));

		assertThat("Atm의 잔액이 부족합니다.", equalTo((String)result.get("messageKey")));
	}

	@Test  //@Ignore
	public void test_03_출금테스트_성공() {
		Account account = new Account();
		Banking banking = new Banking();
		AtmSlot atmSlot = new AtmSlot();

		int withdrawalAmount = 300;
		account.setAccountBalance(400);
		atmSlot.setAtmBalance(500);
		account.setAccountNumber(12345);

		Map<String, Object> result = atmService.withdraw(account, withdrawalAmount);
		assertThat(true, equalTo((Boolean)result.get("resultKey")));

		Account accountResult = (Account)result.get("accountKey");
		assertThat(100, equalTo(accountResult.getAccountBalance()));

		Banking bankingResult = (Banking)result.get("bankingKey");
		assertThat(100, equalTo(bankingResult.getAccountBalance()));

		AtmSlot atmSlotResult = (AtmSlot)result.get("atmSlotKey");
		assertThat(200, equalTo(atmSlotResult.getAtmBalance()));
	}

	@Ignore @Test  //
	public void test_04_출금테스트_성공이나_트랜잭션실패_잔고그대로() {
		Account account = new Account();
		Banking banking = new Banking();
		AtmSlot atmSlot = new AtmSlot();

		int withdrawalAmount = 300;
		account.setAccountBalance(400);
		atmSlot.setAtmBalance(500);
		account.setAccountNumber(12345);

		//atmController.withdraw(account, withdrawalAmount);
		Map<String, Object> result = new HashMap<String, Object>();
//		try{
			result = atmService.withdraw(account, withdrawalAmount);
//		} catch (Exception e){
//
//		}

		//Map<String, Object> result = atmService.withdraw(account, withdrawalAmount);
		assertThat(null, equalTo((Boolean)result.get("resultKey")));  //

		Account accountResult = (Account)result.get("accountKey");
		assertThat(400, equalTo(accountResult.getAccountBalance()));

		Banking bankingResult = (Banking)result.get("bankingKey");
		assertThat(400, equalTo(bankingResult.getAccountBalance()));

		AtmSlot atmSlotResult = (AtmSlot)result.get("atmSlotKey");
		assertThat(500, equalTo(atmSlotResult.getAtmBalance()));

		assertThat("입금/출금이 정상처리 되지 않았습니다. 다시 메뉴를 선택해 주세요.", equalTo((String)result.get("messageKey")));
	}
	//	@Test
	//	public void test_01_예금금테스트() {
	//		Account account = new Account();
	//		Banking banking = new Banking();
	//		AtmSlot atmSlot = new AtmSlot();
	//		int DepositAmount = 300;
	//		account.setAccountBalance(200);
	//		atmSlot.setAtmBalance(400);
	//		account.setAccountNumber(12345);
	//
	//		Map<String, Object> result = atmService.deposit(account, DepositAmount);
	//		assertThat(true, equalTo((Boolean)result.get("resultKey")));
	//
	//		//고객잔액에 예금금액이 추가되었는지
	//		System.out.println(Screen.ANSI_PURPLE +"고객잔액 : " + account.getAccountBalance() + Screen.ANSI_RESET);
	//		assertThat(500, equalTo(account.getAccountBalance()));
	//
	//		System.out.println(Screen.ANSI_PURPLE +"atm 잔액 3 : " + atmSlot.getAtmBalance() + Screen.ANSI_RESET);
	//		assertThat(700, equalTo(atmSlot.getAtmBalance()));
	//	}
}