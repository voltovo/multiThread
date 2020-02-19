package ResultByCallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ResultByCallableExample
 */
public class ResultByCallableExample {
    public static void main(String[] args) {
        //CPu 코어의 수만큼 최대 스레드를 사용하는 스레드풀을 생성
        ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
        );

        System.out.println("[작업 처리 요청]");
        Callable<Integer> task = new Callable<Integer>(){
            @Override
            public Integer call() throws Exception{
                int sum = 0;
                for (int i = 0; i <= 10; i++) {
                    sum += i;
                }
                return sum;
            }
        };

        Future<Integer> future = executorService.submit(task);
        try {
            int sum = future.get();
            System.out.println("[처리 결과]" + sum);
            System.out.println("[작업 처리 완료]");
        } catch (Exception e) {
            System.out.println("[실행 예외 발생함] " + e.getMessage());
        }

        executorService.shutdown();
    }
    
}