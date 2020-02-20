package CompletionSevice;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * CompletionServiceExample
 */
public class CompletionServiceExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletionService<String> completionService = new ExecutorCompletionService<String>(executorService);

        for (int i = 0; i < 20; i++) {
            // 스레드풀에 작업 처리 요청
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("[작업 처리 요청 : " + threadName + " ]");
                    int sum = 0;
                    for (int j = 1; j <= 10; j++) {
                        sum += j;
                    }
                    return "작업 스레드 = " + threadName + " / 결과 = " + Integer.toString(sum);
                }
            });
        }

        System.out.println("[처리 완료된 작업 확인]");
        // 스레드풀의 스레드에서 실행하도록 함
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Future<String> future = completionService.take();
                        String value = future.get();
                        System.out.println("[처리 결과] " + value);
                    } catch (Exception e) {
                        break;
                    }
                }

            }
        });

        // 3초 후 스레드풀 종료
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        executorService.shutdown();

    }

}