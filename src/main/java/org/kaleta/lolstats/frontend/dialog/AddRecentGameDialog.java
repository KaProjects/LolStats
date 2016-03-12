package org.kaleta.lolstats.frontend.dialog;

import org.kaleta.lolstats.backend.entity.GameInfo;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.LolApiService;
import org.kaleta.lolstats.frontend.component.RecentGamePanel;

import javax.swing.*;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 12.03.2016.
 */
public class AddRecentGameDialog extends JDialog{
    private JPanel panelGames;

    public AddRecentGameDialog(Component parent){
        this.setTitle("Add Recent Game");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLocationRelativeTo(parent);

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));

        panelGames = new JPanel();
        panelGames.setLayout(new BoxLayout(panelGames,BoxLayout.Y_AXIS));
        panelGames.add(new JLabel("Searching recent games..."));
        this.getContentPane().add(panelGames);

        new Thread(() -> {
            LolApiService service =  new LolApiService();
            List<JPanel> panels = new ArrayList<>();
            for (GameInfo info : service.getRecentRankedGamesInfo()){
                Season.Game game = service.getGameById(info.getId(),true);
                RecentGamePanel panel = new RecentGamePanel(game);
                panels.add(panel);
            }
            panelGames.removeAll();
            panelGames.revalidate();
            panelGames.repaint();
            panels.stream().forEach(p -> panelGames.add(p));
            panelGames.repaint();
            panelGames.revalidate();
            AddRecentGameDialog.this.pack();
        }).start();

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddRecentGameDialog.this.dispose();
            }
        });
        this.getContentPane().add(buttonCancel);

        this.pack();
    }

}
