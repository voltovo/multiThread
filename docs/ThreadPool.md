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

### 스레드풀 종료
스레드풀의 스레드는 데몬 스레드가 아니기 때문에 main 스레드가 종료되더라도 작업을 처리하기 위해 계속 실행 상태로 남아있다. 스레드풀을 종료시키는 메소드
|리턴타입|메소드명(매개 변수)|설명|
|:---:|:---:|:---|
|void|shutdown()|현재 처리 중인 작업뿐만 아니라 작업 큐에 대기하고 있는 모든 작업을 처리한 뒤에 스레드풀을 종료시킨다|
|List<Runnable>|shutdownNow()|현재 작업 처리 중인 스레드를 interrupt해서 작업 중지를 시도하고 스레드풀을 종료시킨다. 리턴값은 작업 큐에 있는 미처리된 작업(Runnable)의 목록이다.|
|boolean|awaitTermination(long timeout,TimeUnit unit)|shutdown()메소드 호출 이후, 모든 작업 처리를 timeout 시간 내에 완료하면 true를 리턴하고, 완료하지 못하면 작업 처리중인 스레드를 interrupt하고 false를 리턴하다.|

### 작업 생성
Runnable 또는 Callable 클래스로 표현한다.<br>
둘의 차이점은 완료후 리턴 값의 유뮤이다.
##### Runnable 구현 클래스
<pre>
Runnable task = new Runnable(){
    @Override
    public void run(){
        //스레드가 처리할 작업 내용
    }
}
</pre>
##### Callable 구현 클래스
<pre>
Callable<T> task = new Callable<T>(){
    @Override
    public T Call()throws Exception{
        //스레드가 처리할 작업 내용
        return T;
    }
}
</pre>

### 작업 처리 요청
|리턴 타입|메소드명(매개 변수)|설명|
|:---:|:---:|:---|
|void|execute(Runnable command)|-Runnable을 작업 큐에 저장<br>-작업 처리 결과를 받지 못함|
|Future<?><br> Future< V > <br>Future< V > |submit(Runnable tas)<br>submit(Runnable task,V result)<br>submit(Callable<V>task)|-Runnable 또는 Callable을 작업 큐에 저장<br>-리턴된 Future를 통해 작업 처리 결과를 얻을 수 있음|
##### execute()
작업 처리 도중 예외가 발생하면 스레드가 종료되고 해당 스레드는 스레드풀에서 제거된다. <br>스레드풀은 다른 작업 처리를 위해 새로운 스레드를 생성한다.
##### submit()
작업 처리 도중 예외가 발생해도 스레드는 종료되지 않고 다음 작업을 위해 재사용.<br>
가급적이면 스레드의 생성 오버헤더를 줄이기 위해서 submit() 사용하는 것이 좋다.

### 블로킹 방식의 작업 완료 통보
ExecutorService의 submit() 메소드는 매개값으로 준 Runnable 또는 Callable 작업을 스레드 풀의 작업 큐에 저장하고 즉시 Future 객체를 리턴.<br>
##### Future객체
작업이 완료될 때까지 기다렸다가 (지연했다가 = 블로킹되었다) 최종 결과를 얻는데 사용. Future를 지연 완료(pending completion)객체라고 한다.
|리턴타입|메소드명(매개 변수)|설명|
|:---:|:---:|:---|
|V|get()|작업이 완료될 떄까지 블로킹되었다가 처리 결과 V를 리턴|
|V|get(long timeout,TimeUnit unit)|timeout 시간 전에 작업이 완료되면 결과 V를 리턴하지만, 작업이 완료되지 않으면 TimeoutException을 발생시킴|
다음은 세 가지 submit()메소드별로 Future의 get()메소드가 리턴하는 값이 무엇인지 보여준다.
|메소드|작업 처리 완료 후 리턴 타입|작업 처리 도중 예외 발생|
|:---|:---:|:---:|
|submit(Runnable task)|future.get()->null|future.get()->예외발생|
|submit(Runnable task,Integer result)|future.get()->int 타입 값|future.get()->예외발생|
|submit(Callable< String>task)|future.get()->String 타입값|future.get()->예외발생|
##### 주의할 점
스레드가 작업을 완료하기 전까지는 **get()메소드가 블로킹되므로 다른 코드를 실행할 수 없다.**<br>
예를 들어, UI를 변경하고 이벤트를 처리하는 스레드가 get()메소드를 호출하면 작업이 완료될 떄까지 UI를 변경과 이벤트 처리를 할 수 없다. 그래서 **get()메소드를 호출하는 스레드는 새로운 스레드이거나 스레드풀의 또 다른 스레드가 되어야 한다.**
##### Future객체의 다른 메소드
|리턴타입|매소드명(매개 변수)|설명|
|:---:|:---:|:---|
|boolean|cancel(boolean mayInterruptIfRunning)|작업 처리가 진행중일 경우 취소|
|boolean|isCancelled()|작업이 취소되었는지 여부|
|boolean|isDone()|작업 처리가 완료되었는지 여부|
### 리턴값이 없는 작업 완료 통보
리턴값이 없는 작업일 경우는 Runnable 객체로 생성하면 된다.
<pre>
Runnable task = new Runnable(){
    @Override
    public void run(){
        //스레드가 처리할 작업 내용
    }
};
</pre>
결과 값이 없는 작업 처리 요청은 submit(Runnable task) 메소드를 이용하면된다.
결과 값이 없으므로 작업 처리 도중에 예외가 발생했는지 확인하기 위한 코드
<pre>
Future future = executorService.submit(task);
try{
    future.get();
}catch(InterruptedException e){
    //작업 처리 도중 스레드가 interrupt될 경우 실행할 코드
}catch(ExecutionException e){
    //작업 처리 도중 예외가 발생된 경우 실행할 코드
}
</pre>
