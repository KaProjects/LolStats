package org.kaleta.lolstats.graphs;

import org.kaleta.lolstats.graph.Graph;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 28.1.2015
 */
@Deprecated
public class LPGraph implements Graph {
    private final LineGraphData graphData;

    public LPGraph(LineGraphData graphData){
        this.graphData = graphData;
        

    }

    @Override
    public JComponent getGraphComponent() {
        final int graphBodyWidth = (graphData.getXIntervalWidth()-1)*graphData.getXBase() + 1;
        final int graphBodyHeight = (graphData.getYIntervalWidth()-1)*graphData.getYBase() + 1;

        JPanel panelXAxis = new JPanel(){
            private Dimension size = new Dimension(graphBodyWidth + LPGraphData.X_FONT_METRICS,40);
            private int xAlignment = LPGraphData.X_FONT_METRICS/2;
//            @Override
//            public Color getBackground() {
//                return Color.CYAN;
//            }

            @Override
              public boolean isOpaque() {
                return false;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(0+xAlignment,0,size.width-1-xAlignment,0);

                for(int i=0;i<graphData.getXIntervalWidth();i++){
                    String value = graphData.getXAxisValue(i);
                    if (value != null) {
                        int alignment = g.getFontMetrics().stringWidth(value)/2;
                        g.setColor(Color.BLACK);
                        int x = i*graphData.getXBase() + xAlignment;
                        g.drawLine(x,0,x,5);
                        g.drawString(value,x-alignment,20);
                    }
                }
            }
            @Override
            public Dimension getPreferredSize() {
                return size;
            }
            @Override
            public Dimension getMaximumSize() {
                return size;
            }
            @Override
            public Dimension getMinimumSize() {
                return size;
            }
        };

        JPanel panelYAxis = new JPanel(){
            private Dimension size = new Dimension(40,graphBodyHeight + LPGraphData.Y_FONT_METRICS);
            private int yAlignment = LPGraphData.Y_FONT_METRICS/2;
//            @Override
//            public Color getBackground() {
//                return Color.YELLOW;
//            }

            @Override
            public boolean isOpaque() {
                return false;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawLine(size.width-1,0+yAlignment,size.width-1,size.height-1-yAlignment);

                for (int i=0;i<graphData.getYIntervalWidth();i++){
                    String value = graphData.getYAxisValue(i);
                    if (value != null) {
                        g.setColor(Color.BLACK);
                        int y = size.height-1 - yAlignment - i;
                        g.drawLine(size.width-1 - 5, y, size.width-1, y);
                        g.drawString(value, 5, y + 5);
                    }
                }
            }
            @Override
            public Dimension getPreferredSize() {
                return size;
            }
            @Override
            public Dimension getMaximumSize() {
                return size;
            }
            @Override
            public Dimension getMinimumSize() {
                return size;
            }
        };

        JPanel panelGraphBody = new JPanel(){
            private Dimension size = new Dimension(graphBodyWidth,graphBodyHeight);

            @Override
            public Color getBackground() {
                return Color.LIGHT_GRAY;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                /*background*/
                for (int i=0;i<(graphData.getXIntervalWidth());i++) {
                    g.setColor(graphData.getBackgroundColor(i));
                    if (i != 0) {
                        int x = (i - 1) * graphData.getXBase();
                        g.fillRect(x,0, x + graphData.getXBase(), size.height);
                    }


                }
                /*division lines*/
                Color bronze = Color.getHSBColor(35/360f,0.96f,0.65f);
                Color silver = Color.getHSBColor(0f,0f,0.6f);
                Color gold = Color.getHSBColor(51/360f,1f,1f);
                Color platinum = Color.getHSBColor(180/360f,1f,0.8f);
                Color diamond = Color.getHSBColor(210/360f,0.5f,1f);
                for (int i=0;i<(graphData.getYIntervalWidth());i++) {
                    if (i % 100 == 0) {
                        int division = (i+graphData.getYIntervalMinimum())/100;
                        if (5 <= division && division < 10){
                            g.setColor(bronze);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                        }
                        if (division == 10){
                            g.setColor(bronze);
                            g.drawLine(0, size.height - 1 - i + 1, size.width - 1, size.height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                            g.setColor(silver);
                            g.drawLine(0, size.height - 1 - i - 1, size.width - 1, size.height - 1 - i - 1);
                        }
                        if (10 < division && division < 15){
                            g.setColor(Color.getHSBColor(0f,0f,0.6f));
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                        }
                        if (division == 15){
                            g.setColor(silver);
                            g.drawLine(0, size.height - 1 - i + 1, size.width - 1, size.height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                            g.setColor(gold);
                            g.drawLine(0, size.height - 1 - i - 1, size.width - 1, size.height - 1 - i - 1);
                        }
                        if (15 < division && division < 20){
                            g.setColor(gold);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                        }
                        if (division == 20){
                            g.setColor(gold);
                            g.drawLine(0, size.height - 1 - i + 1, size.width - 1, size.height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                            g.setColor(platinum);
                            g.drawLine(0, size.height - 1 - i - 1, size.width - 1, size.height - 1 - i - 1);
                        }
                        if (20 < division && division < 25){
                            g.setColor(platinum);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                        }
                        if (division == 25){
                            g.setColor(platinum);
                            g.drawLine(0, size.height - 1 - i + 1, size.width - 1, size.height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                            g.setColor(diamond);
                            g.drawLine(0, size.height - 1 - i - 1, size.width - 1, size.height - 1 - i - 1);
                        }
                        if (25 < division && division < 30){
                            g.setColor(diamond);
                            g.drawLine(0, size.height - 1 - i, size.width - 1, size.height - 1 - i);
                        }
                    }
                }
                /*value lines*/
                g.setColor(Color.getHSBColor(230/360f,1f,1f));
                int lastYValue = -1;
                for (int i = 0; i < graphData.getXIntervalWidth(); i++) {
                    int iYValue = (size.height - 1) - graphData.getYValue(i);
                    if (i != 0) {
                        int x = (i - 1) * graphData.getXBase();
                        g.drawLine(x, lastYValue, x + graphData.getXBase(), iYValue);
                    }
                    lastYValue = iYValue;
                }

            }
            @Override
            public Dimension getPreferredSize() {
                return size;
            }
            @Override
            public Dimension getMaximumSize() {
                return size;
            }
            @Override
            public Dimension getMinimumSize() {
                return size;
            }
        };

        JPanel graph = new JPanel(){
            private Dimension size = new Dimension(graphBodyWidth+40+10,graphBodyHeight+40+10);
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0,0,size.width,size.height);
            }
            @Override
            public Dimension getMinimumSize() {
                return size;
            }
            @Override
            public Dimension getMaximumSize() {
                return size;
            }
            @Override
            public Dimension getPreferredSize() {
                return size;
            }
        };

        SpringLayout layout = new SpringLayout();
        graph.setLayout(layout);
        graph.add(panelXAxis);
        graph.add(panelYAxis);
        graph.add(panelGraphBody);
        layout.putConstraint(SpringLayout.NORTH,panelYAxis,10-(LPGraphData.Y_FONT_METRICS/2),SpringLayout.NORTH,graph);
        layout.putConstraint(SpringLayout.WEST,panelYAxis,0,SpringLayout.WEST,graph);
        layout.putConstraint(SpringLayout.NORTH,panelGraphBody,10,SpringLayout.NORTH,graph);
        layout.putConstraint(SpringLayout.WEST,panelGraphBody,0,SpringLayout.EAST,panelYAxis);
        layout.putConstraint(SpringLayout.NORTH,panelXAxis,0,SpringLayout.SOUTH,panelGraphBody);
        layout.putConstraint(SpringLayout.WEST,panelXAxis,0-(LPGraphData.X_FONT_METRICS/2),SpringLayout.WEST,panelGraphBody);

        return graph;
    }

    @Override
    public JComponent getLegendComponent() {
        return null;
    }
}
