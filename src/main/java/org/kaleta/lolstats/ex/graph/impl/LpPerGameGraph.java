package org.kaleta.lolstats.ex.graph.impl;

import org.kaleta.lolstats.ex.entities.GameRecord;
import org.kaleta.lolstats.ex.graph.Graph;
import org.kaleta.lolstats.ex.graph.line.GraphComponent;
import org.kaleta.lolstats.ex.graph.line.data.LpPerGameDataModel;
import org.kaleta.lolstats.ex.graph.line.gui.LpPerGameGuiModel;

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
