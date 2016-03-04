package org.kaleta.lolstats.frontend.action.menu;

import org.kaleta.lolstats.frontend.Configuration;
import org.kaleta.lolstats.frontend.component.GameTrackingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 22.02.2016.
 */
public class StartTrackingGames extends MenuAction{
    private GameTrackingPanel panel;

    public StartTrackingGames(Configuration config, GameTrackingPanel panel) {
        super(config, "Start Tracking");
        panel.addCancelAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StartTrackingGames.this.setEnabled(true);
                // inform tracking thread
            }
        });
        this.panel = panel;
    }

    @Override
    protected void actionPerformed() {
        // TODO
        panel.setVisible(true);
        this.setEnabled(false);
        // inform tracking thread
    }
}
