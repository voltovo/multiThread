ThreadPool
========
### 스레드풀이 필요한 이유
병렬 처리 증가 , 스레드 개수 증가, 스레드 생성과 스케쥴링으로 CPU의 메모리 사용량 증가로<br> 애플리케이션의 **성능 저하** <br>
**이렇게 갑작스런 스레드의 폭증을 막기위해서 스레드풀을 사용해야 한다.**<br>
##### 처리 방식
스레드를 제한된 개수만큼 정해 놓고 작업 큐(Queue)에 들어오는 작업들을 하나씩 스레드가 맡아 처리. 처리가 끝난 스레드는 다시 작업 큐에서 새로운 작업들을 하나씩 맡아서 처리.<br>
**작업 처리 요청이 폭증되어도 스레드의 전체 개수가 늘어나지 않아서 애플리케이션 성능이 급격히 저하되지 않는다.**

### 스레드풀 생성
기본적으로 ExecutorService 인터페이스와 Executors 클래스로 생성<br>
|메소드명(매개변수)|초기 스레드 수|코어 스레드 수|최대 스레드 수|
|:---:|:---:|:---:|:---:|
|newCachedThreadPool()|0|0|Integer.MAX_VALUE|
|newFixedThreadPool(int nThreads)|0|nThreads|nThreads|
##### 초기 스레드 수
ExecutorService객체가 생성될 때 기본적으로 생성되는 스레드 수
##### 코어 스레스 수
사용되지 않는 스레드를 스레드풀에서 제거할 때 최소한 유지해야 할 스레드 수
##### 최대 스레드 수
스레드풀에서 관리하는 최대 스레드 수
##### newCachedThreadPool
이론적으로 int 값이 가질 수 있는 최대값만큼 스레드 추가, 하지만 운영체제 성능과 상황에 따라 달라진다. 1개 이상의 스레드가 추가되었을 경우 60초 동안 추가된 스레드가 아무 작업을 하지 않으면 추가된 스레드를 종료하고 풀에서 제거한다.
<pre>
ExecutorService executorService = Executors.netCachedThreadPool();
</pre>
##### newFixedThreadPool
스레드가 작업을 처리하지 않고 놀고 있더라도 스레드 개수가 줄지 않는다.
<pre>
ExecutorService executorService = Executors.newFixedThreadPool{
    Runtime.getRuntime().availableProcessors()
};
</pre>
##### ThreadPoolExecutor
코어 스레드 개수와 최대 스레드 개수를 설정할 수 있다.
<pre>
ExecutorService threadPool = new ThreadPoolExecutor(
    3,      //코어 스레드 개수
    100,   //최대 스레드 개수
    120L,  //놀고 있는 시간
    TimeUnit.SECONDS,   //놀고 있는 시간 단위
    new SynchronousQueue<Runnable>()   //작업 큐
);
</pre>