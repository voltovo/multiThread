package Synchronized;

public class Calculator {
	private int memory;
	
	public int getMemory() {
		return memory;
	}
	
	//계산기 메모리 값을 저장하는 메소드
	//synchronized로 메소드 전체 동기화
	public synchronized void setMemory(int memory) {
		//매개 값을 memory에 저장
		this.memory = memory;
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		System.out.println(Thread.currentThread().getName() + " : " + this.memory);
	}
}
