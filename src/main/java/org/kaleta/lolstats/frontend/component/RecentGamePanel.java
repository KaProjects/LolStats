package org.kaleta.lolstats.frontend.component;

import org.kaleta.lolstats.backend.entity.GameIdentifier;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.backend.service.LolApiService;
import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.common.Constants;
import org.kaleta.lolstats.frontend.dialog.AddGameDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

/**
 * Created by Stanislav Kaleta on 12.03.2016.
 */
public class RecentGamePanel extends JPanel {
    private boolean added = false;
    private JLabel labelInfo;

    public RecentGamePanel(GameIdentifier info, Component parent){
        String labelText = info.getChampion() + " " + new SimpleDateFormat("HH:mm dd.MM.yyyy").format(Long.parseLong(info.getDateInMillis()));
        labelInfo = new JLabel(labelText);
        labelInfo.setFont(Constants.LABEL_FONT);

        this.add(labelInfo);
        this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        this.setBackground(Constants.PANEL_BACKGROUND_GREEN);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!added){
                    RecentGamePanel.this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
                    RecentGamePanel.this.setBackground(Color.BLUE.brighter());
                    labelInfo.setText("Loading...");
                    RecentGamePanel.this.repaint();
                    RecentGamePanel.this.revalidate();

                    AddGameDialog dialog = new AddGameDialog(parent);
                    LolApiService service = new LolApiService();
                    Season.Game game = service.getGameById(info.getId());
                    dialog.setUpDialog(game);
                    dialog.setVisible(true);

                    if (dialog.getResult()){
                        DataSourceService.addNewGame(dialog.getGame());
                        ((Configuration) parent).updateComponents();
                        RecentGamePanel.this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                        RecentGamePanel.this.setBackground(Color.LIGHT_GRAY);
                        labelInfo.setText(labelText);
                        RecentGamePanel.this.repaint();
                        RecentGamePanel.this.revalidate();
                        added = true;
                    } else  {
                        RecentGamePanel.this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                        RecentGamePanel.this.setBackground(Constants.PANEL_BACKGROUND_GREEN);
                        labelInfo.setText(labelText);
                        RecentGamePanel.this.repaint();
                        RecentGamePanel.this.revalidate();
                    }
                }

            }
        });
    }

    public RecentGamePanel(GameIdentifier info){
        String labelText = "ID " + info.getId() + " not found";
        labelInfo = new JLabel(labelText);
        labelInfo.setFont(Constants.LABEL_FONT);

        this.add(labelInfo);
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.setBackground(Constants.PANEL_BACKGROUND_RED);
    }

    public RecentGamePanel(String time){
        labelInfo = new JLabel("Started: " + time);
        labelInfo.setFont(Constants.LABEL_FONT);

        this.add(labelInfo);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.setBackground(Color.getHSBColor(240/360f,0.5f,0.75f));
    }
}