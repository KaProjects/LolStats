package org.kaleta.lolstats.graphs;

import org.kaleta.lolstats.entities.GameRecord;
import org.kaleta.lolstats.entities.Result;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 28.1.2015
 */
@Deprecated
public class LPGraphData implements LineGraphData {
    public static final int X_FONT_METRICS = 40; // 28 font metrics width of "0000" + 12 gaps
    public static final int Y_FONT_METRICS = 100; // only division marking
    private List<Integer> xValues = new ArrayList<>();
    private List<Integer> yValues = new ArrayList<>();
    private List<Result> rValues = new ArrayList<>();

    public LPGraphData(List<GameRecord> recordList){
        int i = 0;
        for (GameRecord record : recordList){
            xValues.add(i, record.getGameNumber());
            yValues.add(i, Integer.valueOf(record.getRank().getStringOffsetLP()));
            rValues.add(i, record.getGameResult());
            i++;
        }
    }

    @Override
    public int getXBase() {
//        if (xValues.size() <= 750){
//            if (xValues.size() <= 500){
//                if (xValues.size() <= 250){
//                    return 4;
//                } else {
//                    return 3;
//                }
//            } else {
//                return 2;
//            }
//        } else {
//            return 1;
//        }
    return 3;

    }

    @Override
    public int getYBase() {
        return 1;
    }

    @Override
    public int getXIntervalWidth() {
        int min = Short.MAX_VALUE;
        int max = 0;
        for (Integer xValue : xValues){
            min = (xValue < min) ? xValue : min;
            max = (xValue > max) ? xValue : max;
        }
        return max - min + 1;
    }

    @Override
    public int getXIntervalMinimum() {
        int min = Short.MAX_VALUE;
        for (Integer xValue : xValues){
            min = (xValue < min) ? xValue : min;
        }
        return min;
    }

    @Override
    public int getYBorderRounding() {
        return 100;
    }

    @Override
    public int getYIntervalWidth() {
        int min = Short.MAX_VALUE;
        int max = 0;
        for (Integer yValue : yValues){
            min = (yValue < min) ? yValue : min;
            max = (yValue > max) ? yValue : max;
        }
        min = (min/getYBorderRounding());
        int tempMaxRemainder = max % getYBorderRounding();
        max = max/getYBorderRounding();
        if (tempMaxRemainder != 0){
            max++;
        }
        return (max - min)*getYBorderRounding() + 1;
    }

    @Override
    public int getYIntervalMinimum() {
        int min = Short.MAX_VALUE;
        for (Integer yValue : yValues){
            min = (yValue < min) ? yValue : min;
        }
        return (min/getYBorderRounding())*getYBorderRounding();
    }

    @Override
    public int getYValue(int x) {
        return yValues.get(x) - getYIntervalMinimum();
    }

    @Override
    public Color getBackgroundColor(int x) {
        switch (rValues.get(x)){
            case Victory:
                return Color.getHSBColor(120/360f,0.1f,1f);
            case Defeat:
                return Color.getHSBColor(0f,0.1f,1f);
            case LossPrevented:
                return Color.getHSBColor(0f,0f,0.8f);
            default:
                return null;
        }
    }

    @Override
    public String getXAxisValue(int x) {
        int period = X_FONT_METRICS/getXBase();
        period = (period == 0) ? 1 : period;

        String value = null;
        if ((x % period) == 0){
            value = String.valueOf(xValues.get(x));
        }
        return value;
    }

    @Override
    public String getYAxisValue(int y) {
        String value = null;
        if ((y % Y_FONT_METRICS) == 0){
            value = String.valueOf(getYIntervalMinimum()+y);
        }
        return value;
    }
}
