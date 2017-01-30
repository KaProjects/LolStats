package org.kaleta.lolstats.frontend.component;

import org.kaleta.lolstats.backend.entity.GameInfo;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.LolApiService;
import org.kaleta.lolstats.backend.service.ServiceFailureException;
import org.kaleta.lolstats.frontend.ErrorDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class GameTrackingPanel extends JPanel{
    private boolean working;
    private JPanel panelFoundGames;
    private List<String> gameIds;
    private Component parent;
    private JButton buttonCancel;

    public GameTrackingPanel(Component parent){
        this.parent = parent;
        gameIds = new ArrayList<>();

        panelFoundGames = new JPanel();
        panelFoundGames.setBackground(Color.RED);
        panelFoundGames.setLayout(new BoxLayout(panelFoundGames, BoxLayout.Y_AXIS));
        this.add(panelFoundGames);


        buttonCancel = new JButton("Stop");
        buttonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                working = false;
                buttonCancel.setVisible(false);
                panelFoundGames.setVisible(false);
            }
        });
        this.add(buttonCancel);

        buttonCancel.setVisible(false);
        panelFoundGames.setVisible(false);
    }

    public void startTracking() {
        working = true;
        panelFoundGames.removeAll();
        gameIds.clear();

        long startTime = System.currentTimeMillis() - 600000;
        panelFoundGames.add(new RecentGamePanel(new SimpleDateFormat("HH:mm - dd.MM.yyyy").format(startTime)));
        panelFoundGames.repaint();
        panelFoundGames.revalidate();
        buttonCancel.setVisible(true);
        panelFoundGames.setVisible(true);

        LolApiService service;
        try{
           service = new LolApiService();
        } catch (ServiceFailureException e) {
            working = false;
            buttonCancel.setVisible(false);
            panelFoundGames.setVisible(false);
            new ErrorDialog(e).setVisible(true);
            return;
        }

        while (working) {
            try {
                List<GameInfo> infoList = service.getRecentRankedGamesInfo();
                for (int i = infoList.size() - 1; i >= 0; i--) {
                    GameInfo info = infoList.get(i);
                    if (Long.parseLong(info.getDateInMillis()) > startTime) {
                        if (!gameIds.contains(info.getId())){
                            Season.Game game = service.getGameById(info.getId(),true);
                            gameIds.add(info.getId());
                            panelFoundGames.add(new RecentGamePanel(game, parent));
                            panelFoundGames.repaint();
                            panelFoundGames.revalidate();
                            Toolkit.getDefaultToolkit().beep();
                        }
                    }
                }
                if (working) Thread.sleep(5000);
                if (working) Thread.sleep(5000);
                if (working) Thread.sleep(5000);
            } catch (Exception e){
                working = false;
                buttonCancel.setVisible(false);
                panelFoundGames.setVisible(false);
                new ErrorDialog(e).setVisible(true);
            }
        }
    }
}
