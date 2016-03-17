package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ema on 07/03/16.
 */
public class UtilTest {

    @Test
    public void testComputeDate() throws Exception {
        String dateString = "1998-05-15";
        assertEquals(Util.computeDate(dateString).toString(), dateString);

        dateString = "1678-09-28";
        assertEquals(Util.computeDate(dateString).toString(), dateString);
    }

}