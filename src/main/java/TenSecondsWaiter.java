public class TenSecondsWaiter implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(10000);
            System.out.println("10 seconds");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
