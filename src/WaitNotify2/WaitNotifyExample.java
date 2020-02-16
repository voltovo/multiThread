package WaitNotify2;

/**
 * WaitNotifyExample
 */
public class WaitNotifyExample {
public static void main(String[] args) {

    DataBox dataBox = new DataBox();
    ConsumerThread consumerThread = new ConsumerThread(dataBox);
    ProducerThread producerThread = new ProducerThread(dataBox);

    producerThread.start();;
    consumerThread.start();;
}
    
}