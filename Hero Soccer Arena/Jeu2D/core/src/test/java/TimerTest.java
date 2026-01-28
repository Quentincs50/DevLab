//
//import com.jeudefoot.coupedumonde.pannel.Timer;
//
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class TimerTest {
//
//    @Test
//    void testConstructeur() {
//        Timer timer = new Timer(60f);
//        assertEquals(60f, timer.getTimeRemain());
//        assertFalse(timer.isFinish());
//    }
//
//    @Test
//    void testStart() {
//        Timer timer = new Timer(60f);
//        timer.start();
//        timer.update(1f);
//        assertEquals(59f, timer.getTimeRemain());
//    }
//
//    @Test
//    void testPause() {
//        Timer timer = new Timer(60);
//        timer.start();
//        timer.pause();
//        timer.update(1f);
//        assertEquals(60, timer.getTimeRemain());
//    }
//
//    @Test
//    void testResume() {
//        Timer timer = new Timer(60);
//
//        timer.start();
//        timer.pause();
//        assertEquals(60, timer.getTimeRemain());
//
//        timer.resume();
//        timer.update(1f);
//        assertEquals(59, timer.getTimeRemain());
//    }
//
//
//    @Test
//    void testTimeDecrease() {
//        Timer timer = new Timer(60);
//        timer.start();
//        timer.update(10f);
//        assertEquals(50, timer.getTimeRemain());
//    }
//
//    @Test
//    void testTimeRemain() {
//        Timer timer = new Timer(60);
//        timer.start();
//        timer.update(15.3f);
//
//        assertEquals(45, timer.getTimeRemain());
//    }
//
//    @Test
//    void testIsFinish() {
//        Timer timer = new Timer(60);
//        timer.start();
//        timer.update(61f);
//
//        assertTrue(timer.isFinish());
//    }
//}
