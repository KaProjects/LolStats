package org.kaleta.lolstats.graph.line;

import org.kaleta.lolstats.graph.line.legend.LegendModel;


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
