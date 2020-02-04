package Preparation;

import java.awt.Toolkit;

public class BeepPrintExample1 {

	public static void main(String[] args) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//비프음 먼저 발생
		for(int i = 0; i < 5; i++) {
			toolkit.beep();
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				
			}
		}
		//프린팅 나중에 발생
		for(int i = 0; i < 5; i++) {
			System.out.println("띵");
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				
			}
		}

	}

}
