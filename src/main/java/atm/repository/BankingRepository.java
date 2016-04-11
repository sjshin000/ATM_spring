package atm.repository;

import atm.model.bank.Banking;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sjshin on 2016-04-03.
 */

@Repository
public class BankingRepository {
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<Banking> selectBankingList(int accountNumber) {
		List<Banking> bankingList = sqlSession.selectList("banking.selectList", accountNumber);
		return bankingList;
	}

	public void insertWithdraw(Banking banking) {
		sqlSession.insert("banking.insertWithdraw", banking);
	}

	public void insertDeposit(Banking banking) {
		sqlSession.insert("banking.insertDeposit", banking);
	}
}
