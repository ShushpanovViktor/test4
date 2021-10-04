import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static final int ONE_SECOND = 1;
    private static final int FIVE_SECOND = 5;
    private static final int SEVEN_SECOND = 7;
    private static final int SET_TIMER = 20;

    public static void main(String[] args) {

        Chrono chrono = new Chrono();

        List<Messenger> messengers = new ArrayList<>();
        messengers.add(new Messenger(ONE_SECOND, chrono));
        messengers.add(new Messenger(FIVE_SECOND, chrono));
        messengers.add(new Messenger(SEVEN_SECOND, chrono));

        new Thread(messengers.get(0), "Первый поток").start();
        new Thread(messengers.get(1), "Второй поток").start();
        new Thread(messengers.get(2), "Третий поток").start();
        chrono.countTime(messengers, SET_TIMER);

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(2);
        File file = new File("voyna.txt");
        Consumer consumer = new Consumer(queue);
        Thread consumerThread = new Thread(consumer);

        Thread producerThread = new Thread(() -> {
            Scanner scanner = null;
            String foundWord;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Pattern pattern = Pattern.compile("страдан", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(line);

                while (matcher.find()) {
                    foundWord = matcher.group();
                    System.out.println("Producer передал сообщение " + foundWord);
                    try {
                        queue.put(foundWord);
                    } catch (InterruptedException e) {
                        System.out.println("Ошибка в прерываниях: " + e.getMessage());
                        ;
                    }
                }
            }
            consumerThread.interrupt();
        });

        producerThread.start();
        consumerThread.start();
        try {
            producerThread.join();
            consumerThread.join();
    } catch (InterruptedException e) {
            e.printStackTrace();
        }



      /*  Thread thread1 = new Thread() {

            @Override
            public void run() {
                for (int i = 1; i < 30; i++) {
                        try {
                            sleep(1000);
                            System.out.println("Прошло " + i + "секунд(ы) (1 поток)");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                              }
            }
        };
        Thread thread2 = new Thread(){
            @Override
            public void run() {
                for (int i = 1; i < 7; i++) {
                    try {
                        sleep(5000);
                        System.out.println("Прошло " + 5*i + "секунд(ы) (2 поток)");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            };

        thread2.start();
        thread1.start();
        //new Thread(new TenSecondsWaiter()).start();
        System.out.println("Main thread");*/
    }
}
