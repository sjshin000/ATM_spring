package atm.service;

import atm.model.bank.Account;

import java.util.Map;

/**
 * Created by sjshin on 2016-04-07.
 */
//@Transactional
public interface Deposit {
	//예금
//	@Transactional
	Map<String, Object> deposit(Account account, int depositAmount);
}
