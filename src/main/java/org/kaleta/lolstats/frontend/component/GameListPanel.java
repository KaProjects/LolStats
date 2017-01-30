package org.kaleta.lolstats.frontend.component;

import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.DataSourceService;
import org.kaleta.lolstats.frontend.common.Constants;

import javax.swing.*;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 26.09.2016.
 */
public class GameListPanel extends JPanel{


    public GameListPanel(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        update();
    }

    public void update(){
        this.removeAll();
        this.repaint();
        this.revalidate();

        List<Season.Game> gameList = DataSourceService.getAllGames();
        Collections.sort(gameList, new GameDecreasingComparator());
        for (Season.Game game : gameList){
            JLabel labelGameNumber = getLabel(game.getNumber());
            JLabel labelDate = null;
            try {
                labelDate = getLabel(new SimpleDateFormat("dd.MM.yyyy").format(new SimpleDateFormat("ddMMyyyy").parse(game.getDate())));
            } catch (ParseException e) {
                labelDate = getLabel(game.getDate());
            }
            JLabel labelChamp = getLabel(game.getUser().getChamp());
            JLabel labelScore = getLabel(String.format("%02d", Integer.parseInt(game.getUser().getScore().getKills()))
                    + " : " + String.format("%02d", Integer.parseInt(game.getUser().getScore().getDeaths()))
                    + " : " + String.format("%02d", Integer.parseInt(game.getUser().getScore().getAssists())));

            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createLineBorder(Boolean.parseBoolean(game.getUserTeam().getWin()) ? Color.GREEN : Color.RED));
            panel.setBackground(Boolean.parseBoolean(game.getUserTeam().getWin()) ? Constants.PANEL_BACKGROUND_GREEN : Constants.PANEL_BACKGROUND_RED);

            GroupLayout layout = new GroupLayout(panel);
            panel.setLayout(layout);
            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGap(10)
                    .addComponent(labelGameNumber, 30, 30, 30)
                    .addComponent(labelDate,110,110,110)
                    .addComponent(labelChamp,100,100,100)
                    .addComponent(labelScore,100,100,100)
                    .addGap(10));
            layout.setVerticalGroup(layout.createParallelGroup()
                    .addComponent(labelGameNumber)
                    .addComponent(labelDate)
                    .addComponent(labelChamp)
                    .addComponent(labelScore));

            this.add(panel);
        }
        this.repaint();
        this.revalidate();
    }

    private JLabel getLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(Constants.LABEL_FONT);
        return label;
    }


    private class GameDecreasingComparator implements Comparator<Season.Game>{
        @Override
        public int compare(Season.Game game1, Season.Game game2) {
            return Integer.parseInt(game2.getNumber()) - Integer.parseInt(game1.getNumber());
        }
    }
}
