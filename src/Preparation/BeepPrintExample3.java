package Preparation;

public class BeepPrintExample3 {

	public static void main(String[] args) {
		
		Thread thread = new BeepThread();
		//thread 객체의 run 메소드 실행
		thread.start();
		
		for(int i = 0; i < 5; i++) {
			System.out.println("띵");
			
			try {
				thread.sleep(500);
			} catch (Exception e) {
				
			}
		}

	}

}
