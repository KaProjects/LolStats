package org.kaleta.lolstats.feature.analytics.lp.graph;

import org.kaleta.lolstats.feature.analytics.GraphFrame;
import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.action.menu.MenuAction;

import java.awt.*;

/**
 * Created by Stanislav Kaleta on 02.04.2016.
 */
public class ShowLpGraph extends MenuAction{

    public ShowLpGraph(Configuration config) {
        super(config, "Items Line Graph");
    }

    @Override
    protected void actionPerformed() {
        LpGraphModel model = new LpGraphModel();

        LpGraphBackground background = new LpGraphBackground(model);
        LpGraphForeground foreground = new LpGraphForeground(model);
        LpGraphLegend legend = new LpGraphLegend(model);
        legend.setVisible(false);
        LpGraphOptions options = new LpGraphOptions(model);
        options.setVisible(false);

        GraphFrame graph = new GraphFrame(options,legend,background,foreground);
        model.setTarget(graph.getContentPane());
        graph.setTitle("Items Graph");
        graph.setLocationRelativeTo((Component) getConfiguration());

        /*TODO decide max or just fixed init size*/
        graph.setExtendedState(Frame.MAXIMIZED_BOTH);
        //graph.setSize(500,500);

        graph.setVisible(true);
    }
}
