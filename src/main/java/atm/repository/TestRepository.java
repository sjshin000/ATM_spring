package atm.repository;

import atm.model.bank.Account;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TestRepository {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public Account selectTest() {
		Account account = sqlSession.selectOne("test.select");
		return account;
	}
}