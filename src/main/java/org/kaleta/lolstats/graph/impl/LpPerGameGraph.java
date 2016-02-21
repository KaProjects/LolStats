package org.kaleta.lolstats.graph.impl;

import org.kaleta.lolstats.entities.GameRecord;
import org.kaleta.lolstats.graph.Graph;
import org.kaleta.lolstats.graph.line.GraphComponent;
import org.kaleta.lolstats.graph.line.data.LpPerGameDataModel;
import org.kaleta.lolstats.graph.line.gui.LpPerGameGuiModel;

import javax.swing.*;
import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 12.6.2015
 */
public class LpPerGameGraph implements Graph {
    private List<GameRecord> records;

    public LpPerGameGraph(List<GameRecord> records){
        this.records = records;
    }

    @Override
    public JComponent getGraphComponent() {
        LpPerGameDataModel dataModel = new LpPerGameDataModel();
        dataModel.initData(records);
        LpPerGameGuiModel guiModel = new LpPerGameGuiModel();
        GraphComponent component = new GraphComponent(dataModel,guiModel);
        guiModel.init(dataModel,component);
        return component;
    }

    @Override
    public JComponent getLegendComponent() {
        return null;
    }
}
