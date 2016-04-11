package atm.service;

import atm.model.AtmSlot;
import atm.model.bank.Account;
import atm.model.bank.Banking;
import atm.view.Screen;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by sjshin on 2016-04-04.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/expert.xml" })
public class AtmDipositTest {

	@Autowired
	DepositService depositService;

	@Test
	public void test_02_예금금테스트_인터페이스분리후() {
		Account account = new Account();
		Banking banking = new Banking();
		AtmSlot atmSlot = new AtmSlot();
		int DepositAmount = 300;
		account.setAccountBalance(200);
		atmSlot.setAtmBalance(400);
		account.setAccountNumber(12345);

		Map<String, Object> result = depositService.deposit(account, DepositAmount);
		assertThat(true, equalTo((Boolean)result.get("resultKey")));

		//고객잔액에 예금금액이 추가되었는지
		System.out.println(Screen.ANSI_PURPLE +"고객잔액 : " + account.getAccountBalance() + Screen.ANSI_RESET);
		assertThat(500, equalTo(account.getAccountBalance()));

		System.out.println(Screen.ANSI_PURPLE +"atm 잔액 3 : " + atmSlot.getAtmBalance() + Screen.ANSI_RESET);
		assertThat(700, equalTo(atmSlot.getAtmBalance()));
	}



}