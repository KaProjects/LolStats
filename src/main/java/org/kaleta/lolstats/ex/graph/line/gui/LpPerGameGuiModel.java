package org.kaleta.lolstats.ex.graph.line.gui;

import org.kaleta.lolstats.ex.graph.line.GraphComponent;
import org.kaleta.lolstats.ex.graph.line.data.LineDataModel;
import org.kaleta.lolstats.ex.graph.line.data.LpPerGameDataModel;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * User: Stanislav Kaleta
 * Date: 12.6.2015
 */
public class LpPerGameGuiModel implements LineGuiModel {
    private int axisLabelSize = 50;
    private JPanel verticalAxis;
    private JPanel horizontalAxis;
    private JPanel graphBody;
    private JPanel graphLogo;

    private LineDataModel model;
    private GraphComponent parent;

    @Override
    public void init(LineDataModel model, GraphComponent parent) {
        this.model = model;
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        horizontalAxis = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getSize().width, getSize().height);

                g.setColor(Color.BLACK);
                g.drawLine(0, 0, getSize().width - 1, 0);

                for (int i = 0; i < model.getXSize(); i++) {
                    String mark = model.getXMark(i);
                    if (mark != null){
                        g.drawLine(getXPoint(i), 0, getXPoint(i), 5);
                        g.drawString(mark,getXPoint(i),20);
                    }
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, axisLabelSize);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(super.getMinimumSize().width, axisLabelSize);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(super.getMaximumSize().width, axisLabelSize);
            }
        };

        verticalAxis = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0,0,getSize().width,getSize().height);

                g.setColor(Color.BLACK);
                g.drawLine(getSize().width - 1,0, getSize().width - 1, getSize().height -1);

                for (int i = 0; i < model.getYSize(); i++) {
                    String mark = model.getYMark(i);
                    if (mark != null){
                        int heightOffset = this.getSize().height - 1;
                        g.drawLine(getSize().width - 6, heightOffset - getYPoint(i), getSize().width - 1, heightOffset - getYPoint(i));
                        int offset = g.getFontMetrics().stringWidth(mark);
                        g.drawString(mark,getSize().width - 6 - offset,heightOffset - getYPoint(i));
                    }
                }
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(axisLabelSize, super.getPreferredSize().height);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(axisLabelSize, super.getMinimumSize().height);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(axisLabelSize, super.getMaximumSize().height);
            }
        };

        graphBody = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getSize().width, getSize().height);

                /*background*/
                for (int i = 0; i < model.getXSize(); i++) {
                    int value = model.getFunctionValue(i, 1);
                    switch (value){
                        case 0:
                            g.setColor(Color.getHSBColor(120/360f,0.1f,1f));
                            break;
                        case 1:
                            g.setColor(Color.getHSBColor(0f,0.1f,1f));
                            break;
                        case 2:
                            g.setColor(Color.getHSBColor(0f,0f,0.8f));
                            break;
                        default:
                            g.setColor(Color.BLACK);
                            break;
                    }
                    if (i != 0) {
                        int x = (i - 1) * parent.getXBase();
                        g.fillRect(x, this.getSize().height, parent.getXBase(), -model.getYSize());
                    }
                }

                /*division lines*/
                Color bronze = Color.getHSBColor(35/360f,0.96f,0.65f);
                Color silver = Color.getHSBColor(0f,0f,0.6f);
                Color gold = Color.getHSBColor(51/360f,1f,1f);
                Color platinum = Color.getHSBColor(180/360f,1f,0.8f);
                Color diamond = Color.getHSBColor(210/360f,0.5f,1f);
                int height = this.getSize().height;
                int width = this.getSize().width;
                for (int i=0;i<(model.getYSize());i++) {
                    if (i % 100 == 0) {
                        int division = (i+((LpPerGameDataModel)model).getYminimum())/100;
                        if (5 <= division && division < 10){
                            g.setColor(bronze);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                        }
                        if (division == 10){
                            g.setColor(bronze);
                            g.drawLine(0, height - 1 - i + 1, width - 1, height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                            g.setColor(silver);
                            g.drawLine(0, height - 1 - i - 1, width - 1, height - 1 - i - 1);
                        }
                        if (10 < division && division < 15){
                            g.setColor(Color.getHSBColor(0f,0f,0.6f));
                            g.drawLine(0, this.getSize().height - 1 - i, this.getSize().width - 1, this.getSize().height - 1 - i);
                        }
                        if (division == 15){
                            g.setColor(silver);
                            g.drawLine(0, height - 1 - i + 1, width - 1, height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                            g.setColor(gold);
                            g.drawLine(0, height - 1 - i - 1, width - 1, height - 1 - i - 1);
                        }
                        if (15 < division && division < 20){
                            g.setColor(gold);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                        }
                        if (division == 20){
                            g.setColor(gold);
                            g.drawLine(0, height - 1 - i + 1, width - 1, height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                            g.setColor(platinum);
                            g.drawLine(0, height - 1 - i - 1, width - 1, height - 1 - i - 1);
                        }
                        if (20 < division && division < 25){
                            g.setColor(platinum);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                        }
                        if (division == 25){
                            g.setColor(platinum);
                            g.drawLine(0, height - 1 - i + 1, width - 1, height - 1 - i + 1);
                            g.setColor(Color.BLACK);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                            g.setColor(diamond);
                            g.drawLine(0, height - 1 - i - 1, width - 1, height - 1 - i - 1);
                        }
                        if (25 < division && division < 30){
                            g.setColor(diamond);
                            g.drawLine(0, height - 1 - i, width - 1, height - 1 - i);
                        }
                    }
                }

                /*value lines*/
                g.setColor(Color.getHSBColor(230/360f,1f,1f));
                int lastYValue = -1;
                for (int i = 0; i < model.getXSize(); i++) {
                    Integer functionValue = model.getFunctionValue(i, 0);
                    if (functionValue == null){
                        continue;
                    }
                    int iYValue = (this.getSize().height - 1) - functionValue;
                    if (lastYValue > 0) {
                        int x = (i - 1) * parent.getXBase();
                        g.drawLine(x, lastYValue, x + parent.getXBase(), iYValue);
                    }
                    lastYValue = iYValue;
                }
            }
        };

        graphLogo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0,0,getSize().width,getSize().height);
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(axisLabelSize, axisLabelSize);
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(axisLabelSize, axisLabelSize);
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(axisLabelSize, axisLabelSize);
            }
        };
    }

    @Override
    public JPanel getGraphBody() {
        return graphBody;
    }

    @Override
    public JPanel getGraphLogo() {
        return graphLogo;
    }

    @Override
    public JPanel getHorizontalAxis() {
        return horizontalAxis;
    }

    @Override
    public JPanel getVerticalAxis() {
        return verticalAxis;
    }

    @Override
    public int getAxisLabelSize() {
        return axisLabelSize;
    }

    protected int getXPoint(int i){
        return i*parent.getXBase();
    }

    protected int getYPoint(int i){
        return i*parent.getYBase();
    }
}
