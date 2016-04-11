package atm.view;

import java.util.Scanner;

/**
 * Created by sjshin on 2016-03-24.
 * //키패드 : 사용자로부터 숫자를 입력받는다
 */
public class Keypad {
	//2. 사용자는 키패드를 사용하여 5자리의 계좌번호를 입력

	//4. 사용자는 키패드를 사용하여 5개의 숫자를 입력
	public int inputData() {
		Scanner input = new Scanner(System.in);
		int inputDatas = input.nextInt();
		return inputDatas;
	}
}
