package org.kaleta.lolstats.ex.graph.role;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;

/**
 * User: Stanislav Kaleta
 * Date: 25.6.2015
 */
public class PerformanceBar extends JPanel {

    /**
     * Value of performanceBar. Have to be in interval <0,100>.
     */
    private int value;


    public PerformanceBar(){
        value = 0;
    }

    public void update(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponents(g);

        if (value >=0 && value <=100){
            g.setColor(Color.BLACK);
            g.fillRect(5,5,this.getWidth()-10,this.getHeight()-10);

            g.setColor(Color.RED);
            g.fillRect(10,10,this.getWidth()-20,this.getHeight()-20);

            g.setColor(Color.GREEN);
            int barWidth = (int) ((this.getWidth()-20)*((float) value/100));
            g.fillRect(10,10,barWidth,this.getHeight()-20);
        }
    }
}
