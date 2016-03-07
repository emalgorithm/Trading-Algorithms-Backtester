package portfolio;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import util.Util;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by ema on 24/02/16.
 */
public interface AssetInterfaceTest {

    @Test
    public void testToString() throws Exception;


    @Test
    public void testGetPreviousTradingDate() throws Exception;


    @Test
    public void testGetNextTradingDate() throws Exception;


    @Test
    public void testGetPreviousOrCurrentTradingDate() throws Exception;


    @Test
    public void testGetNextOrCurrentTradingDate() throws Exception;


    @Test
    public void testGetClosingPrice() throws Exception;


    @Test
    public void testIsTradingDate() throws Exception;

}