import java.util.List;

public class Chrono {

    public int time = 0;
    private final int ONE_SECOND = 1000;
    public void countTime(List<Messenger> messengers, int period)
    {
        for (int index = 0; index < period; index++) {
            synchronized (this) {
                time +=1;
                System.out.println("Прошло с момента начала сессии:" + String.valueOf(time) + " сек.");
                if (index == period -1)
                {
                    Messenger.finish = true;
                }
                for (Messenger messenger : messengers)
                {
                    messenger.flag = setFlagFalse();
                }
            }
            try {
                Thread.sleep(ONE_SECOND);
            } catch (InterruptedException e) {
                System.out.println("Ошибка прерывания");
            }
        }
    }
    private boolean setFlagFalse(){
        this.notify();
        return false;
    }
}
