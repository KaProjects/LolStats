package org.kaleta.lolstats.ex.graph.role;

import org.kaleta.lolstats.ex.entities.Champion;
import org.kaleta.lolstats.ex.entities.GameRecord;
import org.kaleta.lolstats.ex.entities.Result;
import org.kaleta.lolstats.ex.entities.Role;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 4.7.2015
 */
public class RoleDevelopmentPanel extends JPanel {
    private ChampIcon panelIcon;
    private JLabel labelName;
    private JComboBox<String> comboBoxGraphs;
    private JPanel panelInfo;
    private JScrollPane scrollPaneGraph;

    private List<RoleDevelGraphData> datas;

    public RoleDevelopmentPanel(){
        datas = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        panelIcon = new ChampIcon();
        labelName = new JLabel("---");
        comboBoxGraphs = new JComboBox<>(new String[]{"Performance", "Stats", "Experience"});
        comboBoxGraphs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = comboBoxGraphs.getSelectedIndex();
                if (index == 0) {
                    JPanel graph = new JPanel(){
                        private int columnWidth = 15;
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);

                            int scoreFarmPointsHeight = 200;
                            int expQuantifHeight = 200;
                            for (int i=0;i<datas.size();i++){
                                RoleDevelGraphData data = datas.get(i);
                                g.setColor(Color.getHSBColor(0/360f,0.25f,0.9f));
                                g.fillRect(i*columnWidth,200,columnWidth,-200);
                                g.setColor(Color.getHSBColor(120/360f,0.25f,0.9f));
                                g.fillRect(i*columnWidth,200,columnWidth,-2*data.getPerformancePoints());

                                g.setColor(Color.getHSBColor(195 / 360f, 1f, 0.75f));
                                int newScoreFarmPointsHeight = 200 - 10*(data.getScorePoints() + data.getFarmPoints());
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,scoreFarmPointsHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newScoreFarmPointsHeight);
                                }
                                scoreFarmPointsHeight = newScoreFarmPointsHeight;

                                g.setColor(Color.getHSBColor(285/360f,1f,0.75f));
                                int newExpQuantifHeight = 200 - (int)(data.getExperienceQuantif()*20);
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,expQuantifHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newExpQuantifHeight);
                                }
                                expQuantifHeight = newExpQuantifHeight;

                                ChampIcon icon = new ChampIcon();
                                icon.update(data.getLastChampion());

                                g.drawImage(icon.getIcon(),i*columnWidth,201,columnWidth,columnWidth,this);
                            }
                        }
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }

                        @Override
                        public Dimension getMinimumSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }

                        @Override
                        public Dimension getMaximumSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }
                    };

                    scrollPaneGraph.setViewportView(graph);

                    panelInfo.removeAll();
                    panelInfo.setLayout(new FlowLayout(FlowLayout.LEADING));

                    JPanel panelInfo1Icon = new JPanel();
                    panelInfo1Icon.setBackground(Color.getHSBColor(120/360f,0.25f,0.9f));
                    panelInfo1Icon.setSize(20, 20);
                    panelInfo1Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo1Icon);
                    panelInfo.add(new JLabel("Total <0,100>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo2Icon = new JPanel();
                    panelInfo2Icon.setBackground(Color.getHSBColor(195 / 360f, 1f, 0.75f));
                    panelInfo2Icon.setSize(20,20);
                    panelInfo2Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo2Icon);
                    panelInfo.add(new JLabel("Stats value (base) <0,20>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo3Icon = new JPanel();
                    panelInfo3Icon.setBackground(Color.getHSBColor(285/360f,1f,0.75f));
                    panelInfo3Icon.setSize(20,20);
                    panelInfo3Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo3Icon);
                    panelInfo.add(new JLabel("Experience value (multiplier) <0,10>"));

                    panelInfo.revalidate();
                    panelInfo.repaint();
                }
                if (index == 1) {
                    JPanel graph = new JPanel(){
                        private int columnWidth = 15;
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);

                            int killsHeight = 200;
                            int deathsHeight = 200;
                            int assistsHeight = 200;
                            int csHeight = 200;
                            int goldHeight = 200;
                            for (int i=0;i<datas.size();i++) {
                                RoleDevelGraphData data = datas.get(i);
                                g.setColor(Color.getHSBColor(195 / 360f, 0.05f,1f));
                                g.fillRect(i * columnWidth, 200, columnWidth, -200);
                                g.setColor(Color.getHSBColor(180 / 360f, 0.25f,0.9f));
                                g.fillRect(i * columnWidth, 200, columnWidth, -10 * data.getScorePoints());

                                if (data.getFarmPoints() < 0){
                                    // negative farm points will just remove previous score rect
                                    g.setColor(Color.getHSBColor(195 / 360f, 0.05f,1f));
                                    g.fillRect(i * columnWidth, 200-10 * data.getScorePoints(), columnWidth, -10 * data.getFarmPoints());
                                } else {
                                    // normal farm points rect
                                    g.setColor(Color.getHSBColor(210 / 360f, 0.25f,0.9f));
                                    g.fillRect(i * columnWidth, 200-10 * data.getScorePoints(), columnWidth, -10 * data.getFarmPoints());
                                }

                                g.setColor(Color.getHSBColor(180 / 360f, 1f,0.75f));
                                int newKillsHeight = 200 - (int) (data.getKillsAvg()*7);
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,killsHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newKillsHeight);
                                }
                                killsHeight = newKillsHeight;

                                g.setColor(Color.getHSBColor(120 / 360f, 1f,0.75f));
                                int newDeathsHeight = 200 - (int) (data.getDeathsAvg()*7);
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,deathsHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newDeathsHeight);
                                }
                                deathsHeight = newDeathsHeight;

                                g.setColor(Color.getHSBColor(60 / 360f, 1f,0.75f));
                                int newAssistsHeight = 200 - (int) (data.getAssistsAvg()*7);
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,assistsHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newAssistsHeight);
                                }
                                assistsHeight = newAssistsHeight;

                                g.setColor(Color.getHSBColor(210 / 360f, 1f,0.75f));
                                int newCsHeight = 200 - (int) (data.getCsAvg()*0.5);
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,csHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newCsHeight);
                                }
                                csHeight = newCsHeight;

                                g.setColor(Color.getHSBColor(270 / 360f, 1f,0.75f));
                                int newGoldHeight = 200 - (int) (data.getGoldAvg()*0.01);
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,goldHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newGoldHeight);
                                }
                                goldHeight = newGoldHeight;

                                ChampIcon icon = new ChampIcon();
                                icon.update(data.getLastChampion());

                                g.drawImage(icon.getIcon(),i*columnWidth,201,columnWidth,columnWidth,this);
                            }

                        }
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }

                        @Override
                        public Dimension getMinimumSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }

                        @Override
                        public Dimension getMaximumSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }
                    };

                    scrollPaneGraph.setViewportView(graph);

                    panelInfo.removeAll();
                    panelInfo.setLayout(new FlowLayout(FlowLayout.LEADING));

                    JPanel panelInfo1Icon = new JPanel();
                    panelInfo1Icon.setBackground(Color.getHSBColor(180 / 360f, 0.25f,0.9f));
                    panelInfo1Icon.setSize(20, 20);
                    panelInfo1Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo1Icon);
                    panelInfo.add(new JLabel("Score part value <0,20>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo11Icon = new JPanel();
                    panelInfo11Icon.setBackground(Color.getHSBColor(180 / 360f, 1f,0.75f));
                    panelInfo11Icon.setSize(20,20);
                    panelInfo11Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo11Icon);
                    panelInfo.add(new JLabel("K <0,28>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo12Icon = new JPanel();
                    panelInfo12Icon.setBackground(Color.getHSBColor(120 / 360f, 1f,0.75f));
                    panelInfo12Icon.setSize(20,20);
                    panelInfo12Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo12Icon);
                    panelInfo.add(new JLabel("D  <0,28>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo13Icon = new JPanel();
                    panelInfo13Icon.setBackground(Color.getHSBColor(60 / 360f, 1f,0.75f));
                    panelInfo13Icon.setSize(20,20);
                    panelInfo13Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo13Icon);
                    panelInfo.add(new JLabel("A  <0,28>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo2Icon = new JPanel();
                    panelInfo2Icon.setBackground(Color.getHSBColor(210 / 360f, 0.25f,0.9f));
                    panelInfo2Icon.setSize(20,20);
                    panelInfo2Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo2Icon);
                    panelInfo.add(new JLabel("Farm part value <0,20>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo21Icon = new JPanel();
                    panelInfo21Icon.setBackground(Color.getHSBColor(210 / 360f, 1f,0.75f));
                    panelInfo21Icon.setSize(20,20);
                    panelInfo21Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo21Icon);
                    panelInfo.add(new JLabel("CS  <0,400>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo22Icon = new JPanel();
                    panelInfo22Icon.setBackground(Color.getHSBColor(270 / 360f, 1f,0.75f));
                    panelInfo22Icon.setSize(20,20);
                    panelInfo22Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo22Icon);
                    panelInfo.add(new JLabel("Gold  <0,20k>"));

                    panelInfo.revalidate();
                    panelInfo.repaint();
                }
                if (index == 2) {
                    JPanel graph = new JPanel(){
                        private int columnWidth = 15;
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);

                            int winRatioHeight = 200;
                            int matchRatioHeight = 200;
                            for (int i=0;i<datas.size();i++){
                                RoleDevelGraphData data = datas.get(i);
                                g.setColor(Color.getHSBColor(285 / 360f, 0.05f,1f));
                                g.fillRect(i*columnWidth,200,columnWidth,-200);
                                g.setColor(Color.getHSBColor(285/360f,0.25f,0.9f));
                                g.fillRect(i*columnWidth,200,columnWidth, (int) (-20*data.getExperienceQuantif()));

                                g.setColor(Color.getHSBColor(225 / 360f, 1f, 0.75f));
                                int wins = data.getWins();
                                int defs = data.getDefs();
                                float ratio = ((wins > defs) ? 2 - ((float) defs/wins) : 0 + (float) wins/defs);
                                int newWinRatioHeight =  200 - (int) (100*ratio);
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,winRatioHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newWinRatioHeight);
                                }
                                winRatioHeight = newWinRatioHeight;

                                g.setColor(Color.getHSBColor(345/360f,1f,0.75f));
                                int newMatchRatioHeight = 200 - (int) (20*((float) Math.log(data.getTotal())));
                                if (i!=0){
                                    g.drawLine(-(columnWidth/2) + i*columnWidth,matchRatioHeight,-(columnWidth/2) + i*columnWidth+columnWidth-1,newMatchRatioHeight);
                                }
                                matchRatioHeight = newMatchRatioHeight;

                                ChampIcon icon = new ChampIcon();
                                icon.update(data.getLastChampion());

                                g.drawImage(icon.getIcon(),i*columnWidth,201,columnWidth,columnWidth,this);
                            }
                        }
                        @Override
                        public Dimension getPreferredSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }

                        @Override
                        public Dimension getMinimumSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }

                        @Override
                        public Dimension getMaximumSize() {
                            return new Dimension(datas.size()*columnWidth,220);
                        }
                    };

                    scrollPaneGraph.setViewportView(graph);

                    panelInfo.removeAll();
                    panelInfo.setLayout(new FlowLayout(FlowLayout.LEADING));

                    JPanel panelInfo1Icon = new JPanel();
                    panelInfo1Icon.setBackground(Color.getHSBColor(285/360f,0.25f,0.9f));
                    panelInfo1Icon.setSize(20, 20);
                    panelInfo1Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo1Icon);
                    panelInfo.add(new JLabel("Total <0,10>"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo2Icon = new JPanel();
                    panelInfo2Icon.setBackground(Color.getHSBColor(225 / 360f, 1f, 0.75f));
                    panelInfo2Icon.setSize(20, 20);
                    panelInfo2Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo2Icon);
                    panelInfo.add(new JLabel("Win ratio (%)"));
                    panelInfo.add(new JSeparator(JSeparator.VERTICAL));

                    JPanel panelInfo3Icon = new JPanel();
                    panelInfo3Icon.setBackground(Color.getHSBColor(345 / 360f, 1f, 0.75f));
                    panelInfo3Icon.setSize(20,20);
                    panelInfo3Icon.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    panelInfo.add(panelInfo3Icon);
                    panelInfo.add(new JLabel("Logarithm of total games <0,10>"));

                    panelInfo.revalidate();
                    panelInfo.repaint();
                }
            }
        });

        panelInfo = new JPanel();

        scrollPaneGraph = new JScrollPane();
        scrollPaneGraph.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panelIcon, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addGap(10)
                        .addComponent(labelName))
                .addComponent(comboBoxGraphs)
                .addComponent(panelInfo)
                .addComponent(scrollPaneGraph));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(panelIcon, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                        .addComponent(labelName, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addComponent(comboBoxGraphs, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelInfo,GroupLayout.PREFERRED_SIZE,30,GroupLayout.PREFERRED_SIZE)
                .addComponent(scrollPaneGraph, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE));
    }

    //champ
    public void update(Champion champion, List<GameRecord> gameRecords){
        panelIcon.update(champion);
        labelName.setText(champion.toString()+" - "+gameRecords.get(0).getMyRole().toString().toUpperCase());
        prepareData(gameRecords, gameRecords.get(0).getMyRole().equals(Role.support));
        comboBoxGraphs.setSelectedIndex(0);
    }
    //role
    public void update(Role role, List<GameRecord> gameRecords){
        panelIcon.update(role);
        labelName.setText(role.toString().toUpperCase());
        prepareData(gameRecords, role.equals(Role.support));
        comboBoxGraphs.setSelectedIndex(0);
    }
    // total
    public void update(List<GameRecord> gameRecords){
        panelIcon.update();
        labelName.setText("TOTAL");
        prepareData(gameRecords, false);
        comboBoxGraphs.setSelectedIndex(0);
    }

    private void prepareData(List<GameRecord> gameRecords, boolean isSupport){
        datas.clear();

        Collections.sort(gameRecords);

        int a = 0;
        for (GameRecord record : gameRecords){
            if (a > record.getGameNumber()){
                return;
            }
            a = record.getGameNumber();
        }

        for (int i=0;i<gameRecords.size();i++){
            int gamesTotal = i + 1;
            int winsG = 0;
            int defsG = 0;
            int kills = 0;
            int deaths = 0;
            int assists = 0;
            int cs = 0;
            int gold = 0;

            for (int j=0;j<=i;j++){
                GameRecord record = gameRecords.get(j);

                if (record.getGameResult().equals(Result.Victory)){
                    winsG++;
                } else {
                    defsG++;
                }
                kills += record.getScore().getKills();
                deaths += record.getScore().getDeaths();
                assists += record.getScore().getAssists();
                cs += record.getMinionsKilled();
                gold += record.getGoldEarned();
            }

            RoleDevelGraphData data = new RoleDevelGraphData();

            if (isSupport){
                data.setPerformancePoints(RoleUtils.getSupportPerformenceFor(gamesTotal, winsG, defsG, kills, deaths, assists, cs, gold));
                data.setScorePoints(RoleUtils.getSupportScorePoints(gamesTotal, kills, deaths, assists));
                data.setFarmPoints(RoleUtils.getSupportFarmPoints(cs, gold, gamesTotal));
            } else {
                data.setPerformancePoints(RoleUtils.getPerformenceFor(gamesTotal, winsG, defsG, kills, deaths, assists, cs, gold));
                data.setFarmPoints(RoleUtils.getFarmPoints(cs, gold, gamesTotal));
                data.setScorePoints(RoleUtils.getScorePoints(gamesTotal, kills, deaths, assists));
            }
            data.setKillsAvg((float) kills / gamesTotal);
            data.setDeathsAvg((float) deaths / gamesTotal);
            data.setAssistsAvg((float) assists / gamesTotal);


            data.setCsAvg((float) cs / gamesTotal);
            data.setGoldAvg((float) gold / gamesTotal);

            data.setExperienceQuantif(RoleUtils.getExperienceQuantif(gamesTotal,winsG,defsG));
            data.setWins(winsG);
            data.setDefs(defsG);
            data.setTotal(gamesTotal);

            data.setLastChampion(gameRecords.get(i).getMyChampion());

            datas.add(data);
        }
    }
}
