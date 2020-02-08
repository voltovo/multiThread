package ThreadState;

public class TargetThread extends Thread {

	@Override
	public void run() {

		// 10억 번 반복문 돌게 해서 RUNNABLE 상태 유지
		for (long i = 0; i < 1000000000; i++) {

		}

		// 1.5초간 TIMED WAITING 상태 유지
		try {
			// 1.5초간 일시 정지
			Thread.sleep(1500);
		} catch (Exception e) {

		}

		for (long i = 0; i < 1000000000; i++) {

		}
	}

}
