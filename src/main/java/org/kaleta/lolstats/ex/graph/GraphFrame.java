package org.kaleta.lolstats.ex.graph;

import org.kaleta.lolstats.ex.entities.GameRecord;
import org.kaleta.lolstats.ex.graph.impl.LpPerDateGraph;
import org.kaleta.lolstats.ex.graph.impl.LpPerGameGraph;
import org.kaleta.lolstats.ex.graph.impl.RoleGraph;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 19.1.2015
 */
public class GraphFrame extends JFrame {
    private JComboBox<String> comboBoxGraphs;
    private JScrollPane scrollPaneGraph;
    private JScrollPane scrollPaneLegend;

    private List<GameRecord> records;

    public GraphFrame(List<GameRecord> records, String title) {
        this.records = records;
        this.setTitle("LoL Stats - Graphs - "+title);
        initComponents();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(700 + 25, 500 + 75));
    }

    private void initComponents() {
        comboBoxGraphs = new JComboBox<>(new String[]{"LP Graph (per game)", "LP Graph (per date)","Role Graph"});
        comboBoxGraphs.setSelectedIndex(-1);
        comboBoxGraphs.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                switch (comboBoxGraphs.getSelectedIndex()) {
                    case 0:
                        Graph g0 = new LpPerGameGraph(records);
                        scrollPaneGraph.setViewportView(g0.getGraphComponent());
                        scrollPaneLegend.setViewportView(g0.getLegendComponent());
                        break;
                    case 1:
                        Graph g1 = new LpPerDateGraph(records);
                        scrollPaneGraph.setViewportView(g1.getGraphComponent());
                        scrollPaneLegend.setViewportView(g1.getLegendComponent());
                        break;
                    case 2:
                        Graph g2 = new RoleGraph(records);
                        scrollPaneGraph.setViewportView(g2.getGraphComponent());
                        scrollPaneLegend.setViewportView(g2.getLegendComponent());
                        break;
                    default:
                        JPanel pG = new JPanel();
                        pG.setBackground(Color.YELLOW);
                        scrollPaneGraph.setViewportView(pG);
                        JPanel pL = new JPanel();
                        pL.setBackground(Color.CYAN);
                        scrollPaneLegend.setViewportView(pL);
                }
            }
        });
        scrollPaneGraph = new JScrollPane();
        scrollPaneGraph.setPreferredSize(new Dimension(500, 500)); /*TODO justify*/
        JPanel pG = new JPanel();
        pG.setBackground(Color.YELLOW);
        scrollPaneGraph.setViewportView(pG);
        scrollPaneLegend = new JScrollPane();
        scrollPaneLegend.setPreferredSize(new Dimension(200, 500)); /*TODO justify */
        JPanel pL = new JPanel();
        pL.setBackground(Color.CYAN);
        scrollPaneLegend.setViewportView(pL);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addComponent(comboBoxGraphs, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(scrollPaneGraph)
                        .addComponent(scrollPaneLegend)));
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGap(5)
                        .addComponent(comboBoxGraphs, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createSequentialGroup()
                        .addGap(5)
                        .addComponent(scrollPaneGraph)
                        .addComponent(scrollPaneLegend, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)));

    }


}
