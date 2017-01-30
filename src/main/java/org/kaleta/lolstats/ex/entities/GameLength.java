package org.kaleta.lolstats.ex.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 1.4.2015
 */
public class GameLength {
    private int minutes;
    private int seconds;

    public GameLength() {
        this.minutes = -1;
        this.seconds = -1;
    }

    public GameLength(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }

    public String getStringGameLength(boolean onlyMins){
        return (onlyMins) ? String.format("%02d", minutes) : String.format("%02d", minutes)+String.format("%02d", seconds);
    }

    public void setStringGameLength(String length, boolean onlyMins){
        if (onlyMins){
            minutes = Integer.parseInt(length);
        } else {
            /*TODO*/
        }

    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }
}
