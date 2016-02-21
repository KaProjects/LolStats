package org.kaleta.lolstats.graphs;

import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 28.1.2015
 */
@Deprecated
public interface LineGraphData {

    /**
     * Counting base for X axis. It means that how many pixel are between two points.(including second point)
     * For example, value 10 means that every 10th pixel, there will be a point, i.e. at pixels 0,10,20,30,...
     *
     * @return value of x axis base.
     */
    public int getXBase();
    /**
     * Counting base for Y axis. It means that how many pixel are between two points.(including second point)
     * For example, value 10 means that every 10th pixel, there will be a point, i.e. at pixels 0,10,20,30,...
     *
     * @return value of y axis base.
     */
    public int getYBase();
    /*TODO maybe also X border rounding, but i thinks its not needed (if yes -> also apply in xIntervalWidth(see Y))*/
    /**
     * Interval width is number of values used in it.
     *
     * @return width of the x interval.
     */
    public int getXIntervalWidth();
    /**
     * Interval minimum is value of left border, i.e. first value in interval.
     *
     * @return minimum of the x interval.
     */
    public int getXIntervalMinimum();
    /**
     * Border rounding is value that rounds interval borders.
     * For example, if your values are between 12 and 27, you would prefer see y interval from 10 to 30,
     * thus you need to choose border value 10.
     *
     * @return value of border rounding at y axis.
     */
    public int getYBorderRounding();
    /**
     * Interval width is number of values used in it.
     * Warning: do not forget about borders rounding.
     *
     * @return width of the y interval.
     */
    public int getYIntervalWidth();
    /**
     * Interval minimum is value of left border, i.e. first value in interval.
     * Warning: do not forget about borders rounding.
     *
     * @return minimum of the y interval.
     */
    public int getYIntervalMinimum();
    /**
     * Returns normalized y value from normalized x, e.i. y = F(x + minimum_of_x_interval) - minimum_of_y_interval.
     *
     * @param x Value for which y value will be count.
     * @return value of normalized y.
     */
    public int getYValue(int x);
    /**
     * For specific x returns color of its background, i.e. color for whole column related with x.
     *
     * @param x Number of column
     * @return color for background if specified, null otherwise
     */
    public Color getBackgroundColor(int x);
    /**
     * For normalized value of x, method returns its real value, for x axis purposes.
     * Warning: do not forget that values can overlap, thus use null in this cases.
     * Warning: do not forget that its a bit related to x axis base.
     *
     * @param x Normalized value for which value will be count.
     * @return x axis value if its possible to show it, null otherwise.
     */
    public String getXAxisValue(int x);
    /**
     * For normalized value of y, method returns its real value, for y axis purposes.
     * Warning: do not forget that values can overlap, thus use null in this cases.
     * Warning: do not forget that its a bit related to y axis base.
     *
     * @param y Normalized value for which value will be count.
     * @return y axis value if its possible to show it, null otherwise.
     */
    public String getYAxisValue(int y);
}
