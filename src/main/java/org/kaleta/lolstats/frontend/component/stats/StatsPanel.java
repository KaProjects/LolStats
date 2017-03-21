package org.kaleta.lolstats.frontend.component.stats;

import org.kaleta.lolstats.backend.entity.Role;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Stanislav Kaleta
 * Date: 25.6.2015
 */
public class StatsPanel extends JPanel {
    private StatsRowPanel totalPanel;
    private RolePanel panelAdc;
    private RolePanel panelMid;
    private RolePanel panelSupp;
    private RolePanel panelJung;
    private RolePanel panelTop;
    public StatsPanel(){
        initComponents();
        update();
    }

    private void initComponents() {
        totalPanel = new StatsRowPanel();
        panelAdc = new RolePanel(Role.adc);
        panelMid = new RolePanel(Role.mid);
        panelSupp = new RolePanel(Role.support);
        panelJung = new RolePanel(Role.jungle);
        panelTop = new RolePanel(Role.top);

        JPanel panelSeparator = new JPanel();
        panelSeparator.setBackground(Color.BLACK);

        StatsRowPanel headerPanel = new StatsRowPanel();
        headerPanel.update();

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(headerPanel)
                .addComponent(panelSeparator)
                .addComponent(totalPanel)
                .addComponent(panelAdc)
                .addComponent(panelMid)
                .addComponent(panelSupp)
                .addComponent(panelJung)
                .addComponent(panelTop));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(headerPanel)
                .addComponent(panelSeparator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
                .addComponent(totalPanel)
                .addComponent(panelAdc)
                .addComponent(panelMid)
                .addComponent(panelSupp)
                .addComponent(panelJung)
                .addComponent(panelTop));
    }

    public void update(){
        List<Season.Game> gameList = DataSourceService.getAllGames();

        totalPanel.update(gameList);

        Map<String, List<Season.Game>> roleMap = new HashMap<>();
        roleMap.put(Role.adc.toString(), new ArrayList<>());
        roleMap.put(Role.mid.toString(), new ArrayList<>());
        roleMap.put(Role.support.toString(), new ArrayList<>());
        roleMap.put(Role.jungle.toString(), new ArrayList<>());
        roleMap.put(Role.top.toString(), new ArrayList<>());
        for (Season.Game game : gameList){
            roleMap.get(game.getUser().getRole()).add(game);
        }
        panelAdc.update(gameList.size(), roleMap.get(Role.adc.toString()));
        panelMid.update(gameList.size(), roleMap.get(Role.mid.toString()));
        panelSupp.update(gameList.size(), roleMap.get(Role.support.toString()));
        panelJung.update(gameList.size(), roleMap.get(Role.jungle.toString()));
        panelTop.update(gameList.size(), roleMap.get(Role.top.toString()));
    }

    private class RolePanel extends JPanel{
        private Role role;
        private StatsRowPanel roleHeaderPanel;
        private JPanel champsPanel;

        private RolePanel(Role role){
            this.role = role;
            JPanel panelSeparator = new JPanel();
            panelSeparator.setBackground(Color.BLACK);

            roleHeaderPanel= new StatsRowPanel();

            champsPanel = new JPanel();
            champsPanel.setLayout(new BoxLayout(champsPanel, BoxLayout.Y_AXIS));
            champsPanel.setVisible(false);

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

            GroupLayout layout = new GroupLayout(RolePanel.this);
            RolePanel.this.setLayout(layout);

            layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                    .addComponent(panelSeparator)
                    .addComponent(roleHeaderPanel)
                    .addComponent(panelToggle)
                    .addComponent(champsPanel));

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addComponent(panelSeparator, GroupLayout.PREFERRED_SIZE, 5, GroupLayout.PREFERRED_SIZE)
                    .addComponent(roleHeaderPanel)
                    .addComponent(panelToggle, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                    .addComponent(champsPanel));
        }

        private void update(int gamesTotal, List<Season.Game> gameList){
            roleHeaderPanel.update(role,gamesTotal,gameList);

            Map<String,List<Season.Game>> champsMap = new HashMap<>();

            for (Season.Game record : gameList){
                String champion = record.getUser().getChamp();
                if (champsMap.keySet().contains(champion)){
                    champsMap.get(champion).add(record);
                } else {
                    List<Season.Game> values = new ArrayList<>();
                    values.add(record);
                    champsMap.put(champion,values);
                }
            }

            List<StatsRowPanel> tempList = new ArrayList<>();
            for (String champion : champsMap.keySet()){
                StatsRowPanel statsRowPanel = new StatsRowPanel();
                statsRowPanel.update(champion, gameList.size(), champsMap.get(champion));
                tempList.add(statsRowPanel);
            }

            tempList.sort(roleHeaderPanel);

            champsPanel.removeAll();

            for (StatsRowPanel row : tempList){
                champsPanel.add(row);
            }
        }
    }
}
