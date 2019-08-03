
import java.time.LocalTime;
import static java.lang.Thread.sleep;

public class VCounter implements VirusCounter {

    public VCounter() {
        this.counterVirus = 1;
    }

    private int counterVirus;
    private LocalTime startT;
    private LocalTime stopTime;
    private boolean counterWork=false;

    public void setCounterVirus(int counterVirus) {
        this.counterVirus = counterVirus;
    }
    public int getCounterVirus() {
        return counterVirus;
    }

    public void setStartT(LocalTime startT) {
        this.startT = startT;
    }
    public LocalTime getStartT() {
        return startT;
    }

    public void setStopTime(LocalTime stopTime) {
        this.stopTime = stopTime;
    }
    public LocalTime getStopTime() {
        return stopTime;
    }

    public boolean isCounterWork() {
        return counterWork;
    }
    public void setCounterWork(boolean counterWork) {
        this.counterWork = counterWork;
    }

    Thread treadCounter ;
    @Override
    public void counter() {
        Thread treadCounter = new Thread(new Runnable(){
            public void run() {//Этот метод будет выполняться в побочном потоке
                while(isCounterWork()&&(stopTime==null||stopTime.isAfter(LocalTime.now()))){
                    for (int m =0;m<60;m++) {
                        try {

                            sleep(1000);
                        } catch (InterruptedException e) {
                            break;
                        }
                        if (m == 59) {
                            counterVirus *= 2;
                        }
                    }
                }
                counterVirus=1;
                setCounterWork(false);
                setCounterVirus(1);
                stopCounter();
            }
        }

        );

        try {treadCounter.start();	//Запуск потока
        } catch (Exception e) {
            treadCounter.run();
            System.out.println("Fucking thread do not start");
        }
    }
    @Override
    public void stopCounter(){
        stopTime=null;
    }
}
