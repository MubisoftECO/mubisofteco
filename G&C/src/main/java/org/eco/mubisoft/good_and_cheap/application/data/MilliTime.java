package org.eco.mubisoft.good_and_cheap.application.data;

/**
 * <p><b>Time in milliseconds</b></p>
 * <p>Enum that contains different time values in milliseconds.</p>
 */
public enum MilliTime {

    FIVE_MINUTES(5),
    TEN_MINUTES(10),
    FIFTEEN_MINUTES(15),
    THIRTY_MINUTES(30),
    ONE_HOUR(60);

    public int time;

    MilliTime(int time) {
        this.time = time * 60 * 1000;
    }
}
