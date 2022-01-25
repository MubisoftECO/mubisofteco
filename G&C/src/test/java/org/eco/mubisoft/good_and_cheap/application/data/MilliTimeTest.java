package org.eco.mubisoft.good_and_cheap.application.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class MilliTimeTest {

    private static final int INCREMENT = 60 * 1000;

    @Test
    void getTime() {
        assertEquals(MilliTime.FIVE_MINUTES.getTime(), 5 * INCREMENT);
        assertEquals(MilliTime.TEN_MINUTES.getTime(), 10 * INCREMENT);
        assertEquals(MilliTime.FIFTEEN_MINUTES.getTime(), 15 * INCREMENT);
        assertEquals(MilliTime.THIRTY_MINUTES.getTime(), 30 * INCREMENT);
        assertEquals(MilliTime.ONE_HOUR.getTime(), 60 * INCREMENT);
        assertEquals(MilliTime.SIX_HOUR.getTime(), 360 * INCREMENT);
    }

}