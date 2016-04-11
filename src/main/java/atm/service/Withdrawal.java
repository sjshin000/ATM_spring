package atm.service;

import atm.model.bank.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Created by sjshin on 2016-04-08.
 */
@Service
public interface Withdrawal {
	@Transactional
	Map<String, Object> withdraw(Account account, int withdrawalAmount);
}
