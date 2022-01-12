import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class RateLimiter {

    @Test
    public void testRequestsWithOneMinutePassing() throws ParseException{
        assertEquals(checkRate(18,"12-01-2022 13:11:00","12-01-2022 13:12:00"),true);
    }
    @Test
    public void testRequestsWithOneMinuteFailing() throws ParseException{
        assertEquals(checkRate(28,"12-01-2022 13:11:00","12-01-2022 13:12:00"),false);
    }
    @Test
    public void testRequestsWithGreaterThanOneMinutePassing() throws ParseException{
        assertEquals(checkRate(36,"12-01-2022 13:11:00","12-01-2022 13:13:00"),true);
    }
    @Test
    public void testRequestsWithGreaterThanOneMinuteFailing() throws ParseException{
        assertEquals(checkRate(56,"12-01-2022 13:11:00","12-01-2022 13:13:00"),false);
    }
    boolean checkRate(int requests,String start,String end) throws ParseException {
        Date start_time=new SimpleDateFormat("DD-MM-YYYY HH:mm:ss").parse(start);
        Date end_time=new SimpleDateFormat("DD-MM-YYYY HH:mm:ss").parse(end);
        long start_time_in_seconds=start_time.getTime()/1000;
        long end_time_in_seconds=end_time.getTime()/1000;
        long diff_in_seconds=end_time_in_seconds-start_time_in_seconds;
        int rate = (int)((double)(requests*60)/(double)diff_in_seconds);
        if(rate<=20){
            System.out.println("OK : "+rate);
            return true;
        }
        else{
            System.out.println("Rate limited exceeded! : "+rate);
            return false;
        }
    }
}
