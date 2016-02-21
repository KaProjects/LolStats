package org.kaleta.lolstats.ex.graph.line;

import org.kaleta.lolstats.ex.graph.line.legend.LegendModel;

import javax.swing.*;

/**
 * User: Stanislav Kaleta
 * Date: 12.6.2015
 */
public class LegendComponent extends JPanel {
    private LegendModel model;

    public LegendComponent(LegendModel model){
        this.model = model;
    }
}
