package org.kaleta.lolstats.frontend.dialog;

import org.kaleta.lolstats.backend.entity.GameIdentifier;
import org.kaleta.lolstats.backend.entity.Season;
import org.kaleta.lolstats.backend.service.LolApiService;
import org.kaleta.lolstats.backend.service.ServiceFailureException;
import org.kaleta.lolstats.frontend.component.RecentGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 12.03.2016.
 */
public class AddRecentGameDialog extends JDialog {
    private JPanel panelGames;

    public AddRecentGameDialog(Component parent) {
        this.setTitle("Add Recent Game");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
        this.setLocationRelativeTo(parent);

        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Searching recent games...");

        panelGames = new JPanel();
        panelGames.setLayout(new BoxLayout(panelGames, BoxLayout.Y_AXIS));
        panelGames.add(label);
        this.getContentPane().add(panelGames);

        new Thread(() -> {
            LolApiService service = new LolApiService();
            List<JPanel> panels = new ArrayList<>();
            for (GameIdentifier info : service.getRecentRankedGamesInfo()) {
                try {
                    RecentGamePanel panel = new RecentGamePanel(info, parent);
                    panels.add(panel);
                } catch (ServiceFailureException e) {
                    RecentGamePanel panel = new RecentGamePanel(info);
                    panels.add(panel);
                } catch (Exception e) {
                    e.printStackTrace();
                    label.setText("Error while getting recent games: \n" + e.getClass().toString() + "\n" + e.getMessage() + "\n" + e.getCause());
                }

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
