public class Messenger implements Runnable {
    private int time;
    public final Chrono chrono;
    public static boolean finish = false;
    public boolean flag = true;

    Messenger(int time, Chrono chrono) {
        this.chrono = chrono;
        this.time = time;
    }
    public void waitForTime()
    {
        while(true)
        {
            synchronized (chrono) {
                try {
                    while (flag) {
                        chrono.wait();
                    }
                    if (finish) return;
                    if (chrono.time % this.time == 0){
                        flag = true;
                        System.out.println("Запущен поток равный: " + String.valueOf(this.time) + " сек.");
                    }
                    flag = true;
                } catch (InterruptedException interruptedException){
                    System.out.println("Ошибка прерывания");
                }
            }
        }
    }
    public void run() {
        waitForTime();
        System.out.println("Окончание работы потока для " + String.valueOf(time) + " сек.");
    }
}
