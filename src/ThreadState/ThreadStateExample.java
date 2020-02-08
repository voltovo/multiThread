package ThreadState;

public class ThreadStateExample {

	public static void main(String[] args) {
		//스레드 상태 출력하는 객체 생성 때, 타겟이 되는 스레드도 같이 생성
		StatePrintThread statePrintThread = new StatePrintThread(new TargetThread());
		statePrintThread.start();

	}

}
