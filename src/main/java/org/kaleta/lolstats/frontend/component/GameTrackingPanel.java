package org.kaleta.lolstats.frontend.component;

import org.kaleta.lolstats.backend.entity.GameInfo;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.LolApiService;
import org.kaleta.lolstats.frontend.dialog.AddGameDialog;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class GameTrackingPanel extends JPanel{
    private boolean working;
    private long startTime;
    private JPanel panelFoundGames;
    private List<String> gameIds;

    public GameTrackingPanel(){
        gameIds = new ArrayList<>();
        this.setBackground(Color.BLUE);
        this.setVisible(false);

        panelFoundGames = new JPanel();
        panelFoundGames.setBackground(Color.RED);
        panelFoundGames.setLayout(new BoxLayout(panelFoundGames, BoxLayout.Y_AXIS));
        this.add(panelFoundGames);


        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GameTrackingPanel.this.setVisible(false);
                working = false;
            }
        });
        this.add(buttonCancel);
    }

    public void startTracking() {
        panelFoundGames.removeAll();
        gameIds.clear();

        startTime = System.currentTimeMillis() - 600000;
        panelFoundGames.add(new GamePanel(new SimpleDateFormat("HH:mm - dd.MM.yyyy").format(startTime)));
        panelFoundGames.repaint();
        panelFoundGames.revalidate();
        this.setVisible(true);

        LolApiService service = new LolApiService();
        working = true;
        while (working) {
            List<GameInfo> infoList = service.getRecentRankedGamesInfo();
            for (int i = infoList.size() - 1; i >= 0; i--) {
                GameInfo info = infoList.get(i);
                if (Long.parseLong(info.getDateInMillis()) > startTime) {
                    if (!gameIds.contains(info.getId())){
                        Season.Game game = service.getGameById(info.getId(),true);
                        gameIds.add(info.getId());
                        panelFoundGames.add(new GamePanel(game));
                        panelFoundGames.repaint();
                        panelFoundGames.revalidate();
                        Toolkit.getDefaultToolkit().beep();
                    }
                }
            }
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                working = false;
            }
        }

    }

    private class GamePanel extends JPanel {
        private Font font = new Font(new JLabel().getFont().getName(),Font.BOLD,15);
        private boolean added = false;

        public GamePanel(final Season.Game game){
            JLabel labelInfo = new JLabel(game.getUser().getChamp());
            labelInfo.setFont(font);

            this.add(labelInfo);
            this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            this.setBackground(Color.getHSBColor(120/360f,0.5f,0.75f));
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!added){
                        AddGameDialog dialog = new AddGameDialog();
                        dialog.setUpDialog(game);
                        dialog.setVisible(true);
                        if (dialog.getResult()){
                            //todo dialog.getGame(); + update data

                            GamePanel.this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                            GamePanel.this.setBackground(Color.LIGHT_GRAY);
                            GamePanel.this.repaint();
                            GamePanel.this.revalidate();
                            added = true;
                        }
                    }

                }
            });
        }

        public GamePanel(String time){
            JLabel labelInfo = new JLabel("Started: " + time);
            labelInfo.setFont(font);

            this.add(labelInfo);
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.setBackground(Color.getHSBColor(240/360f,0.5f,0.75f));
        }
    }
}
