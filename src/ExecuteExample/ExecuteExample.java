package ExecuteExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * ExecuteExample
 */
public class ExecuteExample {
    public static void main(String[] args)throws Exception {
        //최대 스레드 개수가 2인 스레드풀 생성
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    //스레드 총 개수 및 작업 스레드 이름 출력
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
                    int poolSize = threadPoolExecutor.getPoolSize();
                    String threadName = Thread.currentThread().getName();
                    System.out.println("[총 스레드 개수 : " + poolSize + "] 작업 스레드 이름 : " + threadName);

                    //예외 발생 시킴
                    int value = Integer.parseInt("삼");
                }
            };
            //작업 처리 요청(작업 처리 도중 예외가 발생하면 해당 스레드 제거
            //새 스레드가 계속 생성)
            //executorService.execute(runnable);
            //작업 처리 요청(작업 처리 도중 예외가 발생해도 스레드가 종료
            //되지 않고 계속 재사용되어 다른 작업을 처리)
            executorService.submit(runnable);

            Thread.sleep(10); //콘솔에 출력 시간을 주기 위해 0.01초 일시 정지 시킴
        }
        //스레드풀 종료
        executorService.shutdown();
    }
}