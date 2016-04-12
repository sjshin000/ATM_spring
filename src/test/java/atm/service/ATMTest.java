package atm.service;

import atm.controller.AtmController;
import atm.model.bank.Account;
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
 * Created by sjshin on 2016-03-25.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:/spring/expert.xml" })
public class ATMTest {
	@Autowired
	AccountValidation accountValidation;
	@Autowired
	AtmController atmController;

	@Test
	public void test_01_계좌번호입력자리수5개_true() {
//		ApplicationContext context =  new ClassPathXmlApplicationContext("expert.xml");
//		ATM atm = context.getBean("atm", ATM.class);

		int inputData = 12345;

		Account account = new Account();
		account.setAccountNumber(inputData);

		Boolean result = accountValidation.validData(account);
		assertThat(true, equalTo(result));
		System.out.println(Screen.ANSI_PURPLE + "result 1 : " + result + Screen.ANSI_RESET);
	}

	@Test
	public void test_02_계좌번호입력자리수5개아닌경우_4자리_false() {
		int inputData = 1234;

		Account account = new Account();
		account.setAccountNumber(inputData);

		Boolean result = accountValidation.validData(account);

		assertThat(false, equalTo(result));
		System.out.println(Screen.ANSI_PURPLE + "result 2 : " + result + Screen.ANSI_RESET);
	}

	@Test
	public void test_03_계좌번호입력자리수5개아닌경우_6자리_false() {
		int inputData = 123456;

		Account account = new Account();
		account.setAccountNumber(inputData);

		Boolean result = accountValidation.validData(account);
		assertThat(false, equalTo(result));
		System.out.println(Screen.ANSI_PURPLE + "result 3 : " + result);
	}

	@Test
	public void test_04_계좌_비밀번호검증_true() {
		Account account = new Account();
		account.setAccountNumber(12345);
		account.setAccoutPwd(1234);

		Map<String, Object> result = accountValidation.validAccount(account);
		assertThat(String.valueOf(true), (Boolean)result.get("resultKey"));
		System.out.println(Screen.ANSI_PURPLE + "result 4 : " + result.get("resultKey") + Screen.ANSI_RESET);
	}


	@Test
	public void test_05_계좌_비밀번호검증_false_계좌틀림() {
		Account account = new Account();
		account.setAccountNumber(12344);
		account.setAccoutPwd(1234);

		Map<String, Object> result = accountValidation.validAccount(account);
//		assertThat(String.valueOf(false), (Boolean)result.get("resultKey"));
		assertThat(false, equalTo(result.get("resultKey")));

		System.out.println(Screen.ANSI_PURPLE + "result 5 : " + result.get("resultKey") + Screen.ANSI_RESET);
	}

	@Test
	public void test_06_계좌_비밀번호검증_false_비번틀림() {
		Account account = new Account();
		account.setAccountNumber(12345);
		account.setAccoutPwd(1233);

		Map<String, Object> result = accountValidation.validAccount(account);
//		assertThat(String.valueOf(false), (Boolean)result.get("resultKey"));
		assertThat(false, equalTo(result.get("resultKey")));
		System.out.println(Screen.ANSI_PURPLE + "result 6 : " + result.get("resultKey") + Screen.ANSI_RESET);
	}
}
