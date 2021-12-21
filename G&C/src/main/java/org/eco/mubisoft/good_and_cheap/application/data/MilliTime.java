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

    /**
     * Time in milliseconds.
     */
    public int time;

    /**
     * <p>Create a new instance of a MilliTime enum value. Set the time in milliseconds
     * of the provided value.</p>
     * @param time Time in minutes.
     */
    MilliTime(int time) {
        this.time = time * 60 * 1000;
    }
}
