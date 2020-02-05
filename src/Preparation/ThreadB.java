package Preparation;

public class ThreadB extends Thread{
	public ThreadB() {
		//이름 설정
		setName("ThreadB");
	}
	
	public void run() {
		for(int i = 0; i < 2; i++) {
			System.out.println(getName() + "출력한 내용");
		}
	}
}
