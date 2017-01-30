package org.kaleta.lolstats.ex.graph.line;

import org.kaleta.lolstats.ex.graph.line.data.LineDataModel;
import org.kaleta.lolstats.ex.graph.line.gui.LineGuiModel;

import javax.swing.*;
import java.awt.Dimension;

/**
 * User: Stanislav Kaleta
 * Date: 26.5.2015
 */
public class GraphComponent extends JPanel {
    private LineDataModel dataModel;
    private LineGuiModel guiModel;

    public GraphComponent(LineDataModel dataModel, LineGuiModel guiModel){
        this.dataModel = dataModel;
        this.guiModel = guiModel;
        this.guiModel.init(dataModel, this);
        initComponents();
    }

    private void initComponents() {
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(guiModel.getVerticalAxis())
                        .addComponent(guiModel.getGraphBody()))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(guiModel.getGraphLogo())
                        .addComponent(guiModel.getHorizontalAxis())));
        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(guiModel.getVerticalAxis())
                        .addComponent(guiModel.getGraphLogo()))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(guiModel.getGraphBody())
                        .addComponent(guiModel.getHorizontalAxis())));
    }

    public int getXBase(){
        int size = this.getSize().width - guiModel.getAxisLabelSize();
        int length = dataModel.getXSize();

        return (size > length) ? size/length : 1;
    }

    public int getYBase(){
        int size = this.getSize().height - guiModel.getAxisLabelSize();
        int length = dataModel.getYSize();

        return (size > length) ? size/length : 1;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(dataModel.getXSize() + guiModel.getAxisLabelSize() + 30,
                dataModel.getYSize() + guiModel.getAxisLabelSize() + 20);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(dataModel.getXSize() + guiModel.getAxisLabelSize() + 30,
                dataModel.getYSize() + guiModel.getAxisLabelSize() + 20);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(dataModel.getXSize() + guiModel.getAxisLabelSize() + 30,
                dataModel.getYSize() + guiModel.getAxisLabelSize() + 20);
    }
}



