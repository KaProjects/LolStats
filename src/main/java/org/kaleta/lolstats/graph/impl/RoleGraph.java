package org.kaleta.lolstats.graph.impl;

import org.kaleta.lolstats.entities.GameRecord;
import org.kaleta.lolstats.graph.Graph;
import org.kaleta.lolstats.graph.role.RolePanel;
import org.kaleta.lolstats.graph.role.RoleRowPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 11.6.2015
 */
public class RoleGraph implements Graph {

    private final List<GameRecord> records;

    public RoleGraph(List<GameRecord> records){
        this.records = records;

    }

    @Override
    public JComponent getGraphComponent() {
        JPanel rolesPanel = new JPanel();
        rolesPanel.setLayout(new BoxLayout(rolesPanel, BoxLayout.Y_AXIS));

        List<GameRecord> adcRecords = new ArrayList<>();
        List<GameRecord> supportRecords = new ArrayList<>();
        List<GameRecord> midRecords = new ArrayList<>();
        List<GameRecord> jungleRecords = new ArrayList<>();
        List<GameRecord> topRecords = new ArrayList<>();
        for (GameRecord record : records) {
            switch (record.getMyRole()) {
                case adc:
                    adcRecords.add(record);
                    break;
                case support:
                    supportRecords.add(record);
                    break;
                case mid:
                    midRecords.add(record);
                    break;
                case jungle:
                    jungleRecords.add(record);
                    break;
                case top:
                    topRecords.add(record);
                    break;
            }
        }
        List<List<GameRecord>> data = new ArrayList<>();
        data.add(adcRecords);
        data.add(supportRecords);
        data.add(midRecords);
        data.add(jungleRecords);
        data.add(topRecords);

        int gamesTotal = adcRecords.size() + supportRecords.size()
                + midRecords.size() + topRecords.size() + jungleRecords.size();
        for (List<GameRecord> roleData : data) {
            if (roleData.isEmpty()) {
                continue;
            }

            RolePanel panelRole = new RolePanel();
            panelRole.update(roleData, gamesTotal);
            rolesPanel.add(panelRole);
        }

        List<GameRecord> totalRecords = new ArrayList<>();
        totalRecords.addAll(adcRecords);
        totalRecords.addAll(supportRecords);
        totalRecords.addAll(midRecords);
        totalRecords.addAll(topRecords);
        totalRecords.addAll(jungleRecords);

        if (totalRecords.size() == 0){
            return null;
        }

        RoleRowPanel totalPanel = new RoleRowPanel();
        totalPanel.update(totalRecords);

        JPanel panelSeparator = new JPanel();
        panelSeparator.setBackground(Color.BLACK);

        RoleRowPanel headerPanel = new RoleRowPanel();
        headerPanel.update();

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(headerPanel)
                .addComponent(panelSeparator)
                .addComponent(totalPanel)
                .addComponent(rolesPanel));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(headerPanel)
                .addComponent(panelSeparator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
                .addComponent(totalPanel)
                .addComponent(rolesPanel));

        return panel;
    }

    @Override
    public JComponent getLegendComponent() {
        return null;
    }
}
