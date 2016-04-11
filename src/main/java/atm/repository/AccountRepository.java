package atm.repository;

import atm.model.bank.Account;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by sjshin on 2016-04-04.
 */

@Repository
public class AccountRepository {
	@Autowired
	private SqlSessionTemplate sqlSession;

	public Account selectAccount(int accountNumber) {
		Account account = sqlSession.selectOne("account.select", accountNumber);
		return account;
	}

	public int updateAccountBalance(Account account) {
		throw new RuntimeException("====================test=======");
		//int count = sqlSession.update("account.updateAccountBalance", account);
		//return count;
	}
}
