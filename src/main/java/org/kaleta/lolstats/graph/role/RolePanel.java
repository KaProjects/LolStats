package org.kaleta.lolstats.graph.role;

import org.kaleta.lolstats.entities.Champion;
import org.kaleta.lolstats.entities.GameRecord;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 25.6.2015
 */
public class RolePanel extends JPanel {
    private RoleRowPanel rolePanel;
    private JPanel champsPanel;

    public RolePanel(){
        initComponents();
    }

    private void initComponents() {
        JPanel panelSeparator = new JPanel();
        panelSeparator.setBackground(Color.BLACK);

        rolePanel= new RoleRowPanel();

        JPanel panelToggle = new JPanel() {
            public boolean a = false;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponents(g);
                if (this.hasFocus()){
                    g.setColor(Color.GRAY.darker());
                } else {
                    g.setColor(Color.GRAY);
                }

                g.fillRect(0,0,this.getWidth(),this.getHeight());
                g.setColor(Color.BLACK);
                g.drawRect(0,0,this.getWidth() - 1, this.getHeight() - 1);
                g.setColor(Color.BLACK);
                int center = this.getWidth()/2;
                g.drawLine(center - 5,3,center + 5,3);
                g.drawLine(center - 5,6,center + 5,6);
            }
        };
        panelToggle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
                    champsPanel.setVisible(!champsPanel.isVisible());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JPanel)e.getSource()).requestFocus();
            }
        });
        panelToggle.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ((JPanel)e.getSource()).repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                ((JPanel)e.getSource()).repaint();
            }
        });

        champsPanel = new JPanel();
        champsPanel.setLayout(new BoxLayout(champsPanel, BoxLayout.Y_AXIS));
        champsPanel.setVisible(false);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(panelSeparator)
                .addComponent(rolePanel)
                .addComponent(panelToggle)
                .addComponent(champsPanel));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(panelSeparator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
                .addComponent(rolePanel)
                .addComponent(panelToggle, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addComponent(champsPanel));
    }

    public void update(List<GameRecord> recordList, int gamesTotal){
        rolePanel.update(recordList.get(0).getMyRole(),gamesTotal,recordList);

        Map<Champion,List<GameRecord>> champsMap = new HashMap<>();

        for (GameRecord record : recordList){
            Champion champion = record.getMyChampion();
            if (champsMap.keySet().contains(champion)){
                champsMap.get(champion).add(record);
            } else {
                List<GameRecord> values = new ArrayList<>();
                values.add(record);
                champsMap.put(champion,values);
            }
        }

        List<RoleRowPanel> tempList = new ArrayList<>();
        for (Champion champion : champsMap.keySet()){
            RoleRowPanel roleRowPanel = new RoleRowPanel();
            roleRowPanel.update(champion, recordList.size(), champsMap.get(champion));
            tempList.add(roleRowPanel);
        }

        Collections.sort(tempList,new RoleRowComparator());

        champsPanel.removeAll();

        for (RoleRowPanel row : tempList){
            champsPanel.add(row);
        }
    }
}
