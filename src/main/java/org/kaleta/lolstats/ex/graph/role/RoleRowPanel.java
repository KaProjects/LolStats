package org.kaleta.lolstats.ex.graph.role;

import org.kaleta.lolstats.ex.entities.Champion;
import org.kaleta.lolstats.ex.entities.GameRecord;
import org.kaleta.lolstats.ex.entities.Result;
import org.kaleta.lolstats.ex.entities.Role;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * User: Stanislav Kaleta
 * Date: 25.6.2015
 */
public class RoleRowPanel extends JPanel {
    private ChampIcon panelIcon;
    private JLabel labelName;
    private PerformanceBar panelPerformance;
    private JLabel labelPreference;
    private JLabel labelGamesTotal;
    private JLabel labelGamesVictories;
    private JLabel labelGamesDefeats;
    private JLabel labelGamesPercentage;
    private JLabel labelKillsAvg;
    private JLabel labelDeathsAvg;
    private JLabel labelAssistsAvg;
    private JLabel labelMinionsAvg;
    private JLabel labelGoldsAvg;

    private RoleDevelopmentPanel roleDevelPanel;

    public RoleRowPanel(){
        initComponents();
        setBackground(Color.LIGHT_GRAY);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1){
                    if (e.getClickCount() == 1 && roleDevelPanel != null){
                        setBackground(Color.LIGHT_GRAY);
                        JFrame frame = new JFrame(labelName.getText());
                        frame.getContentPane().add(roleDevelPanel);
                        frame.setLocationRelativeTo(RoleRowPanel.this);
                        frame.setVisible(true);
                        frame.pack();
                        frame.setSize(800,frame.getHeight());
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                ((JPanel)e.getSource()).requestFocus();
                if (roleDevelPanel != null){
                    setBackground(Color.LIGHT_GRAY.darker());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (roleDevelPanel != null){
                    setBackground(Color.LIGHT_GRAY);
                }
            }
        });
    }

    private void initComponents() {
        int height = 30;
        Color xPanelsColor = Color.BLACK;
        Font labelFont = new Font(new JLabel().getName(), Font.BOLD, 15);
        int xPanelWidth = 5;
        int xInnerPanelWidth = 3;

        JPanel xPanel0 = new JPanel();
        xPanel0.setBackground(xPanelsColor);

        panelIcon = new ChampIcon();
        labelName = new JLabel("----");
        labelName.setFont(labelFont);

        JPanel xPanel1 = new JPanel();
        xPanel1.setBackground(xPanelsColor);

        panelPerformance = new PerformanceBar();

        JPanel xPanel2 = new JPanel();
        xPanel2.setBackground(xPanelsColor);

        labelPreference = new JLabel("-%");
        labelPreference.setFont(labelFont);
        labelPreference.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel3 = new JPanel();
        xPanel3.setBackground(xPanelsColor);

        labelGamesTotal = new JLabel("-");
        labelGamesTotal.setFont(labelFont);
        labelGamesTotal.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel3a = new JPanel();
        xPanel3a.setBackground(xPanelsColor);

        labelGamesVictories = new JLabel("-");
        labelGamesVictories.setFont(labelFont);
        labelGamesVictories.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel3b = new JPanel();
        xPanel3b.setBackground(xPanelsColor);

        labelGamesDefeats = new JLabel("-");
        labelGamesDefeats.setFont(labelFont);
        labelGamesDefeats.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel3c = new JPanel();
        xPanel3c.setBackground(xPanelsColor);

        labelGamesPercentage = new JLabel("-%");
        labelGamesPercentage.setFont(labelFont);
        labelGamesPercentage.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel4 = new JPanel();
        xPanel4.setBackground(xPanelsColor);

        labelKillsAvg = new JLabel("-.-");
        labelKillsAvg.setFont(labelFont);
        labelKillsAvg.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel4a = new JPanel();
        xPanel4a.setBackground(xPanelsColor);

        labelDeathsAvg = new JLabel("-.-");
        labelDeathsAvg.setFont(labelFont);
        labelDeathsAvg.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel4b = new JPanel();
        xPanel4b.setBackground(xPanelsColor);

        labelAssistsAvg = new JLabel("-.-");
        labelAssistsAvg.setFont(labelFont);
        labelAssistsAvg.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel4c = new JPanel();
        xPanel4c.setBackground(xPanelsColor);

        labelMinionsAvg = new JLabel("-.-");
        labelMinionsAvg.setFont(labelFont);
        labelMinionsAvg.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel4d = new JPanel();
        xPanel4d.setBackground(xPanelsColor);

        labelGoldsAvg = new JLabel("-.-");
        labelGoldsAvg.setFont(labelFont);
        labelGoldsAvg.setHorizontalAlignment(JLabel.RIGHT);

        JPanel xPanel5 = new JPanel();
        xPanel5.setBackground(xPanelsColor);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setVerticalGroup(layout.createParallelGroup()
                .addComponent(xPanel0, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelIcon, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelName, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel1, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelPreference, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel2, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelPerformance, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel3, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesTotal, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel3a, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesVictories, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel3b, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesDefeats, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel3c, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesPercentage, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel4, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelKillsAvg, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel4a, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelDeathsAvg, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel4b, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelAssistsAvg, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel4c, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelMinionsAvg, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel4d, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGoldsAvg, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel5, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE));

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(xPanel0, GroupLayout.PREFERRED_SIZE, xPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelIcon, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(labelName, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel1, GroupLayout.PREFERRED_SIZE, xPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelPreference, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel2, GroupLayout.PREFERRED_SIZE, xPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelPerformance, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addComponent(xPanel3, GroupLayout.PREFERRED_SIZE, xPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesTotal, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel3a, GroupLayout.PREFERRED_SIZE, xInnerPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesVictories, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel3b, GroupLayout.PREFERRED_SIZE, xInnerPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesDefeats, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel3c, GroupLayout.PREFERRED_SIZE, xInnerPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGamesPercentage, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel4, GroupLayout.PREFERRED_SIZE, xPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelKillsAvg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel4a, GroupLayout.PREFERRED_SIZE, xInnerPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelDeathsAvg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel4b, GroupLayout.PREFERRED_SIZE, xInnerPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelAssistsAvg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel4c, GroupLayout.PREFERRED_SIZE, xInnerPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelMinionsAvg, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel4d, GroupLayout.PREFERRED_SIZE, xInnerPanelWidth, GroupLayout.PREFERRED_SIZE)
                .addComponent(labelGoldsAvg, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(xPanel5, GroupLayout.PREFERRED_SIZE, xPanelWidth, GroupLayout.PREFERRED_SIZE));

        roleDevelPanel = new RoleDevelopmentPanel();
    }
    // champ
    public void update(Champion champion, int gamesTotal, List<GameRecord> gameRecords){
        panelIcon.update(champion);
        labelName.setText(String.valueOf(champion));

        float preference = (((float) gameRecords.size()/gamesTotal)*100);
        labelPreference.setText(String.format("%.1f",preference) + "%");

        updateContent(gameRecords, gameRecords.get(0).getMyRole().equals(Role.support));

        roleDevelPanel.update(champion, gameRecords);
    }
    // role
    public void update(Role role, int gamesTotal, List<GameRecord> gameRecords){
        panelIcon.update(role);
        labelName.setText(String.valueOf(role));

        float preference = (((float) gameRecords.size()/gamesTotal)*100);
        labelPreference.setText(String.format("%.1f",preference) + "%");

        updateContent(gameRecords, role.equals(Role.support));

        roleDevelPanel.update(role, gameRecords);
    }
    // total
    public void update(List<GameRecord> gameRecords){
        panelIcon.update();
        labelName.setText("TOTAL");
        labelPreference.setText("100%");

        updateContent(gameRecords, false);

        roleDevelPanel.update(gameRecords);
    }
    // header
    public void update(){
        panelIcon.update();
        labelName.setText("");
        labelPreference.setText("G(%)");
        labelPreference.setHorizontalAlignment(JLabel.CENTER);
        panelPerformance.update(-1);
        panelPerformance.removeAll();
        JLabel labelPerformance= new JLabel("Performance");
        labelPerformance.setFont(new Font(new JLabel().getName(), Font.BOLD, 15));
        labelPerformance.setHorizontalAlignment(JLabel.CENTER);
        panelPerformance.add(labelPerformance);
        labelGamesTotal.setText("G");
        labelGamesTotal.setHorizontalAlignment(JLabel.CENTER);
        labelGamesVictories.setText("W");
        labelGamesVictories.setHorizontalAlignment(JLabel.CENTER);
        labelGamesDefeats.setText("L");
        labelGamesDefeats.setHorizontalAlignment(JLabel.CENTER);
        labelGamesPercentage.setText("W(%)");
        labelGamesPercentage.setHorizontalAlignment(JLabel.CENTER);
        labelKillsAvg.setText("K(\u03bb)");
        labelKillsAvg.setHorizontalAlignment(JLabel.CENTER);
        labelDeathsAvg.setText("D(\u03bb)");
        labelDeathsAvg.setHorizontalAlignment(JLabel.CENTER);
        labelAssistsAvg.setText("A(\u03bb)");
        labelAssistsAvg.setHorizontalAlignment(JLabel.CENTER);
        labelMinionsAvg.setText("CS(\u03bb)");
        labelMinionsAvg.setHorizontalAlignment(JLabel.CENTER);
        labelGoldsAvg.setText("Gold(\u03bb)");
        labelGoldsAvg.setHorizontalAlignment(JLabel.CENTER);

        roleDevelPanel = null;
    }

    private void updateContent(List<GameRecord> gameRecords, boolean isSupport){
        int gamesTotal = gameRecords.size();

        int winsG = 0;
        int defsG = 0;
        int kills = 0;
        int deaths = 0;
        int assists = 0;
        int cs = 0;
        int gold = 0;
        for (GameRecord record : gameRecords){
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

        labelGamesTotal.setText(String.valueOf(gamesTotal));
        labelGamesVictories.setText(String.valueOf(winsG));
        labelGamesDefeats.setText(String.valueOf(defsG));
        labelGamesPercentage.setText(String.format("%.1f",(float) winsG / gamesTotal * 100) + "%");
        labelKillsAvg.setText(String.format("%.1f",(float) kills / gamesTotal));
        labelDeathsAvg.setText(String.format("%.1f",(float) deaths / gamesTotal));
        labelAssistsAvg.setText(String.format("%.1f",(float) assists / gamesTotal));
        labelMinionsAvg.setText(String.format("%.1f",(float) cs / gamesTotal));
        labelGoldsAvg.setText(String.format("%.1f",(float) gold / gamesTotal));
        // performance

        if (isSupport){
            panelPerformance.update(RoleUtils.getSupportPerformenceFor(gamesTotal, winsG, defsG, kills, deaths, assists, cs, gold));
        } else {
            panelPerformance.update(RoleUtils.getPerformenceFor(gamesTotal, winsG, defsG, kills, deaths, assists, cs, gold));
        }
    }

    public int getCompareValue(){//TODO more columns compare
        return panelPerformance.getValue();
    }
}
