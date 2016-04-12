package atm.repository;

import atm.batch.Statistics;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by sjshin on 2016-04-12.
 */
@Repository
public class StatisticsRepository {
	@Autowired
	private SqlSessionTemplate sqlSession;

	public int setStatistics(Statistics statistics) {
		int count = sqlSession.insert("statistics.insertStatistics", statistics);
		return count;
	}

	public int bankingListOneDay() {
		int count = 0;
		return count;
	}
}
