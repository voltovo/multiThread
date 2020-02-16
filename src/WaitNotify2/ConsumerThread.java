package WaitNotify2;

/**
 * ConsumerThread
 */
public class ConsumerThread extends Thread{
    private DataBox dataBox;

    public ConsumerThread(DataBox dataBox){
        this.dataBox = dataBox;
    }

    @Override
    public void run(){
        for(int i = 1; i < 4; i++){
            String data = dataBox.getDate();
        }
    }
    
}