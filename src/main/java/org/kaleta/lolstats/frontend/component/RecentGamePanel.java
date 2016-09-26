package org.kaleta.lolstats.frontend.component;

import org.kaleta.lolstats.backend.entity.GameInfo;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.frontend.dialog.AddGameDialog;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 12.03.2016.
 */
public class RecentGamePanel extends JPanel {
    private Font font = new Font(new JLabel().getFont().getName(),Font.BOLD,15);
    private boolean added = false;

    public RecentGamePanel(final Season.Game game, Component parent){
        String labelText = game.getUser().getChamp() + " " + game.getUser().getScore().getKills()+":"
                + game.getUser().getScore().getDeaths()+":"+game.getUser().getScore().getAssists();
        JLabel labelInfo = new JLabel(labelText);
        labelInfo.setFont(font);

        this.add(labelInfo);
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        this.setBackground(Color.getHSBColor(120/360f,0.5f,0.75f));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!added){
                    AddGameDialog dialog = new AddGameDialog(parent);
                    dialog.setUpDialog(game);
                    dialog.setVisible(true);
                    if (dialog.getResult()){
                        DataSourceService.addNewGame(dialog.getGame());
                        RecentGamePanel.this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                        RecentGamePanel.this.setBackground(Color.LIGHT_GRAY);
                        RecentGamePanel.this.repaint();
                        RecentGamePanel.this.revalidate();
                        added = true;
                    }
                }

            }
        });
    }

    public RecentGamePanel(GameInfo info){
        String labelText = "ID " + info.getId() + " not found";
        JLabel labelInfo = new JLabel(labelText);
        labelInfo.setFont(font);

        this.add(labelInfo);
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.setBackground(Color.getHSBColor(0/360f,0.5f,0.75f));
    }

    public RecentGamePanel(String time){
        JLabel labelInfo = new JLabel("Started: " + time);
        labelInfo.setFont(font);

        this.add(labelInfo);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.getHSBColor(240/360f,0.5f,0.75f));
    }
}