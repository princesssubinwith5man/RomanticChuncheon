public class SleepThread extends Thread{
    public SleepThread(){

    }

    public void start(){
        while(true) {
            try {
                sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //System.out.println("실행중,,");
        }
    }
}
