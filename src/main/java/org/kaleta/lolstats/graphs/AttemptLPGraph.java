package org.kaleta.lolstats.graphs;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 19.1.2015
 */
@Deprecated
public class AttemptLPGraph extends JPanel{
    private AttemptLPGraphData data;
    private Dimension size = new Dimension(0,0);
    private final int xBorderRounding = 1;// no rounding
    private final int yBorderRounding = 100;// division rounding
    private final int xStringMetricsMax;

    public AttemptLPGraph(AttemptLPGraphData data){
        this.data = data;

        size = new Dimension(40 + data.getMaxX(xBorderRounding)*data.getXBase() + 10,
                40 + data.getMaxY(yBorderRounding)*data.getYBase() + 10);

        xStringMetricsMax = 28 + 12; // font metrics width of "0000"
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, size.width, size.height);
        g.setColor(Color.BLACK);
        g.drawLine(40,10-5,40,size.height-40+5);
        g.drawLine(40-5,size.height -40,size.width-10+5,size.height-40);

        for(int i=1;i<=data.getMaxX(xBorderRounding);i++){
            String value = data.getXAxisValue(i, xStringMetricsMax);
            if (value != null) {
                g.setColor(Color.BLACK);
                int x = 40 + 1 + (i-1)*data.getXBase();
                g.drawLine(x,size.height-40,x,size.height-40+5);
                g.drawString(value,x-10,size.height-20);
            }
        }

        for (int i=1;i<=data.getMaxY(yBorderRounding);i++){
            String value = data.getYAxisValue(i,100/*only division marking*/);
            if (value != null) {
                g.setColor(Color.GRAY);/*TODO depends of division*/
                int y = size.height - 40 - i;
                g.drawLine(40 - 5, y, size.width - 10 + 5, y);
                g.setColor(Color.BLACK);
                g.drawString(value, 5, y + 5);
            }
        }

        g.setColor(Color.RED);
        int lastYValue = -1;
        for(int i=1;i<=data.getMaxX(xBorderRounding);i++) {
            int iYValue = (size.height-40) - data.getYValue(i);
            int x = 40 + 1 + (i - 2) * data.getXBase();
            if (i == 1) {
                lastYValue = iYValue;
                g.drawLine(x, lastYValue, x+5, iYValue);
            } else {
                g.drawLine(x, lastYValue, x + (data.getXBase()-1), iYValue);
            }


            lastYValue = iYValue;
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }
}
