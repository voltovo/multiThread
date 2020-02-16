package WaitNotify;

/**
 * WorkObject
 */
public class WorkObject {

    public synchronized void methodA(int i){
        System.out.println("ThreadA의 methodA() 작업 실행 " + i);
        notify();;
        try {
            wait();
        } catch (InterruptedException e) {
        
        }
    }

    public synchronized void methodB(int i){
        System.out.println("ThreadB의 methodB() 작업 실행 " + i);
        notify();;
        try {
            wait();
        } catch (InterruptedException e) {
        
        }
    }
}