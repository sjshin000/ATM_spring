package atm.batch;

import atm.model.bank.Account;
import atm.model.bank.Banking;
import atm.repository.AccountRepository;
import atm.repository.BankingRepository;
import atm.repository.StatisticsRepository;
import atm.view.Screen;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by sjshin on 2016-04-12.
 */
@Component
@Scope("step")
public class BatchReader implements ItemReader {
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	BankingRepository bankingRepository;

	@Autowired
	StatisticsRepository statisticsRepository;

	@Override
	public Statistics read() throws Exception {
		System.out.println("========read=====");
		int accountSrl = 12345;
		Account account = accountRepository.selectAccount(accountSrl);
		System.out.println("====이름 : " + account.getUserName());

		List<Banking> bankingList = bankingRepository.selectBankingList(accountSrl);

		Map<String, Integer> depositMap = new HashedMap();
		Map<String, Integer> withdrawalMap = new HashedMap();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


		for(Banking banking : bankingList) {
			String date = dateFormat.format(banking.getDate());

			System.out.println(Screen.ANSI_PURPLE + "======= date=== " + date + Screen.ANSI_RESET);
			System.out.println(Screen.ANSI_PURPLE + "======= depositMap.get(date)=== " + depositMap.get(date) + Screen.ANSI_RESET);

			if (null == depositMap.get(date)) {
				depositMap.put(date, banking.getDepositAmount());
				withdrawalMap.put(date, banking.getWithdrawalAmount());
			} else {
				depositMap.put(date, banking.getDepositAmount() + depositMap.get(date));
				withdrawalMap.put(date, banking.getWithdrawalAmount() + withdrawalMap.get(date));
			}

			System.out.println(Screen.ANSI_PURPLE + "=== date " + dateFormat.format(banking.getDate()) + Screen.ANSI_RESET);
		}

		//모델에 set 하기
		for( String key : depositMap.keySet() ){
			System.out.println(Screen.ANSI_YELLOW + String.format("키 : %s, 값 : %s", key, depositMap.get(key)) + Screen.ANSI_RESET);
			for (String key2 : withdrawalMap.keySet()) {
				if(key == key2) {
					Statistics statistics = new Statistics();
					statistics.setDate(dateFormat.parse(key));
					statistics.setDateDepositAmount(depositMap.get(key));
					statistics.setDateWithdrawalAmount(withdrawalMap.get(key));
					statisticsRepository.setStatistics(statistics);
				}
			}
		}
		return null;
	}
}
