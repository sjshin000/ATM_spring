package atm.view;

import atm.controller.AtmController;
import atm.model.bank.Account;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by sjshin on 2016-03-25.
 */
public class RunDriver {
	public static void main(String[] arg){
		ApplicationContext context =  new ClassPathXmlApplicationContext("spring/expert.xml");

		AtmController atmController = context.getBean("atmController", AtmController.class);

		Account account = new Account();
		Map<String, Object> result = atmController.process(1,  account);
		//atmController.process(1,  account);
		while (true) {
			Map<String, Object> result2 = atmController.process((Integer) result.get("actionKey"),  (Account) result.get("accountKey"));
			System.out.println("액션키 : "+result2.get("actionKey") + " 오브젝트 : " + result2.get("accountKey"));
			if((Integer)result2.get("actionKey") > 9){
				break;
			} else {
				result = result2;
			}
		}
	}
}
