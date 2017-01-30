package org.kaleta.lolstats.ex.graph.line.gui;

import org.kaleta.lolstats.ex.graph.line.GraphComponent;
import org.kaleta.lolstats.ex.graph.line.data.LineDataModel;

import javax.swing.*;

/**
 * User: Stanislav Kaleta
 * Date: 27.5.2015
 */
public interface LineGuiModel {

    /**
     *
     * @param lineDataModel
     * @param parent
     */
    public void init(LineDataModel lineDataModel, GraphComponent parent);

    /**
     *
     * @return
     */
    public JPanel getGraphBody();

    /**
     *
     * @return
     */
    public JPanel getGraphLogo();

    /**
     *
     * @return
     */
    public JPanel getHorizontalAxis();

    /**
     *
     * @return
     */
    public JPanel getVerticalAxis();

    /**
     *
     * @return
     */
    public int getAxisLabelSize();
}
