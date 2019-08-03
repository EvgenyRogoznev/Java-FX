import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.*;

public class VCounterTest {

    @Test
    public void setCounterVirus() {
        VCounter vc=new VCounter();
        vc.setCounterVirus(12);
        assertEquals(12, vc.getCounterVirus());
    }

    @Test
    public void setCounterVirus2() {
        VCounter vc=new VCounter();
        vc.setCounterVirus(1);
        assertEquals(1, vc.getCounterVirus());
    }

    @Test
    public void setCounterVirus3() {
        VCounter vc=new VCounter();
        assertEquals(1, vc.getCounterVirus());
    }

    @Test
    public void getCounterVirus1() {
        VCounter vc=new VCounter();
        assertEquals(1, vc.getCounterVirus());
    }

    @Test
    public void getCounterVirus2() {
        VCounter vc=new VCounter();
        vc.setCounterVirus(4);
        assertEquals(4, vc.getCounterVirus());
    }

    @Test
    public void setAndGetStartT() {
        VCounter vc=new VCounter();
        LocalTime time =LocalTime.parse("12:12");
        vc.setStartT(time);
        assertEquals(time  , vc.getStartT());
    }

    @Test
    public void setAndGetStopTime() {
        VCounter vc=new VCounter();
        LocalTime time =LocalTime.parse("12:12");
        vc.setStopTime(time);
        assertEquals(time, vc.getStopTime());
    }

    @Test
    public void isCounterWork() {
        VCounter vc=new VCounter();
        assertEquals(false, vc.isCounterWork());
    }

    @Test
    public void setCounterWork() {
        VCounter vc=new VCounter();
        vc.setCounterWork(true);
        assertEquals(true, vc.isCounterWork());
    }

    @Test
    public void counter() {
        VCounter vc=new VCounter();
        LocalTime time =LocalTime.parse("12:12");
        vc.setStopTime(time);
        vc.setCounterWork(true);
    }

    @Test
    public void stopCounter() {
        VCounter vc=new VCounter();
        LocalTime time =LocalTime.parse("12:12");
        vc.setStopTime(time);
        vc.stopCounter();
        assertEquals(null, vc.getStopTime());
        assertEquals(false, vc.isCounterWork());
        assertEquals(1, vc.getCounterVirus());
    }
}
