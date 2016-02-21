package org.kaleta.lolstats.graphs;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 19.1.2015
 */
@Deprecated
public class AttemptLPGraphData {
    private List<Integer> realXValues;
    private List<Integer> realYValues;

    private int yMinimum = -1;
    private int yMaximum = -1;
    /*TODO !!! every method to interface and impl them here*/
    public AttemptLPGraphData(List<Integer> realXValues, List<Integer> realYValues){/*TODO maybe exc. if size=0*/
        this.realXValues = realXValues;
        this.realYValues = realYValues;
    }
    /* <1,10> point will be represented as 100 pixels
    * <11,100> -||- as 10 pixels
    * <101,...) -||- as 1 pixel*/
    public int getXBase(){
        if (realXValues.size() <= 100){
            if (realXValues.size() <= 10){
                return 100;
            } else {
                return 10;
            }
        } else {
            return 1;
        }
    }
    public int getYBase(){/*Warning: using only after getMaxY() (or counting without rounding)*/
//        if ((yMaximum-yMinimum) <= 100){
//            if ((yMaximum-yMinimum) <= 10){
//                return 100;
//            } else {
//                return 10;
//            }
//        } else {
//            return 1;
//        }
        return 1;
    }
    /*Ker(x) = <1,MAX>
    * borderRounding - (border mod borderRounding) = 0
    * 1 means min,max values are borders (no rounding)
    * 10 means if 42 is min then 40 is border, etc.*/
    public int getMaxX(int borderRounding){
        int min = Short.MAX_VALUE;
        int max = 0;
        for (Integer xValue : realXValues){
            min = (xValue < min) ? xValue : min;
            max = (xValue > max) ? xValue : max;
        }
        min = (min/borderRounding);
        max = max/borderRounding;
        if ((max % borderRounding) != 0){
            max++;
        }
        return (max - min)*borderRounding;
    }
    /* Img(x) = <1,MAX>
    * borderRounding - (border mod borderRounding) = 0
    * 1 means min,max values are borders
    * 10 means if 42 is min then 40 is border, etc.*/
    public int getMaxY(int borderRounding){
        int min = Short.MAX_VALUE;
        int max = 0;
        for (Integer yValue : realYValues){
            min = (yValue < min) ? yValue : min;
            max = (yValue > max) ? yValue : max;
        }
        min = (min/borderRounding);
        max = max/borderRounding + 1;
        yMinimum = min*borderRounding;
        yMaximum = max*borderRounding;
        return yMaximum - yMinimum;
    }
    /* returns F(x), (x from Ker(x))*/
    public int getYValue(int x){
        return realYValues.get(x-1) - yMinimum;/*TODO ja mam od 1!!*/
    }
    /*returns X axis value as String if is defined, null otherwise
    * widestValueWidth means number of points between X axis values(including)
    * (because of overlapping numbers) a bit related to xBase (4digits needs cca 29pixels))  */
    public String getXAxisValue(int x, int widestValueWidth){
        int period = widestValueWidth/getXBase();
        period = (period == 0) ? 1 : period;

        String value = null;
        if (((x-1) % period) == 0){
            value = String.valueOf(realXValues.get(x-1));
        }
        return value;
    }
    /*returns Y axis value as String if is defined, null otherwise*/
    public String getYAxisValue(int y, int highestValueHigh){
        int period = highestValueHigh/getYBase();
        period = (period == 0) ? 1 : period;
        String value = null;
        if ((y % period) == 0){
            value = String.valueOf(yMinimum+y);
        }
        return value;
    }

    /*TODO maybe also legend (decide)*/
}
