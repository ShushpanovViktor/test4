import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    BlockingQueue<String> queue;

    public Consumer(BlockingQueue<String> queue){this.queue = queue;}

    @Override
    public void run() {
        try {
            while (true){
                String value = queue.take();
                System.out.println("Consumer получил сообщение: " + value);
            }
        } catch (InterruptedException e) {
            System.out.println("Остановка работы приложения");
        }
    }
}
