package org.kaleta.lolstats.ex.entities;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 1.4.2015
 */
public class Date {
    private int day;
    private int month;
    private int year;

    public Date(){
        this.day = -1;
        this.month = -1;
        this.year = -1;
    }

    public Date(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String getStringDate(){
        if (day < 0 || month < 0 || year < 0){
            return "-";
        } else {
            return String.format("%02d", day)+String.format("%02d", month)+String.format("%04d", year);
        }
    }

    public void setStringDate(String date){
        if (date.length() == 8) {
            day = Integer.parseInt(date.substring(0, 2));
            month = Integer.parseInt(date.substring(2, 4));
            year = Integer.parseInt(date.substring(4));
        }
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Date date = (Date) o;

        if (day != date.day) return false;
        if (month != date.month) return false;
        if (year != date.year) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = day;
        result = 31 * result + month;
        result = 31 * result + year;
        return result;
    }
}
